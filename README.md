# S.A.P.P: Social Account Profile Program
## CS 180 Project 5: Option 2

### Team 15-3
- Christopher Chan
- Brandon Lamer-Connolly
- Amy Curtland
- Parker Laughner
- Vincent Vu

### How to run application
In terminal, compile all Java classes: `javac */*.java`

To run server, `java backend/Server`. To run client, `java frontend/Client`

### Backend Class Descriptions

* **Account Class:**
	* This class is called to hold information regarding any info in a user's account.
	* Testing done on this class is through JUnit tests in RunLocalTest.java.
  	* Constructor
    	* The constructor sets parameters equal to private variables in the class and also initializes 3 Account arrayLists. Parameters include String username, String password,
		String email, String phoneNumber, String bio, and String interests. The arrayLists initialized are friends, friendRequests, and requestedFriends. 
  	* getUserName
   		* Gets the username.
	* getPassword
 		* Gets the password.
 	* setUserName
    	* Sets the username.
 	* setPassword
   		* Sets the password
   	* getEmail
   		* Gets the email.
   	* getPhoneNumber
		* Gets the phone number.
   	* getBio
		* Gets the bio.
  	* getInterests
		* Gets the interests.
   	* setEmail
		* Sets the email.
   	* setPhoneNumber
		* Sets the phone number.
  	* setBio
		* Sets the bio.
   	* setInterests
		* Sets the interests.
	* getFriends
		* Gets the friends and returns as an arrayList.
	* getFriendRequests
		* Gets the friend requests and returns as an arrayList.
	* getRequestedFriends
		* Gets the users that you have requested to friend and returns as an arrayList.
	* isFriendsWith
		* Checks whether one user is friends with another.
	* hasRequested
		* Checks whether another user has requested to be a friend and is in our friend requests arrayList.
	* sendFriendRequest
		* Sends friend request to another user and makes sure that you aren't currently already friends.
	* acceptDeclineFriendRequest
		* Accepts or denies friend requests, 1 is returned if successful and else, -1 is returned.
	* removeFriend
		* Removes friend from list and unfriends the person. 1 returned if successful and -1 else.
	* userInList
		* Helps find a certain user in a list. Index in the list is returned if the user exists and else, -1 is returned.
* **Manager Class:**
	* This class acts as a database for storing all accounts and manages retrieval of of specific accounts. Serves as the backbone of the backend.
	* Testing done on this class is through JUnit tests in RunLocalTest.java.
	* Constructor
		* Reads in allUsers.txt file and if the file doesn't exist, creates a new file callde allUsers.txt.
	* getAllUsers
		* Gets all the users.
	* getUser
		* Gets one specific user.
	* createAccount
		* Creates a new account given the parameters String username, String password, String email, String phoneNumber, String bio, String interests. After creation,
		the account is added to the database and also checks for duplicate accounts. Returns 1 if successful, -1 for an invalid username and -2 for duplicate username.
	* updateAccount
		* Goes into the database and updates the account information for a user when given the following parameters: String username, String email, String phoneNumber,
		String bio, String interests. Returns 1 if succcessful and -1 if the username couldn't be found.
	* updateAccount (overload)
		* An alternate version of updateAccount that overloads the method to use different set of paramenters: String username, String email, String phoneNumber, String
		bio, String interests, String currentPassword, String newUsername, String newPassword. Returns -1 for an invalid username, -2 if the new username exists, -3 if
		the password is incorrect and -4 if the username wasn't found.
	* deleteAccount
		* Removes a certain user from the database. Returns 1 if successful, -1 if password is incorrect and -2 if the username isn't found.
	* searchUsers
		* Finds related users in database given a search String.
	* findUser
		* Finds a user account by username. Returns index in the database if found and -1 if not found.
	* saveToFile
		* Saves everything to allUsers.txt.
* **Server Class:**
	* This class handles the creation of the server and sessions so that multiple clients can connect with the server. It also handles authentication and client requests,
	think of this class as the control room of the whole application.
	* main
		* Creates the server and starts the manager class (database). Also handles creation of multiple sessions for clients to interact with the server.
* **Server Thread Class:**
	* This class handles the authentication, requests and used to avoid overloading the server.
	* Testing done on this class is through JUnit tests in RunLocalTest.java.
	* Constructor
		* Instantiates a new server thread and a new manager.
	* run
		* Instantiates the readers and writers and allows for information transfer and status updates to be sent from the client to the server.
	* sendData
		* Sends a status update.
	* sendData
		* Is an overloaded method to sendData; sends data and a status update.

## Frontend Class Descriptions

* **JA Button Class:**
	* Serves as an extension to the regular JButton and allows for assigning user accounts to a JButton.
	* Testing done on this class is through JUnit tests in RunLocalTest.java.
	* Constructor
		* Makes a regular JButton but adds a client action.
	* Constructor
		* Is an overloaded contructor that makes a regular JButton but adds a client action and account name.
	* getAccountName
		* Gets the account name.
	* setAccountName
		* Sets the account name.
	* getActionType
		* Gets the action type.
	* setActionType
		* Sets the action type.
* **Client Class:**
	* Allows the user to interact with the application. Shows appropriate GUIs for each interaction, manages the client logic, and makes requests to the server.
	* Testing done on this class is through JUnit tests in RunLocalTest.java. GUI testing included just entering correct inputs to see if inputs inputted correctly would
	work. Then incorrect inputs are put in to get the error messages to display to make sure error messages are displayed in the correct situations.
	* actionPerformed
		* Watches/manages the buttons. Chooses what to do after a button is pressed depending on circumstances.
	* main
		* Creates and loops the authentication menu.
	* startingMenu
		* Opens the authentication menu. This gives the user the choice to login or create a new account. Returns 0 to login, 1 to create account and -1 to close the
		window.
	* login
		* Shows the login menu. Returns true if successful and false if an error occured or if the user wants to cancel. Also displays error messages if there are
		errors.
	* createAccount
		* Shows the create account menu. Returns true if successful and false if an error occured or if the user wants to cancel. Also displays error messages if there
		are errors.
	* showMainMenu
		* Shows the main menu. Using this menu, the user can access the friends list, the account profile and search for other accounts. Also keeps the menu updated in 
		case account information was updated.
	* showProfile
		* Shows the user profile in a new window. If the person viewing the profile is the user, allows for the editing of account info. If the person view the
		profile is another user then allows the person to view the friends list and to send a friend request to the profile they are viewing. Updates real time.
	* showEditProfile
		* Shows the window that allows users to edit their own profile. Can display differently depending on the user's profile.
	* showFriendsList
		* Shows the window that has a list of the user's current friends, friend requests and sent friend requests. Upadates real time.
	* showSearchMenu
		* Shows the window that has a search bar and search button. Does not show search results.
	* showSearchResults
		* Shows window that has the search results (after user presses search button). Updates real time.
	* showConnectionError
		* Shows message that client was unable to connect to the server.
	* sendToServer
		* Sends data to the server. Shows connection failed if unable to connect.
