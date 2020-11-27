package backend;

import java.io.*;
import java.net.*;

/**
    * SesrverThread
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
    private PrintWriter writer;
    private ObjectOutputStream objectWriter;

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
            reader = new ObjectInputStream(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream());
            objectWriter = new ObjectOutputStream(socket.getOutputStream());
            String requestType = "";
            do {
                // read request and the request type
                // The first element in the array will always be the request type
                String[] requestBody = (String[]) reader.readObject();
                requestType = requestBody[0];
                // handle the request type, read parameters, call the appropriate functions
                // first check if any fields are empty. If they are, no need to continue
                boolean fieldsEmpty = false;
                for (String field : requestBody) {
                    if (field.length() == 0) {
                        sendStatus("emptyFields");
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
                            sendStatus("success");
                            sendData(manager.getUser(requestBody[1]));
                        } else if (createStatus == -1) {
                            sendStatus("invalidUsername");
                        } else {
                            sendStatus("usernameExists");
                        }
                        break;
                    case ("updateAccount"):
                        // update account has 2 types of requestBodies it can take. They are 2 different
                        // size arrays
                        if (requestBody.length == 6) { // we are only changing profile info
                            int updateStatus = manager.updateAccount(requestBody[1], requestBody[2], requestBody[3],
                                    requestBody[4], requestBody[5]);
                            if (updateStatus == 1) {
                                sendStatus("success");
                                sendData(manager.getUser(requestBody[1]));
                            } else if (updateStatus == -1) {
                                sendStatus("usernameNotFound");
                            }
                        } else if (requestBody.length == 9) { // we are changing account info (username, password)
                            int updateStatus = manager.updateAccount(requestBody[1], requestBody[2], requestBody[3],
                                    requestBody[4], requestBody[5], requestBody[6], requestBody[7], requestBody[8]);
                            if (updateStatus == 1) {
                                sendStatus("success");
                                sendData(manager.getUser(requestBody[1]));
                            } else if (updateStatus == -1) {
                                sendStatus("invalidUsername");
                            } else if (updateStatus == -2) {
                                sendStatus("usernameExists");
                            } else if (updateStatus == -3) {
                                sendStatus("incorrectPassword");
                            } else {
                                sendStatus("usernameNotFound");
                            }
                        }
                        break;
                    case ("deleteAccount"):
                        int deleteStatus = manager.deleteAccount(requestBody[1], requestBody[2]);
                        sendStatus(deleteStatus == 1 ? "success"
                                : (deleteStatus == -1 ? "incorrectPassword" : "usernameNotFound"));
                        break;
                    case ("loginUser"):
                        String username = requestBody[1];
                        String password = requestBody[2];
                        Account loginUser = manager.getUser(username);
                        if (loginUser == null) {
                            sendStatus("usernameNotFound");
                        } else if (password.equals(loginUser.getPassword())) {
                            sendStatus("success");
                            sendData(loginUser);
                        } else {
                            sendStatus("incorrectPassword");
                        }
                        break;
                    case ("getUser"):
                        Account getUser = manager.getUser(requestBody[1]);
                        if (getUser == null) {
                            sendStatus("usernameNotFound");
                            sendData(getUser);
                        } else {
                            sendStatus("success");
                        }
                        break;
                    case ("isFriendsWith"):
                        Account user1 = manager.getUser(requestBody[1]);
                        Account user2 = manager.getUser(requestBody[2]);
                        if (user1 != null && user2 != null) {
                            sendStatus("success");
                            sendData(user1.isFriendsWith(user2));
                        } else {
                            sendStatus(user1 == null ? "usernameNotFound" : "username2NotFound");
                        }
                        break;
                    case ("hasRequested"):
                        Account user3 = manager.getUser(requestBody[1]);
                        Account user4 = manager.getUser(requestBody[2]);
                        if (user3 != null && user4 != null) {
                            sendStatus("success");
                            sendData(user3.hasRequested(user4));
                        } else {
                            sendStatus(user3 == null ? "usernameNotFound" : "username2NotFound");
                        }
                        break;
                    case ("sendFriendRequest"):
                        Account user5 = manager.getUser(requestBody[1]);
                        Account user6 = manager.getUser(requestBody[2]);
                        if (user5 != null && user6 != null) {
                            user5.sendFriendRequest(user6);
                            sendStatus("success");
                            sendData(user5);
                        } else {
                            sendStatus(user5 == null ? "usernameNotFound" : "username2NotFound");
                        }
                        break;
                    case ("cancelFriendRequest"):
                        Account user7 = manager.getUser(requestBody[1]);
                        Account user8 = manager.getUser(requestBody[2]);
                        if (user7 != null && user8 != null) {
                            user7.cancelFriendRequest(user8);
                            sendStatus("success");
                            sendData(user7);
                        } else {
                            sendStatus(user7 == null ? "usernameNotFound" : "username2NotFound");
                        }
                        break;
                    case ("acceptFriendRequest"):
                        Account user9 = manager.getUser(requestBody[1]);
                        Account user10 = manager.getUser(requestBody[2]);
                        if (user9 != null && user10 != null) {
                            user9.acceptDeclineFriendRequest(user10, true);
                            sendStatus("success");
                            sendData(user9);
                        } else {
                            sendStatus(user9 == null ? "usernameNotFound" : "username2NotFound");
                        }
                        break;
                    case ("declineFriendRequest"):
                        Account user11 = manager.getUser(requestBody[1]);
                        Account user12 = manager.getUser(requestBody[2]);
                        if (user11 != null && user12 != null) {
                            user11.acceptDeclineFriendRequest(user12, false);
                            sendStatus("success");
                            sendData(user11);
                        } else {
                            sendStatus(user11 == null ? "usernameNotFound" : "username2NotFound");
                        }
                        break;
                    case ("removeFriend"):
                        Account user13 = manager.getUser(requestBody[1]);
                        Account user14 = manager.getUser(requestBody[2]);
                        if (user13 != null && user14 != null) {
                            user13.removeFriend(user14);
                            sendStatus("success");
                            sendData(user13);
                        } else {
                            sendStatus(user13 == null ? "usernameNotFound" : "username2NotFound");
                        }
                        break;
                    default:
                        break;
                    }
                }
            } while (!requestType.equals("closeSession")); // this is probably temporarily
            socket.close();
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

    private void sendStatus(String message) {
        writer.write(message);
        writer.println();
        writer.flush();
    }

    private void sendData(Object data) throws IOException {
        objectWriter.writeObject(data);
        objectWriter.flush();
    }

    private void sendData(boolean data) throws IOException {
        objectWriter.writeBoolean(data);
        objectWriter.flush();
    }
}
