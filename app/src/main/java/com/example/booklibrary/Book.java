package com.example.booklibrary;

import java.io.Serializable;

public class Book implements Serializable {
    public String title;
    public String author;
    public String genre;
    public String status;
    public float rating;
    public String notes;

    public Book(String title, String author, String genre, String status, float rating, String notes) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.status = status;
        this.rating = rating;
        this.notes = notes;
    }
}
