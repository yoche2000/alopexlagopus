// Student name: Lee Yo-Che
// Student ID  : 55348947

/*
Facing Compiler issue, stated as below (Line 116)
Error (active) | E1087 | no instance of overloaded function "std::vector<_Ty, _Alloc>::erase [with _Ty=unsigned int, _Alloc=std::allocator<unsigned int>]" 
						 matches the argument list and object (the object has type qualifiers that prevent a match)	
*/

/*
	Submission deadline: Friday, 13 March 2020, 12 noon

	We shall use the data structure vector<T> in this exercise.
	http://www.cplusplus.com/reference/vector/vector/

	Study the notes and watch the videos that explain vector, and
	recursion and backtracking.

	The storage capacity of a vector is expanded automatically when more
	elements are added to it.
	You can access an element in a vector using the subscript operator,
	e.g. v[i] references the element at index i of vector v.

	Operators that are relevant to this exercise:
		push_back(item) : add the item at the end of the vector
		pop_back()      : remove the last item in the vector
		back()          : get the last item in the vector
		size()          : return the number of items in the vector
		empty()         : return true if the vector is empty, otherwise return false
*/

#include <iostream>
#include <cstdlib>
#include <iomanip>
#include <vector>
#include <algorithm>

using namespace std;

struct tube  // Implement the member functions add() and pop()
{
	unsigned len;          // length of the tube
	unsigned avail_space;  // vacant space in the tube
	vector<unsigned> list; // cylindrical pins added to the tube

	tube(unsigned size)  // constructor
	{
		len = avail_space = size;
	}

	
	bool add(unsigned e)
	{
		if (avail_space > e)
		{
			this->list.push_back(e);
			return true;
		}
		else
			return false;
	}
	void pop()
	{
		this->list.pop_back();
	}
};

//Implementing sorting function can enhance the effeciency
void revsort(vector<unsigned>& v)
{
	sort(v.begin(), v.end());
	reverse(v.begin(), v.end());
}



ostream& operator<<(ostream& os, const tube& t)
{
	// Function to print a tube.

	os << "len = " << t.len << ", pins : ";
	for (int i = 0; i < t.list.size(); i++)
		os << t.list[i] << ", ";
	
	os << endl;
	return os;
}


// Other supporting functions required in your design

void packing(const vector<unsigned>& pins, unsigned tubeLen)
{
	
	unsigned maxel = *max_element(pins.begin(), pins.end());	//Get max element in vector
	if (maxel > tubeLen)										//Case: max itself can not fit in the tube 
	{
		cout << "No Solution";
		return;
	}

	vector<tube> tubes;  // the vector is empty initially
	cout << "tubeLen = " << tubeLen << " cm" << endl;

	int lap = 0;
	while (pins.size() != 0)
	{
		int avail = tubeLen;
		int index = 0;
		while (index < pins.size() && avail != 0)
		{
			if (pins[index] <= avail)
			{
				tubes[lap].add(pins[index]);		//Stores the qualified element in the list of the tube
				avail -= pins[index];				//Update the space
				pins.erase(pins.begin()+index);		//Erase the stored elementFaced Compiling Error. Tried several ways to code but failed.
			}
		}
		tubes[lap].len = tubeLen;					//Set length of tube
		lap++;
	}



	// Statements to print the results, if a solution is found.	
	unsigned spaceUsed = 0;
	unsigned count = 0;
	for (unsigned i = 0; i < tubes.size(); i++)
	{
		cout << "Tube " << i+1 << " : " << tubes[i];

		spaceUsed += tubes[i].len - tubes[i].avail_space;
		count += tubes[i].list.size();
	}
	unsigned totalSpace = tubes.size() * tubeLen;
	double utilization = totalSpace > 0 ? spaceUsed * 100.0 / totalSpace : 0;
	cout << "Total no. of pins = " << count << "; space utilization = " << utilization << "%\n";
}

int main()
{	
	unsigned a[] = {4, 10, 7, 12, 6, 10, 10, 8, 5, 13, 13, 11, 3, 14};	
	unsigned n = 14;

	/*
		You may test your program with 1 more test case:
		unsigned a[] = {3, 3, 4, 6, 5, 3};
		unsigned n = 6;
		unsigned tubeLen = 12;
		Result: packed in 2 tubes (6, 3, 3), (5, 4, 3)
	*/

	vector<unsigned> pins;
	for (unsigned i = 0; i < n; i++)
		pins.push_back(a[i]);

	cout << "Lengths of cylindrical pins (cm) :" << endl;
	for (unsigned i = 0; i < pins.size(); i++)
	{
		if (i > 0 && i % 10 == 0)
			cout << endl;
		cout << setw(3) << pins[i] << ", ";
	}
	cout << endl << endl;
	revsort(pins);			//Sort the vector first will make things more efficient

	// DO NOT modify this for-loop.
	for (unsigned tubeLen = 22; tubeLen >= 13; tubeLen -= 3)
	{
		cout << "-----------------------------------------------------\n";
		packing(pins, tubeLen);
	}

	system("pause");
	return 0;
}