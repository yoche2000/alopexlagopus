// Student name: Lee Yo-Che
// Student ID  : 55348947

/*
	Submission deadline: Friday, 21 Feb 2020, 12 noon.
	Upload your .cpp file to Canvas.
	Put down your name and student ID at the top of your program file.
	10% marks will be deducted if your program file does not contain your name and ID.
	Submitted program that cannot be compiled (with syntax errors) by Visual Studio
	will receive no more than 30% marks.
	If your program crashes or infinite-loop when executed with Visual Studio,
	you should state the problem at the top of your program file
	(right below student name and ID); otherwise significant percentage (30% or more)
	of marks will be deducted.
	Late submissions or submissions by other means are not accepted.
*/

#include <iostream>
#include <iomanip>
#include <cstdlib>

using namespace std;

// -------------------------------------------- functions to be implemented by you
void longest_block(const int* a, int n, int& start, int& len)
{
	int i = 0;					// Startng index
	int j;						// tar, the starting index of the processing block
	int blk = 0;				// blk, the length of the longest block inrecord so far
	int tar = 0;				// tar, the starting index of the longest block inrecord so far

	if (&len == 0)				// Filter the case of empty loop-array
	{
		start = 0;
		return;
	}

	int flag = 0;
	while (i<n && flag==0)		// The purpose of flag is implied on line 58 
	{
		j = i+1;
		while (a[j] == a[i])
		{
			if (j-i == n-1)		// Filter the case of all elements in the loop-array are identical 
			{
				start = 0;
				len = n;
				return;
			}
			else if (j == n-1)	// Flag when j passes through the starting point
			{
				j = 0;
				flag = 1;
			}
			else
				j++;
		}
		int cnt = j - i;		// cnt, the length of the processing block, an extra var for possible adjustment and operation
		if (cnt < 0)			// Adjust length when j is smaller than i, which happens on the blk covering the starting  
		{
			cnt += n;
			flag = 1;
		}
		if (cnt>blk)			// Update the longest block and its starting point
		{
			blk = cnt;
			tar = i;
		}
		i = j;
	}
	start = tar;
	len = blk;
}

void longestSubSeq_NoDup(const int* a, int n, int& start, int& len)
{
	int i = 0;
	int	j;
	int inpt = 0;
	int flag = 0;
	
	if (n==0 || n==1)	//Filtering loop-array with no content
	{
		start = 0;
		len = n;
		return;
	}

	while (i < n)
	{
		flag = 0;
		//cout << "I = " << i << endl;		//For debugging 
		for (int j= i+1; flag == 0 ; j++)	//If flag==1, a lap has been done
		{
			//cout <<"J = "<< j << endl;	//For debugging 
			for (int k = i; k < j; k++)
			{
				//cout << i <<",  "<<j<<",  "<< k << endl;	//For debugging 
				if (a[j%n] == a[k%n])		//Over-starting-pt adjustment
				{
					if (len<(j-i))
					{
						len = j - i;
						start = i;
					}
					i = k + 1;				//Move L to the first duplicated index to increase efficiency
					flag = 1;
					break;
				}
				else if ((j-i) >= n)		//Case of all elements in the lop-arry are identical
				{
					len = n;
					start = 0;
					return;
				}
			}
		}
	}



}

// -------------------------------------------------- functions given to you
void printArray(const char *header, const int* a, int n)
{
	cout << header << ", size = " << n << endl;
	for (int i = 0; i < n; i++)
	{
		if (i > 0 && i % 10 == 0)
			cout << endl;
		cout << setw(4) << a[i] << ", ";
	}
	cout << endl << endl;
}


void printSeq(const int *a, int n, int start, int len)
{
	cout << "subsequence: ";
	for (int i = 0; i < len; i++)
		cout << a[(start + i) % n] << ", ";
	cout << endl;
}

void test_longest_block(const char *header, const int *a, int n)
{
	printArray(header, a, n);
	int start, len;
	longest_block(a, n, start, len);
	cout << "Longest block:\n";
	cout << "start index = " << start << ", block len = " << len << endl;
	cout << "----------------------------------------------------------\n";
}

void part_1()
{
	cout << "Part_1:\n\n";

	int a[] = { 1, 2, 2, 3, 3, 4, 4, 4, 6, 6, 6, 5, 5, 5, 5, 5, 2, 0, 1, 3 };
	int n = 20;

	int b[] = { 1, 1, 3, 3, 1, 4, 4, 4, 0, 8, 6, 6, 5, 5, 5, 5, 2, 1, 1, 1 };
	int m = 20;

	int c[] = { 3, 3, 3, 3 };
	int p = 4;

	int d[] = { 2 };
	int q = 1;

	test_longest_block("a[]", a, n);
	test_longest_block("b[]", b, m);
	test_longest_block("c[]", c, p);
	test_longest_block("d[]", d, q);
	test_longest_block("d[]", d, 0);  // empty array
}

void test_longestSubSeq_NoDup(const char *prefix, const int *a, int n)
{
	printArray(prefix, a, n);
	int start, len;
	longestSubSeq_NoDup(a, n, start, len);
	cout << "Longest subsequence with no duplicate value:\n";
	cout << "start index = " << start << ", subSeq len = " << len << endl;
	printSeq(a, n, start, len);
	cout << "----------------------------------------------------------\n";
}

void part_2()
{
	cout << "\n==========================================================\n";
	cout << "Part_2:\n\n";

	int a[] = { 1, 2, 3, 0, 2, 7, 3, 4, 5, 6, 4, 8, 0, 2, 3, 1, 6, 8, 3, 2 };
	int n = 20;

	int b[] = { 5, 9, 7, 4, 5, 7, 3, 4, 5, 6, 4, 0, 9, 0, 4, 1, 6, 8, 3, 2 };
	int m = 20;

	int c[] = { 3, 3, 3, 3 };
	int p = 4;

	int d[] = { 1, 0, 2, 3 };
	int q = 4;

	int e[] = { 2 };
	int r = 1;

	test_longestSubSeq_NoDup("a[]", a, n);
	test_longestSubSeq_NoDup("b[]", b, m);
	test_longestSubSeq_NoDup("c[]", c, p);
	test_longestSubSeq_NoDup("d[]", d, q);
	test_longestSubSeq_NoDup("e[]", e, r);
	test_longestSubSeq_NoDup("e[]", e, 0);  // empty array
}

int main()
{
	part_1();
	part_2();

	system("pause");
	return 0;
}
