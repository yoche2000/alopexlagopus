#include <iostream>
using namespace std;

void main() {

	int a[7] = { 1,2,3,4,5,6,5};
	int n = 7;

	int i, j;
	int inpt = 0;
	int flag = 0;
	int temp[20];

	while (i < n)
	{
		j = i;
		while (flag == 0)
		{
			int temp[20];
			for (int k = 0; k < inpt; k++)
			{
				if (a[j] == temp[k])
				{
					i += 5;
					flag = 1;
					cout << k;
					break;
				}
			}
			temp[inpt] = a[j];
			inpt++;
			j++;
		}
	}
}
