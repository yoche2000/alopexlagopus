#include <iostream>
#include <string>
using namespace std;

int main() {
	int cnt;
	cin >> cnt;
	for (int k = 0; k < cnt; k++)
	{
		string tmp;
		string result="";
		cin >> tmp;
		int len = tmp.length();
		
		for (int i = 0; i < (int)tmp[0] - 48; i++)
			result += '(';
		result += tmp[0];
		for (int i = 0; i < len-1; i++)
		{
			if (tmp[i+1]>tmp[i])
			{
				for (int j = 0; j < tmp[i+1]-tmp[i]; j++)
					result += '(';
			}
			else if (tmp[i + 1] < tmp[i])
			{
				for (int j = 0; j < tmp[i]-tmp[i+1]; j++)
					result += ')';
			}
			result += tmp[i+1];
		}
		for (int i = 0; i < (int)tmp[len-1] - 48; i++)
			result += ')';
		cout << "Case #" << 1 + k << ": " << result << endl;
	}
	return 0;
}
