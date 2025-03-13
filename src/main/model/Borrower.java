package model;

import persistence.Writable;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

import java.util.*;

public class Borrower implements Writable {
    String name;
    String id;
    ArrayList<Book> borrowingHistory;
    int finesAccumulated;

    public Borrower(String name, String id, ArrayList<Book> borrowingHistory, int finesAccumulated) {
        this.name = name;
        this.id = id;
        this.borrowingHistory = new ArrayList<>();
        this.finesAccumulated = finesAccumulated;

    }


    // EFFECTS: Borrows the given book if it is available, updates the borrowing history,
    //          and returns a message indicating success or failure

    public String borrowBook(Book b) {
        if (b.isAvailable()) {
            borrowingHistory.add(b);
            b.setBorrowed(true);
            b.loanRecord.add(this);
            EventLog.getInstance().logEvent(new Event("Borrowed Book:" + b.getTitle()));
            return "Borrowed!";
        } else {
            return "Book is already borrowed";
        }




    }

    // EFFECTS: Donates the given book to the library and returns a message indicating success
    public String donateBook(Book b, Library l) {
        return l.addBook(b);

    }

    // MODIFIES: this, b
    // EFFECTS: If the book (b) is currently borrowed and is in the borrower's borrowing history,
    //          sets the book's 'borrowed' status to false and returns a success message.
    //          Otherwise, returns an error message indicating that the book was not borrowed by the borrower.
    public String returnBook(Book b) {
        if (b.borrowed && borrowingHistory.contains(b)) {
            b.setBorrowed(false);
            return ("book succesfully returned");
        } else {
            return  ("invalid return, this book was never borrowed by you");
        }

    }

    // EFFECTS: Pays the fines for the borrower, updates the fines, and returns a message
    public String payFines(int amt) {
        if (amt <= finesAccumulated) {
            this.finesAccumulated = finesAccumulated - amt;
            return ("Total fines due:" + finesAccumulated);
        } else {
            int diff = amt - finesAccumulated;
            finesAccumulated = 0;
            return ("Amount is greater than fine due, the rest has been returned to you."
                    + " " + "amount returned: " + diff);
//changing this to accept a higher amount as well.

        }
    }

    // MODIFIES : this
    // EFFECTS: Sets the fines accumulated by the borrower to the given value
    public void setFinesAccumulated(int fine) {
        this.finesAccumulated = fine;
    }

    // EFFECTS: Returns the total fines accumulated by the borrower
    public int getFinesAccumulated() {
        return this.finesAccumulated;
    }

    // EFFECTS: Returns the ID of the borrower
    public String getID(Borrower b) {
        return b.id;
    }

    // EFFECTS: Returns the name of the borrower
    public String getName() {
        return name;
    }

    // EFFECTS: Returns the borrowing history of the borrower
    public ArrayList<Book> getBorrowingHistory() {
        return borrowingHistory;
    }


    // MODIFIES : this
    // EFFECTS: Sets the borrowing history of the borrower to the given list

    public void setBorrowingHistory(ArrayList<Book> history) {
        this.borrowingHistory = history;

    }

    // EFFECTS: converts the borrower object to a JSONObject.

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("id", id);
        json.put("finesAccumulated", finesAccumulated);
        json.put("borrowingHistory", borrowingHistoryToJson());
        return json;
    }

    // REQUIRES: borrowingHistory != null
    // MODIFIES: None
    // EFFECTS: Converts the borrowing history of the borrower into a JSONArray of JSON representations of each book

    public JSONArray borrowingHistoryToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Book b : borrowingHistory) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;
    }


}