// Student name: 
// Student ID  : 

/*
	Submission deadline: Thursday, 23 Jan 2020, 5 pm

	Part-1 : simple operations on char array.
	Part-2 : conversion of IPv6 address from binary to text format.

	Upload your .cpp file to Canvas.

	Put down your name and student ID at the top of your program file.
	10% marks will be deducted if your program file does not contain your name and ID.

	Submitted program that cannot be compiled (with syntax errors) by Visual Studio
	will receive no more than 30% marks.

	Late submissions or submissions by other means are not accepted.
*/

#include <iostream>
#include <iomanip>
#include <cstdlib>

using namespace std;
char toHex(unsigned d);

// ------------------------------------ functions to be implemented by student
void trim(char *a)
{
	// Remove leading and trailing white-space characters in a[].
	// Example:
	//		char a[] = "  Test 1  2 3   ";
	//		After trimming, a[] = "Test 1  2 3";

	// first : index of the first non-white space char (include '\0')
	// last  : index of the last non-white space char (exclude '\0')

	int first = 0;  
	while (isspace(a[first]))
		first++;

	int last = first - 1;
	for (int i = first; a[i] != '\0'; i++)
		if (!isspace(a[i]))
			last = i;

	if (first > 0)
	{
		int k = 0;
		for (int i = first; i <= last; i++)
			a[k++] = a[i];
		a[k] = '\0';
	}
	else
		a[last+1] = '\0';
}

void squeeze(char *a)
{
	// Remove all white-space characters in a[].
	// Example:
	//		char a[] = "  Test 1  2 3   ";
	//		After squeezing, a[] = "Test123";

	int k = 0;
	for (int i = 0; a[i] != '\0'; i++)
		if (!isspace(a[i]))
			a[k++] = a[i];
	a[k] = '\0';
}

char* bin2Text_IPv6(const unsigned char *v)
{
	// Convert an IPv6 address from binary to text format.

	// IPv6 address is an 128-bit bit-vector represented by an array of 16 unsigned char.
	// v[0] represents the left most 8 bits of the IPv6 address, and so on.
	// Bits in a byte are numbered from 7 down to 0 (from left to right).
	// The 7-th bit of v[0] is the first bit of the IPv6 address.

	char *a = new char[40];  // IPv6 address in text format is up to 40 chars, including '\0'

	int start = -1;  // start index of zero-hextet (16-bit group) to be compressed
	int maxLen = 1;  // length of the sequence of zero-hextet to be compressed
	int ref = -1;
	int len = 0; 
	bool isZero = false;

	for (int i = 0; i < 8; i++)  // 8 groups of 16-bit hextet
	{
		if (v[2*i] == 0 && v[2*i+1] == 0)  // hextet is zero
		{
			if (!isZero)
				ref = i;
			
			len++;
			isZero = true;
			if (len > maxLen)
			{
				maxLen = len;
				start = ref;
			}
		}
		else
		{
			isZero = false;
			len = 0;
		}
	}

	// <start, maxLen> = sequence of zero-hextet to be compressed 
	int k = 0;
	int h = 0;
	while (h < 8)  // hextet 0 to 7
	{
		if (h == start)
		{
			a[k] = a[k+1] = ':';
			k += 2;
			h += maxLen;
		}
		else
		{
			for (int j = 0; j < 2; j++)  // 2 bytes in a 16-bit hextet
			{
				unsigned d1 = v[2*h+j] / 16;
				unsigned d2 = v[2*h+j] % 16;

				if (d1 > 0 || (j == 1 && v[2*h] > 0))
					a[k++] = toHex(d1);
				if (d2 > 0 || d1 > 0 || j == 1)
					a[k++] = toHex(d2);
			}

			if (h < 7 && h+1 != start)
				a[k++] = ':';
			h++;
		}
	}
	a[k] = '\0';
	return a;
}

// ------------------------------------ functions given to student

void test1(char *a, char *b)
{
	cout << "-------------------------------------\n";
	
	cout << "Character array: \"" << a << '\"' << endl;
	trim(a);
	cout << "After trimming : \"" << a << '\"' << endl;
	squeeze(b);
	cout << "After squeezing: \"" << b << '\"' << endl;
}

void part_1()
{
	cout << "Part-1: char array\n\n";

	char a1[] = "Testing    ";
	char a2[] = "Testing    ";
	test1(a1, a2);

	char b1[] = "   Test";
	char b2[] = "   Test";
	test1(b1, b2);

	char c1[] = "   Test  1   2 3  ";
	char c2[] = "   Test  1   2 3  ";
	test1(c1, c2);

	char d1[] = "  ";
	char d2[] = "  ";
	test1(d1, d2);

	char e1[] = "";
	char e2[] = "";
	test1(e1, e2);
}

char toHex(unsigned d)  
{
	// Precondition: 0 <= d <= 15

	if (d >= 10)
		return d - 10 + 'a';
	return d + '0';
}

void printIPv6address_hex(const unsigned char *v)
{
	cout << "IPv6 address (bin)  ";
	for (int i = 0; i < 16; i++)
	{
		cout << toHex(v[i] / 16) << toHex(v[i] % 16);
		if (i % 2 && i < 14)
			cout << ':';
	}
	cout << endl;
}

void test2(unsigned char *v)
{	
	cout << "-----------------------------------------------------------\n";
	printIPv6address_hex(v);
	char *a = bin2Text_IPv6(v);
	cout << "IPv6 address (text) " << a << endl << endl;	
	delete[] a;
}

void part_2()
{
	cout << "\n===========================================================\n";
	cout << "Part-2: IPv6 address\n\n";

	// An IPv6 address (in binary) is represented by an array of unsigned char of size 16.

	unsigned char v1[] = {0x12, 0x34, 0x0a, 0xbc, 0x00, 0x00, 0x00, 0x78, 0xff, 0x00, 0x00, 0x00, 0x00, 0x30, 0x00, 0x05};
	unsigned char v2[] = {0x20, 0x01, 0x0d, 0xb8, 0x00, 0xa3, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0x00, 0x00, 0x08};
	unsigned char v3[] = {0x20, 0x01, 0x0d, 0xb8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	unsigned char v4[] = {0x00, 0x00, 0x00, 0x00, 0x01, 0x01, 0x70, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x04};
	unsigned char v5[] = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01};
	unsigned char v6[] = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

	test2(v1);
	test2(v2);
	test2(v3);
	test2(v4);
	test2(v5);
	test2(v6);
}

int main()
{	
	part_1();
	part_2();

	system("pause");
	return 0;
}