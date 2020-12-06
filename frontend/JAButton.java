package frontend;

import javax.swing.*;
/**
    * JAButton
    *
    * Class that extends the JButton
    * It has the additional ability to assign a user account to the JBUtton
    * This is useful if certain buttons show certain profiles
    * 
    * @author Team 15-3 CS 180 - Merge
    * @version November 26, 2020
*/

public class JAButton extends JButton {
    
    private String accountName;
    private Client.Action action;

    public JAButton(String text, Client.Action action) {
        super(text);
        this.action = action;
    }

    public JAButton(String text, String accountName, Client.Action action) {
        super(text);
        this.accountName = accountName;
        this.action = action;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Client.Action getActionType() {
        return this.action;
    }

    public void setActionType(Client.Action action) {
        this.action = action;
    }
}