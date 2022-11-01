// Student name: LEE YO-CHE
// Student ID  : 55348947

/*
	Submission deadline: Friday, 28 Feb 2020, 12 noon.
	
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

using namespace std;

struct person
{
	string dob;     // date of birth, yyyy-mm-dd
	char gender;    // 'F' for female, 'M' for male
	double income;  
};

ostream& operator<<(ostream& os, const person& p)
{
	os << p.dob << ' ' << p.gender << ' ' << p.income << endl;
	return os;
}

// Use of global variable is acceptable if it is read only.
const string today = "2020-02-24";

// --------------------------------------- functions to be implemented by student

// You may define other supporting functions, if required.

//function sti is to transform the dob string to int(yyyymmdd) for calculation 
int sti(string str) {
	int val = int(str[0]) * 1000 + int(str[1]) * 100 + int(str[2]) * 10 + int(str[3]);
	val *= 100;
	val += int(str[5]) * 10 + int(str[6]);
	val *= 100;
	val += int(str[8]) * 10 + int(str[9]);
	return val;
}

void analyse(const person *list, int n, int age_L, int age_H)
{	
	int td_sti = sti(today);
	
	int f_cnt=0;
	double f_inc = 0;
	int m_cnt = 0;
	double m_inc = 0;
	int start;
	int end;

	for (int i = 0; i < n; i++)
	{
		if ((sti(list[n].dob) - td_sti) / 10000 < age_H)
		{
			if (list[n].gender == 'f')
			{
				f_cnt++;
				f_inc += list[n].income;
			}
			else
			{
				m_cnt++;
				m_inc += list[n].income;
			}
		}
		else if (((sti(list[n].dob) - td_sti) / 10000 < age_L))
		{
			end = n;
			break;
		}
	}

	double f_inc_avg = f_inc / f_cnt;
	double m_inc_avg = m_inc / m_cnt;
	start = end - m_cnt - f_cnt;

	cout << "FCNT: " << f_inc << endl;
	cout << "FAVG: " << f_inc_avg << endl << endl;
	cout << "MCNT: " << f_inc << endl;
	cout << "MAVG: " << m_inc_avg << endl;
	// preconditions: list[] is sorted by dob in ascending order,
	//                n = length of list, and age_H >= age_L    
	//
	// age_L and age_H (inclusive) define an age-group.
	// Age is determined as of today (a constant value is used in this exercise).
	//
	// Find the average income of females, and average income of males in the age-group.
	// Print the results to standard output.
	//
	// Do not traverse the array from the beginning using sequential search.
	// Use binary search to locate a person within the age-group,
	// and then process persons in the age-group sequentially.
	//
	// For age_L = 50, age_H = 59, and today = "2020-02-24", process persons  
	// that were born after 1960-02-24, and were born on or before 1970-02-24.
	// Do not hardcode the dates in your program. 
	// Values of age_L, age_H and today may vary.
	/*
	cout << "Reference date is " << today << endl << endl;
	cout << "Index range of persons in the age-group " << age_L << " to " << age_H << ":\n";

	cout << "Number of females in age-group " << age_L << " to " << age_H << " = " << f_cnt;

	*/
}

// -------------------------------------------------- functions given to student

person* readDataFile(string filename, int& n)
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
	infile >> temp >> n;

	person *list = new person[n];
	for (int i = 0; i < n; i++)
		infile >> list[i].dob >> list[i].gender >> list[i].income;

	return list;
}

int main()
{		
	// Put the data file "persons.txt" in the working directory of your project.
	// Suppose your project is located at c:\myProject,
	// put the data file in c:\myProject\myProject

	int n = 0;
	person *list = readDataFile("persons.txt", n);  // DO not modify this statement
	//analyse(list, n, 50, 59);

	for (int i = 0; i < n; i++)
		cout << list[n].dob << " " << list[n].gender << " " << list[n].income;

	system("pause");
	return 0;
}