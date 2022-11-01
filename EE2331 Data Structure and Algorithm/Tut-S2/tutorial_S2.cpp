//	Submission not required.

// ------------------------------------------------------------------------------
// Try to design your functions such that no debugging is required.
// That is, your program can be compiled successfully,
// and produces the correct results when it is executed.

// Make a plan using paper and pen before you start to type any program statement.
// When you get the correct results, try to improve your program where appropriate. 
// Improvement can be (1) clarity and elegance, (2) simplicity, (3) efficiency.
// -------------------------------------------------------------------------------

#include <iostream>
#include <iomanip>
#include <cstdlib>

using namespace std;

// Do not modify the function interface
int removeAll_unordered(int *a, int& n, const int *b, int m)  
{
	// n = no. of elements in a[]
	// m = no. of elements in b[]
	// Elements in a[] are distinct, and elements in b[] are distinct.

	// Remove elements in a[] that can be found in b[].
	// Return the number of elements that are removed from a[].

	return 0;  // dummy return statement, replace it by your own code.
}

int removeAll_ordered(int *a, int& n, const int *b, int m)
{
	// n = no. of elements in a[]
	// m = no. of elements in b[]
	// Elements in a[] are distinct, and elements in b[] are distinct.
	// a[] and b[] are sorted in ascending order.

	// Remove elements in a[] that can be found in b[].
	// Return the number of elements that are removed from a[].

	// Do not define or create other array.
	// This task can be done in linear time, i.e. O(n+m) time.


	return 0;  // dummy return statement, replace it by your own code.
}

// --------------------------------------------------- functions given to you
void printArray(const char *label, const int *a, int n)
{
	cout << label << " { ";
	for (int i = 0; i < n; i++)
	{
		cout << a[i];
		if (i < n-1)
			cout << ", ";
	}
	cout << " }" << endl;
}

void part_1()  // unordered arrays with distinct elements
{
	int b[] = {6, 4, 3, 10, 9, 15, 7};  
	int m = 7;

	cout << "Part-1: Unordered array" << endl << endl;
	printArray("b[] = ", b, m);
	cout << endl;

	int a1[] = {5, 7, 14, 0, 6, 2, 9, 11, 3};  
	int n = 9;
	printArray("a1[] = ", a1, n);

	int count = removeAll_unordered(a1, n, b, m);
	cout << "Remove elements in a1[] that can be found in b[]\n";
	printArray("a1[] = ", a1, n);
	cout << "No. of elements removed = " << count << endl << endl;
	cout << "==================================================\n";
}

void part_2()  // ordered arrays with distinct elements
{
	cout << "Part-2: Ordered array" << endl << endl;

	int b[] = {2, 5, 6, 8, 9, 10, 12};  
	int m = 7;
	printArray("b[] = ", b, m);
	cout << endl;

	int a2[] = {0, 2, 3, 4, 6, 8, 10, 11, 12};  
	int n = 9;
	printArray("a2[] = ", a2, n);

	int count2 = removeAll_ordered(a2, n, b, m);
	cout << "Remove elements in a2[] that can be found in b[]\n";
	printArray("a2[] = ", a2, n);
	cout << "No. of elements removed = " << count2 << endl << endl;
	cout << "---------------------------------------------------\n";

	int a3[] = {3, 4, 7, 13};
	int u = 4;
	printArray("a3[] = ", a3, u);

	int count3 = removeAll_ordered(a3, u, b, m);
	cout << "Remove elements in a3[] that can be found in b[]\n";
	printArray("a3[] = ", a3, u);
	cout << "No. of elements removed = " << count3 << endl << endl;
	cout << "---------------------------------------------------\n";

	int a4[] = {5, 6, 9, 10};
	int v = 4;
	printArray("a4[] = ", a4, v);

	int count4 = removeAll_ordered(a4, v, b, m);
	cout << "Remove elements in a4[] that can be found in b[]\n";
	printArray("a4[] = ", a4, v);
	cout << "No. of elements removed = " << count4 << endl << endl;
}

int main()
{	
	part_1();
	part_2();

	system("pause");
	return 0;
}