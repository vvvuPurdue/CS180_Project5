package backend;

/**
    * Testing
    *
    * Ensuring the account classes work as intended
    *
    * @author Team 15-3, CS 180 - Merge
    * @version November 23, 2020
*/

public class Testing {
    
    public static void main(String[] args) {
        System.out.println("Working");

        Account bob = new Account("bob123", "password", "bob@email", "123", "a cool guy", "pizza, soccer, and sleeping");
        Account steve = new Account("steve45", "password", "steve@email", "45", "a chill guy", "diamonds, mining, and apples");
        Account sam = new Account("sam67", "password", "sam@email", "67", "a cool gal", "books, animals, and writing");

        bob.sendFriendRequest(steve);
        steve.acceptDeclineFriendRequest(bob, true);
        bob.acceptDeclineFriendRequest(sam, true);
        sam.sendFriendRequest(bob);
        steve.sendFriendRequest(bob);
        bob.acceptDeclineFriendRequest(sam, false);

        System.out.println("Finished");
    }
}
