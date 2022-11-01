//Student name: Lee Yo-Che
//Student ID  : 55348947

/*
	Submission deadline: Friday, 10 April 2020, 12 noon.

	This tutorial is on binary tree.
	Implement the functions buildTree(), getMin(), getAvg(), getMaxLevelCount().
	
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
#include <cstdlib>
#include <queue>
#include <stack>

using namespace std;

template<class Type>
struct treeNode
{
	Type info;
	treeNode<Type> *left, *right;
};

// ------------------------ Functions to be implemented by student

treeNode<int>* buildTree(const int *inorder, const int *postorder, int n)
{
	treeNode<int>* node = new treeNode<int>;							//great a node
	node->info = postorder[n - 1];										//Set the node info
	//cout << "Set node info to " << postorder[n - 1] << endl;			

	int indexinin = 0;													//Indexinin refers to Index-in-inorder
	for (int i = 0; i < n; i++)
		if (inorder[i] == postorder[n - 1])
			indexinin = i;												//Get Index-in-inorder
	//cout << "Found postorder[n - 1] in inorder index " << indexinin<< endl;

	if (n>1)
	{
		/*
		cout << "Create left tree with parameters: " << inorder[0] << "/" << postorder[0] << "/" << indexinin<< endl;
		if (n - indexinin - 1 > 0)
			cout << "Create right tree with parameters: " << inorder[indexinin + 1] << "/" << postorder[indexinin] << "/" << n - indexinin - 1 << endl;
		else
			cout << "Create left tree with parameters: nullptr" << endl;
		cout << "===" << endl << endl;
		*/

		node->left = buildTree(inorder, postorder, indexinin);												//Build left, one branch
		if (n - indexinin - 1 > 0)
			node->right = buildTree(inorder + indexinin + 1, postorder + indexinin, n - indexinin - 1);		//Of there is another branch, Build right branch
		else
			node->right = nullptr;																			//If not build nothing
	}
	else
	{
		//cout << "Create left tree with parameters: nullptr" << endl;
		//cout << "Create right tree with parameters: nullptr" << endl;
		//cout << "===" << endl << endl;
		node->left = nullptr;		//Case where no branch exists
		node->right = nullptr;		//Set both to nullptr
	}
	return node;
}

int getMin(const treeNode<int> *tree)
{
	if (tree == nullptr)
		return 2147483647;			//Return a huge number so the other value will be chosen returing to the previous level, case of nullptr
	if (tree->left == nullptr || tree->right == nullptr)
		return tree->info;			//Leaf nodes, returns
	else {
		int min_l = getMin(tree->left);
		int min_r = getMin(tree->right);
		//cout << "At node " << tree->info << " Left min = " << min_l << " Left right = "<< min_r <<endl;
		if ((tree->info < min_l) && (tree->info < min_r))			//Compare the minimum of the left, that of the right, and itself
			return tree->info;										//And return the smallest one
		else if (min_l < min_r)			
			return min_l;
		else
			return min_r;
	}
}

int nodecount(const treeNode<int> *tree)							//Number of nodes in the tree
{
	if (tree == nullptr)
		return 0;
	return 1 + nodecount(tree->left) + nodecount(tree->right);
}

int nodesum(const treeNode<int> *tree)								//Sum of the value of the nodes
{
	if (tree == nullptr)
		return 0;
	return tree->info + nodesum(tree->left) + nodesum(tree->right);
}

double getAvg(const treeNode<int> *tree)
{
	if (tree == nullptr)
		return 0;
	else
	{
		int node_cnt = nodecount(tree);								//Get the number of nodes
		int node_sum = nodesum(tree);								//Get sum
		
		//double result = (double)node_sum / (double)node_cnt;
		//cout << "Got number of nodes:" << node_cnt << endl;
		//cout << "Got sum of the nodes:" << node_sum << endl;
		//cout << result << endl;

		return (double)node_sum / (double)node_cnt;					//return the average
	}
}

int geth(const treeNode<int> *tree)									//Get the height of the tree
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

void cal(const treeNode<int> *tree, int *list, int n)			//Tree, the binary tree | List, the checklist array | n, the level of the node
{																//For every level in the tree, set a counter
	if (tree!=nullptr)											//increment the value of that level when a node is read
	{
		//cout << "element " << tree->info << " at level " << n << endl;
		list[n]++;
		cal(tree->left, list, n + 1);
		cal(tree->right, list, n + 1);
	}
}

pair<int, int> getMaxLevelCount(const treeNode<int> *tree)
{
	// struct pair<T1, T2> is defined in C++, http://www.cplusplus.com/reference/utility/pair/
	/*
	template<class T1, class T2>
	struct pair 
	{
		T1 first;
		T2 second;
	};
	*/
	int height = geth(tree);
	int checklist[100];
	for (int i = 0; i <= height; i++)					//Set the checklist with all values 0
		checklist[i] = 0;
	cal(tree, checklist, 0);
	//cout << "checklist : ";
	//cout << endl;

	int maxind = 0;										//Index of the max value
	for (int i = 0; i < height; i++)
		if (checklist[i] > checklist[maxind])
			maxind = i;


	pair <int, int> ans;
	ans.first = maxind;
	ans.second = checklist[maxind];
	return ans;

	//Return pair<int, int> ans
	// ans.first = level number, ans.second = node count on the level

	// Root is on level 0.

}


// ----------------------------------- functions given to you

template <class Type>
void printTree(const treeNode<Type> *p, int indent)
{
	if (p != NULL)
	{
		printTree(p->right, indent + 4);
		cout << setw(indent) << p->info << endl;
		printTree(p->left, indent + 4);
	}	
}

int main()
{	
	// Inorder and postorder traversal sequences.
	int inorder[] = {63, 55, 20, 89, 50, 16, 41, 70, 76, 35, 85};
	int postorder[] = {63, 20, 89, 55, 16, 50, 70, 76, 85, 35, 41};
	int n = 11;

	
	treeNode<int> *tree = buildTree(inorder, postorder, n); 
	cout << "Binary tree :\n\n";
	printTree(tree, 4);
	cout << endl;
	
	cout << "Min value in the binary tree = " << getMin(tree) << endl << endl;
	
	cout << "Average value of numbers in the binary tree = " << getAvg(tree) << endl << endl;
	
	pair<int, int> LC = getMaxLevelCount(tree);
	cout << "Level with largest number of nodes : level = " << LC.first << ", node count = " << LC.second << endl << endl;
	

	system("pause");
	exit(0);
}