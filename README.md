#Team Member
Jiacheng Wu （715867）  
Jiaxin Li （874025）  
#Overview
The document is to demonstrate the software architecture of the Enrollment System, which aims to provide subject enrollment service for students doing different degrees at a university. The two main kind of users are administrators and students. Administrators are responsible for managing degrees, courses and users. The students can enroll and withdraw from courses, as well as modifying their personal information.
#Test Scenarios
##Feature A: Admin
###Login
Please use this account:  
Username: admin  
Password: admin
###Degree Management
####View degree list
1.	Click the “Degree List” in the side bar
2.	The main area will show a table, including all degrees and their basic information
####View degree’s detail information
1.	Click the “Degree List” in the side bar
2.	Click the degree that you want to get detail information
3.	The main area will show selected degree’s detail information, including degree name, degree description and the subjects in this degree
####Create a degree
1.	Click the “Degree List” in the side bar
2.	Click “Add” button
3.	There will be a pop-up window, including an editable form
4.	Fill the degree information in the form
5.	Click “Add” button
####Update degree’s detail information
1.	Click the “Degree List” in the side bar
2.	Click the degree that needs to be updated
3.	The main area will show selected degree’s detail information, including degree name, degree description and the subjects in this degree
4.	Click “Update” button
5.	The form will become editable
6.	Edit the information in the form
7.	Click “Save” button
####Add subjects into a degree
1.	Click the “Degree List” in the side bar
2.	Click the degree that you want to add subjects into
3.	The main area will show selected degree’s detail information, including degree name, degree description and the subjects in this degree
4.	Click “Add” button above the subjects table
5.	There will be a pop-up window that shows the subjects not in this degree
6.	Tick the subjects that need to be added
7.	Click “Add” button
####Delete subjects in a degree
1.	Click the “Degree List” in the side bar
2.	Click the degree that needs to be added
3.	The main area will show degree’s detail information, including degree name, degree description and the subjects in this degree
4.	Tick the subjects that need to be deleted
5.	Click “Delete” button above the subject table
6.	Confirm delete operation
####Delete degrees
1.	Click the “Degree List” in the side bar
2.	Tick the degrees that need to be deleted
3.	Click “Delete” button
4.	Confirm delete operation
###Subject Management
####View subject list
1.	Click the “Subject List” in the side bar
2.	The main area will show a table, including all subjects and their basic information
####View subject’s detail information
1.	Click the “Subject List” in the side bar
2.	Click the subject that you want to get detail information
3.	The main area will show selected subject’s detail information, including subject name, subject code, subject description
####Create a subject
1.	Click the “Subject List” in the side bar
2.	Click “Add” button
3.	There will be a pop-up window, including an editable form
4.	Fill the subject information in the form
5.	Click “Add” button
####Update subject’s detail information
1.	Click the “Subject List” in the side bar
2.	Click the subject that needs to be updated
3.	The main area will show selected subject’s detail information, including subject name, subject code, subject description
4.	Click “Update” button
5.	The form will become editable
6.	Edit the information in the form
7.	Click “Save” button
####Delete subjects
1.	Click the “Subject List” in the side bar
2.	Tick the subjects that need to be deleted
3.	Click “Delete” button
4.	Confirm delete operation
###Account Management
####View account list
1.	Click the “Account List” in the side bar
2.	The main area will show a table, including all accounts, their basic information and a “Reset Password” Button
####View account’s detail information
1.	Click the “Account List” in the side bar
2.	Click the account that you want to get detail information
3.	The main area will show selected account’s detail information, including username, a “Reset” button, role, first name, last name, gender, e-mail, phone number and address. If the role is “student”, there will be an extra row that shows his/her degree name
####Create an account
1.	Click the “Account List” in the side bar
2.	Click “Add” button
3.	There will be a pop-up window, including an editable form
4.	Fill the account information in the form
5.	Click “Add” button
####Update account’s detail information
1.	Click the “Account List” in the side bar
2.	Click the account that needs to be updated
3.	The main area will show selected account’s detail information, including username, a “Reset Password” button, role, first name, last name, gender, e-mail, phone number and address. If the role is “student”, there will be an extra line that shows his/her degree name.
4.	Click “Update” button
5.	The form will become editable
6.	Edit the information in the form
7.	Click “Save” button
####Delete account
1.	Click the “account List” in the side bar
2.	Tick the accounts that need to be deleted
3.	Click “Delete” button
4.	Confirm delete operation
####Reset password
1.	Click the “Account List” in the side bar
2.	Click the “Reset Password” button in same row with the account that needs to be reset
3.	There will be a pop-up window, including selected account’s username and an empty password input area
4.	Fill the new password into the form
5.	Click “Reset” button
##Feature B: Student
###Login
Please use this account:  
Username: student  
Password: student
###Enrollment Management
####View enrolled subjects list
1.	Click the “Enrollment” in the side bar
2.	The main area will show a table, including enrolled subjects and their basic information
####View enrolled subject’s detail information
1.	Click the “Enrollment” in the side bar
2.	Click the subject that you want to get detail information
3.	There will be a pop-up window that shows subject’s detail information, including subject name, subject code, subject description
####Withdraw from a subject
1.	Click the “Enrollment” in the side bar
2.	Click the subject that you want to withdraw from
3.	There will be a pop-up window that shows selected subject’s detail information, including subject name, subject code, subject description
4.	Click “Withdraw” button
####View subjects that can be enrolled
1.	Click the “Enrollment” in the side bar
2.	Click “Enroll Subjects” button
3.	The main area will show a table, including subjects that can be enrolled (These subjects are in your degree and not be enrolled in) and their basic information
####Enroll in a subject
1.	Click the “Enrollment” in the side bar
2.	Click “Enroll Subjects” button
3.	The main area will show a table, including subjects that can be enrolled (These subjects are in your degree and not be enrolled in) and their basic information
4.	Click the subject that you want to enroll in
5.	There will be a pop-up window that shows selected subject’s detail information, including subject name, subject code, subject description
6.	Click “Enroll” button
###My Account Management
####View my account information
1.	Click the “My account” in the side bar
2.	The main area will show this account’s detail information, including username, a “Reset” button, degree name, first name, last name, gender, e-mail, phone number and address
####Update my account information
1.	Click the “My account” in the side bar
2.	The main area will show this account’s detail information, including username, a “Reset” button, degree name, first name, last name, gender, e-mail, phone number and address
3.	Click “Update” button
4.	The form will become editable except the username
5.	Edit the information in the form
6.	Click “Save” button
####Reset password
1.	Click the “My Account” in the side bar
2.	Click the “Reset” button
3.	There will be a pop-up window, including account’s username and an empty password input area
4.	Fill the new password into the form
5.	Click “Reset” button

