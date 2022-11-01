// Student name: Lee Yo-Che
// Student ID  : 55348947

/*
	Submission deadline: Friday, 6 March 2020, 12 noon.

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
#include <string>
#include <cstdlib>
#include "my_bsearch.h"

using namespace std;
string getCurDate();
string adjustYear(const string& date, int y);

// Acceptable to use global variable that is read only.
// today is a constant global variable, set to the date of current local time.
const string today = getCurDate();  

struct person
{
	string dob;     // date of birth, yyyy-mm-dd
	char gender;    // 'F' for female, 'M' for male
	double income;  

	// Define your own public member function(s) if appropriate.
};

// Other supporting functions required in your design.

int comparedob( const void *a, const void* b)  //compare function for b_search
{
	//in the .h file, key(which is the Today-date string) will be passed to b

	string* a1 = (string*)a;
	person* b1 = (person*)b;

	if (*a1 < b1->dob)
	{
		//cout << *a1 << " < " << b1->dob << endl;
		return 1;
	}
	if (*a1 == b1->dob)
	{
		//cout << *a1 << " == " << b1->dob << endl;
		return 0;
	}
	if (*a1 > b1->dob) 
	{
		//cout << *a1 << " > " << b1->dob << endl;
		return -1;
	}
}

int comparesallary(const void *a, const void* b)	//compare function, comparing person base on their sallary
{													//for later use of sorting, Line 114
	person* a1 = (person*)a;
	person* b1 = (person*)b;
	if (a1->income < b1->income) return 1;
	if (a1->income == b1->income) return 0;
	if (a1->income > b1->income) return -1;
}

void analyse(person *list, int n)
{
	cout << "Reference date is " << today << endl << endl;
	int key1 = -20;
	
	int ind[7];
	int i = 0;

	person *sal_list = new person[5000];

	while (key1 > -70)
	{
		int key2 = key1 -10;
		string keystring1 = adjustYear(getCurDate(), key1);
		string keystring2 = adjustYear(getCurDate(), key2);

		person* start = (person*)bsearch_first(&keystring1, list, n, sizeof(person), comparedob);	//get the index of the begining of the range 
		person* end = (person*)bsearch_first(&keystring2, list, n, sizeof(person), comparedob);		//get the index of the begining of the next range 
		int length = start-end;		//length, number of persons, in the range
		int startpt = end - list;	//starting index
		int endpt = start - list;	//ending index


		person* sal_list = new person[length];		//create another list of people within start and end, or within the range
		
		//cout << "starting ind: " << startpt << " / ending ind: " << endpt << endl;				//debugging line	

		ind[i] = endpt;
		ind[i+1] = startpt;
		i++;

		for (int i = 0; i < length; i++)
			sal_list[i] = list[startpt + i];
		qsort(sal_list, length, sizeof(person), comparesallary);	//Sorting the new list by person.sallary, using the compare function in Line 72
	
		//debugging lines
		/*
		for (int i = 0; i < length; i++)
		{
			cout << i << ":" << sal_list[i].income << "   ";
			if (i % 5 == 0)
				cout << endl;
		}
		*/

		int sum=0;
		for (int i = 0; i < length/4; i++)							//Adding up the salary in the top-quartile
			sum += sal_list[i].income;
		cout << "Average income of the persons in the top-quartile of the age-group " << -key1 << " to " << -key2 - 1 << " = " << sum*4 / double(length) << endl <<endl;

		key1 -= 10;
	}	
}

// -------------------------------------------------- functions given to you

person* readDataFile(string filename, int& n)
{
	ifstream infile;
	infile.open(filename);
	if (infile.is_open() == false)
	{
		cout << "Cannot open data file" << endl;
		system("pause");
		exit(0);
	}

	string temp;
	infile >> temp >> n;

	person *list = new person[n];
	for (int i = 0; i < n; i++)
		infile >> list[i].dob >> list[i].gender >> list[i].income;

	infile.close();
	return list;
}

string adjustYear(const string& date, int y)
{
	// Return a string that represents the date where 
	// the year is adjusted by adding the value y.

	return to_string(stoi(date.substr(0, 4)) + y) + date.substr(4);
}

string getCurDate()
{
	// Return a string that represents the date of current time in the format yyyy-mm-dd.

	time_t cur = time(NULL);
	struct tm date;
	localtime_s(&date, &cur);
	int year = 1900 + date.tm_year;
	int month = 1 + date.tm_mon;
	int day = date.tm_mday;

	char s[11];
	_itoa_s(year, s, 10);  // _itoa_s(int value, char *buffer, int radix)
	s[4] = '-';
	s[5] = month / 10 + '0';
	s[6] = month % 10 + '0';
	s[7] = '-';
	s[8] = day / 10 + '0';
	s[9] = day % 10 + '0';
	s[10] = '\0';
	return string(s);
}

int main()
{
	int n = 0;
	person *list = readDataFile("persons.txt", n);
	analyse(list, n);

	system("pause");
	return 0;
}