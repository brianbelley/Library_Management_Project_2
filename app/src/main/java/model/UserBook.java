package model;

import java.io.Serializable;

public class UserBook implements Serializable {
    private String isbn;
    private String typeOfAction;
    private String dateUpdated;

    public UserBook() {
        this.isbn = "N/A";
        this.typeOfAction = "N/A";
    }

    public UserBook(String isbn, String typeOfAction, String dateUpdated) {
        this.isbn = isbn;
        this.typeOfAction = typeOfAction;
        this.dateUpdated = dateUpdated;
    }


    public String getIsbn() {
        return isbn;
    }

    public String getTypeOfAction() {
        return typeOfAction;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Override
    public String toString() {
        return "ISBN: " + isbn + "\n" + "Action: " + typeOfAction + "\n" + "Date: " + dateUpdated;
    }
}
