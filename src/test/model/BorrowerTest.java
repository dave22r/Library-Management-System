package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BorrowerTest {
    Borrower b;
    Book b1;
    Library l;
    ArrayList<Book> books;
    ArrayList<Borrower> borrowers;
    Borrower bx = new Borrower("Borrower1", "00001", new ArrayList<>(), 0);

    @BeforeEach
    public void setUp() {
        b = new Borrower("rudra", "00002", new ArrayList<>(), 0);
        b1 = new Book("The Silent Patient", "AuthorABC", "Suspense", false
                , 3.8, new ArrayList<>(), "Gabriel kills Alicia.", 0);
        books = new ArrayList<>();
        borrowers = new ArrayList<>();
        borrowers.add(bx);
        borrowers.add(new Borrower("Borrower2", "00002", new ArrayList<>(), 0));
        books.add(new Book("Book4", "Author4", "Genre4", false, 3.8, new ArrayList<>(), "Description4", 0));
        books.add(new Book("Book5", "Author5", "Genre5", false, 3.8, new ArrayList<>(), "Description5", 0));


        l = new Library(books, borrowers);
    }

    @Test
    public void testBorrowBook() {
        ArrayList<Book> books = new ArrayList<>();
        assertEquals("Borrowed!", b.borrowBook(b1));
        books.add(b1);
        assertEquals(books, b.getBorrowingHistory());
        assertEquals("Book is already borrowed", b.borrowBook(b1));



    }

    @Test
    public void testReturnBook() {
        b.borrowBook(b1);
        assertEquals("invalid return, this book was never borrowed by you", bx.returnBook(b1));
        assertEquals("book succesfully returned", b.returnBook(b1));
        assertEquals("invalid return, this book was never borrowed by you", b.returnBook(b1));
        ;;  }

    @Test
    public void testDonateBook() {
        bx.donateBook(b1, l);
        assertTrue(l.books.contains(b1));


    }

    @Test
    public void testGetAndSetFinesAccumulated() {
        assertEquals(0, b.getFinesAccumulated());
        b.setFinesAccumulated(90);
        assertEquals(90, b.getFinesAccumulated());


    }



    @Test
    public void testGetName() {
        assertEquals("rudra", b.getName());
    }

    @Test
    public void testPayFines() {
        b.setFinesAccumulated(100);
        b.payFines(90);
        assertEquals(10, b.getFinesAccumulated());
        b.setFinesAccumulated(100);
        assertEquals("Amount is greater than fine due, the rest has been returned to you. amount returned: " + 1, b.payFines(101));

    }

    @Test
    void testBorrowingHistoryToJson() {
        Book book1 = new Book("Title1", "Author1", "Genre1", false, 4.5, new ArrayList<Borrower>(), "Description1", 0);
        Book book2 = new Book("Title2", "Author2", "Genre2", true, 3.8, new ArrayList<Borrower>(), "Description2", 0);


        Borrower borrower = new Borrower("Borrower1", "ID1", new ArrayList<Book>(), 0);
        borrower.borrowBook(book1);
        borrower.returnBook(book1);

        borrower.borrowBook(book2);


        JSONArray jsonArray = borrower.borrowingHistoryToJson();

        assertEquals(2, jsonArray.length());
    }

    @Test
    public void testSetBorrowingHistory() {
        ArrayList<Book> bs = new ArrayList<>();
        bs.add(b1);
        assertEquals(new ArrayList<>(), b.getBorrowingHistory());
        b.setBorrowingHistory(bs);
        assertEquals(bs, b.getBorrowingHistory());

    }





}