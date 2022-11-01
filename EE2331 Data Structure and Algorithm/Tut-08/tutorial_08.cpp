// Student name: Lee Yo-Che
// Student ID : 55348947

/*
Submission deadline: Friday, 27 March, 12 noon.

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
#include <iomanip>
#include <fstream>
#include <cstdlib>
#include <string>

using namespace std;

template<class Type>
struct node
{
	Type info;
	node<Type> *link;
};

struct person
{
	string dob;
	char gender;
	double income;
};

// Design a functional class/struct to compare person by gender and then by income.
struct comparePerson
{
	// Your codes
	bool operator()(const person& pa, const person& pb) {
		if (pa.gender > pb.gender)
			return false;
		else if (pa.gender < pb.gender)
			return true;
		else
			return pb.income >= pa.income;		//Second Comparison
	}
} compPerson;

ostream& operator<<(ostream& os, const person& p)
{
	os << p.dob << ' ' << p.gender << ' ' << p.income << endl;
	return os;
}

template<class Type, class Compare>
node<Type>* merge(node<Type>* nodea, node<Type>* nodeb, Compare comp)
{
	node<Type> *r, *head;
	if ( comp(nodea->info, nodeb->info) == true) {		//Determining the head
		head = nodea;
		nodea = nodea->link;		
	}
	else
	{
		head = nodeb;
		nodeb = nodeb->link;
	}

	r = head;											//r is the end of the result list, for later comparison
	while (nodea != nullptr && nodeb != nullptr)
	{
		if (comp(nodea->info, nodeb->info) == true) {	//Appending element to the result list 
			r->link = nodea;
			nodea = nodea->link;
		}
		else
		{
			r->link = nodeb;
			nodeb = nodeb->link;
		}
		r = r->link;									//Shift to the end of the result list
	}
	while (nodeb!= nullptr)								//Move all remaining elements to the result list
	{
		r->link = nodeb;
		nodeb = nodeb->link;
		r = r->link;
	}
	while (nodea != nullptr)							//Move all remaining elements to the result list
	{
		r->link = nodea;
		nodea = nodea->link;
		r = r->link;
	}
	
	return head;										//Returt the head of the result linked list
}

// Other functions required in your design

template<class Type, class Compare>
void msort_list(node<Type>*& list, Compare comp)
{
	// Sort the list using the merge sort algorithm.
	// Your sorting method should be stable.
	// Implement the sorting algorithm using recursion.

	// Your codes
	node<Type> *mid, *cnt, *last;
	int length = 0;
	last = nullptr;		//Initialization to avoid compilation error
	cnt = list;
	mid = list;
	while (cnt != nullptr)			//Get the length of the linked list
	{
		cnt = cnt->link;
		length++;
	}

	if (length == 1)				//Base case
		return;

	length = length / 2;
	while (length != 0)
	{
		last = mid;
		mid = mid->link;
		length--;
	}

	last->link = nullptr;
	msort_list(list, comp);
	msort_list(mid, comp);
	list = merge(list, mid, comp);
}

// ------------------------------------ functions given to you

template<class Type>
void printList(const char *label, const node<Type> *list)
{
	cout << label << endl;
	const node<Type> *p = list;
	while (p != nullptr)
	{
		cout << p->info;
		p = p->link;
	}
	cout << endl;
}

node<person>* readDataFile(const string& filename)
{
	ifstream infile;
	infile.open(filename);
	if (infile.is_open() == false)
	{
		cout << "Cannot open data file." << endl;
		system("pause");
		exit(0);
	}

	string temp;
	int n;
	infile >> temp >> n;

	node<person> *header = new node<person>;
	header->link = nullptr;
	node<person> *last = header;
	for (int i = 0; i < n; i++)
	{
		last->link = new node<person>;
		last = last->link;
		infile >> last->info.dob >> last->info.gender >> last->info.income;
	}
	last->link = nullptr;

	infile.close();
	node<person> *list = header->link;
	delete header;
	return list;
}

int main()
{
	node<person> *list = readDataFile("tut-08-data.txt");
	printList("List of persons:", list);
	// The list is initially sorted by dob.

	// Sort the list by gender, and then by income using merge sort.
	// Your sorting method should be stable.
	msort_list(list, compPerson);
	printList("List of persons (sorted by gender and then by income):", list);
	cout << endl;

	system("pause");
	return 0;
}