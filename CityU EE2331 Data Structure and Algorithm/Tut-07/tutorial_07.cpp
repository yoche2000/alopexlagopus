// Student name: Lee Yo-Che
// Student ID  : 55348947

/*
	Submission deadline: Friday, 20 March, 12 noon.
	
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
#include <cstdlib>

using namespace std;

template<class Type>
struct node
{
	Type info;
	node<Type> *link;
};

template<class Type>
bool findAndRemove(node<Type>*& list, const node<Type>* subseq)
{
	node<Type> *p = list;
	const node<Type> *r = subseq;
	node<int> *cut = p;

	bool flag = false;						//See line 58

	while (p->link != nullptr)
	{
		bool flag = false;					//See line 58
		if (p->info != r->info)				//If the element does not hit the first of the subseq, shift to the next
		{
			cut = p;						//Cut takes the possition of p, as p previous
			p = p->link;
		}
		else
		{
			bool match = true;				//Match, marking the pattern matching 
			bool run = true;
			while (r->link != nullptr)		//Run from the first element to the last
			{
				if (p==list)				//Flag, marking the case when the first element hits
					flag = true;
				if (p->info == r->info )	//Deep check if the element hits the first element of the pattern
				{
					p = p->link;
					r = r->link;
				}
				else
				{
					flag = false;			//Resets flag, since matching fail
					match = false;			//Resets Match, since matching fail
					r = subseq;				//Resets r
					if (cut != list)		//Reset p, two cases
						p = cut->link;
					else
						p = cut;			
					break;					//Break since the pattern matching fail
				}
			}
			if (match == true)				//when matching succeed
			{
				cut->link = p->link;		//remove the subseq from list
				if (p->link == nullptr)		//Case of empty list
				{
					list = nullptr;			//set list to nullptr
					return true;			//then return
				}
				if (flag)					//case of the first element hits
				{
					list->info = p->link->info;		//remove the first element
					list->link = p->link->link;		//by redefining list
				}
				return true;				//return
			}
			cut = p;						//shift both cut and p 
			p = p->link;					// |cut|->| p |->|   |   to  |   |->|cut|->| p |         
		}									
	}
	return false; 
}

// ------------------------------------ functions given to you

template<class Type>
void printList(const char *label, const node<Type> *list)
{
	cout << label << " { ";
	const node<Type> *p = list;
	while (p != nullptr)
	{
		cout << p->info;
		if (p->link != nullptr)
			cout << ", ";
		 
		p = p->link;
	}
	cout << " }" << endl;
}

template<class Type>
node<Type>* createList(const Type a[], int n)
{
	if (n == 0)
		return nullptr;

	node<Type> *p, *r;
	p = r = new node<Type>;
	p->info = a[0];
	for (int i = 1; i < n; i++)
	{
		r->link = new node<Type>;
		r = r->link;
		r->info = a[i];
	}
	r->link = nullptr;
	return p;
}

int main()
{	
	cout << "Find and remove a subsequnce from a list" << endl << endl;
	int a[] = {1, 1, 1, 2, 4, 2, 3, 5, 0, 8, 3 };  
	int na = 11;

	int b[] = {1, 1, 2, 4};  
	int nb = 4;

	node<int> *listA = createList(a, na);
	node<int> *listB = createList(b, nb);

	printList("listA:", listA);
	printList("lisbB:", listB);

	cout << "\nfindAndRemove(listA, listB)\n";
	bool success = findAndRemove(listA, listB);
	if (success)
		printList("listA:", listA);
	else
		cout << "listB is not found in listA\n";

	cout << "\nfindAndRemove(listA, listB)\n";
	success = findAndRemove(listA, listB);
	if (success)
		printList("listA:", listA);
	else
		cout << "listB is not found in listA\n";

	int c[] = {1, 2, 3};  
	int nc = 3;
	node<int> *listC = createList(c, nc);

	cout << endl;
	printList("listC:", listC);
	cout << "findAndRemove(listA, listC)\n";
	success = findAndRemove(listA, listC);
	if (success)
		printList("listA:", listA);
	else
		cout << "listC is not found in listA\n";
	
	int d[] = {5, 0, 8, 3};
	int nd = 4;
	node<int> *listD = createList(d, nd);

	cout << endl;
	printList("listD:", listD);
	cout << "findAndRemove(listA, listD)\n";
	success = findAndRemove(listA, listD);
	if (success)
		printList("listA:", listA);
	else
		cout << "listD is not found in listA\n";

	cout << endl;
	
	system("pause");
	return 0;
}