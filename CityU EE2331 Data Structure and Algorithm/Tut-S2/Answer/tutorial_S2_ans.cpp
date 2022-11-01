// Student name:
// Student ID  :

/*
	Submission not required.
*/

#include <iostream>
#include <iomanip>
#include <cstdlib>

using namespace std;

int removeAll_unordered(int *a, int& n, const int *b, int m)
{
	// n = no. of elements in a[]
	// m = no. of elements in b[]
	// Elements in a[] are distinct, and elements in b[] are distinct.

	// Remove elements in a[] that can be found in b[].
	// Return the number of elements that are removed from a[].

	// i : index for referencing a[]
	// j : index for referencing b[]

	if (m == 0)
		return 0;

	int n_original = n;
	int i = 0;
	while (i < n)
	{
		// step 1: determine if a[i] is found in b[]
		bool found = false;
		for (int j = 0; j < m && !found; j++)
			if (a[i] == b[j])
				found = true;

		// step 2: remove a[i] if it is found in b[]
		if (found)
			a[i] = a[--n];  // a[i] receives a new value, i is not changed in this case
		else
			i++;            // update i to process the next element
	}
	return n_original - n;
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

	int i = 0;    // index for referencing a[]
	int j = 0;    // index for referencing b[]
	int k = 0;    // location to save the retained element in a[]
	while (i < n && j < m)
	{
		if (a[i] < b[j])		 // a[i] is not contained in b[]
			a[k++] = a[i++]; // retain a[i]
		else 
		{
			if (a[i] == b[j]) // remove a[i]			
				i++;
			j++;
		}
	}

	int count = 0;  
	if (k < i)  // some elements in a[] are removed
	{
		count = i - k;
		while (i < n)          // assert: a[i] to a[n-1] are not found in b[]
			a[k++] = a[i++]; // move them forward to fill the vacant slots
		n = k;
	}
	return count;
}

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

	cout << "Part-1: Unordered array" << endl;

	int a1[] = {5, 7, 14, 6, 2, 9, 11, 3};  
	int n = 8;
	printArray("a1[] = ", a1, n);
	printArray("b[] = ", b, m);

	int count = removeAll_unordered(a1, n, b, m);
	cout << "\nRemove elements in a1[] that can be found in b[]\n";
	printArray("a1[] = ", a1, n);
	cout << "No. of elements removed = " << count << endl;
	cout << "==================================================\n";
}

void part_2()  // ordered arrays with distinct elements
{
	cout << "Part-2: Ordered array" << endl;

	int b[] = {2, 5, 6, 8, 9, 10, 12};  
	int m = 7;
	printArray("b[] = ", b, m);
	cout << endl;

	int a2[] = {2, 3, 4, 6, 8, 10, 11, 12};  
	int n = 8;
	printArray("a2[] = ", a2, n);

	int count2 = removeAll_ordered(a2, n, b, m);
	cout << "\nRemove elements in a2[] that can be found in b[]\n";
	printArray("a2[] = ", a2, n);
	cout << "No. of elements removed = " << count2 << endl;
	cout << "---------------------------------------------------\n";

	int a3[] = {3, 4, 7, 13};
	int u = 4;
	printArray("a3[] = ", a3, u);

	int count3 = removeAll_ordered(a3, u, b, m);
	cout << "\nRemove elements in a3[] that can be found in b[]\n";
	printArray("a3[] = ", a3, u);
	cout << "No. of elements removed = " << count3 << endl;
	cout << "---------------------------------------------------\n";

	int a4[] = {5, 6, 9, 10};
	int v = 4;
	printArray("a4[] = ", a4, v);

	int count4 = removeAll_ordered(a4, v, b, m);
	cout << "\nRemove elements in a4[] that can be found in b[]\n";
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