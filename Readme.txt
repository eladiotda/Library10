Library10
Overview
Library10 is a Java-based library management system designed to manage materials and user interactions with specific roles: Admin, Teacher, and Student. This project leverages concepts such as abstract classes, polymorphism, inheritance, and interfaces, fulfilling the requirements of "Software Construction I" for a final assignment.

Key Features
Admin Controls: Full access to manage users, materials, and loans, including adding or modifying entries.
Teacher Access: Permissions to view all student loans and loan materials for personal use.
Student Access: Basic access to view available materials and manage personal loans.
Loan Rules: Strict rules based on user roles, restricting the maximum number of loans and loan duration.
Data Storage: All data is stored in TXT files, with automatic file creation and data loading if no files exist.
Project Requirements
This project fulfills the following academic requirements:

Abstract Classes: Implements two abstract classes: Material and User, which are further extended by specific subclasses.
Polymorphism: Utilized in user actions such as loaning materials, where different user types handle loans according to their permissions.
Inheritance: Used in the User class hierarchy, where Admin, Teacher, and Student classes extend from User.
Interfaces: The Loanable interface is implemented to standardize loan operations across user types.
File Handling: Data is read from and written to TXT files, which store information on users, materials, and loan records.
Error Handling: Exception handling is applied throughout the program, particularly in file operations and user input validation.
Project Structure
Classes
The main classes include:

Material - Abstract class representing library resources with common attributes.
Book, Magazine (extends Material)
User - Abstract base class for library users.
Admin, Teacher, Student (extends User)
Loan - Manages loan details and restrictions for materials.
Menu - Organizes role-specific options and program navigation.
FileManager - Manages file input/output operations, including error handling.
Role-Based Permissions
Admin:

Full access to add, modify, and delete users and materials.
Can issue and return loans and is the only role that can exit the program.
Can see all user and material details.
Teacher:

Can view all materials and all student loans.
Can loan materials for personal use only.
Can view personal loan records.
Student:

Can view all available materials.
Can manage loans for personal use with limits on loan duration and quantity.
Loan Rules
Loan Limits:
Students: Up to 4 materials.
Teachers: Up to 10 materials.
Loan Duration:
Maximum of 8 days per loan. Overdue loans restrict further borrowing.
Installation and Usage
Prerequisites
Java 8 or higher.
NetBeans or any preferred IDE for Java development.
A text editor to view or modify TXT files directly.
Getting Started
Clone the Repository: Clone or download the project files.
Run the Program: Open the project in an IDE, compile, and run the main class.
TXT File Setup: The program generates necessary TXT files on the first run, populating them with initial data if they do not exist.
File Structure
Materials.txt: Stores material data.
Users.txt: Stores user information.
Loans.txt: Manages loan records with user ID and material codes.
Future Enhancements
The project is designed for extensibility, allowing new features to be added, such as additional material types, enhanced loan policies, and more user roles.

Authors
Eladio Lopez
Andres Gomez