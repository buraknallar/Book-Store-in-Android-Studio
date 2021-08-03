# Book Store Applicaton in Android Studio
 
Path of Java Files: \project\app\src\main\java\com\example\project

Path of XML Files: \project\app\src\main\res\layout

Path of AndroidManifest.xml: \project\app\src\main

# Project Description 
Our project is a book sale application. There are two types of users. One them is admin, and 
the other is customer. Admin is able to delete customer, add new book, update book and 
delete book. Customer is able to see books and add them to the basket and also has settings 
pages to update his/her information. Also there is a 3D secure page (representation) in the 
customer side.

# Database Design
There are two databases on the application. One of them will is keeping customers and one 
of them is keeping books. Only admin can reach and see all the databases. Admin is able to 
arrange databases. Customer is able to see only books database. Customer database will 
keep -username, name, surname, email, password- of customers. Book database will contain 
–id, author, name, year, type- of book.

![alt text](https://github.com/buraknallar/Book-Store-in-Android-Studio/blob/main/databases%20and%20UML/databases.png)
	

# Application Design
There are two options at the beginning on the application. In the first page, there are two 
buttons and user will select one them to enter as admin or user. Admin panel will has four
tabs. One them is for add books with the attributes of –id, author , name, year, type -. In 
another tab, admin has permission to see all the books and be able to arrange database such 
as add, delete or update. In another tab, admin is able to delete books with the key “id, 
name”. ADD, DELETE and DELETE tabs can show databases in a listView. In another tab, 
admin can see customers database and also have authority to delete. 
In the customer side, there are a login page and also sign up age. In this page, customer can 
enter the shop or if customer is not member of the shop, he/she can become a member with 
additional page. In this page we are taking information from customer such as username, 
name, surname, email and password. There are an authentication before enter system with 
checking username and password. After customer enter the system, there are two tabs. In 
the first tab, customer arrange his/her information with the key “username”. In the second
tab, customer can see the books in the system and add them to his/her basket. In the third
tab customer is able to see his/her basket. Also, in this tab, address information are taking 
from user. Telephone number is must for payment. We transferred telephone number to
intent. And there is a button for payment. With this button, an intent is opening and 
customers credit card information is asked. This intent has a countdown and three buttons.
There are MenuItem in every page to Logout.

![alt text](https://github.com/buraknallar/Book-Store-in-Android-Studio//blob/main/databases%20and%20UML/UML.png)

# Used Tools
•	Button

•	Text View

•	Edit Text

•	Image View

•	Spinner

•	Menu

•	List View

•	Tab Host

•	SQLiteOpenHelper

•	SQLiteDatabase

•	Intent

•	Bundle

•	Toast

•	Cursor

•	Array List

•	Array Adapter

•	Dialog Interface

•	Countdown Timer

•	Date

•	Calendar

•	Time Unit

•	Alert Dialog

•	AppCompat Activity

•	plantUML (for UML diagram)
