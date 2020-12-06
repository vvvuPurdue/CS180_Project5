package backend;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
    * ServerThread
    *
    * A server thread. Handles requests, and authentication
    * Used to avoid overloading the server
    * 
    * @author Team 15-3, CS 180 - Merge
    * @version November 23, 2020
*/

public class ServerThread extends Thread {

    private Socket socket;
    private Manager manager;

    private ObjectInputStream reader;
    private ObjectOutputStream writer;

    // When we instantiating t1he thread, use the server socket
    // and the database manager (static) assigned to us
    public ServerThread(Socket clientSocket, Manager manager) {
        this.socket = clientSocket;
        this.manager = manager;
    }

    public void run() {
        // Instantiating our readers and writers
        // Request bodies we receive will be String[]
        // We send our status codes via the writer
        // and any object data via the objectWriter
        try {
            System.out.println("Running thread");
            reader = new ObjectInputStream(socket.getInputStream());
            writer = new ObjectOutputStream(socket.getOutputStream());
            writer.flush();
            String requestType = "";
            // read request and the request type
            // The first element in the array will always be the request type
            System.out.println("Awaiting request...");
            String[] requestBody = (String[]) reader.readObject();
            requestType = requestBody[0];
            // handle the request type, read parameters, call the appropriate functions
            // first check if any fields are empty. If they are, no need to continue
            boolean fieldsEmpty = false;
            for (String field : requestBody) {
                if (field.length() == 0 && !requestType.equals("searchUsers")) {
                    sendData("emptyFields");
                    fieldsEmpty = true;
                    break;
                }
            }
            if (!fieldsEmpty) {
                // if we have fields, parse the request depending on what it is
                switch (requestType) {
                    case ("createAccount"):
                        int createStatus = manager.createAccount(requestBody[1], requestBody[2], requestBody[3],
                                requestBody[4], requestBody[5], requestBody[6]);
                        if (createStatus == 1) {
                            sendData("success", manager.getUser(requestBody[1]));
                        } else if (createStatus == -1) {
                            sendData("emptyFields"); // changed to emptyFields ... used to be invalidUsername
                        } else {
                            sendData("usernameExists");
                        }
                        break;
                    case ("updateAccount"):
                        // update account has 2 types of requestBodies it can take. They are 2 different
                        // size arrays
                        if (requestBody.length == 6) { // we are only changing profile info
                            int updateStatus = manager.updateAccount(requestBody[1], requestBody[2], requestBody[3],
                                    requestBody[4], requestBody[5]);
                            if (updateStatus == 1) {
                                sendData("success", manager.getUser(requestBody[1]));
                            } else if (updateStatus == -1) {
                                sendData("usernameNotFound");
                            }
                        } else if (requestBody.length == 9) { // we are changing account info (username, password)
                            int updateStatus = manager.updateAccount(requestBody[1], requestBody[2], requestBody[3],
                                    requestBody[4], requestBody[5], requestBody[6], requestBody[7], requestBody[8]);
                            if (updateStatus == 1) {
                                sendData("success", manager.getUser(requestBody[7]));
                            } else if (updateStatus == -1) {
                                sendData("invalidUsername");
                            } else if (updateStatus == -2) {
                                sendData("usernameExists");
                            } else if (updateStatus == -3) {
                                sendData("incorrectPassword");
                            } else {
                                sendData("usernameNotFound");
                            }
                        }
                        break;
                    case ("deleteAccount"):
                        int deleteStatus = manager.deleteAccount(requestBody[1], requestBody[2]);
                        sendData(deleteStatus == 1 ? "success" : (deleteStatus == -1 ? "incorrectPassword" : "usernameNotFound"));
                        break;
                    case ("loginUser"):
                        String username = requestBody[1];
                        String password = requestBody[2];
                        Account loginUser = manager.getUser(username);
                        if (loginUser == null) {
                            sendData("usernameNotFound");
                        } else if (password.equals(loginUser.getPassword())) {
                            sendData("success", loginUser);
                        } else {
                            sendData("incorrectPassword");
                        }
                        break;
                    case ("searchUsers"):
                        ArrayList<Account> usersFound = manager.searchUsers(requestBody[1]);
                        sendData("success", usersFound);
                        break;
                    case ("getUser"):
                        Account getUser = manager.getUser(requestBody[1]);
                        if (getUser == null) {
                            sendData("usernameNotFound", getUser);
                        } else {
                            sendData("success", getUser);
                        }
                        break;
                    case ("isFriendsWith"):
                        Account user1 = manager.getUser(requestBody[1]);
                        Account user2 = manager.getUser(requestBody[2]);
                        if (user1 != null && user2 != null) {
                            sendData("success", user1.isFriendsWith(user2));
                        } else {
                            sendData(user1 == null ? "usernameNotFound" : "username2NotFound");
                        }
                        break;
                    case ("hasRequested"):
                        Account user3 = manager.getUser(requestBody[1]);
                        Account user4 = manager.getUser(requestBody[2]);
                        if (user3 != null && user4 != null) {
                            sendData("success", user3.hasRequested(user4));
                        } else {
                            sendData(user3 == null ? "usernameNotFound" : "username2NotFound");
                        }
                        break;
                    case ("sendFriendRequest"):
                        Account user5 = manager.getUser(requestBody[1]);
                        Account user6 = manager.getUser(requestBody[2]);
                        if (user5 != null && user6 != null) {
                            user5.sendFriendRequest(user6);
                            sendData("success", user5);
                        } else {
                            sendData(user5 == null ? "usernameNotFound" : "username2NotFound");
                        }
                        break;
                    case ("cancelFriendRequest"):
                        Account user7 = manager.getUser(requestBody[1]);
                        Account user8 = manager.getUser(requestBody[2]);
                        if (user7 != null && user8 != null) {
                            user7.cancelFriendRequest(user8);
                            sendData("success", user7);
                        } else {
                            sendData(user7 == null ? "usernameNotFound" : "username2NotFound");
                        }
                        break;
                    case ("acceptFriendRequest"):
                        Account user9 = manager.getUser(requestBody[1]);
                        Account user10 = manager.getUser(requestBody[2]);
                        if (user9 != null && user10 != null) {
                            user9.acceptDeclineFriendRequest(user10, true);
                            sendData("success", user9);
                        } else {
                            sendData(user9 == null ? "usernameNotFound" : "username2NotFound");
                        }
                        break;
                    case ("declineFriendRequest"):
                        Account user11 = manager.getUser(requestBody[1]);
                        Account user12 = manager.getUser(requestBody[2]);
                        if (user11 != null && user12 != null) {
                            user11.acceptDeclineFriendRequest(user12, false);
                            sendData("success", user11);
                        } else {
                            sendData(user11 == null ? "usernameNotFound" : "username2NotFound");
                        }
                        break;
                    case ("removeFriend"):
                        Account user13 = manager.getUser(requestBody[1]);
                        Account user14 = manager.getUser(requestBody[2]);
                        if (user13 != null && user14 != null) {
                            user13.removeFriend(user14);
                            sendData("success", user13);
                        } else {
                            sendData(user13 == null ? "usernameNotFound" : "username2NotFound");
                        }
                        break;
                    default:
                        break;
                }
            }
            System.out.println(requestType + " request received!");
            socket.close();
            reader.close();
            writer.close();
            System.out.println("Closing thread...");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

    private void sendData(String status) throws IOException{
        writer.writeObject(new String[] { status });
        writer.flush();
    }

    private void sendData(String status, Object data) throws IOException {
        writer.writeObject(new Object[] { status, data });
        writer.flush();
    }
}
