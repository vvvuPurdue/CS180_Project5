package backend;

import java.io.Serializable;
import java.util.ArrayList;

/**
    * Account class
    *
    * Holds info about a user account/profile
    * Username, password, interests/bio, contact
    * and a list of current friends and friend requests
    *
    * @author Team 15-3, CS 180 - Merge
    * @version November 23, 2020
*/

public class Account implements Serializable {
    
    // account info
    private String username;
    private String password;

    // profile info
    private String email;
    private String phoneNumber;
    private String bio;
    private String interests;

    // friends is the user's current friends
    private ArrayList<Account> friends;
    // friendRequests is a list of friend requests that others have sent to this use
    private ArrayList<Account> friendRequests;
    // friendRequests is a list of friend requests sent by this user
    private ArrayList<Account> requestedFriends;

    public Account(String username, String password, String email, String phoneNumber, String bio, String interests) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
        this.interests = interests;
        this.friends = new ArrayList<Account>();
        this.friendRequests = new ArrayList<Account>();
        this.requestedFriends = new ArrayList<Account>();
    }

    // getters and setters for account info
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

    // getters and setters for profile info
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getBio() { return bio; }
    public String getInterests() { return interests; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setBio(String bio) { this.bio = bio; }
    public void setInterests(String interests) { this.interests = interests; }

    // getters for friend requests
    public ArrayList<Account> getFriends() { return friends; }
    public ArrayList<Account> getFriendRequests() { return friendRequests; }
    public ArrayList<Account> getRequestedFriends() { return requestedFriends; }

    // checks whether or not a user is friends with this user
    public boolean isFriendsWith(Account user) {
        return userInList(user.username, friends) != -1;
    }

    // check if we have the other user in our friend requests
    // also check if the other user has requested this user
    public boolean hasRequested(Account user) {
        int i = userInList(user.username, this.requestedFriends);
        int j = userInList(this.username, user.friendRequests);
        return i != -1 && j != -1;
    }

    // send a friend request to other user (adding to current requestedFriends)
    // ensure that they both are not already friends
    public int sendFriendRequest(Account user) {
        if (!isFriendsWith(user)) {
            this.requestedFriends.add(user);
            user.friendRequests.add(this);
            return 1;
        }
        return -1;
    }

    // remove a friend request that this user has made
    // Returns 1 if successful, -1 if not
    public int cancelFriendRequest(Account user) {
        // check if we have the other user in our friend requests
        int i = userInList(user.username, this.requestedFriends);
        // check if the other user has requested this user
        int j = userInList(this.username, user.friendRequests);
        if (i != -1 && j != -1) {
            this.requestedFriends.remove(i);
            user.friendRequests.remove(j);
            return 1;
        }
        return -1;
    }

    // accept or decline a friend request in friendRequests
    // if function successful, return 1. Else, return -1
    public int acceptDeclineFriendRequest(Account user, boolean accepting) {
        int i = userInList(user.username, this.friendRequests);
        int j = userInList(this.username, user.requestedFriends);
        if (i != -1 && j != -1) {
            // if this user is accepting friend request, add the users and remove from request lists
            // else, if this user is declining, only remove from friend requests lists
            if (accepting) {
                this.friends.add(user);
                user.friends.add(this);
            }
            this.friendRequests.remove(i);
            user.requestedFriends.remove(j);
            return 1;
        }
        return -1;
    }

    // "unfriend" and remove a friend from user's friends list
    // Returns 1 if successful, -1 if not
    public int removeFriend(Account user) {
        int i = userInList(user.username, this.friends);
        int j = userInList(this.username, user.friends);
        if (i != -1 && j != -1) {
            this.friends.remove(i);
            user.friends.remove(j);
            return 1;
        }
        return -1;
    }

    // method to ease the finding of a user in a certain list
    // for example, when we need to check if a user exists in our friends list
    // if found, returns the index in list
    // if not found, return -1
    private int userInList(String username, ArrayList<Account> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).username.equals(username)) {
                return i;
            }
        }
        return -1;
    }
}
