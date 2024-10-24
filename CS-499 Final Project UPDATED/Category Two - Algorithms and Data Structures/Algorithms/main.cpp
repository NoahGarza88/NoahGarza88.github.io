#include <iostream>     //Including iostream
#include <fstream>      //including fstream
#include <sstream>      //including sstream
#include <string>       //including string
#include <vector>       //including vector

using namespace std;    
struct Node {   //Create a struct of Node
    string courseId;        //String variable to hold the courseId
    string courseName;       //String variable to hold the courseName
    vector<string> prerequisites;   //Vectorvariable to hold the prequisites
    Node* left;     //Left node
    Node* right;    //Right node

    Node(){ //Empty constructor
        return;
    }
    Node(string id, string name, vector<string> prerequisites){ //Basic constructor for the node structure
        this->courseId = id;
        this->courseName = name;
        this->prerequisites = prerequisites;
        this->left = nullptr;
        this->right = nullptr;
    }
};


//Function to insert the node into the binary tree
Node* insertNode(Node* root, string courseId,  string courseName,  vector<string> prerequisites) {      
    if (root == nullptr) {  //If the root is null, it will create a node node with the the inputed parameters
        return new Node(courseId, courseName, prerequisites);
    } else if (courseId.compare(root->courseId)<0) {    //IF it is lower the the courseId of the root node it will go left
        root->left = insertNode(root->left, courseId, courseName, prerequisites);
    } else if (courseId.compare(root->courseId) > 0) {       //IF it is higher the the courseId of the root node it will go right
        root->right = insertNode(root->right, courseId, courseName, prerequisites);
    }
    return root;
}

//Functiln to add the file into the binary tree data structure
void addFileToBinaryTree(const string filePath, Node*& root) {  
    ifstream file(filePath);        //Opens the file for reading
    string line;    //Created a string variable called line to get each line of the file

    while (getline(file, line)) {   //While loop for each line.
        stringstream ss(line);  //Stringstream to read the line string as input and output
        string courseIdStr, courseName, prerequisitesStr;       //Create 3 string variables to hold the courseId, name and prerequisites

        getline(ss, courseIdStr, ',');  //Gets the course Id
        getline(ss, courseName, ',');   //Gets the course Name
        getline(ss, prerequisitesStr);  //Gets the preqequisites

        stringstream prerequisitesStream(prerequisitesStr); //Stringstream to take prerequisitesStr as input or output
        vector<string> prerequisites;   //Crreating a string vector called prerequisistes
        string prerequisite;    //Creating string variable called preqrequisite

        while (getline(prerequisitesStream, prerequisite, ' ')) {   //While loop to put each prerequisite into a vector.
            prerequisites.push_back(prerequisite);
        }

        root = insertNode(root, courseIdStr, courseName, prerequisites);    //Calling insertNode function for the root node
    }

    file.close(); //close file
}



//Function that displays courses in order
void displayInOrder(Node* root) {    
    if (root != nullptr) {  //If root is not null
        displayInOrder(root->left); //Recursive function to left
        cout << root->courseId << ",  " << root->courseName << endl;    //Print out course Id and name
        displayInOrder(root->right);        //Recursive function to right
    }
}


// Function to Convert both strings to lowercase for case-insensitive comparison
bool equalsIgnoreCase(const string str1, const string str2) { 
    string lowerStr1 = str1;
    string lowerStr2 = str2;
    transform(lowerStr1.begin(), lowerStr1.end(), lowerStr1.begin(), ::tolower);
    transform(lowerStr2.begin(), lowerStr2.end(), lowerStr2.begin(), ::tolower);

    return lowerStr1 == lowerStr2;
}








void menu(){        //Function that displays the menu
    cout << "1. Load Data Structure." << endl;
    cout << "2. Print Course List." <<endl;
    cout << "4. Exit" << endl << endl;
    cout << "What would you like to do? ";
}



//MAIN FUNCTION
int main() {
    int userInput;  //Creating a variable to take user input
    string courseIdent; //Creating variable for option 3
    Node* root = nullptr;       //Initializing a root pointer to null
    Node* course; //Initializing course as a Pointer
    string filePath = "course_List.csv";    //Creating a string called the file that is going to be extracted
    bool quit = true;  //Initialize boolean called quit to true
    vector<string>id;

    cout << "Welcome to the course planner." << endl << endl;

    while(quit){    //While loop for the whole program
        int count = 0;   //creating a int variable called count, used for user input validation on switch 3.
        menu(); //Calling menu function
        cin >> userInput;   //Obtaining user input

        while(userInput != 1 && userInput != 2 && userInput != 3 && userInput!=4 || isalpha(userInput)){    //While loop for user input validity
            cin.clear();    //Clear userInput
            cin.ignore(123, '\n');      //Ignore any input tunwanted input
            cout << "That is not a valid option." << endl;     //Inform user of invalid entry
            cin >> userInput;       //Getting user input again
        }

        switch(userInput){
            case 1: 
                addFileToBinaryTree(filePath, root);    //Calling function to add the file into the data structure, or LOAD it.
                break;

            case 2:
                cout << "Here is a sample schedule." << endl << endl;   //Creating a space for readability.
                displayInOrder(root);  //Calling function to print out the course in order
                cout << endl;
                break;
            case 4: //Case 4, which ends the program and sends a thank you message to user
                cout << "Thank you for using the course planner!" << endl;
                quit = false;
                break;
        }
        cin.clear();    //Clear the input for any cin
        cin.ignore(123, '\n');      //Ignore any input tunwanted input

    }

    return 0;
}
