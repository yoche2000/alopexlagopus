#include<iostream>
#include<vector>
using namespace std;

// UVa11496 - Musical Loop
// Using Vector to store chars

int main() {

	int n;
	cin >> n;
	vector<int> v(n);
	for (int i = 0; i < n; i++) {
		int input;
		cin >> input;
		v[i] = input; //insert
		vector<int>::iterator it;
	}
	
	int cnt = 0;
	v.push_back(v[0]);
	v.push_back(v[1]);

	for (int i = 0; i < n; i++) {
		if ((v[i+1]-v[i])*(v[i + 2] - v[i + 1]) < 0){
			cnt++;
		}
	}
	cout << cnt;

}
