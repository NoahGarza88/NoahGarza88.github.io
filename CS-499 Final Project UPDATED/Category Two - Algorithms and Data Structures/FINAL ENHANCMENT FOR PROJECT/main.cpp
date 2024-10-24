// Noah Lane Garza
// 10/23/2024
// CS-499 Computer Science Capstone
// Implemeneted AVL balancing logic and testing based on Feedback, Updated menu options as well

#include <iostream>  // Including iostream
#include <fstream>   // Including fstream
#include <sstream>   // Including sstream
#include <string>    // Including string
#include <vector>    // Including vector
#include <algorithm> // For transform and max

using namespace std;

const int BALANCED_TREE_CHECK = 0; // ENHANCEMENT - Created a constant variable for balancing

struct Node
{                                 // Create a struct of Node
    string courseId;              // String variable to hold the courseId
    string courseName;            // String variable to hold the courseName
    vector<string> prerequisites; // Vector variable to hold the prerequisites
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

// Function to display courses in order
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

//----------FINAL ENHANCEMENTS ADDED BASED ON FEEDBACK-----------------------------
// Function to test AVL balancing after sorted insertions
void testAVLBalancing(Node *&root)
{
    vector<string> sortedCourses = {"C101", "C102", "C103", "C104", "C105"}; // Example sorted course IDs
    for (const string &courseId : sortedCourses)
    {
        root = insertNode(root, courseId, "Course " + courseId, {}); // Insert in sorted order
    }

    cout << "Course List After Sorted Insertions (In-Order):" << endl;
    displayInOrder(root); // Displaying the tree to verify balancing
}

//----------FINAL ENHANCEMENTS ADDED BASED ON FEEDBACK-----------------------------
// Function to display the balance factors of the nodes
void displayBalanceFactors(Node *root)
{
    if (root != nullptr)
    {
        cout << "Balance Factor of " << root->courseId << ": " << getBalanceFactor(root) << endl;
        displayBalanceFactors(root->left);
        displayBalanceFactors(root->right);
    }
}

// Menu function for user interaction ADDED two new options for the AVL balancing Logic!!!
void displayMenu()
{
    cout << "Menu:" << endl;
    cout << "1. Load courses from file" << endl;
    cout << "2. Display courses (In-Order)" << endl;
    cout << "3. Display courses (Pre-Order)" << endl;
    cout << "4. Display courses (Post-Order)" << endl;
    cout << "5. Test AVL Balancing Logic" << endl; // Option for AVL balancing logic testing
    cout << "6. Display Balance Factors" << endl;  // Option for displaying balance factors
    cout << "7. Exit" << endl;
}

int main()
{
    Node *root = nullptr; // Pointer to the root of the AVL tree
    int choice;

    while (true)
    {
        displayMenu(); // Show menu options
        cin >> choice; // Take user input for choice

        switch (choice)
        {
        case 1:
            addFileToBinaryTree("courses.txt", root); // Add file to AVL tree
            break;
        case 2:
            displayInOrder(root); // Display in order
            break;
        case 3:
            displayPreOrder(root); // Display pre-order
            break;
        case 4:
            displayPostOrder(root); // Display post-order
            break;
        case 5:                     // FINAL ENHANCEMENTS ADDED BASED ON FEEDBACK
            testAVLBalancing(root); // Test AVL balancing logic
            break;
        case 6:                          // FINAL ENHANCEMENTS ADDED BASED ON FEEDBACK
            displayBalanceFactors(root); // Display balance factors
            break;
        case 7:
            cout << "Exiting program..." << endl;
            return 0; // Exit the program
        default:
            cout << "Invalid choice. Please try again." << endl; // Error handling for invalid choice
            break;
        }
    }

    return 0; // Return 0 to indicate successful execution
}
