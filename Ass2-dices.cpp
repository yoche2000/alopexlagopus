#include<iostream>
#include<iomanip>
using namespace std;

int main() {

	/*
	int nu_dice;
	cout << "Input the nubmer of dice(s) :";
	cin >> nu_dice;
	int faces[50];
	int prob[50];
	for (int i = 0; i < nu_dice; i++){
		if (i==0){
			cout << "Input the number of faces of the " << i + 1 << "st dices";
		}else if (i == 1) {
			cout << "Input the number of faces of the " << i + 1 << "nd dices";
		}else if (i == 2) {
			cout << "Input the number of faces of the " << i + 1 << "rd dices";
		}else{
			cout << "Input the number of faces of the " << i + 1 << "th dices";
		}
		cin >> faces[i];
	}
	int min = nu_dice;
	int max = 0;
	int denom = 1;
	for (int i = 0; i < nu_dice; i++) {
		max += faces[i];
		denom *= faces[i];
	}
	const int length = denom;
	// min is the number of dices, whose min_val are all 1
	// max is the sum of the largest value of different dices
	int pre[40] = {0};
	int pre2[40] = {0};
	int sum = 0;
	for (int i = 1; i <= faces[0]; i++) {
		pre[i]++;
	}
	sum += faces[0];
	for (int j = 1; j < faces[1]; j++){
		for (int k = 0; k < sum; k++)
		{
			pre2[j + k] += pre[k];
		}
	}
	sum += faces[1];
	for (int i = 1; i <= sum; i++) {
		pre2[i] += pre[i];
	}
	for (int i = min; i <= max; i++) {
		int numerator;
		cout << "Probabilit of " << i << "= " << numerator << "/" << denom;
	}
	*/

	int dice_n;
	int dice[5000];
	long long int dice_v1[5000] = { 0 };
	long long int dice_v2[5000] = { 0 };
	long long int M = 1;
	int S = 0;
	int a;
	int t = 1000;
	double tmp;
	int cnt = 0;

	cout << "Input the number of dice(s): ";
	cin >> dice_n;

	for (int i = 0; i < dice_n; i++) {
	Start:
		if (i > 9 && i < 19) {
			cout << "Input the number of faces for the " << i + 1 << "th dice: ";
		}
		else if (i % 10 == 0) {
			cout << "Input the number of faces for the " << i + 1 << "st dice: ";
		}
		else if (i % 10 == 1) {
			cout << "Input the number of faces for the " << i + 1 << "nd dice: ";
		}
		else if (i % 10 == 2) {
			cout << "Input the number of faces for the " << i + 1 << "rd dice: ";
		}
		else {
			cout << "Input the number of faces for the " << i + 1 << "th dice: ";
		}
		cin >> tmp;
		if ((tmp > 12) || (tmp < 1) || (int(tmp) != tmp)) {
			cout << "Error: input value should be integer in range 1..12" << endl;
			goto Start;
		}
		dice[i] = tmp;
		M *= dice[i];
	}

	S = dice[0];
	if (dice_n == 1) {
		for (int i = 0; i < dice[0]; i++) {
			dice_v1[i] = 1;
			cnt = i;
		}
	}
	else {
		for (int i = 0; i < dice_n - 1; i++) {
			for (int n = 0; n <= cnt; n++) { dice_v1[n] = 0; }
			for (int j = 0; j < S - i; j++) {
				for (int k = 0; k < dice[i + 1]; k++) {
					if (i == 0) { dice_v1[j + k]++; }
					else { dice_v1[j + k] += dice_v2[j]; }
					cnt = j + k;
				}
			}
			S += dice[i + 1];
			for (int m = 0; m <= cnt; m++) {
				dice_v2[m] = dice_v1[m];
				// cout << dice_v2[m] << endl;
			}
		}
	}
	if (dice_n == 1) {
		dice_v2[1000] = { 1 };
	}

	int num[1000];
	int hold1 = 0;
	int hold2 = -1;

	int res_1[1000];
	int res_2[1000];

	for (int m = 0; m <= cnt; m++) {
		num[m] = m + dice_n;
	}

	/*
	for (int m = 0; m <= cnt; m++) {
		if (dice_n == 1) {
			res_2[m] = 1;
		}
		if (m + dice_n < 10) {
			if (cnt + dice_n <= 10) {
				cout << "Probability of " << num[m] << " = " << dice_v2[m] << "/" << M << endl;
			}
			else {
				cout << "Probability of  " << num[m] << " = " << dice_v2[m] << "/" << M << endl;
			}
		}
		else {
			cout << "Probability of " << num[m] << " = " << dice_v2[m] << "/" << M << endl;
		}
	}
	*/

	for (int d = 0; d <= cnt; d++) {
		int hold1 = 0;
		int hold2 = 0;
		for (int m = 0; m <= cnt; m++) {
			if (dice_v2[m] > hold2) {
				hold2 = dice_v2[m];
				hold1 = num[m];
			}
		}
		res_1[d] = hold1;
		res_2[d] = hold2;
		dice_v2[hold1 - dice_n] = 0;
	}


	int mm[1000];
	for (int d = 0; d <= cnt; d++) {
		int prime = 0;
		int m = M;
		while (prime != 1)
		{
			if (res_2[d] % 2 == 0 && m % 2 == 0) {
				res_2[d] /= 2;
				m /= 2;
			}
			else if (res_2[d] % 3 == 0 && m % 3 == 0) {
				res_2[d] /= 3;
				m /= 3;
			}
			else if (res_2[d] % 5 == 0 && m % 5 == 0) {
				res_2[d] /= 5;
				m /= 5;
			}
			else if (res_2[d] % 7 == 0 && m % 7 == 0) {
				res_2[d] /= 7;
				m /= 7;
			}
			else if (res_2[d] % 11 == 0 && m % 11 == 0) {
				res_2[d] /= 11;
				m /= 11;
			}
			else {
				mm[d] = m;
				prime = 1;
			}
		}
	}


	if (dice_n == 1) {
		for (int k = 0; k <= cnt; k++){
			cout << "Probability of " << k+1 << " = " << 1 << "/" << M << endl;
		}
	}else{
		for (int m = 0; m <= cnt; m++) {

			if (res_1[m] < 10) {
				if (cnt + dice_n <= 10) {
					cout << "Probability of " << res_1[m] << " = " << res_2[m] << "/" << mm[m] << endl;
				}
				else {
					cout << "Probability of  " << res_1[m] << " = " << res_2[m] << "/" << mm[m] << endl;
				}
			}
			else {
				cout << "Probability of " << res_1[m] << " = " << res_2[m] << "/" << mm[m] << endl;
			}
		}
	}
}
