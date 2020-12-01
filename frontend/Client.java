package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import backend.Account;

/**
 * Client class
 *
 * Connects to the server as a client instance Performs a login or account
 * creation Loads a GUI to view friends and a profile
 *
 * @author Team 15-3 CS 180 - Merge
 * @version November 26, 2020
 */

public class Client { // TODO create friend and profile menus, establish all server requests
    // user account variables
    private static String accountName;
    private static String pass;
    private static Account user;
    // static request arrays for requests with no parameters
    public static final String[] createSession = { "createSession" };
    public static final String[] closeSession = { "closeSession" };

    private static JButton editProfileButton;
    private static JButton saveChangesButton;
    private static JLabel phoneNumberLabel;
    private static JTextField phoneNumberTxtField;
    private static JLabel emailLabel;
    private static JTextField emailTxtField;
    private static JLabel bioLabel;
    private static JTextField bioTxtField;
    private static JLabel interestsLabel;
    private static JTextField interestsTxtField;
    private static JLabel usernameLabel;
    private static JTextField usernameTxtField;
    private static JLabel passwordLabel;
    private static JTextField passwordTxtField;

    // io declaration
    public static Socket socket;
    public static ObjectOutputStream writer;
    public static ObjectInputStream reader;

    public static String serverHost;
    public static int serverPort;

    public static ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof UsernameButton) {
                UsernameButton clickedButton = (UsernameButton) e.getSource();
                String friendType = clickedButton.getFriendType();
                switch (friendType) {
                    // list 1 - friends
                    case "friend" -> actionsMenu(clickedButton.getAccount(), 1);
                    // list 2 - outgoing friend requests
                    case "friendRequest" -> actionsMenu(clickedButton.getAccount(), 2);
                    // list 3 - incoming friend requests
                    case "requestedFriend" -> actionsMenu(clickedButton.getAccount(), 3);
                    // list 4 - found in search menu
                    case "searched" -> actionsMenu(clickedButton.getAccount(), 4);
                }
            }
            if (e.getSource() instanceof ActionButton) {
                ActionButton clickedAction = (ActionButton) e.getSource();
                int actionType = clickedAction.getActionType();
                String username = clickedAction.getAccount().getUsername();
                switch (actionType) {
                    case 0 -> {
                        try {
                            viewProfile(username);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }
                    }
                    case 1 -> {
                        try {
                            removeFriend(username);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }
                    }
                    case 2 -> {
                        try {
                            cancelFriendRequest(username);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }
                    }
                    case 3 -> {
                        try {
                            acceptFriendRequest(username);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }
                    }
                    case 4 -> {
                        try {
                            declineFriendRequest(username);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }
                    }
                    case 5 -> {
                        try {
                            sendFriendRequest(username);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }
                    }
                }
            }
            if (e.getSource() == editProfileButton) {
                editProfile();
            }
            if (e.getSource() == saveChangesButton) {
                String oldUsername = user.getUsername();
                String newUsername;
                boolean changedUsername = false;
                if (usernameTxtField.getText().equals(oldUsername)) {
                    newUsername = oldUsername;
                } else {
                    newUsername = usernameTxtField.getText();
                    changedUsername = true;
                }

                String oldPassword = user.getPassword();
                String newPassword;
                boolean changedPassword = false;
                if (passwordTxtField.getText().equals(oldPassword)) {
                    newPassword = oldPassword;
                } else {
                    newPassword = passwordTxtField.getText();
                    changedPassword = true;
                }

                String oldPhoneNumber = user.getPhoneNumber();
                String newPhoneNumber;
                if (phoneNumberTxtField.getText().equals(oldPhoneNumber)) {
                    newPhoneNumber = oldPhoneNumber;
                } else {
                    newPhoneNumber = phoneNumberTxtField.getText();
                }

                String oldEmail = user.getEmail();
                String newEmail;
                if (emailTxtField.getText().equals(oldEmail)) {
                    newEmail = oldEmail;
                } else {
                    newEmail = emailTxtField.getText();
                }

                String oldBio = user.getBio();
                String newBio;
                if (bioTxtField.getText().equals(oldBio)) {
                    newBio = oldBio;
                } else {
                    newBio = bioTxtField.getText();
                }

                String oldInterests = user.getInterests();
                String newInterests;
                if (interestsTxtField.getText().equals(oldInterests)) {
                    newInterests = oldInterests;
                } else {
                    newInterests = interestsTxtField.getText();
                }

                if (changedUsername || changedPassword) {
                    try {
                        updateAccount(newEmail, newPhoneNumber, newBio, newInterests, oldPassword, newUsername,
                                newPassword);

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                } else {
                    try {
                        updateAccount(newEmail, newPhoneNumber, newBio, newInterests);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
                usernameTxtField = new JTextField(user.getUsername(), 12);
                passwordTxtField = new JTextField(user.getPassword(), 12);
                phoneNumberTxtField = new JTextField(user.getPhoneNumber(), 15);
                emailTxtField = new JTextField(user.getEmail(), 20);
                bioTxtField = new JTextField(user.getBio(), 30);
                interestsTxtField = new JTextField(user.getInterests(), 30);
            }
        }
    };

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {

        // find the server info so that the server host can be remote, or on a different port
        // serverHost = JOptionPane.showInputDialog(null, "Server Hostname", JOptionPane.QUESTION_MESSAGE);
        // serverPort = Integer.parseInt(JOptionPane.showInputDialog(null, "Server Port", JOptionPane.QUESTION_MESSAGE));
        serverHost = "localhost";
        serverPort = 4242;
        // connects to the server and shows confirmation
        // socket = new Socket(serverHost, serverPort);
        // JOptionPane.showInternalMessageDialog(null, "Successfully connected to server", "Connection Established",
        //         JOptionPane.INFORMATION_MESSAGE);

        // establishes IO method with server
        // objectOut = new ObjectOutputStream(socket.getOutputStream());
        // reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // objectInput = new ObjectInputStream(socket.getInputStream());

        // creates server session
        // objectOut.writeObject(createSession);
        // objectOut.flush();
        // String sessionCode = reader.readLine();
        // System.out.println(sessionCode);

        boolean hasAccount = false;
        // creates a login or account creation request to send to the server
        String[] makeNew = { "Login", "Create new Account" };
        do {
            String response = (String) JOptionPane.showInputDialog(null, "Login Prompt", "",
                    JOptionPane.QUESTION_MESSAGE, null, makeNew, makeNew[0]);
            accountName = JOptionPane.showInputDialog(null, "Account Name", JOptionPane.QUESTION_MESSAGE);
            pass = JOptionPane.showInputDialog(null, "Password", JOptionPane.QUESTION_MESSAGE);

            if (response.equals("Login")) {
                // send account name and pass to login
                hasAccount = loginUser();

            } else {
                // send account name and pass and do account setup
                hasAccount = createAccount();

            }

            // if connection is established, and account login is valid / valid account
            // creation, then hasAccount becomes true.

        } while (!hasAccount);
        // at this point client has connected to server and logged in

        // disconnect to better allow multiple clients, will reconnect for IO with
        // server
        disconnectServer();

        // base menu for client, when running the client is still running
        // once the Close option is chosen, the client closes all resources and
        // terminates
        // the Delete Account option also calls the close option if the account is
        // succesfully deleted
        String[] menus = { "Friends", "Profile", "Search", "Close Client", "Delete Account" };

        int counter = 0;
        do {
            counter++;
            String menuChoice = (String) JOptionPane.showInputDialog(null, "Home", "", JOptionPane.QUESTION_MESSAGE,
                    null, menus, menus[0]);
            switch (menuChoice) {
                case "Friends":
                    friendMenu();
                    break;
                case "Profile":
                    profileMenu();
                    break;
                case "Delete Account":
                    if (deleteAccount()) {
                        return;
                    }
                case "Search":
                    searchMenu();
                    break;
                case "Close Client":
                    // close all client resources here

                    closeClient();
                    return;
            }

            if (counter > 10000) {
                counter = 0;
                update();
            }

        } while (true);

    }

    public static void searchMenu() throws IOException, ClassNotFoundException {
        JFrame search = new JFrame();
        Container content = search.getContentPane();
        content.setLayout(new BorderLayout());
        JLabel header = new JLabel("All Users", SwingConstants.CENTER);
        content.add(header, BorderLayout.NORTH);
        JPanel usersPanel = new JPanel();
        ArrayList<Account> allUsers = getAllUsers();
        if (allUsers.size() != 0) {
            for (int i = 0; i < allUsers.size(); i++) {
                if (!allUsers.get(i).getUsername().equals(user.getUsername())) {
                    UsernameButton eachUser = new UsernameButton(allUsers.get(i).getUsername(), "searched",
                            allUsers.get(i));
                    eachUser.addActionListener(actionListener);
                    usersPanel.add(eachUser);
                }
            }
        } else {
            JLabel emptyList = new JLabel("There are no users.");
            usersPanel.add(emptyList);
        }

    }

    // method to open a window with the friend menu
    public static void friendMenu() {
        JFrame friendFrame = new JFrame();
        boolean menuOpen = true;
        // int counter = 0;
        // do {
        //     counter++;
        //     if (counter > 10000) {
        //         // update whatever is being viewed here
        //     }
        // } while (menuOpen);

        Container content = friendFrame.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel headerGUI = new JPanel();
        JLabel friendRequestsHeader = new JLabel("Incoming Friend Requests", SwingConstants.CENTER);
        JLabel requestedFriendsHeader = new JLabel("Outgoing Friend Requests", SwingConstants.CENTER);
        JLabel friendsHeader = new JLabel("Friends", SwingConstants.CENTER);
        headerGUI.add(friendsHeader);
        headerGUI.add(friendRequestsHeader);
        headerGUI.add(requestedFriendsHeader);
        content.add(headerGUI, BorderLayout.NORTH);

        JPanel friendRequestsGUI = new JPanel(new FlowLayout());
        ArrayList<Account> friendRequestsList = user.getFriendRequests();
        if (friendRequestsList.size() != 0) {
            for (int i = 0; i < friendRequestsList.size(); i++) {
                UsernameButton eachFriendRequest = new UsernameButton(friendRequestsList.get(i).getUsername(),
                        "friendRequest", friendRequestsList.get(i));
                eachFriendRequest.addActionListener(actionListener);
                friendRequestsGUI.add(eachFriendRequest);
            }
        } else {
            JLabel emptyList = new JLabel("You currently have no incoming friend requests.");
            friendRequestsGUI.add(emptyList);
        }
        content.add(friendRequestsGUI, BorderLayout.CENTER);

        JPanel requestedFriendsGUI = new JPanel(new FlowLayout());
        ArrayList<Account> requestedFriendsList = user.getRequestedFriends();
        if (requestedFriendsList.size() != 0) {
            for (int i = 0; i < requestedFriendsList.size(); i++) {
                UsernameButton eachRequestedFriend = new UsernameButton(requestedFriendsList.get(i).getUsername(),
                        "requestedFriend", requestedFriendsList.get(i));
                eachRequestedFriend.addActionListener(actionListener);
                requestedFriendsGUI.add(eachRequestedFriend);
            }
        } else {
            JLabel emptyList = new JLabel("You currently have no outgoing friend requests.");
            requestedFriendsGUI.add(emptyList);
        }
        content.add(requestedFriendsGUI, BorderLayout.EAST);

        JPanel friendsGUI = new JPanel(new FlowLayout());
        ArrayList<Account> friendsList = user.getFriends();
        if (friendsList.size() != 0) {
            for (int i = 0; i < friendsList.size(); i++) {
                UsernameButton eachFriend = new UsernameButton(friendsList.get(i).getUsername(), "friend",
                        friendsList.get(i));
                eachFriend.addActionListener(actionListener);
                friendsGUI.add(eachFriend);
            }
        } else {
            JLabel emptyList = new JLabel("You currently have no friends.");
            friendsGUI.add(emptyList);
        }
        content.add(friendsGUI, BorderLayout.WEST);

        friendFrame.setSize(600, 400);
        friendFrame.setLocationRelativeTo(null);
        friendFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // might need to remove this later
        friendFrame.pack();
        friendFrame.setVisible(true);

    }

    public static void actionsMenu(Account account, int list) {
        JFrame menu = new JFrame();
        Container content = menu.getContentPane();
        content.setLayout(new FlowLayout());
        JLabel header = new JLabel(account.getUsername(), SwingConstants.CENTER);
        JPanel buttonPanel = new JPanel();
        ActionButton viewProfile = new ActionButton("View Profile", account, 0);
        viewProfile.addActionListener(actionListener);
        buttonPanel.add(viewProfile);
        switch (list) {
            // from friend menu
            case 1 -> {
                // makes delete friend button
                ActionButton removeFriend = new ActionButton("Remove Friend", account, 1);
                removeFriend.addActionListener(actionListener);
                buttonPanel.add(removeFriend);
            }
            // from outgoing friend requests menu
            case 2 -> {
                // makes cancel friend request button
                ActionButton cancelFriendRequest = new ActionButton("Cancel Friend Request", account, 2);
                cancelFriendRequest.addActionListener(actionListener);
                buttonPanel.add(cancelFriendRequest);
            }
            // from incoming friend requests menu
            case 3 -> {
                // makes accept and decline friend request buttons
                ActionButton acceptFriendRequest = new ActionButton("Accept Friend Request", account, 3);
                acceptFriendRequest.addActionListener(actionListener);
                ActionButton declineFriendRequest = new ActionButton("Decline Friend Request", account, 4);
                declineFriendRequest.addActionListener(actionListener);
                buttonPanel.add(acceptFriendRequest);
                buttonPanel.add(declineFriendRequest);
            }
            // from search menu
            case 4 -> {
                // makes send friend request button
                ActionButton sendFriendRequest = new ActionButton("Send Friend Request", account, 5);
                sendFriendRequest.addActionListener(actionListener);
                buttonPanel.add(sendFriendRequest);
            }
        }

        content.add(header, BorderLayout.NORTH);
        content.add(buttonPanel, BorderLayout.CENTER);

        menu.setSize(600, 400);
        menu.setLocationRelativeTo(null);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.pack();
        menu.setVisible(true);

    }

    // method to open window to view other account's profile
    public static void viewProfile(String username) throws IOException, ClassNotFoundException {
        Account account = getUser(username);
        JFrame profile = new JFrame();
        Container content = profile.getContentPane();
        content.setLayout(new BorderLayout());
        JLabel email = new JLabel(account.getEmail(), SwingConstants.CENTER);
        JLabel phoneNumber = new JLabel(account.getPhoneNumber(), SwingConstants.CENTER);
        JLabel bio = new JLabel(account.getBio(), SwingConstants.CENTER);
        JLabel interests = new JLabel(account.getInterests(), SwingConstants.CENTER);
        String friendCount = String.format("%d friends", account.getFriends().size());
        JLabel friends = new JLabel(friendCount, SwingConstants.CENTER);
        JLabel header = new JLabel(username, SwingConstants.CENTER);
        JPanel contactInfo = new JPanel(new BorderLayout());
        contactInfo.add(phoneNumber, BorderLayout.NORTH);
        contactInfo.add(email, BorderLayout.SOUTH);
        content.add(header, BorderLayout.NORTH);
        content.add(contactInfo, BorderLayout.WEST);
        content.add(bio, BorderLayout.CENTER);
        content.add(interests, BorderLayout.EAST);
        content.add(friends, BorderLayout.SOUTH);

        profile.setSize(600, 400);
        profile.setLocationRelativeTo(null);
        profile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        profile.pack();
        profile.setVisible(true);
    }

    // method to open a window with the profile menu
    public static void profileMenu() throws ClassNotFoundException, IOException {
        JFrame profileFrame = new JFrame();
        // boolean menuOpen = true;
        // int counter = 0;
        // do {
        //     counter++;
        //     if (counter > 10000) {
        //         update();
        //     }
        // } while (menuOpen);

        JFrame profile = new JFrame();
        Container content = profile.getContentPane();
        content.setLayout(new BorderLayout());
        JLabel email = new JLabel(user.getEmail(), SwingConstants.CENTER);
        JLabel phoneNumber = new JLabel(user.getPhoneNumber(), SwingConstants.CENTER);
        JLabel bio = new JLabel(user.getBio(), SwingConstants.CENTER);
        JLabel interests = new JLabel(user.getInterests(), SwingConstants.CENTER);

        String friendCount = String.format("%d friends", user.getFriends().size());
        JLabel friends = new JLabel(friendCount, SwingConstants.CENTER);
        String incomingRequestsCount = String.format("%d incoming friend requests", user.getFriendRequests().size());
        JLabel incomingRequests = new JLabel(incomingRequestsCount, SwingConstants.CENTER);
        String outgoingRequestsCount = String.format("%d outgoing friend requests", user.getRequestedFriends().size());
        JLabel outgoingRequests = new JLabel(outgoingRequestsCount, SwingConstants.CENTER);
        JPanel allFriendsInfo = new JPanel(new BorderLayout());
        allFriendsInfo.add(incomingRequests, BorderLayout.WEST);
        allFriendsInfo.add(friends, BorderLayout.CENTER);
        allFriendsInfo.add(outgoingRequests, BorderLayout.EAST);

        JPanel header = new JPanel();
        JLabel name = new JLabel(user.getUsername(), SwingConstants.CENTER);
        header.add(name);
        editProfileButton = new JButton("EDIT PROFILE");
        header.add(editProfileButton);

        JPanel contactInfo = new JPanel(new BorderLayout());
        contactInfo.add(phoneNumber, BorderLayout.NORTH);
        contactInfo.add(email, BorderLayout.SOUTH);
        content.add(header, BorderLayout.NORTH);
        content.add(contactInfo, BorderLayout.WEST);
        content.add(bio, BorderLayout.CENTER);
        content.add(interests, BorderLayout.EAST);

        content.add(allFriendsInfo, BorderLayout.SOUTH);

        profile.setSize(600, 400);
        profile.setLocationRelativeTo(null);
        profile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        profile.pack();
        profile.setVisible(true);

        // updateAccount method implemented
    }

    // method to open window to edit profile
    public static void editProfile() {
        JFrame profile = new JFrame();
        Container content = profile.getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernameLabel = new JLabel("Username:");
        usernameTxtField = new JTextField(user.getUsername(), 12);
        usernamePanel.add(usernameLabel, BorderLayout.WEST);
        usernamePanel.add(usernameTxtField, BorderLayout.EAST);
        content.add(usernamePanel);

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordLabel = new JLabel("Password:");
        passwordTxtField = new JTextField(user.getPassword(), 12);
        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(passwordTxtField, BorderLayout.EAST);
        content.add(passwordPanel);

        JPanel phoneNumberPanel = new JPanel(new BorderLayout());
        phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberTxtField = new JTextField(user.getPhoneNumber(), 15);
        phoneNumberPanel.add(phoneNumberLabel, BorderLayout.WEST);
        phoneNumberPanel.add(phoneNumberTxtField, BorderLayout.EAST);
        content.add(phoneNumberPanel);

        JPanel emailPanel = new JPanel(new BorderLayout());
        emailLabel = new JLabel("Email:");
        emailTxtField = new JTextField(user.getEmail(), 20);
        emailPanel.add(emailLabel, BorderLayout.WEST);
        emailPanel.add(emailTxtField, BorderLayout.EAST);
        content.add(emailPanel);

        JPanel bioPanel = new JPanel(new BorderLayout());
        bioLabel = new JLabel("Biography:");
        bioTxtField = new JTextField(user.getBio(), 30);
        bioPanel.add(bioLabel, BorderLayout.WEST);
        bioPanel.add(bioTxtField, BorderLayout.EAST);
        content.add(bioPanel);

        JPanel interestsPanel = new JPanel(new BorderLayout());
        interestsLabel = new JLabel("Interests:");
        interestsTxtField = new JTextField(user.getInterests(), 30);
        interestsPanel.add(interestsLabel, BorderLayout.WEST);
        interestsPanel.add(interestsTxtField, BorderLayout.EAST);
        content.add(interestsPanel);

        JPanel saveButtonPanel = new JPanel(new BorderLayout());
        saveChangesButton = new JButton("Save Changes");
        saveChangesButton.addActionListener(actionListener);
        saveButtonPanel.add(saveChangesButton, BorderLayout.CENTER);
        content.add(saveButtonPanel);

        profile.setSize(600, 400);
        profile.setLocationRelativeTo(null);
        profile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        profile.pack();
        profile.setVisible(true);
    }

    // method to log in an already existing user
    public static boolean loginUser() throws IOException, ClassNotFoundException {
        connectServer();
        boolean hasAccount = false;
        // creates an array to send to server as the request
        String[] accountInfo = { "loginUser", accountName, pass };
        writer.writeObject(accountInfo);
        // reads the status code and responds according to success or the error code
        Object[] response = (Object[]) reader.readObject();
        String status = (String) response[0];
        switch (status) {
            // success code, initializes the user account
            case "success":
                hasAccount = true;
                user = (Account) response[1];
                break;
            // error code for an invalid username, displays error, then returns to login
            // loop
            case "usernameNotFound":
                JOptionPane.showInternalMessageDialog(null, "Username does not exist", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error code for an invalid password, displays error, then returns to login
            // loop
            case "incorrectPassword":
                JOptionPane.showInternalMessageDialog(null, "Incorrect Password", "Password Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }
        disconnectServer();
        return hasAccount;
    }

    public static boolean createAccount() throws ClassNotFoundException, IOException {
        connectServer();
        boolean hasAccount = false;
        // section for contact info
        String email = JOptionPane.showInputDialog(null, "Please enter your email", JOptionPane.QUESTION_MESSAGE);
        String phone = JOptionPane.showInputDialog(null, "Please enter your phone number",
                JOptionPane.QUESTION_MESSAGE);

        // section to gain all other needed account info to create the Account
        String bio = JOptionPane.showInputDialog(null, "Please enter your bio", JOptionPane.QUESTION_MESSAGE);
        String interests = JOptionPane.showInputDialog(null, "Please enter some of your interests",
                JOptionPane.QUESTION_MESSAGE);

        String[] accountParams = { "createAccount", accountName, pass, email, phone, bio, interests };
        writer.writeObject(accountParams);
        // reads the status code and responds according to success or the error code
        Object[] response = (Object[]) reader.readObject();
        String status = (String) response[0];
        switch (status) {
            // success code, initializes user
            case "success":
                JOptionPane.showInternalMessageDialog(null, "Successfully created account", "Account Created!",
                        JOptionPane.INFORMATION_MESSAGE);
                user = (Account) response[1];
                hasAccount = true;
                break;
            // error for a username already existing, returns to account initialization loop
            case "usernameExists":
                JOptionPane.showInternalMessageDialog(null, "Username already exists", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error for empty fields in account info, returns to account initialization loop
            case "emptyFields":
                JOptionPane.showInternalMessageDialog(null, "You must fill every field to create an account",
                        "User Error!", JOptionPane.ERROR_MESSAGE);
                break;
        }
        disconnectServer();
        return hasAccount;
    }

    public static void updateAccount(String email, String phoneNo, String bio, String interests)
            throws IOException, ClassNotFoundException {
        connectServer();
        String[] updateString = { "updateAccount", accountName, email, phoneNo, bio, interests };
        writer.writeObject(updateString);
        // reads the status code and responds according to success or the error code
        Object[] response = (Object[]) reader.readObject();
        String status = (String) response[0];
        switch (status) {
            // success code, updates the Client account
            case "success":
                user = (Account) response[1];
                break;
            // error for a username that could not be found, Client account is not updated
            case "usernameNotFound":
                JOptionPane.showInternalMessageDialog(null, "Username could not be found!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error for an incorrect password, Client account is not updated
            case "incorrectPassword":
                JOptionPane.showInternalMessageDialog(null, "Password was not correct!", "Password Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error for attempting to change to a username that already exists, Client
            // account is not updated
            case "usernameExists":
                JOptionPane.showInternalMessageDialog(null, "Username already exists!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error for attempting to use an invalid username, Client account is not
            // updated
            case "invalidUsername":
                JOptionPane.showInternalMessageDialog(null, "Username is invalid!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error for including empty account information fields, Client account is not
            // updated
            case "emptyFields":
                JOptionPane.showInternalMessageDialog(null, "Account information cannot be empty!", "Account Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }
        disconnectServer();
    }

    public static void updateAccount(String email, String phoneNo, String bio, String interests, String pass,
            String newUsername, String newPassword) throws IOException, ClassNotFoundException {
        connectServer();
        String[] updateString = { "updateAccount", accountName, email, phoneNo, bio, interests, newUsername,
                newPassword };
        writer.writeObject(updateString);
        // reads the status code and responds according to success or the error code
        Object[] response = (Object[]) reader.readObject();
        String status = (String) response[0];
        switch (status) {
            // success code, updates the Client account
            case "success":
                user = (Account) response[1];
                break;
            // error for a username that could not be found, Client account is not updated
            case "usernameNotFound":
                JOptionPane.showInternalMessageDialog(null, "Username could not be found!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error for an incorrect password, Client account is not updated
            case "incorrectPassword":
                JOptionPane.showInternalMessageDialog(null, "Password was not correct!", "Password Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error for attempting to change to a username that already exists, Client
            // account is not updated
            case "usernameExists":
                JOptionPane.showInternalMessageDialog(null, "Username already exists!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error for attempting to use an invalid username, Client account is not
            // updated
            case "invalidUsername":
                JOptionPane.showInternalMessageDialog(null, "Username is invalid!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error for including empty account information fields, Client account is not
            // updated
            case "emptyFields":
                JOptionPane.showInternalMessageDialog(null, "Account information cannot be empty!", "Account Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }
        disconnectServer();
    }

    public static boolean isFriendsWith(String username, String username2) throws IOException, ClassNotFoundException {
        connectServer();
        String[] isFriends = { "isFriendsWith", username, username2 };
        writer.writeObject(isFriends);
        // reads the status code and responds according to success or the error code
        Object[] response = (Object[]) reader.readObject();
        String status = (String) response[0];
        switch (status) {
            // success code, returns if two Accounts are friends
            case "success":
                boolean isFriend = (boolean) response[1];
                disconnectServer();
                return isFriend;
            // error code for an invalid user, returns that Accounts are not friends as at least one is invalid
            case "usernameNotFound":
                JOptionPane.showInternalMessageDialog(null, "Username " + username + " is not found!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error code for an invalid user, returns that Accounts are not friends as at least one is invalid
            case "username2NotFound":
                JOptionPane.showInternalMessageDialog(null, "Username " + username2 + " is not found!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error code for an invalid user, returns that Accounts are not friends as at least one is invalid
            case "emptyFields":
                JOptionPane.showInternalMessageDialog(null, "Fields are empty!", "Input Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }
        disconnectServer();
        return false;
    }

    public static void sendFriendRequest(String username) throws IOException, ClassNotFoundException {
        connectServer();
        String[] friendRequest = { "sendFriendRequest", accountName, username };
        writer.writeObject(friendRequest);
        // reads the status code and responds according to success or the error code
        Object[] response = (Object[]) reader.readObject();
        String status = (String) response[0];
        switch (status) {
            // success code, updates Account with updated friend requests
            case "success":
                user = (Account) response[1];
                break;
            // error code for an invalid username, Account is not updated
            case "usernameNotFound":
                JOptionPane.showInternalMessageDialog(null, "Username " + accountName + " is not found!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error code for an invalid username, Account is not updated
            case "username2NotFound":
                JOptionPane.showInternalMessageDialog(null, "Username " + username + " is not found!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error code for empty fields, Account is not updated
            case "emptyFields":
                JOptionPane.showInternalMessageDialog(null, "Friend request cannot be empty!", "Input Error!",
                        JOptionPane.ERROR_MESSAGE);
        }
        disconnectServer();
    }

    public static void cancelFriendRequest(String username) throws IOException, ClassNotFoundException {
        connectServer();
        String[] friendRequest = { "cancelFriendRequest", accountName, username };
        writer.writeObject(friendRequest);
        // reads the status code and responds according to success or the error code
        Object[] response = (Object[]) reader.readObject();
        String status = (String) response[0];
        switch (status) {
            // success code, updates Account with updated friend requests
            case "success":
                user = (Account) response[1];
                break;
            // error code for an invalid username, Account is not updated
            case "usernameNotFound":
                JOptionPane.showInternalMessageDialog(null, "Username " + accountName + " is not found!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error code for an invalid username, Account is not updated
            case "username2NotFound":
                JOptionPane.showInternalMessageDialog(null, "Username " + username + " is not found!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error code for empty fields, Account is not updated
            case "emptyFields":
                JOptionPane.showInternalMessageDialog(null, "Friend request to cancel cannot be empty!", "Input Error!",
                        JOptionPane.ERROR_MESSAGE);
        }
        disconnectServer();
    }

    public static void acceptFriendRequest(String username) throws IOException, ClassNotFoundException {
        connectServer();
        String[] friendRequest = { "acceptFriendRequest", accountName, username };
        writer.writeObject(friendRequest);
        // reads the status code and responds according to success or the error code
        Object[] response = (Object[]) reader.readObject();
        String status = (String) response[0];
        switch (status) {
            // success code, updates Account with updated friends list
            case "success":
                user = (Account) response[1];
                break;
            // error code for an invalid username, Account is not updated
            case "usernameNotFound":
                JOptionPane.showInternalMessageDialog(null, "Username " + accountName + " is not found!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error code for an invalid username, Account is not updated
            case "username2NotFound":
                JOptionPane.showInternalMessageDialog(null, "Username " + username + " is not found!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error code for empty fields, Account is not updated
            case "emptyFields":
                JOptionPane.showInternalMessageDialog(null, "Friend to accept must not be empty!", "Input Error!",
                        JOptionPane.ERROR_MESSAGE);
        }
        disconnectServer();
    }

    public static void declineFriendRequest(String username) throws IOException, ClassNotFoundException {
        connectServer();
        String[] friendRequest = { "declineFriendRequest", accountName, username };
        writer.writeObject(friendRequest);
        // reads the status code and responds according to success or the error code
        Object[] response = (Object[]) reader.readObject();
        String status = (String) response[0];
        switch (status) {
            // success code, updates Account with updated friend requests
            case "success":
                user = (Account) response[1];
                break;
            // error code for an invalid username, Account is not updated
            case "usernameNotFound":
                JOptionPane.showInternalMessageDialog(null, "Username " + accountName + " is not found!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error code for an invalid username, Account is not updated
            case "username2NotFound":
                JOptionPane.showInternalMessageDialog(null, "Username " + username + " is not found!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error code for empty fiels, Account is not updated
            case "emptyFields":
                JOptionPane.showInternalMessageDialog(null, "Friend to deny must not be empty!", "Input Error!",
                        JOptionPane.ERROR_MESSAGE);
        }
        disconnectServer();
    }

    public static void removeFriend(String username) throws IOException, ClassNotFoundException {
        connectServer();
        String[] friendRequest = { "removeFriend", accountName, username };
        writer.writeObject(friendRequest);
        // reads the status code and responds according to success or the error code
        Object[] response = (Object[]) reader.readObject();
        String status = (String) response[0];
        switch (status) {
            // success code, updates Account with updated friends list
            case "success":
                user = (Account) response[1];
                break;
            // error code for an invalid username, Account is not updated
            case "usernameNotFound":
                JOptionPane.showInternalMessageDialog(null, "Username " + accountName + " is not found!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error code for an invalid username, Account is not updated
            case "username2NotFound":
                JOptionPane.showInternalMessageDialog(null, "Username " + username + " is not found!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error code for empty fiels, Account is not updated
            case "emptyFields":
                JOptionPane.showInternalMessageDialog(null, "Friend to remove must not be empty!", "Input Error!",
                        JOptionPane.ERROR_MESSAGE);
        }
        disconnectServer();
    }

    public static Account getUser(String username) throws IOException, ClassNotFoundException {
        connectServer();
        String[] request = { "getUser", username };
        writer.writeObject(request);
        // reads the status code and responds according to success or the error code
        Object[] response = (Object[]) reader.readObject();
        String status = (String) response[0];
        switch (status) {
            // success code, returns the requested user Account
            case "success":
                disconnectServer();
                return (Account) response[1];
            // error for a user that could not be found, returns null
            case "usernameNotFound":
                JOptionPane.showInternalMessageDialog(null, "Username " + username + " is not found!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                disconnectServer();
                return null;
        }
        disconnectServer();
        return null;
    }

    public static ArrayList<Account> getAllUsers() throws IOException, ClassNotFoundException {
        connectServer();
        String[] request = { "getAllUsers", user.getUsername() };
        writer.writeObject(request);
        // reads the status code and responds according to success or the error code
        Object[] response = (Object[]) reader.readObject();
        String status = (String) response[0];
        switch (status) {
            // success code, returns the requested user Account
            case "success":
                disconnectServer();
                return (ArrayList<Account>) response[1];
            // error for a user that could not be found, returns null
            default:
                JOptionPane.showInternalMessageDialog(null, "Something went wrong when getting all users.", "Error!",
                        JOptionPane.ERROR_MESSAGE);
                disconnectServer();
                return null;
        }
    }

    public static boolean hasRequested(String username, String username2) throws IOException, ClassNotFoundException {
        connectServer();
        String[] request = { "hasRequested", username, username2 };
        writer.writeObject(request);
        // reads the status code and responds according to success or the error code
        Object[] response = (Object[]) reader.readObject();
        String status = (String) response[0];
        switch (status) {
            // success code, returns if the first user has requested the second user
            case "success":
                boolean hasRequest = (boolean) response[1];
                disconnectServer();
                return hasRequest;
            // error code for a requesting user which could not be found, returns false
            case "usernameNotFound":
                JOptionPane.showInternalMessageDialog(null, "Username " + username + " is not found!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error code for a requested user which could not be found, returns false
            case "username2NotFound":
                JOptionPane.showInternalMessageDialog(null, "Username " + username2 + " is not found!", "User Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
            // error code for sending an empty field, returns false
            case "emptyFields":
                JOptionPane.showInternalMessageDialog(null, "Usernames must not be empty!", "Input Error!",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }
        disconnectServer();
        return false;
    }

    public static void connectServer() throws UnknownHostException, IOException {
        socket = new Socket(serverHost, serverPort);
        // establishes IO method with server
        writer = new ObjectOutputStream(socket.getOutputStream());
        reader = new ObjectInputStream(socket.getInputStream());
    }

    public static void disconnectServer() throws IOException {
        socket.close();
        reader.close();
        writer.close();
    }

    // TODO: get rid of all GUIss
    public static void closeClient() throws IOException {
        
    }

    public static boolean deleteAccount() throws IOException {
        connectServer();
        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?",
                "Confirmation Required", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            String[] deleteAccount = { "deleteAccount", accountName, pass };
            writer.writeObject(deleteAccount);
            disconnectServer();
            return true;
        }
        disconnectServer();
        return false;
    }

    public static void update() throws ClassNotFoundException, IOException {
        getUser(accountName);
    }

}