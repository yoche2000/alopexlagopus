#include <iostream>
#include <cctype>
#include <cstdlib>
#include <string>
using namespace std;

//UVa1586 – Molar mass

inline int C(char a) {
	if (a == '1') { return 1; }
	if (a == '2') { return 2; }
	if (a == '3') { return 3; }
	if (a == '4') { return 4; }
	if (a == '5') { return 5; }
	if (a == '6') { return 6; }
	if (a == '7') { return 7; }
	if (a == '8') { return 8; }
	if (a == '9') { return 9; }
	if (a == '0') { return 0; }
	return 0;
};



int main(){
	cout << "input times";
	int num;
	cin >> num;
	int k = 0;
	double molcount = 0;
	while (k < num + 1){
		string mol;
		getline(cin, mol);
		molcount = 0;
		for (unsigned int j = 0; j < mol.size(); j++){
			if (isdigit(mol[j]) == 0){
				if (isdigit(mol[j + 1]) && isdigit(mol[j + 2])) {
					if (mol[j] == 'C') {
						molcount = molcount + ((10 * C(mol[j + 1]) + C(mol[j + 2])) * 12.010);
					}
					else if (mol[j] == 'H') {
						molcount = molcount + ((10 * C(mol[j + 1]) + C(mol[j + 2])) * 1.008);
					}
					else if (mol[j] == 'O') {
						molcount = molcount + ((10 * C(mol[j + 1]) + C(mol[j + 2])) * 16);
					}
					else if (mol[j] == 'N') {
						molcount = molcount + ((10 * C(mol[j + 1]) + C(mol[j + 2])) * 14.01);
					}
					j++;
					j++;
				}
				else if (isdigit(mol[j + 1])) {
					if (mol[j] == 'C') {
						molcount = molcount + (C(mol[j + 1]) * 12.010);
					}
					else if (mol[j] == 'H') {
						molcount = molcount + (C(mol[j + 1]) * 1.008);
					}
					else if (mol[j] == 'O') {
						molcount = molcount + (C(mol[j + 1]) * 16);
					}
					else if (mol[j] == 'N') {
						molcount = molcount + (C(mol[j + 1]) * 14.01);
					}
					j++;
				}else {
					if (mol[j] == 'C') {
						molcount = molcount + 12.010;
					}
					else if (mol[j] == 'H') {
						molcount = molcount + 1.008;
					}
					else if (mol[j] == 'O') {
						molcount = molcount + 16;
					}
					else if (mol[j] == 'N') {
						molcount = molcount + 14.01;
					}
				}
			}
		}
		if (k!=0){
			cout << molcount << endl;
		}
		k++;
	}
}
