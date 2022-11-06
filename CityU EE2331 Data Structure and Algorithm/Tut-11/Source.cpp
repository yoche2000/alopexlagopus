// Student name: Lee Yo-Che
// Student ID  : 55348947
//
// The program will get stuck with error message 
// "The program '[22936] tutorial11.exe' has exited with code -1073741510 (0xc000013a)."
// Along with several "Cannot find or open the PDB file." messages

/*
	Submission deadline: Friday, 24 April 2020, 12 noon

	Put down your name and student ID at the top of your program file.
	10% marks will be deducted if your program file does not contain your name and ID.

	Submitted program that cannot be compiled (with syntax errors) by Visual Studio
	will receive no more than 30% marks.

	If your program crashes when executed with Visual Studio, you should state the
	problem at the top of your program file (right below student name and ID); otherwise
	significant percentage (30% or more) of marks will be deducted.

	Late submissions or submissions by other means are not accepted.
*/

#include <cstdlib>
#include <iostream>
#include <iomanip>

using namespace std;

// ------------------ Generic/Template functions for basic binary tree operations
template<class Type>
struct treeNode
{
	Type info;
	treeNode *left, *right;
};

template<class Type>
void printTree(const treeNode<Type> *p, int indent, int inc)
{
	if (p != nullptr)
	{
		printTree(p->right, indent + inc, inc);
		cout << setw(indent) << p->info << endl;
		printTree(p->left, indent + inc, inc);
	}
}

template<class Type>
void deleteTree(treeNode<Type> *p)
{
	// Delete all nodes in the tree referenced by pointer p.

	if (p != nullptr)
	{
		deleteTree(p->left);
		deleteTree(p->right);
		delete p;
	}
}

// --------------------------------- class dataSet
struct dataRec
{
	int value;   // data value
	int weight;  // number of nodes in the subtree
};

ostream& operator<<(ostream& osObject, const dataRec& r)
{
	osObject << r.value << "(" << r.weight << ")";
	return osObject;
}

class dataSet
{
	// Elements (integers) of the data set are organized in a binary search tree.
	// We also maintain the weight of each node in the BST.
	// The weight of a node is equal to the number of nodes in the subtree.
	// DO NOT modify the function interface given to you.

private:
	treeNode<dataRec> *tree;  // pointer to the root of the BST
	// You may define other (private) supporting functions, where appropriate.

public:
	void print()  // This function is implemented for you.
	{
		cout << "Data set (organized as binary search tree) :\n\n";
		printTree(tree, 5, 7);
		cout << endl;
	}

	dataSet()  // default constructor for empty data set
	{
		tree = nullptr;
	}

	~dataSet()  // destructor
	{
		deleteTree(tree);
	}
	// ----  3 Member functions to be implemented by student

	dataSet(const int* preorder, const int* ranking, int n);  // constructor
	int getByRank(int rank);
	bool isHeightBalanced();
	void addnode(treeNode<dataRec>*& root, int val);
	bool treeisbalanced(const treeNode<dataRec> *tree);
	int geth(const treeNode<dataRec> *tree);

	// ---------------------------------------------------
};
// ----------- end of class declaration

// ***  Put your functions here  ***

dataSet::dataSet(const int* preorder, const int* ranking, int n)
{
	//int preorder_1[] = { 62, 38, 23, 18, 46, 53, 59, 85, 70, 78, 94, 89 };
	//int ranking_1[] = { 7,  3,  2,  1,  4,  5,  6, 10,  8,  9, 12, 11 };
	// preorder[] is the preorder traversal sequence of the BST.
	// ranking[i] is the rank of the element preorder[i] in the data set.

	tree = nullptr;
	for (int i = 0; i < n; i++)
	{
		treeNode<dataRec> *q, *p;
		q = nullptr;
		p = tree;
		while (p != nullptr)
		{
			q = p;
			if (preorder[i] < p->info.value)
				p = p->left;
			else if (preorder[i] <= p->info.value)
				p = p->right;
			q->info.weight += 1;
		}
		if (p == tree)
		{
			tree = new treeNode<dataRec>;
			tree -> info.value = preorder[i];
			tree -> info.weight = 1;
			tree -> left = nullptr;
			tree -> right = nullptr;
			cout << "Rootnode: " << q << " -> " << tree->info.value << endl;
		}
		else
		{
			treeNode<dataRec>* v = new treeNode<dataRec>;
			v->info.value = preorder[i];
			v->info.weight = 1;
			v->left = nullptr;
			v->right = nullptr;
			p = v;
			cout << "Newnode: " << q << " -> " << p->info.value << endl;
		}
	}
	// Construct the BST, and determine the weight of each node.
	// Do NOTcreate other array or container in your design.
	// You may define another recusive function to build the tree.
	// *** Your codes.
}

void dataSet::addnode(treeNode<dataRec>*& tree, int val)
{
	treeNode<dataRec> *q, *p;
	p = tree;
	while (p != nullptr)
	{
		q = p;
		if (val < p->info.value)
			p = p->left;
		else
			p = p->right;
		q->info.weight += 1;
	}
	
	treeNode<dataRec> *v = new treeNode<dataRec>;
	v->info.value = val;
	v->info.weight = 1;
	v->left = nullptr;
	v->right = nullptr;
	p = v;
}


int dataSet::getByRank(int rank)
{
	if (tree == nullptr)
		return 0;

	int rnk = 0;
	return rnk;
}

bool dataSet::isHeightBalanced()
{
	return treeisbalanced(tree);
}

bool dataSet::treeisbalanced(const treeNode<dataRec> *tree)
{
	if (tree == nullptr)
		return true;
	int lefth = geth(tree->left);
	int righth = geth(tree->right);
	bool children_balance = (lefth == righth);
	if (!children_balance)
		return false;
	else
		return (treeisbalanced(tree->left) && treeisbalanced(tree->right));
}

int dataSet::geth(const treeNode<dataRec> *tree)									//Get the height of the tree
{
	if (tree == nullptr)
		return 0;
	if ((tree->left == nullptr) && (tree->right == nullptr))
		return 0;
	int lefth = geth(tree->left);
	int righth = geth(tree->right);

	if (lefth > righth)
		return lefth + 1;
	else
		return righth + 1;
}

// ------------------------------ Test and driver functions

void test(const int* preorder, const int* ranking, int n)
{
	dataSet s1(preorder, ranking, n);
	s1.print();

	cout << endl;

	//for (int i = 3; i <= n; i += 3)
	//	cout << "Element at rank " << i << " = " << s1.getByRank(i) << endl;

	//cout << endl;

	if (s1.isHeightBalanced())
		cout << "BST of data set is height-balanced\n";
	else
		cout << "BST of data set is not height-balanced\n";

	cout << "----------------------------------------------\n\n";
}

int main()
{
	// rank of 18 (smallest element) is 1 
	// rank of 23 is 2, and so on
	int preorder_1[] = { 62, 38, 23, 18, 46, 53, 59, 85, 70, 78, 94, 89 };
	int ranking_1[] = { 7,  3,  2,  1,  4,  5,  6, 10,  8,  9, 12, 11 };
	int n1 = 12;
	test(preorder_1, ranking_1, n1);


	int preorder_2[] = { 62, 38, 23, 30, 46, 41, 85, 70, 94, 89 };
	int ranking_2[] = { 6,  3,  1,  2,  5,  4,  8,  7, 10,  9 };
	int n2 = 10;
	test(preorder_2, ranking_2, n2);

	system("pause");
	exit(0);
}