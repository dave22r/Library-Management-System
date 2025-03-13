package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


import java.lang.reflect.Array;
import java.util.ArrayList;
public class LibraryTest {

    Borrower b;
    Book b1;
    Book b2 = new Book("Book4", "Author4", "Genre4", false, 3.8, new ArrayList<>(), "Description4", 7);
    Library l;
    ArrayList<Book> books;
    ArrayList<Borrower> borrowers;
    Borrower bx = new Borrower("Borrower1", "00001", new ArrayList<>(), 0);
    Book b3 = new Book("Book5", "Author5", "Genre5", false, 3.8, new ArrayList<>(), "Description5", 9);
    @BeforeEach
    public void setUp() {

        b = new Borrower("rudra", "00002", new ArrayList<>(), 0);
        b1 = new Book("The Silent Patient", "AuthorABC", "Suspense", false
                , 3.8, new ArrayList<>(), "Gabriel kills Alicia.", 8);
        books = new ArrayList<>();
        borrowers = new ArrayList<>();
        borrowers.add(bx);
        borrowers.add(new Borrower("Borrower2", "00002", new ArrayList<>(), 0));
        books.add(b2);
        books.add(b3);

        l = new Library(books, borrowers);
    }

    @Test
    public void testRemoveBook() {
        assertTrue(l.books.contains(b2));
        l.removeBook(b2);
        assertFalse(l.books.contains(b2));


    }

    @Test
    public void testAddBook() {
        assertEquals("The library already has this book, but we'll still take it :)", l.addBook(b2));
        assertEquals("book succesfully added", l.addBook(b1));


    }

    @Test
    public void testCalculateFines() {
        bx.setFinesAccumulated(10);
        assertEquals(80, l.calculateFines(bx, b2));



    }

    @Test
    public void testsearchByTitleReturn() {
        //doesn't check if it is borrowed
        assertEquals(b2, l.searchByTitleReturn("Book4"));
        bx.borrowBook(b2);
        assertEquals(b2, l.searchByTitleReturn(b2.getTitle()));


    }

    @Test
    public void testgetBorrowerbyID() {
        assertEquals(bx, l.getBorrowerById("00001"));
        assertNull(l.getBorrowerById("000003"));
    }

    @Test
    public void testSearchByTitle() {
        assertEquals(b2, l.searchByTitle("Book4"));
        assertNull(l.searchByTitle("Doesn't Exist"));
        b.borrowBook(b2);
        assertNull(l.searchByTitle(b2.getTitle()));

    }

    @Test
    public void testShowAvailableBooks() {
        ArrayList<String> bks = new ArrayList<>();
        bks.add(b2.title);
        bks.add(b3.title);
        assertEquals(bks, l.showAvailableBooks());
        bx.borrowBook(b2);
        bks.remove(0);
        assertEquals(bks, l.showAvailableBooks());
    }

    @Test
    public void testGetBorrowerByID() {
        assertNull(l.getBorrowerById("00008"));
        assertEquals(bx, l.getBorrowerById("00001"));
    }

    @Test
    public void testShowBooksInfo() {

        bx.borrowBook(b2);
        String expectedInfoBorrowed = b2.getTitle() + "\n" + b2.displayInfo()
                + "\n Borrowed: true\n";
        String expectedInfoAvailable = b3.getTitle() + "\n" + b3.displayInfo()
                + "\n Borrowed: false\n";
        ArrayList<String> result = l.showBooksInfo();
        assertTrue(result.contains(expectedInfoBorrowed));
        assertTrue(result.contains(expectedInfoAvailable));
        assertEquals(2, result.size());

    }

    @Test
    public void testAddBorrowers() {
        assertTrue(l.addBorrower(b));
        assertFalse(l.addBorrower(b));



    }


}