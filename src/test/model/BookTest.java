package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookTest {

    Book b;


    @BeforeEach
    public void setUp() {
        b = new Book("The Silent Patient", "AuthorABC", "Suspense", false
                , 3.8, new ArrayList<>(), "Gabriel kills Alicia.", 0);


    }

    @Test
    public void testdisplayInfo() {
        assertEquals("The Silent Patient was written by AuthorABC and belongs to the Suspense genre \n" +
                "this book has an average rating of 3.8 here is a short summary of the book: \n" +
                "Gabriel kills Alicia.", b.displayInfo());


    }
    @Test
    public void testaddRecord() {
        ArrayList<Borrower> bs = new ArrayList<>();
        Borrower bo = new Borrower("rudra", "00001", new ArrayList<>(), 0);
        bo.borrowBook(b);
        bs.add(bo);



        assertEquals(bs, b.getLoanRecord());

    }

    @Test
    public void testAddRecord() {
        Borrower bo = new Borrower("rudra", "00001", new ArrayList<>(), 0);
        b.addRecord(bo);
        assertTrue(b.getLoanRecord().contains(bo));
        assertEquals(b.getLoanRecord().get(0), bo);


    }

    @Test
    public void testIsAvailable() {
        assertTrue(b.isAvailable());
        b.setBorrowed(true);
        assertFalse(b.isAvailable());

    }

    @Test
    public void testGetOverdueFine() {
        assertEquals(10, b.getOverdueFine());
    }

    @Test
    public void testSetOverdueFine() {
        assertEquals(10, b.getOverdueFine());
        b.setOverdueFine(20);
        assertEquals(20, b.getOverdueFine());

    }

    @Test
    public void testGetTitle() {
        assertEquals("The Silent Patient", b.getTitle());
    }

    @Test
    public void testGetGenre() {
        assertEquals("Suspense", b.getGenre());
    }

    @Test
    public void testGetRating() {
        assertEquals(3.8, b.getRating());

    }

    @Test
    public void testGetDesc() {
        assertEquals("Gabriel kills Alicia.", b.getDesc());

    }

    @Test
    public void testGetAuthor() {
        assertEquals("AuthorABC", b.getAuthor());
    }

    @Test
    public void testSetDaysOverdue(){
        b.setDaysOverdue(8);
        assertEquals(8, b.daysOverdue);
    }
    @Test
    void testBorrowerstoJsonArray() {
        Book book1 = new Book("Title1", "Author1", "Genre1", false, 4.5, new ArrayList<Borrower>(), "Description1", 0);
        Book book2 = new Book("Title2", "Author2", "Genre2", true, 3.8, new ArrayList<Borrower>(), "Description2", 0);

        Borrower borrower = new Borrower("Borrower1", "ID1", null, 0);
        borrower.borrowBook(book1);
        borrower.returnBook(book1);
        Borrower bo = new Borrower("rudra", "00001", new ArrayList<Book>(), 0);
        bo.borrowBook(book1);


        JSONArray jsonArray = book1.borrowersToJsonArray();

        assertEquals(2, jsonArray.length());

    }

    @Test
    public void testGetDaysOverdue() {
        b.setDaysOverdue(8);
        assertEquals(8, b.getDaysOverdue());

    }






}