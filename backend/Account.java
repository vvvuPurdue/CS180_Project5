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

public class Account {
    
    // account info
    private String username;
    private String password;

    // profile info
    private ContactInfo contactInfo;
    private String bio;
    private String interests;

    // friends is the user's current friends
    private ArrayList<Account> friends;
    // friendRequests is a list of friend requests that others have sent to this user
    private ArrayList<Account> friendRequests;
    // friendRequests is a list of friend requests sent by this user
    private ArrayList<Account> requestedFriends;

    public Account(String username, String password, String email, String phoneNumber, String bio, String interests) {
        this.username = username;
        this.password = password;
        this.contactInfo = new ContactInfo(email, phoneNumber);
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
    public ContactInfo getContactInfo() { return contactInfo; }
    public String getBio() { return bio; }
    public String getInterests() { return interests; }
    public void setBio(String bio) { this.bio = bio; }
    public void setInterests(String interests) { this.interests = interests; }

    // getters for friend requests
    public ArrayList<Account> getFriends() { return friends; }
    public ArrayList<Account> getFriendRequests() { return friendRequests; }
    public ArrayList<Account> getRequestedFriends() { return requestedFriends; }
}
