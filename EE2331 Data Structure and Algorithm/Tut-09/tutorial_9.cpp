// Student name: LEE Yo-Che
// Student ID  : 55348947

/*
	Submission deadline: Friday, 3 April 2020, 12 noon.

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
#include <fstream>
#include <string>
#include <stack>
#include <cmath>  // pow(double x, double y), compute the value of x to power y

using namespace std;

bool isOperator(const string& expr, int i)					//isOperator function provided by the course material
{
	char c = expr[i];
	if (c == '*' || c == '/' || c == '$')
		return true;
	if ((c == '-' || c == '+') &&
		(expr[i + 1] == '\0' || isspace(expr[i + 1])))
		return true;
	return false;
}

double evop(char opr, const double& d1, const double& d2)	//evop function provided by the course material
{
	switch (opr)
	{
	case '+': return d1 + d2;
	case '-': return d1 - d2;
	case '*': return d1 * d2;
	case '/': return d1 / d2;
	case '$': return pow(d1, d2);
	default: return 0;
	}
}

double nextOperand(const string& expr, int& i)				//NextOperand Function, from right to left
{															//i is the ending index of the operand
	//cout << "Call nextOperand with i = " << i << endl;
	int j = i;
	int len = 0;
	while (j != 0 && !isspace(expr[j]))
	{
		//cout << "=== i=" << i << ", j=" << j <<endl;
		j--;
	}
	//cout << "//== i=" << i << ", j=" << j << endl;
	string number = expr.substr(j, i-j+1);					//Get substring, j as the begining index, i-j+1 as the length
	i = j;
	//cout << "Fetch Operand Result : " << number << endl;
	return stod(number); // string to double conversion
}


double evaluate_prefix(const string& expr)
{
	stack<double> s;

	int i = expr.length() - 1;								
	while (i >= 0)
	{
		if (isspace(expr[i]))
			i--;
		else if (isOperator(expr, i))
		{
			char opr = expr[i--];
			double operand2 = s.top();
			s.pop();
			//cout << "size=" << s.size() << "  i=" << i << endl;
			double operand1 = s.top();
			s.pop();
			double newnum = evop(opr, operand2, operand1);
			s.push(newnum);
			//cout << "Execute calculation " << operand2 << opr << operand1 << " = " << newnum << endl;
		}
		else
			s.push(nextOperand(expr, i));
		if (i == 0 && s.size() == 1)
			break;							//Case of single operand
	}
	return s.top();
}

int main()
{	
	cout << "----- Evaluate Prefix Expression -----" << endl << endl;

	ifstream infile("prefix_expr.txt");
	if (!infile.is_open())
	{
		cout << "Error: cannot open prefix_expr.txt" << endl;
		system("pause");
		exit(0);
	}

	string prefix_expr;
	while (getline(infile, prefix_expr))
	{		
		double value = evaluate_prefix(prefix_expr);
		cout << "prefix expression: " << prefix_expr << " = " << value << endl << endl;
	}
	infile.close();

	system("pause");
	exit(0);
}