/**
    * Contact Info class
    *
    * A simple class designed to hold info about contacts
    * Super simple, just to help with the coding process
    *
    * @author Team 15-3, CS 180 - Merge
    * @version November 23, 2020
*/

public class ContactInfo {
    private String email;
    private String phoneNumber;

    public ContactInfo(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}