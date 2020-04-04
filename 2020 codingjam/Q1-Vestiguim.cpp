#include <iostream>
using namespace std;


int main() {
	int casecnt;
	int matrix[100][100];
	cin >> casecnt;
	for (int i = 0; i < casecnt; i++)
	{
		int n;
		cin >> n; 
		for (int j = 0; j < n; j++)
		{
			for (int k = 0; k < n; k++)
				cin >> matrix[j][k];
		}

		int sum = 0;
		for (int m = 0; m < n; m++)
			sum+=matrix[m][m];

		int rows = n;
		bool check[101];
		for (int z = 0; z < n+1; z++)
			check[z] = false;
		int tmp;
		for (int j = 0; j < n; j++)
		{
			for (int z = 0; z <= n+1; z++)
				check[z] = false;

			for (int k = 0; k < n; k++)
			{
				tmp = matrix[j][k];
				if (check[tmp])
				{
					rows--;
					//cout << "found duplicate " << matrix[k][j] << "at " << j << k << endl;
					break;
				}
				else
					check[tmp] = true;
			}
		}

		int columns = n;
		for (int j = 0; j < n; j++)
		{
			for (int z = 0; z < n+1; z++)
				check[z] = false;

			for (int k = 0; k < n; k++)
			{
				tmp = matrix[k][j];
				if (check[tmp])
				{
					columns--;
					//cout << "found duplicate " << matrix[k][j] << "at " << k<< j <<endl;
					break;
				}
				else
					check[tmp] = true;
			}
			
		}
		cout << "Case #" << i+1 << ": " << sum << " " << n-rows << " " << n-columns << endl;
	}


	return 0;
}
