package frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import backend.Account;

/**
    * Client class
    *
    * Manages logic of client
    *
    * @author Team 15-3 CS 180 - Merge
    * @version November 26, 2020
*/

public class NewClient {
    
    // storing the current client signed in on client side
    private static Account currentUser;

    // gui constants
    private static final Font titleFont = new Font("Arial", 1, 20);
    private static final Font subTitleFont = new Font("Arial", 1, 15);
    private static final EmptyBorder padding = new EmptyBorder(10, 10, 10, 10);

    // edit account fields
    private static JTextField phoneNumberTxtField;
    private static JTextField emailTxtField;
    private static JTextField bioTxtField;
    private static JTextField interestsTxtField;
    private static JTextField newUsernameTxtField;
    private static JTextField oldPasswordTxtField;
    private static JTextField newPasswordTxtField;

    // search user fields
    private static JTextField searchField;

    // defines ALL of the possible button actions
    public static enum Action {
        ViewProfile, ViewFriends,
        EditAccount, UpdateAccount, DeleteAccount,
        SendFriendRequest, CancelFriendRequest,
        AcceptFriendRequest, DeclineFriendRequest,
        RemoveFriend, ViewSearchMenu, SearchUsers
    }

    /*
        * There are several actions that we can perform after we listen
        * The action depends on what the Action enum assigned to the JAButton
    */
    public static ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JAButton) {
                // We are using the button's text to determine the action required
                JAButton source = (JAButton) e.getSource();
                Action buttonAction = source.getActionType();
                switch (buttonAction) {
                    // if view profile button was pressed, show profile window
                    // based on what account the button is linked to
                    case ViewProfile -> {
                        showProfile(source.getAccountName());
                    }
                    // if view friends button was pressed, show friends list window
                    // based on what account the button is linked to
                    case ViewFriends -> {
                        showFriendsList(source.getAccountName());
                    }
                    // if edit profile button was pressed, show profile window. Can only be shown for current user
                    case EditAccount -> {
                        showEditProfile();
                    }
                    // if saved changes button was pressed, send new info request to server. Can only be used for current user
                    case UpdateAccount -> {
                        Object[] response;
                        // if old password, new password, and new username are blank, that means we're not updating them
                        // send request that only updates email, phoneNumber, bio, and interests
                        if (oldPasswordTxtField.getText().equals("")
                            && newPasswordTxtField.getText().equals("")
                            && newUsernameTxtField.getText().equals("")
                        ) {
                            String[] request = new String[] {
                                "updateAccount", currentUser.getUsername(),
                                emailTxtField.getText(), phoneNumberTxtField.getText(),
                                bioTxtField.getText(), interestsTxtField.getText()
                            };
                            response = ClientRequest.sendToServer(request);
                        } else {
                            // else, if they are not blank, we are updating password and username
                            // send a request to include everything
                            String[] request = new String[] {
                                "updateAccount", currentUser.getUsername(),
                                emailTxtField.getText(), phoneNumberTxtField.getText(),
                                bioTxtField.getText(), interestsTxtField.getText(),
                                oldPasswordTxtField.getText(), newUsernameTxtField.getText(), newPasswordTxtField.getText()
                            };
                            response = ClientRequest.sendToServer(request);
                        }
                        // check status code to see if successful, and show appropriate messages
                        String status = (String) response[0];
                        switch (status) {
                            case "success":
                                currentUser = (Account) response[1];
                                JOptionPane.showMessageDialog(null, "Changes have been successfully saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case "usernameNotFound":
                                JOptionPane.showMessageDialog(null, "Username could not be found!", "Error!", JOptionPane.ERROR_MESSAGE);
                                break;
                            case "incorrectPassword":
                                JOptionPane.showMessageDialog(null, "Password was not correct!", "Error!", JOptionPane.ERROR_MESSAGE);
                                break;
                            case "usernameExists":
                                JOptionPane.showMessageDialog(null, "Username already exists!", "Error!", JOptionPane.ERROR_MESSAGE);
                                break;
                            case "invalidUsername":
                                JOptionPane.showMessageDialog(null, "Username is invalid!", "Error!", JOptionPane.ERROR_MESSAGE);
                                break;
                            case "emptyFields":
                                JOptionPane.showMessageDialog(null, "Account information cannot be empty!", "Error!", JOptionPane.ERROR_MESSAGE);
                                break;
                            case "connectionFailed":
                                showConnectionError();
                                break;
                        }
                    }
                    // If user pressed delete account button, confirm if they want to delete. If so, proceed with operation
                    // Can only be shown with current user
                    case DeleteAccount -> {
                        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account? This cannot be undone.", "Delete Account?", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            String status = (String) ClientRequest.sendToServer(new String[] { "deleteAccount", currentUser.getUsername(), currentUser.getPassword() })[0];
                            switch (status) {
                                case "success":
                                    JOptionPane.showMessageDialog(null, "Account successfully delete! Program will close now...", "Success", JOptionPane.INFORMATION_MESSAGE);
                                    break;
                                case "connectionFailed":
                                    showConnectionError();
                                    break;
                            }
                        }
                    }
                    // If user pressed the friend request button, do a server request,
                    // where we send a friend request from the current user to the account linked to the JAButton. 
                    case SendFriendRequest -> {
                        Object[] response = ClientRequest.sendToServer(new String[] { "sendFriendRequest", currentUser.getUsername(), source.getAccountName() });
                        String status = (String) response[0];
                        switch (status) {
                            case "success":
                                currentUser = (Account) response[1];
                                JOptionPane.showMessageDialog(null, "Friend request sent!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case "connectionFailed":
                                showConnectionError();
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Unable to send friend request", "Error", JOptionPane.INFORMATION_MESSAGE);
                                break;
                        }
                    }
                    // If user pressed the cancel friend request button, do a server request,
                    // where we cancel a friend request sent from current user to the account linked to the JAButton.
                    case CancelFriendRequest -> {
                        Object[] response = ClientRequest.sendToServer(new String[] { "cancelFriendRequest", currentUser.getUsername(), source.getAccountName() });
                        String status = (String) response[0];
                        switch (status) {
                            case "success":
                                currentUser = (Account) response[1];
                                JOptionPane.showMessageDialog(null, "Friend request canceled!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case "connectionFailed":
                                showConnectionError();
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Could not cancel friend request", "Error", JOptionPane.INFORMATION_MESSAGE);
                                break;
                        }
                    }
                    // If user pressed the accept friend request button, do a server request, and check status
                    case AcceptFriendRequest -> {
                        Object[] response = ClientRequest.sendToServer(new String[] { "acceptFriendRequest", currentUser.getUsername(), source.getAccountName() });
                        String status = (String) response[0];
                        switch (status) {
                            case "success":
                                currentUser = (Account) response[1];
                                JOptionPane.showMessageDialog(null, "Friend request accepted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case "connectionFailed":
                                showConnectionError();
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Could not accept friend request", "Error", JOptionPane.INFORMATION_MESSAGE);
                                break;
                        }
                    }
                    // If user pressed the decline friend request button, do a server request. Check status
                    case DeclineFriendRequest -> {
                        Object[] response = ClientRequest.sendToServer(new String[] { "declineFriendRequest", currentUser.getUsername(), source.getAccountName() });
                        String status = (String) response[0];
                        switch (status) {
                            case "success":
                                currentUser = (Account) response[1];
                                JOptionPane.showMessageDialog(null, "Friend request declined!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case "connectionFailed":
                                showConnectionError();
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Could not decline friend request", "Error", JOptionPane.INFORMATION_MESSAGE);
                                break;
                        }
                    }
                    // If user pressed the remove friend request button, do a server request. Check status
                    case RemoveFriend -> {
                        Object[] response = ClientRequest.sendToServer(new String[] { "removeFriend", currentUser.getUsername(), source.getAccountName() });
                        String status = (String) response[0];
                        switch (status) {
                            case "success":
                                currentUser = (Account) response[1];
                                JOptionPane.showMessageDialog(null, "Friend removed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case "connectionFailed":
                                showConnectionError();
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Could not remove friend request", "Error", JOptionPane.INFORMATION_MESSAGE);
                                break;
                        }
                    }
                    // if user wishes to search users, show the search menu (displaying search bar)
                    case ViewSearchMenu -> {
                        showSearchMenu();
                    }
                    // once the user has pressed the search button, show the results
                    case SearchUsers -> {
                        String searchWord = searchField.getText();
                        if (!searchWord.equals("")) {
                            showSearchResults(searchField.getText());
                            searchField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "Search field cannot be blank!", "Error", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        }
    };

    public static void main(String[] args) {
        System.out.println("Starting program...");

        // loop until user cancels at start menu or enters valid user info
        while (true) {
            int loginOrCreate = startingMenu();
            if ((loginOrCreate == 0 && login()) || (loginOrCreate == 1 && createAccount())) {
                break;
            } else if (loginOrCreate == -1) {
                return;
            }
        }
        showMainMenu();
    }

    /*
        First menu that shows when program is opened. Give user the choice to login or create account
        Returns 0 to login, 1 to createAccount, -1 to close window
    */
    public static int startingMenu() {
        String[] options = { "Login", "Create New Account" };
        int result = JOptionPane.showOptionDialog(null, "Welcome to the Friends App!", "Friends App", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return result;
    }

    /*
        * Menu to allow user to login
        * Return true if successful, false if error occurred or user wants to cancel
        * If there are errors, show appropriate message dialogs
    */
    public static boolean login() {
        System.out.println("Logging in...");
        String username = JOptionPane.showInputDialog(null, "Username", JOptionPane.QUESTION_MESSAGE);
        // if user clicked cancel, return to start menu
        if (username == null) return false;
        String password = JOptionPane.showInputDialog(null, "Password", JOptionPane.QUESTION_MESSAGE);
        // if user clicked cancel, return to start menu
        if (password == null) return false;
        Object[] response = ClientRequest.sendToServer(new String[] { "loginUser", username, password });
        String status = (String) response[0];
        switch (status) {
            case "success":
                currentUser = (Account) response[1];
                return true;
            case "usernameNotFound":
                JOptionPane.showMessageDialog(null, "Username does not exist", "User Error!", JOptionPane.ERROR_MESSAGE);
                break;
            case "incorrectPassword":
                JOptionPane.showMessageDialog(null, "Incorrect Password", "Password Error!", JOptionPane.ERROR_MESSAGE);
                break;
            case "connectionFailed":
                showConnectionError();
                break;
        }
        return false;
    }


    /*
        * Menu to allow user to create a new account
        * Return true if successful, false if error occurred or user wants to cancel
        * If there are errors, show appropriate message dialogs
    */
    public static boolean createAccount() {
        System.out.println("Creating new account...");
        String username = JOptionPane.showInputDialog(null, "Enter a username", JOptionPane.QUESTION_MESSAGE);
        if (username == null) return false;
        String password = JOptionPane.showInputDialog(null, "Enter a password", JOptionPane.QUESTION_MESSAGE);
        if (password == null) return false;
        String email = JOptionPane.showInputDialog(null, "Please enter your email", JOptionPane.QUESTION_MESSAGE);
        if (email == null) return false;
        String phone = JOptionPane.showInputDialog(null, "Please enter your phone number", JOptionPane.QUESTION_MESSAGE);
        if (phone == null) return false;
        String bio = JOptionPane.showInputDialog(null, "Please enter your bio", JOptionPane.QUESTION_MESSAGE);
        if (bio == null) return false;
        String interests = JOptionPane.showInputDialog(null, "Please enter some of your interests", JOptionPane.QUESTION_MESSAGE);
        if (interests == null) return false;

        String[] accountInfo = { "createAccount", username, password, email, phone, bio, interests };
        Object[] response = ClientRequest.sendToServer(accountInfo);
        String status = (String) response[0];
        switch (status) {
            case "success":
                JOptionPane.showMessageDialog(null, "Successfully created account", "Account Created!", JOptionPane.INFORMATION_MESSAGE);
                currentUser = (Account) response[1];
                return true;
            case "usernameExists":
                JOptionPane.showMessageDialog(null, "Username already exists", "User Error!", JOptionPane.ERROR_MESSAGE);
                break;
            case "emptyFields":
                JOptionPane.showMessageDialog(null, "You must fill every field to create an account", "User Error!", JOptionPane.ERROR_MESSAGE);
                break;
            case "connectionFailed":
                showConnectionError();
                break;
        }
        return false;
    }

    /*
        * The main menu that the user will see once successfully logged in
        * Can view friends list, view profile, search new people, and delete account
    */
    public static void showMainMenu() {
        // initial setup
        JFrame mainMenu = new JFrame();
        Container content = mainMenu.getContentPane();
        content.setLayout(new BorderLayout());

        // creating title
        JLabel header = new JLabel("Logged in as: " + currentUser.getUsername(), SwingConstants.CENTER);
        header.setFont(titleFont);
        content.add(header, BorderLayout.NORTH);

        // create new buttons
        JAButton profileMenuButton = new JAButton("View Profile", currentUser.getUsername(), Action.ViewProfile);
        profileMenuButton.addActionListener(actionListener);
        JAButton friendMenuButton = new JAButton("View Friends", currentUser.getUsername(), Action.ViewFriends);
        friendMenuButton.addActionListener(actionListener);
        JAButton searchMenuButton = new JAButton ("Search for Users", Action.ViewSearchMenu);
        searchMenuButton.addActionListener(actionListener);
        
        // adding new buttons to new panel, then add to menu
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.add(profileMenuButton);
        mainMenuPanel.add(friendMenuButton);
        mainMenuPanel.add(searchMenuButton);
        content.add(mainMenuPanel, BorderLayout.CENTER);
        
        // after adding everything, finally show menu
        mainMenu.setTitle("Main Menu");
        mainMenu.setSize(600, 400);
        mainMenu.setLocationRelativeTo(null);
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.pack();
        mainMenu.setVisible(true);
    }

    /*
        * Shows a user's profile in a new window
        * If this user is the currentUser, allow editProfile abilities
        * Else, if it is a different user, allow the ability to view their friends request them
    */
    public static void showProfile(String username) {
        Object[] response = ClientRequest.sendToServer(new String[] { "getUser", username });
        String status = (String) response[0];
        if (status.equals("success")) {
            Account user = (Account) response[1];
            JFrame profileWindow = new JFrame();
            Container content = profileWindow.getContentPane();
            JPanel panel = new JPanel();
            panel.setBorder(padding);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JLabel header = new JLabel("Profile of: " + user.getUsername(), SwingConstants.CENTER);
            header.setFont(titleFont);
            panel.add(header, BorderLayout.NORTH);
            panel.add(Box.createVerticalStrut(10));

            JLabel usernameToShow = new JLabel("Username: " + user.getUsername(), SwingConstants.CENTER);
            JLabel email = new JLabel("Email: " + user.getEmail(), SwingConstants.CENTER);
            JLabel phoneNumber = new JLabel("Phone Number: " + user.getPhoneNumber(), SwingConstants.CENTER);
            JLabel bio = new JLabel("Biography: " + user.getBio(), SwingConstants.CENTER);
            JLabel interests = new JLabel("Interests: " + user.getInterests(), SwingConstants.CENTER);
            panel.add(usernameToShow);
            panel.add(Box.createVerticalStrut(10));
            panel.add(email);
            panel.add(Box.createVerticalStrut(10));
            panel.add(phoneNumber);
            panel.add(Box.createVerticalStrut(10));
            panel.add(bio);
            panel.add(Box.createVerticalStrut(10));
            panel.add(interests);
            panel.add(Box.createVerticalStrut(10));

            // if the user we are viewing is the current user, show edit and delete account features
            if (username.equals(currentUser.getUsername())) {
                JAButton editProfile = new JAButton("Edit Account", Action.EditAccount);
                editProfile.addActionListener(actionListener);
                JAButton deleteAccountButton = new JAButton("Delete Account", Action.DeleteAccount);
                deleteAccountButton.addActionListener(actionListener);
                panel.add(editProfile);
                panel.add(deleteAccountButton);
            } else {
                // if it is anothe user, allow the ability to view their friends
                JAButton viewFriends = new JAButton("View Friends", username, Action.ViewFriends);
                viewFriends.addActionListener(actionListener);
                panel.add(viewFriends);
                // also add ability to remove friend or send/cancel/accept/decline friend requests
                // first check if we are friends with them
                Object[] isFriendsResponse = ClientRequest.sendToServer(new String[] { "isFriendsWith", currentUser.getUsername(), username });
                String isFriendsStatus = (String) isFriendsResponse[0];
                if (isFriendsStatus.equals("success")) {
                    // if we are friends with them, add option to remove the friend
                    if ((boolean) isFriendsResponse[1]) {
                        JAButton removeFriend = new JAButton("Remove Friend", username, Action.RemoveFriend);
                        removeFriend.addActionListener(actionListener);
                        panel.add(removeFriend);
                    } else {
                        // if we're not friends with them, check if we have requested user or if we are friends with them, 
                        Object[] hasRequestedResponse = ClientRequest.sendToServer(new String[] { "hasRequested", currentUser.getUsername(), username });
                        String hasRequestedStatus = (String) hasRequestedResponse[0];
                        if (hasRequestedStatus.equals("success")) {
                            // if we have not requested them, first check if they have requested us
                            if (!(boolean) hasRequestedResponse[1]) {
                                // check if they have requested us, in which we show "Accept" or "Decline"
                                hasRequestedResponse = ClientRequest.sendToServer(new String[] { "hasRequested", username, currentUser.getUsername() });
                                hasRequestedStatus = (String) hasRequestedResponse[0];
                                if (hasRequestedStatus.equals("success")) {
                                    if ((boolean) hasRequestedResponse[1]) {
                                        JAButton acceptFriendRequest = new JAButton("Accept Friend Request", username, Action.AcceptFriendRequest);
                                        acceptFriendRequest.addActionListener(actionListener);
                                        JAButton declineFriendRequest = new JAButton("Decline Friend Request", username, Action.DeclineFriendRequest);
                                        declineFriendRequest.addActionListener(actionListener);
                                        panel.add(acceptFriendRequest);
                                        panel.add(declineFriendRequest);
                                    } else {
                                        // else, we know that they havent requested us, and we havent requested them
                                        JAButton requestFriend = new JAButton("Send Friend Request", username, Action.SendFriendRequest);
                                        requestFriend.addActionListener(actionListener);
                                        panel.add(requestFriend);
                                    }
                                } else {
                                    showConnectionError();
                                }
                            } else {
                                // if we have requested, give option to cancel request
                                JAButton cancelRequest = new JAButton("Cancel Friend Request", username, Action.CancelFriendRequest);
                                cancelRequest.addActionListener(actionListener);
                                panel.add(cancelRequest);
                            }
                        } else {
                            showConnectionError();
                        }
                    }
                } else  {
                    showConnectionError();
                }
            }

            content.add(panel);
            profileWindow.setTitle("Profile Menu");
            profileWindow.setSize(600, 400);
            profileWindow.setLocationRelativeTo(null);
            profileWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            profileWindow.pack();
            profileWindow.setVisible(true);
        } else if (status.equals("usernameNotFound")) {
            JOptionPane.showMessageDialog(null, "Username " + username + " could not be found!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            showConnectionError();
        }
    }

    /*
        * Shows the window to edit current user's profile
        * Note: this only allows the user to edit ONLY their own profile
        * unlike the other functions that can display for different users
    */
    public static void showEditProfile() {
        JFrame editProfile = new JFrame();
        Container content = editProfile.getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel header = new JLabel("Edit Account", SwingConstants.CENTER);
        header.setFont(titleFont);
        content.add(header);
        content.add(Box.createVerticalStrut(10));

        JPanel emailPanel = new JPanel(new BorderLayout());
        JLabel emailLabel = new JLabel("Email:");
        emailTxtField = new JTextField(currentUser.getEmail(), 20);
        emailPanel.add(emailLabel, BorderLayout.WEST);
        emailPanel.add(emailTxtField, BorderLayout.EAST);
        content.add(emailPanel);
        content.add(Box.createVerticalStrut(10));

        JPanel phoneNumberPanel = new JPanel(new BorderLayout());
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberTxtField = new JTextField(currentUser.getPhoneNumber(), 15);
        phoneNumberPanel.add(phoneNumberLabel, BorderLayout.WEST);
        phoneNumberPanel.add(phoneNumberTxtField, BorderLayout.EAST);
        content.add(phoneNumberPanel);
        content.add(Box.createVerticalStrut(10));

        JPanel bioPanel = new JPanel(new BorderLayout());
        JLabel bioLabel = new JLabel("Biography:");
        bioTxtField = new JTextField(currentUser.getBio(), 30);
        bioPanel.add(bioLabel, BorderLayout.WEST);
        bioPanel.add(bioTxtField, BorderLayout.EAST);
        content.add(bioPanel);
        content.add(Box.createVerticalStrut(10));

        JPanel interestsPanel = new JPanel(new BorderLayout());
        JLabel interestsLabel = new JLabel("Interests:");
        interestsTxtField = new JTextField(currentUser.getInterests(), 30);
        interestsPanel.add(interestsLabel, BorderLayout.WEST);
        interestsPanel.add(interestsTxtField, BorderLayout.EAST);
        content.add(interestsPanel);
        content.add(Box.createVerticalStrut(10));

        JLabel header2 = new JLabel("If you are changing username or password, you must enter old password!", SwingConstants.CENTER);
        content.add(header2);
        content.add(Box.createVerticalStrut(10));

        JPanel oldPasswordPanel = new JPanel(new BorderLayout());
        JLabel oldPasswordLabel = new JLabel("Old Password:");
        oldPasswordTxtField = new JTextField("", 12);
        oldPasswordPanel.add(oldPasswordLabel, BorderLayout.WEST);
        oldPasswordPanel.add(oldPasswordTxtField, BorderLayout.EAST);
        content.add(oldPasswordPanel);
        content.add(Box.createVerticalStrut(10));

        JPanel usernamePanel = new JPanel(new BorderLayout());
        JLabel usernameLabel = new JLabel("New Username:");
        newUsernameTxtField = new JTextField("", 12);
        usernamePanel.add(usernameLabel, BorderLayout.WEST);
        usernamePanel.add(newUsernameTxtField, BorderLayout.EAST);
        content.add(usernamePanel);
        content.add(Box.createVerticalStrut(10));

        JPanel newPasswordPanel = new JPanel(new BorderLayout());
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordTxtField = new JTextField("", 12);
        newPasswordPanel.add(newPasswordLabel, BorderLayout.WEST);
        newPasswordPanel.add(newPasswordTxtField, BorderLayout.EAST);
        content.add(newPasswordPanel);
        content.add(Box.createVerticalStrut(10));

        JPanel saveButtonPanel = new JPanel(new BorderLayout());
        JAButton saveChangesButton = new JAButton("Save Changes", Action.UpdateAccount);
        saveChangesButton.addActionListener(actionListener);
        saveButtonPanel.add(saveChangesButton, BorderLayout.CENTER);
        content.add(saveButtonPanel);

        editProfile.setTitle("Edit Account");
        editProfile.setSize(600, 400);
        editProfile.setLocationRelativeTo(null);
        editProfile.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editProfile.pack();
        editProfile.setVisible(true);
    }

    /*
        Friends list window shows a list of the user's current friends,
        friend requests, and requested friends
    */
    public static void showFriendsList(String username) {
        Object[] response = ClientRequest.sendToServer(new String[] { "getUser", username });
        String status = (String) response[0];
        if (status.equals("success")) {
            Account user = (Account) response[1];
            JFrame friendsList = new JFrame();
            Container content = friendsList.getContentPane();
            content.setLayout(new BorderLayout());

            JLabel header = new JLabel("Friends List of " + username, SwingConstants.CENTER);
            header.setFont(titleFont);
            content.add(header, BorderLayout.NORTH);

            // displaying all the current friends
            JPanel currentFriendsPanel = new JPanel();
            currentFriendsPanel.setBorder(padding);
            currentFriendsPanel.setLayout(new BoxLayout(currentFriendsPanel, BoxLayout.Y_AXIS));
            JLabel friendsHeader = new JLabel("Friends", SwingConstants.CENTER);
            friendsHeader.setFont(subTitleFont);
            currentFriendsPanel.add(friendsHeader, BorderLayout.NORTH);
            if (user.getFriends().size() != 0) {
                for (Account friend : user.getFriends()) {
                    JAButton friendButton = new JAButton(friend.getUsername(), friend.getUsername(),
                            Action.ViewProfile);
                    friendButton.addActionListener(actionListener);
                    currentFriendsPanel.add(friendButton, BorderLayout.CENTER);
                }
            } else {
                JLabel emptyList = new JLabel("You currently have no friends :(");
                currentFriendsPanel.add(emptyList);
            }
            content.add(currentFriendsPanel, BorderLayout.WEST);

            if (username.equals(currentUser.getUsername())) {
                // displaying all friend requests
                JPanel friendRequestsPanel = new JPanel();
                friendRequestsPanel.setBorder(padding);
                friendRequestsPanel.setLayout(new BoxLayout(friendRequestsPanel, BoxLayout.Y_AXIS));
                JLabel friendRequestsHeader = new JLabel("Friend Requests", SwingConstants.CENTER);
                friendRequestsPanel.add(friendRequestsHeader, BorderLayout.NORTH);
                if (user.getFriendRequests().size() != 0) {
                    for (Account friendRequest : user.getFriendRequests()) {
                        JAButton friendButton = new JAButton(friendRequest.getUsername(), friendRequest.getUsername(),
                                Action.ViewProfile);
                        friendButton.addActionListener(actionListener);
                        friendRequestsPanel.add(friendButton, BorderLayout.CENTER);
                    }
                } else {
                    JLabel emptyList = new JLabel("You currently have no incoming friend requests.");
                    friendRequestsPanel.add(emptyList);
                }
                content.add(friendRequestsPanel, BorderLayout.CENTER);

                // displaying all requested friends
                JPanel requestedFriendsPanel = new JPanel();
                requestedFriendsPanel.setBorder(padding);
                requestedFriendsPanel.setLayout(new BoxLayout(requestedFriendsPanel, BoxLayout.Y_AXIS));
                JLabel requestedFriendHeader = new JLabel("Requested Friends", SwingConstants.CENTER);
                requestedFriendsPanel.add(requestedFriendHeader, BorderLayout.NORTH);
                if (user.getRequestedFriends().size() != 0) {
                    for (Account requestedFriend : user.getRequestedFriends()) {
                        JAButton friendButton = new JAButton(requestedFriend.getUsername(), requestedFriend.getUsername(),
                                Action.ViewProfile);
                        friendButton.addActionListener(actionListener);
                        requestedFriendsPanel.add(friendButton, BorderLayout.CENTER);
                    }
                } else {
                    JLabel emptyList = new JLabel("You currently have no outgoing friend requests.");
                    requestedFriendsPanel.add(emptyList);
                }
                content.add(requestedFriendsPanel, BorderLayout.EAST);
            }

            friendsList.setTitle("Friends List");
            friendsList.setSize(600, 700);
            friendsList.setLocationRelativeTo(null);
            friendsList.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            friendsList.pack();
            friendsList.setVisible(true);
        } else if (status.equals("usernameNotFound")) {
            JOptionPane.showMessageDialog(null, "Username " + username + " could not be found!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            showConnectionError();
        }
    }

    public static void showSearchMenu() {
        // initial setup, layout, and padding
        JFrame searchMenu = new JFrame();
        Container content = searchMenu.getContentPane();
        JPanel panel = new JPanel();
        panel.setBorder(padding);
        panel.setLayout(new BorderLayout());

        // title header
        JLabel header = new JLabel("Search for Users", SwingConstants.CENTER);
        header.setFont(titleFont);
        panel.add(header, BorderLayout.NORTH);

        // search panel. Shows label, search bar, and search button
        JPanel searchBarPanel = new JPanel();
        searchBarPanel.setBorder(padding);
        searchBarPanel.setLayout(new BoxLayout(searchBarPanel, BoxLayout.X_AXIS));
        JLabel searchTitle = new JLabel("Enter username to search:");
        searchField = new JTextField("", 15);
        JAButton searchButton = new JAButton("Search", Action.SearchUsers);
        searchButton.addActionListener(actionListener);
        searchBarPanel.add(searchTitle);
        searchBarPanel.add(Box.createHorizontalStrut(10));
        searchBarPanel.add(searchField);
        searchBarPanel.add(Box.createHorizontalStrut(10));
        searchBarPanel.add(searchButton);

        // finalizing and then showing the window
        panel.add(searchBarPanel, BorderLayout.CENTER);
        content.add(panel);
        searchMenu.setTitle("Search Menu");
        searchMenu.setSize(600, 400);
        searchMenu.setLocationRelativeTo(null);
        searchMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchMenu.pack();
        searchMenu.setVisible(true);
    }

    public static void showSearchResults(String searchWord) {
        Object response[] = ClientRequest.sendToServer(new String[] { "searchUsers", searchWord });
        String status = (String) response[0];
        if (status.equals("success")) {
            // If there are results, show them. Otherewise, no results, show a simple message
            ArrayList<Account> users = (ArrayList<Account>) response[1];
            if (users.size() != 0) {
                // setting up the window, layout, and padding
                JFrame resultsWindow = new JFrame();
                Container content = resultsWindow.getContentPane();
                JPanel panel = new JPanel();
                panel.setBorder(padding);
                panel.setLayout(new BorderLayout());
                // setting up title
                JLabel header = new JLabel("Search Results for: " + searchWord, SwingConstants.CENTER);
                header.setFont(titleFont);
                panel.add(header, BorderLayout.NORTH);
                // setting up grid panel to display users
                JPanel resultsPanel = new JPanel();
                resultsPanel.setBorder(padding);
                resultsPanel.setLayout(new GridLayout(0, 4));
                // display all users
                for (Account user : users) {
                    JAButton userButton = new JAButton(user.getUsername(), user.getUsername(), Action.ViewProfile);
                    userButton.addActionListener(actionListener);
                    resultsPanel.add(userButton);
                }
                panel.add(resultsPanel, BorderLayout.CENTER);
                // showing window
                content.add(panel);
                resultsWindow.setTitle("Search Results");
                resultsWindow.setSize(600, 400);
                resultsWindow.setLocationRelativeTo(null);
                resultsWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                resultsWindow.pack();
                resultsWindow.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "No users found!", "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            showConnectionError();
        }
    }

    // shown whenever we are unable to connect to the server
    public static void showConnectionError() {
        JOptionPane.showMessageDialog(null, "Could not connect to server!", "Connection Error!", JOptionPane.ERROR_MESSAGE);
    }
}
