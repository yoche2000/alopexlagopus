// Student name:
// Student ID  :

/*
	Supplementary tutorial. Submission not required.

	Part-1: find the longest block (subsequence of elements with same value) in an array.

	Part-2: find all subsequences in an array of int that add up to a given sum.

	part-3: find the longest subsequence in an array of int without repeating value.
*/

#include <iostream>
#include <cstdlib>
#include <ctime>

using namespace std;
void printSeq(int* a, int s, int e);

// -------------------------------------------- functions to be implemented by you

void longestBlock(const int* a, int n, int& s, int& e)
{
	// s = start index of the block
	// e = end index of the block (exclusive, i.e. the block contains elements from a[s] to a[e-1])

	s = e = 0;  // empty block
	int i = 0;
	while (i < n)
	{
		int j = i + 1;
		while (j < n && a[j] == a[i])
			j++;
		
		if (j - i > e - s)
		{
			s = i;
			e = j;
		}
		i = j;
	}
}


/*
	Determine all subsequences in the array such that the sum 
	of each subsequence is equal to the targetSum

	example: A[] = {5, 1, 4, 4, 2, 5, 3, 2, 4, 5}

	if targetSum = 10
	the function prints:	subsequence : 5, 1, 4
					subsequence : 4, 4, 2
					subsequence : 2, 5, 3
					subsequence : 5, 3, 2

	if targetSum = 13
	the function prints:	no subsequence found
*/
void findAllSubSeq(int a[], int n, int targetSum)
{
	cout << "Subsequence with sum = " << targetSum << endl;
	bool found = false;
	for (int i = 0; i < n; i++)
	{
		int sum = 0;
		for (int j = i; j < n; j++)
		{
			sum += a[j];
			//assert: sum = sum of a[i..j]

			if (sum == targetSum)
			{
				cout << "subsequence : ";
				printSeq(a, i, j+1);
				found = true;
			}
		}
	}
	if (!found)
		cout << "No subsequence found." << endl;
}


void longestSubSeq_NoDup(const int* a, int n, int& s, int& e)
{	
	// s = start index of the subsequence
	// e = end index of the subsequence (exclusive)

	s = 0;
	e = n > 0 ? 1 : 0;
	int left = 0;  // start index of current subsequence

	for (int i = 1; i < n; i++)  // try to append a[i] to current subsequence
	{
		int j = i - 1;
		while (j >= left && a[j] != a[i])
			j--;

		if (j < left) // a[i] is not contained in current subsequence, extend the subsequence
		{
			if (i + 1 - left > e - s)
			{
				s = left;
				e = i + 1;
			}
		}
		else // a[i] == a[j] and left <= j < i, a[j+1] to a[i] are distinct
			left = j + 1; // new start index of current subsequence 
	}
}


// -------------------------------------------------- functions given to you
void printSeq(int* a, int s, int e)
{
    // print elements a[s] to a[e-1]

    for (int i = s; i < e; i++)
	   cout << a[i] << ", ";
    cout << endl;
}


void part_1()
{
	cout << "Part_1:\n\n";

	int a[] = {1, 2, 3, 3, 1, 4, 4, 4, 5, 8, 6, 6, 5, 5, 5, 5, 2, 1, 7, 7};
	int n = 20;

	cout << "a[] : ";
	printSeq(a, 0, n);
	cout << endl;

	int s, e;
	longestBlock(a, n, s, e);
	cout << "Longest block in the array:\n";
	cout << "Data value = " << (e > s ? a[s] : 0) << ", start index = " << s;
	cout << ", block length = " << e - s << endl << endl;

	cout << "Test with empty array:\n";
	longestBlock(a, 0, s, e);
	cout << "Longest block in the array:\n";
	cout << "Data value = " << (e > s ? a[s] : 0) << ", start index = " << s;
	cout << ", block length = " << e - s << endl << endl;
}

void part_2()
{
	cout << "\n------------------------------------------------------\n";
	cout << "Part-2:\n\n";

	int a[] = {5, 1, 4, -4, 2, 1, 5, 3, -2, 4, 5, -8, 7, 9, 1};
	int n = 15;

	cout << "a[] : ";
	printSeq(a, 0, n);
	cout << endl;

	int targetSum = 16;
	findAllSubSeq(a, n, targetSum);

	cout << endl;
	targetSum = 31;
	findAllSubSeq(a, n, targetSum);

	cout << "\nTest with empty array:\n";
	targetSum = 16;
	findAllSubSeq(a, 0, targetSum);
}

void part_3()
{
	cout << "\n----------------------------------------------------------\n";
	cout << "Part_3:\n\n";

	int a[] = {1, 2, 3, 1, 2, 7, 3, 4, 5, 6, 4, 8, 9, 2, 3, 1, 6, 8, 3, 2};
	int n = 20;

	cout << "a[] : ";
	printSeq(a, 0, n);
	cout << endl;

	int s, e;
	longestSubSeq_NoDup(a, n, s, e);
	cout << "Longest subsequence without repeating value:\n";
	cout << "start index = " << s << ", end index (exclusive) = " << e;
	cout << ", subsequence length = " << e - s << endl;

	printSeq(a, s, e);
	cout << endl << endl;

	for (int i = 0; i < n;i++)
		a[i] = 2;
	cout << "Test with array filled with 1 value: \n";
	longestSubSeq_NoDup(a, n, s, e);
	cout << "Longest subsequence without repeating value:\n";
	cout << "start index = " << s << ", end index (exclusive) = " << e;
	cout << ", subsequence length = " << e - s << endl;

	int t = 150000;
	int *x = new int[t];
	for (int i = 0; i < t; i++)
		x[i] = rand() % 9999;

	// Create a subsequence with no repeating value from first to last-1.
	int first = 120000, last = 140000;
	x[first] = x[first-1];
	for (int i = first + 1; i < last; i++)
		x[i] = x[i-1] + 1;
	x[last] = x[last-1];

	cout << "\nTest with array of size " << t << " : \n";

	clock_t begin = clock();
	longestSubSeq_NoDup(x, t, s, e);
	clock_t end = clock();	

	cout << "Longest subsequence without repeating value:\n";
	cout << "start index = " << s << ", end index (exclusive) = " << e;
	cout << ", subsequence length = " << e - s << endl;
	
	double elapsedTime = (double)(end - begin)/CLOCKS_PER_SEC;
	cout << "Elapsed time = " << elapsedTime << " seconds" << endl;

	// Expected computation time is less than 0.5 second.
}

int main()
{		
	part_1();
	part_2();
	part_3();

	cout << endl;
	system("pause");
	return 0;
}