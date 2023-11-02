package model;

import java.util.Date;

public class Book {

    //Initialize
    private String isbn;
    private String author;
    private String title;
    private Date yearPublished;



    //Constructor
    public Book() {
    }

    public Book(String isbn, String author, String title, Date yearPublished) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.yearPublished = yearPublished;
    }


    //Getters and Setters


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(Date yearPublished) {
        this.yearPublished = yearPublished;
    }

    //ToString


    @Override
    public String toString() {
        return "Isbn: " + isbn + "| Author: " + author + "| title: " + title + "| Year Published: " + yearPublished;
    }
}
