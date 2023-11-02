package model;

import androidx.annotation.NonNull;

public class Users extends UserBook {
    private static final String isbn = null;
    private static final String typeOfAction = null;
    private static final String dateUpdated = null;

    private long phoneNumber;
    private String name;
    private String password;
    private String type;
    private String email;

    //Constructors

    public Users() {
        this.phoneNumber = 0;
        this.name = "N/A";
        this.password = "N/A";
        this.email = "N/A";

        this.type = "N/A";
    }

    public Users(long phoneNumber, String name, String password, String email) {
        super(isbn, typeOfAction, dateUpdated);
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
        this.email = email;

        //Super



        this.type = "user";
    }

    /*
    public Users(long phoneNumber, String name, String password, String email) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
        this.email = email;

        //Initializing every account created locally as "user"
        //Initialize admin accounts using the real-time database
        this.type = "user";
    }
    */


    //Getters and Setters


    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    //ToString

    @NonNull
    @Override
    public String toString() {
        return "PhoneNumber: " + phoneNumber + "| Name: " + name + "| Password: " + password + "| Email: " + email + "| Type: " + type;
    }


}
