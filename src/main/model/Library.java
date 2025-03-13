package model;

import persistence.Writable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import java.util.ArrayList;
import java.util.Locale;

public class Library implements Writable {
    ArrayList<Book> books;
    ArrayList<Borrower> borrowers;


    public Library(ArrayList<Book> books, ArrayList<Borrower> borrowers) {
        this.books = books;
        this.borrowers = borrowers;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<Borrower> getBorrowers() {
        return borrowers;
    }

    // MODIFIES: this
    // EFFECTS: Adds the given book to the library. If the book is already in the library,
    //          still adds it and returns an appropriate message.
    public String addBook(Book b) {
        if (!books.contains(b)) {
            books.add(b);
            EventLog.getInstance().logEvent(new Event("Added Book:" + b.getTitle()));
            return "book succesfully added";
        } else {

            books.add(b);
            EventLog.getInstance().logEvent(new Event("Added Book:" + b.getTitle()));
            return "The library already has this book, but we'll still take it :)";
        }




    }

    // MODIFIES: this
    // EFFECTS: Removes the given book from the library
    public void removeBook(Book b) {
        books.remove(b);

    }

    // EFFECTS: Searches for a book in the library by its title and returns it
    public Book searchByTitle(String title) {
        Book result = null;
        for (Book book : books) {
            if (book.title.equalsIgnoreCase(title) && book.isBorrowed() == false) {
                result = book;
            }
        }
        return result;
    }

    public Book searchByTitleReturn(String title) {
        Book result = null;
        for (Book book : books) {
            if (book.title.equalsIgnoreCase(title)) {
                result = book;
            }
        }
        return result;
    }



    // EFFECTS: Calculates fines for the given borrower and book, updates the borrower's fines,
    //          and returns the total fines accumulated by the borrower
    public int calculateFines(Borrower u, Book b) {
        u.setFinesAccumulated((u.getFinesAccumulated() + (b.getOverdueFine() * b.daysOverdue)));
        return u.getFinesAccumulated();
//pre-change: changing this to



    }

    // EFFECTS: Searches for a borrower in the library by ID and returns it if found,
    //          otherwise returns null
    public Borrower getBorrowerById(String id) {
        for (Borrower borrower : borrowers) {
            if (borrower.getID(borrower).equals(id)) {
                return borrower; // Borrower found
            }
        }
        return null; // Borrower not found
    }



    // EFFECTS: Returns a list of available book titles in the library
    public ArrayList<String> showAvailableBooks() {
        ArrayList<String> bookNames = new ArrayList<>();
        for (Book b: books) {
            if (!b.isBorrowed()) {
                bookNames.add(b.title);
            }
        }
        return bookNames;
    }

    //EFFECTS: Returns an ArrayList of strings containing information about each book in the library.
    //         Each string includes the book title, detailed information, and borrowing status.

    public ArrayList<String> showBooksInfo() {
        ArrayList<String> res = new ArrayList<>();
        for (Book b: books) {
            String result = b.getTitle() + "\n" + b.displayInfo()
                    + "\n " + "Borrowed:" + " " + b.isBorrowed() + "\n";
            res.add(result);

        }
        EventLog.getInstance().logEvent(new Event("Showed information on all books"));
        return res;
    }

    // MODIFIES: this
    // EFFECTS: Adds the given borrower to the library and returns true.
    // If the borrower is already in the library's records
    //          then doesn't add them and returns false

    public boolean addBorrower(Borrower b) {
        if (!borrowers.contains(b)) {
            this.borrowers.add(b);
            return true;
        } else {
            return false;
        }
    }


    private JSONArray bookstoJson() {
        JSONArray jsonArray = new JSONArray();
        for (Book b : books) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;


    }

    private JSONArray borrowerstoJson() {
        JSONArray jsonArray = new JSONArray();
        for (Borrower b: borrowers) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("books", bookstoJson());
        json.put("borrowers", borrowerstoJson());
        return json;
    }



}