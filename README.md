# CS180 Project5
## Team 15-3

* **Account Class:**
	* This class is called to hold information regarding any info in a user's account.
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
	* 
