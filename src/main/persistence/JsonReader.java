// JsonReader.java

package persistence;

import model.Book;
import model.Borrower;
import model.Library;
import org.json.JSONArray;
import org.json.JSONObject;
import model.Library.*;
import model.Book.*;
import model.Borrower.*;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;




// Represents a reader that reads library from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads library from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Library read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLibrary(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses library from JSON object and returns it
    private Library parseLibrary(JSONObject jsonObject) {
        ArrayList<Book> books = parseBooks(jsonObject.getJSONArray("books"));
        ArrayList<Borrower> borrowers = parseBorrowers(jsonObject.getJSONArray("borrowers"));

        return new Library(books, borrowers);




    }

    // EFFECTS: parses books from JSON array and returns a list of books
    private ArrayList<Book> parseBooks(JSONArray jsonArray) {
        ArrayList<Book> books = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            books.add(parseBook(nextBook));
        }
        return books;
    }

    // EFFECTS: parses borrowers from JSON array and returns a list of borrowers

    private ArrayList<Borrower> parseBorrowers(JSONArray jsonArray) {
        ArrayList<Borrower> borrowers = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextBorrower = (JSONObject) json;
            borrowers.add(parseBorrower(nextBorrower));
        }
        return borrowers;
    }

    // EFFECTS: parses a book from JSON object and returns it
    private Book parseBook(JSONObject jsonObject) {

        String title = jsonObject.getString("title");
        String author = jsonObject.getString("author");
        String genre = jsonObject.getString("genre");;
        boolean borrowed = jsonObject.getBoolean("borrowed");
        double rating = jsonObject.getDouble("rating");;
        ArrayList<Borrower> loanRecord = parseBorrowers(jsonObject.getJSONArray("loanRecord"));
        String desc = jsonObject.getString("desc");
        int daysOverdue = jsonObject.getInt("daysOverdue");


        // Create and return a new Book object
        return new Book(title, author, genre, borrowed,
        rating, loanRecord, desc, daysOverdue);
    }


    // EFFECTS: parses a borrower from JSON object and returns it
    private Borrower parseBorrower(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String id = jsonObject.getString("id");
        ArrayList<Book> borrowingHistory = parseBooks(jsonObject.getJSONArray("borrowingHistory"));
        int finesAccumulated = jsonObject.getInt("finesAccumulated");

        return new Borrower(name, id, borrowingHistory, finesAccumulated);


    }






}