 // Student name:
// Student ID  :

/*
	Submission deadline: Friday, 14 Feb 2020, 12 noon.

	Upload your .cpp file to Canvas.

	Put down your name and student ID at the top of your program file.
	10% marks will be deducted if your program file does not contain your name and ID.

	Submitted program that cannot be compiled (with syntax errors) by Visual Studio
	will receive no more than 30% marks.

	If your program crashes when executed with Visual Studio, you should state the
 	problem at the top of your program file (right below student name and ID); otherwise
	significant percentage (30% or more) of marks will be deducted.

	Late submissions or submissions by other means are not accepted.
*/

#include <iostream>
#include <cstdlib>
#include <iomanip>

using namespace std;

// -------------------------------------------- functions to be implemented by you

void filter_1(int *a, int& n, int countThreshold)
{
	// Input array a[] is not ordered.

	// Remove numbers in a[] that appear less than countThreshold number of times.
	// Relative order of elements retained in the array need not be preserved.

	// Do NOT sort the array a[] in your answer.

}

void filter_2(int *a, int& n, int countThreshold)  
{
	// Input array a[] is sorted in ascending order.

	// Remove numbers in a[] that appear less than countThreshold number of times.
	// Relative order of elements retained in the array should be preserved.

}

// ------------------------------------------- functions given to you
void printArray(const char *header, int* a, int n)
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

void test_1(const char *header, int *a, int& n, int threshold)
{
	cout << "-----------------------------------------------------------\n";
	printArray(header, a, n);
	cout << "Remove numbers that appear less than " << threshold << " times.\n";
	filter_1(a, n, threshold);
	printArray(header, a, n);
}

void part_1()
{
	cout << "Part_1: Unordered array\n\n";

	int a[] = {1, 2, 3, 3, 1, 4, 4, 4, 5, 8, 6, 6, 5, 5, 2, 5, 5, 1, 7, 7};
	int n = 20;
	test_1("a[]", a, n, 3);
	
	int b[] = {1, 3, 2, 4, 3, 2, 6, 7, 8, 7};
	int m = 10;
	test_1("b[]", b, m, 3);
	
	int c[] = {1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 2, 1, 2, 2, 1, 1, 0};
	int r = 20;
	test_1("c[]", c, r, 5);
}

void test_2(const char *header, int *a, int& n, int threshold)
{
	cout << "-----------------------------------------------------------\n";
	printArray(header, a, n);
	cout << "Remove numbers that appear less than " << threshold << " times.\n";
	filter_2(a, n, threshold);
	printArray(header, a, n);	
}

void part_2()
{
	cout << "============================================================\n";
	cout << "Part_2: Ordered array\n\n";

	int a[] = {1, 1, 2, 2, 2, 2, 5, 5, 6, 6, 7, 7, 7, 9, 9, 9, 10, 12, 13, 13};
	int n = 20;
	test_2("a[]", a, n, 3);
	
	int b[] = {1, 1, 2, 2, 3, 4, 6, 7, 7, 8};
	int m = 10;
	test_2("b[]", b, m, 3);

	int c[] = {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2};
	int r = 20;
	test_2("c[]", c, r, 5);
}

int main()
{	
	part_1();
	part_2();

	system("pause");
	return 0;
}