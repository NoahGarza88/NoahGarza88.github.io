#include <iostream>  //Including iostream
#include <fstream>   //including fstream
#include <sstream>   //including sstream
#include <string>    //including string
#include <vector>    //including vector
#include <algorithm> // For transform and max

using namespace std;

const int BALANCED_TREE_CHECK = 0; // ENHANCEMENT - Created a constant variable for balancing

struct Node
{                                 // Create a struct of Node
    string courseId;              // String variable to hold the courseId
    string courseName;            // String variable to hold the courseName
    vector<string> prerequisites; // Vectorvariable to hold the prequisites
    Node *left;                   // Left node
    Node *right;                  // Right node
    int height;                   // ENHANCEMENT - Added height for AVL balancing

    Node()
    { // Empty constructor
        return;
    }
    Node(string id, string name, vector<string> prerequisites)
    { // Basic constructor for the node structure
        this->courseId = id;
        this->courseName = name;
        this->prerequisites = prerequisites;
        this->left = nullptr;
        this->right = nullptr;
        this->height = 1; // ENHANCEMENT - Initial height is 1
    }
};

// Function to get the height of the node
int getHeight(Node *node)
{
    return (node == nullptr) ? 0 : node->height;
}

// Function to get the balance factor of the node
int getBalanceFactor(Node *node)
{
    return (node == nullptr) ? 0 : getHeight(node->left) - getHeight(node->right);
}

// Function to perform a right rotation
Node *rightRotate(Node *y)
{
    Node *x = y->left;
    Node *T2 = x->right;

    // Perform rotation
    x->right = y;
    y->left = T2;

    // Update heights
    y->height = max(getHeight(y->left), getHeight(y->right)) + 1;
    x->height = max(getHeight(x->left), getHeight(x->right)) + 1;

    // Return new root
    return x;
}

// Function to perform a left rotation
Node *leftRotate(Node *x)
{
    Node *y = x->right;
    Node *T2 = y->left;

    // Perform rotation
    y->left = x;
    x->right = T2;

    // Update heights
    x->height = max(getHeight(x->left), getHeight(x->right)) + 1;
    y->height = max(getHeight(y->left), getHeight(y->right)) + 1;

    // Return new root
    return y;
}

// ENHANCED Function to insert the node into the AVL tree
Node *insertNode(Node *root, string courseId, string courseName, vector<string> prerequisites)
{
    if (root == nullptr)
    { // If the root is null, it will create a new node with the inputted parameters
        return new Node(courseId, courseName, prerequisites);
    }

    try
    {
        if (courseId.compare(root->courseId) < BALANCED_TREE_CHECK) // Replaced 0 with BALANCED_TREE_CHECK
        {
            // If it is lower than the courseId of the root node, it will go left
            root->left = insertNode(root->left, courseId, courseName, prerequisites);
        }
        else if (courseId.compare(root->courseId) > BALANCED_TREE_CHECK) // replaced 0 with BALANCED_TREE_CHECK
        {
            // If it is higher than the courseId of the root node, it will go right
            root->right = insertNode(root->right, courseId, courseName, prerequisites);
        }
    }
    catch (const exception &e)
    {
        cerr << "Error inserting node: " << e.what() << endl; // Added error handling
    }

    // Update height of this ancestor node
    root->height = 1 + max(getHeight(root->left), getHeight(root->right));

    // Get the balance factor of this node to check whether it is unbalanced
    int balanceFactor = getBalanceFactor(root);

    // If the node is unbalanced, then perform rotations

    // Left Left Case
    if (balanceFactor > 1 && courseId.compare(root->left->courseId) < BALANCED_TREE_CHECK)
        return rightRotate(root);

    // Right Right Case
    if (balanceFactor < -1 && courseId.compare(root->right->courseId) > BALANCED_TREE_CHECK)
        return leftRotate(root);

    // Left Right Case
    if (balanceFactor > 1 && courseId.compare(root->left->courseId) > BALANCED_TREE_CHECK)
    {
        root->left = leftRotate(root->left);
        return rightRotate(root);
    }

    // Right Left Case
    if (balanceFactor < -1 && courseId.compare(root->right->courseId) < BALANCED_TREE_CHECK)
    {
        root->right = rightRotate(root->right);
        return leftRotate(root);
    }

    return root;
}

// Function that adds the file into the binary tree (AVL tree)
void addFileToBinaryTree(const string filePath, Node *&root)
{
    ifstream file(filePath); // Opens the file for reading
    string line;             // Created a string variable called line to get each line of the file

    if (!file)
    {
        cerr << "Error opening file!" << endl; // Added error handling for file opening
        return;
    }

    while (getline(file, line))
    {                                                     // While loop for each line.
        stringstream ss(line);                            // Stringstream to read the line string as input and output
        string courseIdStr, courseName, prerequisitesStr; // Create 3 string variables to hold the courseId, name, and prerequisites

        getline(ss, courseIdStr, ','); // Gets the course Id
        getline(ss, courseName, ',');  // Gets the course Name
        getline(ss, prerequisitesStr); // Gets the prerequisites

        stringstream prerequisitesStream(prerequisitesStr); // Stringstream to take prerequisitesStr as input/output
        vector<string> prerequisites;                       // Creating a string vector called prerequisites
        string prerequisite;                                // Creating string variable called prerequisite

        while (getline(prerequisitesStream, prerequisite, ' '))
        { // While loop to put each prerequisite into a vector.
            prerequisites.push_back(prerequisite);
        }

        // Insert the data into the AVL tree
        root = insertNode(root, courseIdStr, courseName, prerequisites); // Calling insertNode function to add to AVL tree
    }

    file.close(); // close file
}

// Function to displaycourses in order
void displayInOrder(Node *root)
{
    if (root != nullptr)
    {                                                                // If root is not null
        displayInOrder(root->left);                                  // Recursive function to left
        cout << root->courseId << ",  " << root->courseName << endl; // Print out course Id and name
        displayInOrder(root->right);                                 // Recursive function to right
    }
}

// NEW FUNCTION: Pre-Order Traversal
void displayPreOrder(Node *root)
{
    if (root != nullptr)
    {
        cout << root->courseId << ",  " << root->courseName << endl; // Print out course Id and name
        displayPreOrder(root->left);
        displayPreOrder(root->right);
    }
}

// NEW FUNCTION: Post-Order Traversal
void displayPostOrder(Node *root)
{
    if (root != nullptr)
    {
        displayPostOrder(root->left);
        displayPostOrder(root->right);
        cout << root->courseId << ",  " << root->courseName << endl; // Print out course Id and name
    }
}

// Function to Convert both strings to lowercase for case-insensitive comparison
bool equalsIgnoreCase(const string str1, const string str2)
{
    string lowerStr1 = str1;
    string lowerStr2 = str2;
    transform(lowerStr1.begin(), lowerStr1.end(), lowerStr1.begin(), ::tolower);
    transform(lowerStr2.begin(), lowerStr2.end(), lowerStr2.begin(), ::tolower);

    return lowerStr1 == lowerStr2;
}

// Updated menu to include Pre-Order and Post-Order Traversals
void menu()
{
    cout << "1. Load Data Structure." << endl;
    cout << "2. Print Course List (In-Order)." << endl;
    cout << "3. Print Course List (Pre-Order)." << endl;
    cout << "4. Print Course List (Post-Order)." << endl;
    cout << "5. Exit" << endl
         << endl;
    cout << "What would you like to do? ";
}

// MAIN FUNCTION
int main()
{
    int userInput;                       // Creating a variable to take user input
    Node *root = nullptr;                // Initializing a root pointer to null
    string filePath = "course_List.csv"; // Creating a string called the file that is going to be extracted
    bool quit = true;                    // Initialize boolean called quit to true

    cout << "Welcome to the course planner." << endl
         << endl;

    while (quit)
    {
        menu();           // Calling menu function
        cin >> userInput; // Obtaining user input

        switch (userInput)
        {
        case 1:
            addFileToBinaryTree(filePath, root); // Calling function to add the file into the AVL tree
            break;

        case 2:
            cout << "Course List (In-Order):" << endl;
            displayInOrder(root); // Calling in-order function to print out the courses
            cout << endl;
            break;

        case 3:
            cout << "Course List (Pre-Order):" << endl;
            displayPreOrder(root); // Calling pre-order function to print out the courses
            cout << endl;
            break;

        case 4:
            cout << "Course List (Post-Order):" << endl;
            displayPostOrder(root); // Calling post-order function to print out the courses
            cout << endl;
            break;

        case 5: // Case 5, which ends the program and sends a thank you message to user
            cout << "Thank you for using the course planner!" << endl;
            quit = false;
            break;

        default:
            cout << "Invalid option! Please choose again." << endl;
        }
    }
}
