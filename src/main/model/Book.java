package model;

import persistence.Writable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.lang.String;


public class Book implements Writable {

    String title;
    String author;
    String genre;
    boolean borrowed;
    double rating;
    ArrayList<Borrower> loanRecord;
    int overdueFine = 10;
    String desc;
    int daysOverdue;

    public Book(String title, String author, String genre, boolean borrowed,
                double rating, ArrayList<Borrower> loanRecord, String desc, int daysOverdue) {

        this.title = title;
        this.author = author;
        this.genre = genre;
        this.borrowed = false;
        this.rating = rating;
        this.loanRecord = loanRecord;
        this.desc = desc;
        this.daysOverdue = daysOverdue;
        this.overdueFine = 10;
    }

    // MODIFIES: this
    // EFFECTS: Sets the 'borrowed' status of the book to the given boolean value
    public void setBorrowed(boolean b) {
        borrowed = b;

    }

    // EFFECTS: Returns how many days the book is overdue
    public int getDaysOverdue() {
        return this.daysOverdue;
    }


    // EFFECTS: Returns the overdue fine per day for the book
    public int getOverdueFine() {
        return overdueFine;
    }


    // EFFECTS: Returns the genre of the book
    public String getGenre() {
        return this.genre;
    }

    // EFFECTS: Returns true if the book is borrowed, false otherwise
    public boolean isBorrowed() {

        return this.borrowed;
    }

    // EFFECTS: Returns true if the book is available (not borrowed), false otherwise
    public boolean isAvailable() {

        return (!borrowed);
    }


    // EFFECTS: Returns the rating of the book
    public double getRating() {
        return rating;
    }

    // EFFECTS: Returns the description of the book
    public String getDesc() {
        return desc;
    }

    // EFFECTS: Returns the loan record of the book
    public ArrayList<Borrower> getLoanRecord() {
        return loanRecord;
    }

    // EFFECTS: Returns the title of the book
    public String getTitle() {
        return this.title;
    }

    // EFFECTS: Returns a formatted string containing information about the book,
    //          including title, author, genre, rating, and a short description.
    public String displayInfo() {
        return (title + " " + "was written by" + " " + author + " " + "and belongs to the" + " " + genre
                + " " + "genre" + " " + "\n" + "this book has an average rating of" + " " + rating
                + " " + "here is a short summary of the book:" + " "
                + "\n"
                + desc);
    }


    // EFFECTS: Adds the given borrower to the loan record of the book
    public void addRecord(Borrower b) {
        loanRecord.add(b);

    }

    // EFFECTS: Returns the title of the book
    public String getAuthor() {
        return this.author;
    }

    //MODIFIES: this
    //EFFECTS: sets the days overdue for a book to the given integer value

    public void setDaysOverdue(int daysOverdue) {
        this.daysOverdue = daysOverdue;
    }

    //MODIFIES: this
    //EFFECTS: sets the overdue fine for a book to the given integer value

    public void setOverdueFine(int fine) {
        this.overdueFine = fine;
    }

    // EFFECTS: Convert the book object to a JSONObject.

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", this.title);
        json.put("author", this.author);
        json.put("genre", this.genre);
        json.put("borrowed", this.borrowed);
        json.put("rating", this.rating);
        json.put("loanRecord", borrowersToJsonArray());
        json.put("desc", this.desc);
        json.put("daysOverdue", this.daysOverdue);
        json.put("overdueFine", this.overdueFine);

        return json;
    }

    // REQUIRES: borrower!= null
    // EFFECTS: Converts the loan record of the borrower to a JSONArray with JSON representations of each book.

    public JSONArray borrowersToJsonArray() {
        JSONArray jsonArray = new JSONArray();
        for (Borrower borrower : this.loanRecord) {
            jsonArray.put(borrower.getName());
        }
        return jsonArray;
    }







}