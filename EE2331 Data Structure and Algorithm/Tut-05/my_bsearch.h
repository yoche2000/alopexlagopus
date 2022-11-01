// Student name: Lee Yo-Che
// Student ID  : 55348947

#include <iostream>
using namespace std;

#ifndef my_bsearch_h
#define my_bsearch_h

// Interface of bsearch_first is the same as the C-library function bsearch

void* bsearch_first(const void* key, const void* base, unsigned n, unsigned size, 
                    int (*compare)(const void *, const void *))
{

	char* b = (char*)base;
	int* k = (int*)key;

	int low = -1;
	int high = n;

	while (low < high - 1)
	{
		int mid = (low + high) / 2;
		int c = compare(k, b + mid * size);
		//cout << "mid = " << *(b + mid * size) << endl;				//debugging line	
		if (c >= 0)
		{
			//cout << "low " << high << " to mid" << mid << endl;		//debugging line	
			high = mid;
		}
		else
		{
			//cout << "high " << high << " to mid" << mid << endl;		//debugging line	
			low = mid;
		}
	}
	return b + high * size; 
}




#endif
