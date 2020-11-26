package backend;

import java.util.ArrayList;

/**
    * Manager
    *
    * Acts as a "database", storing all accounts
    * and manages retrieval of specific accounts
    * The "model" of the entire system
    *
    * @author Team 15-3, CS 180 - Merge
    * @version November 23, 2020
*/

public class Manager {

    // the main list/database. Stores every single user
    private ArrayList<Account> allUsers;

    public Manager() {
        allUsers = new ArrayList<Account>();
    }

    // getter method for all users
    public ArrayList<Account> getAllUsers() { return allUsers; }

    // getter method for a specifc user
    public Account getUser(String username) {
        int i = findUser(username);
        if (i != -1) {
            return allUsers.get(i);
        }
        return null;
    }

    // creates a new account given info and adds it to our list/database
    // avoid duplicates of usernames
    public boolean createAccount (String username, String password, String email, String phoneNumber, String bio, String interests) {
        // check to make sure that username doesn't already exist in the list/database already
        int i = findUser(username);
        if (i == -1) {
            Account newUser = new Account(username, password, email, phoneNumber, bio, interests);
            allUsers.add(newUser);
            return true;
        }
        return false;
    }

    // removes user from our list/database
    public boolean deleteAccount(Account user) {
        // check to make sure that user exists in our list/database
        int i = findUser(user.getUsername());
        if (i != -1) {
            allUsers.remove(i);
            return true;
        }
        return false;
    }

    // method to easily find users by username
    // if found, returns the index in list/database
    // if not found, return -1
    private int findUser(String username) {
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }
}
