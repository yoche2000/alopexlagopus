/*
 * NOTE TO STUDENTS: Before you do anything else, please
 * provide your group information here.
 *
 * Group No.: 10 (Join a project group in Canvas)
 * First member's full name: HSU Pao-Heng
 * First member's email address:andyhsu3-c@my.cityu.edu.hk
 * Second member's full name: LEE Yo-Che
 * Second member's email address:thomaslee7-c@my.cityu.edu.hk
 * Third member's full name: LIN Haier
 * Third member's email address: yuenflam2-c@my.cityu.edu.hk
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/sysinfo.h>
#include <sys/sysmacros.h>
#include <unistd.h>
#include <stdint.h>
#include <assert.h>
#include <dirent.h>

#define DEFAULT_WORKS_CAP 100
#define DEFAULT_OUTPUT_CAP 1024
#define MIN_SINGLE_LEN (5 * 1024 * 1024) /* 5MB */

typedef struct output_layout_s output_layout_t;
struct output_layout_s {
    uint32_t count;
    char c;
} __attribute__((packed));

typedef struct {
    void* addr; /* memory returned by mmap */
    size_t remain_bytes;
    size_t offset;
} work_t;

typedef struct {
    void* buff;
    size_t length;
    int index;
} output_t;

typedef struct {
    /* Overall control variables */

    int done;     /* finish adding files */
    int new_work; /* number of encoding and output work */
    int nthreads; /* for future use */

    /* Encoding work variables */

    work_t* works; /* can be optimized using queue */
    int nworks;
    int works_cap;
    int cur_works;
    int work_index;
    size_t remain_bytes;

    /* Output work variables */

    output_t* output;
    int noutput;
    int output_cap;
    int output_index;
    int output_min_index;
    int outputting;
    output_layout_t last;

    /* Mutex and condition */

    pthread_mutex_t mutex;
    pthread_cond_t cond;
} workloads_t;

/* Function prototypes */

void test_map();
size_t file_size(int fd);
int wkl_add_directory_helper(workloads_t* wkl, char* dir);
void wkl_add_directory(workloads_t* wkl, char* dir);
void wkl_init(workloads_t* wkl, int nthreads);
void wkl_add_file(workloads_t* wkl, char* path);
int wkl_get_work(workloads_t* wkl, void** buff, size_t* length, int* index);
void wkl_add_output(workloads_t* wkl, void* buff, size_t length, int index);
size_t rle(void* outbuf, void* buff, size_t length);
void* output(void* buff, size_t length, output_layout_t* last);
void* worker(void* param);

/*==========================================================================*/

/* The worker thread. */
void* worker(void* param) {
    workloads_t* wkl = (workloads_t*)param;
    int type, index;
    void *buff, *outbuf;
    output_layout_t* last;
    size_t length, outlength;
    while (1) {
        /* get work from workloads */
        type = wkl_get_work(wkl, &buff, &length, &index);
        if (type == 1) { /* encoding work */
            outbuf = malloc(length * sizeof(output_layout_t));
            assert(outbuf);
            outlength = rle(outbuf, buff, length);
            wkl_add_output(wkl, outbuf, outlength, index);
        } else if (type == 2) { /* outputting work */

            last = output(buff, length, &wkl->last);

            pthread_mutex_lock(&wkl->mutex);
            wkl->last.count = last->count;
            wkl->last.c = last->c;
            wkl->outputting = 0;
            pthread_mutex_unlock(&wkl->mutex);
            free(buff);
        } else if (type == 0) { /* do nothing */
        } else { /* all done */
            break;
        }
    }
    return NULL;
}

int main(int argc, char** argv) {
    /* test_map(); */

    int i, nthreads;
    pthread_t* threads;
    workloads_t workloads;


    if (argc == 1) { /* no files */
        fprintf(stdout, "pzip: file1 [file2 ...]\n");
        return 1;
    }

    /* best number of threads is the number of cpu cores */
    nthreads = get_nprocs() - 1;

    wkl_init(&workloads, nthreads);

    if (nthreads > 0) {
        threads = (pthread_t*)malloc(sizeof(pthread_t) * nthreads);
    }else{
        threads = NULL;
    }
    /* create worker threads */
    for (i = 0; i < nthreads; i++) {
        if (pthread_create(&threads[i], NULL, worker, &workloads) != 0) {
            perror("pthread_create()");
            exit(1);
        }
    }
    /* add workloads */
    for (i = 1; i < argc; i++) {
        wkl_add_file(&workloads, argv[i]);
    }
    workloads.done = 1; /* do not need to lock mutex */
    /* the main thread is also a worker */
    worker(&workloads);
    /* in case some of those threads didn't exit */
    /* notify all the worker threads to exit again */
    workloads.new_work = 1;
    pthread_cond_broadcast(&workloads.cond);
    /* wait for all to complete */
    for (i = 0; i < nthreads; i++) {
        pthread_join(threads[i], NULL);
    }
    free(threads);
    /* output the very last */
    if (workloads.last.count) {
        fwrite(&workloads.last, sizeof(output_layout_t), 1, stdout);
    }
    /* cleanup */
    free(workloads.works);
    free(workloads.output);
    pthread_cond_destroy(&workloads.cond);
    pthread_mutex_destroy(&workloads.mutex);
    return 0;
}

/*==========================================================================*/

/* Initialize the workloads structure. */
void wkl_init(workloads_t* wkl, int nthreads) {
    wkl->done = 0;
    wkl->new_work = 0;
    wkl->nthreads = nthreads;

    wkl->works = NULL;
    wkl->nworks = 0;
    wkl->works_cap = 0;
    wkl->cur_works = 0;
    wkl->work_index = 0;
    wkl->remain_bytes = 0;

    wkl->output = NULL;
    wkl->noutput = 0;
    wkl->output_cap = 0;
    wkl->output_index = 0;
    wkl->output_min_index = 0;
    wkl->outputting = 0;
    wkl->last.c = EOF;
    wkl->last.count = 0;

    pthread_mutex_init(&wkl->mutex, NULL);
    pthread_cond_init(&wkl->cond, NULL);
}

/* Add a file to the workloads. */
void wkl_add_file(workloads_t* wkl, char* path) {
    int fd;
    void* addr;
    size_t length;
    struct stat statbuf;

    stat(path, &statbuf);

    if ((statbuf.st_mode & __S_IFMT) == __S_IFDIR) { /* directory */
        wkl_add_directory(wkl, path);
    } else { /* file */
        if ((fd = open(path, O_RDONLY)) == -1) {
            perror("open()");
            exit(1);
        }

        length = file_size(fd);
        /* mmap is faster when reading large files */
        addr = mmap(NULL, length, PROT_READ, MAP_PRIVATE, fd, 0);
        close(fd);

        pthread_mutex_lock(&wkl->mutex);
        /* increase the works array if necessary */
        if (wkl->nworks == wkl->works_cap) {
            if (wkl->works_cap == 0) {
                wkl->works_cap = DEFAULT_WORKS_CAP;
            } else {
                wkl->works_cap *= 2;
            }
            wkl->works =
                (work_t*)realloc(wkl->works, sizeof(work_t) * wkl->works_cap);
            assert(wkl->works);
        }
        /* add the work */
        wkl->works[wkl->nworks].addr = addr;
        wkl->works[wkl->nworks].remain_bytes = length;
        wkl->works[wkl->nworks].offset = 0;
        wkl->remain_bytes += length;
        ++(wkl->nworks);

        ++(wkl->new_work);
        /* notify all the worker threads that there are new work to do */
        pthread_cond_broadcast(&wkl->cond);
        pthread_mutex_unlock(&wkl->mutex);
    }
}

/* Add all files in the direcoty. */
void wkl_add_directory(workloads_t* wkl, char* dir) {
    char path[FILENAME_MAX];
    strcpy(path, dir);
    wkl_add_directory_helper(wkl, path);
}

/* There are 2 kinds of workloads, the first is encoding, the second is
 * outputting.
 */
int wkl_get_work(workloads_t* wkl, void** buff, size_t* length, int* index) {
    int i, ret;
    work_t* work;
    output_t* output = NULL;
    pthread_mutex_lock(&wkl->mutex);

    while (!wkl->new_work) {
        pthread_cond_wait(&wkl->cond, &wkl->mutex);
    }

    /* check if there is outputting work need to do */
    if (!wkl->outputting && wkl->noutput - wkl->output_index > 0) {
        for (i = wkl->output_min_index; i < wkl->noutput; i++) {
            if (wkl->output[i].index == wkl->output_index) {
                output = &wkl->output[i];
                break;
            }
        }
    }
    if (output) { /* return outputting work */
        *buff = output->buff;
        *length = output->length;
        output->buff = NULL;
        wkl->outputting = 1;
        ++(wkl->output_index);
        --(wkl->new_work);
        if (wkl->new_work == 0 && wkl->done) {
            wkl->new_work = 1;
            /* notify all the worker threads to exit */
            pthread_cond_broadcast(&wkl->cond);
        }
        while (wkl->output_min_index < wkl->noutput &&
               wkl->output[wkl->output_min_index].buff == NULL) {
            wkl->output_min_index++;
        }
        ret = 2;
    } else {
        if (wkl->remain_bytes) { /* return encoding work */
            work = &wkl->works[wkl->cur_works];
            *buff = work->addr + work->offset;

            if (work->remain_bytes <= MIN_SINGLE_LEN) {
                *length = work->remain_bytes;
                ++(wkl->cur_works);
                if (wkl->cur_works == wkl->nworks) {
                    --(wkl->new_work);
                }
            } else {
                *length = MIN_SINGLE_LEN;
                work->offset += *length;
                work->remain_bytes -= *length;
            }
            *index = wkl->work_index++;
            wkl->remain_bytes -= *length;
            ret = 1;
        } else if (!wkl->done) { /* do nothing */
            ret = 0;
        } else { /* all done */
            ret = -1;
        }
    }
    pthread_mutex_unlock(&wkl->mutex);
    return ret;
}

/* Add outputting work to the workloads. */
void wkl_add_output(workloads_t* wkl, void* buff, size_t length, int index) {
    output_t* output;
    pthread_mutex_lock(&wkl->mutex);
    if (wkl->output_index == wkl->output_cap) {
        if (wkl->output_cap == 0) {
            wkl->output_cap = DEFAULT_OUTPUT_CAP;
        } else {
            wkl->output_cap *= 2;
        }
        wkl->output =
            (output_t*)realloc(wkl->output, sizeof(output_t) * wkl->output_cap);
    }
    output = &wkl->output[wkl->noutput++];
    output->buff = buff;
    output->length = length;
    output->index = index;
    ++(wkl->new_work);
    /* notify all the worker threads that there are new work to do */
    pthread_cond_broadcast(&wkl->cond);
    pthread_mutex_unlock(&wkl->mutex);
}

/* Run length encoding algorithm. */
size_t rle(void* outbuf, void* buff, size_t length) {
    size_t i, ind = 0;
    char last = ((char*)buff)[0];
    uint32_t last_count = 1;
    for (i = 1; i < length; i++) {
        if (((char*)buff)[i] == last) {
            last_count++;
        } else {
            ((output_layout_t*)outbuf)[ind].c = last;
            ((output_layout_t*)outbuf)[ind].count = last_count;
            ind++;
            last = ((char*)buff)[i];
            last_count = 1;
        }
    }
    ((output_layout_t*)outbuf)[ind].c = last;
    ((output_layout_t*)outbuf)[ind].count = last_count;
    ind++;
    return ind * sizeof(output_layout_t);
}

/* Output result of rle. Return the last 5 bytes. */
void* output(void* buff, size_t length, output_layout_t* last) {
    size_t n;
    n = length / sizeof(output_layout_t) - 1;
    if (last->c == ((output_layout_t*)buff)->c) {
        /* merge the first character of this output with the last character of
         * the last output
         */
        ((output_layout_t*)buff)->count += last->count;
    } else if (last->count) {
        fwrite(last, sizeof(output_layout_t), 1, stdout);
    }
#ifdef DEBUG
    size_t i;
    for (i = 0; i < n; i++) {
                  ((output_layout_t*)buff)[i].c);
    }
#endif

    if (n) fwrite(buff, sizeof(output_layout_t), n, stdout);
    return buff += (sizeof(output_layout_t) * (n));
}

/*==========================================================================*/

/* Get file size by fd. */
size_t file_size(int fd) {
    size_t len = lseek(fd, 0, SEEK_END);
    lseek(fd, 0, SEEK_SET);
    return len;
}

/* Recursively traverse the directory. */
int wkl_add_directory_helper(workloads_t* wkl, char* dir) {
    DIR* p_dir = NULL;
    struct dirent* p_entry = NULL;
    struct stat statbuf;
    size_t len;

    if ((p_dir = opendir(dir)) == NULL) {
        perror("opendir");
        return -1;
    }

    len = strlen(dir);
    dir[len] = '/';

    while (NULL != (p_entry = readdir(p_dir))) {
        if (len + strlen(p_entry->d_name) + 1 <= FILENAME_MAX) {
            strcpy(dir + len + 1, p_entry->d_name);
        } else {
            continue;
        }

        stat(dir, &statbuf);

        if ((statbuf.st_mode & __S_IFMT) == __S_IFDIR) { /* if it is dir */
            /* ignore "." and ".." */
            if (strcmp(".", p_entry->d_name) != 0 &&
                strcmp("..", p_entry->d_name) != 0) {
                wkl_add_directory_helper(wkl, dir);
            }
        } else {
            wkl_add_file(wkl, dir);
        }
    }

    closedir(p_dir);

    return 0;
}

void test_map() {
    char file1[] = "test1.txt";
    char file2[] = "test2.txt";
    int fd1 = open(file1, O_RDONLY);
    size_t len1 = file_size(fd1);
    int fd2 = open(file2, O_RDONLY);
    size_t len2 = file_size(fd2);

    void* addr1 = mmap(NULL, len1, PROT_READ, MAP_PRIVATE, fd1, 0);
    void* addr2 = mmap(addr1 + len1, len2, PROT_READ, MAP_PRIVATE, fd2, 0);
    assert(addr2 == addr1 + len1); /* Test failed, can't map multiple files into
                                      a continues memory space */

    /* don't need to cleanup since it's a functionality test */
}

