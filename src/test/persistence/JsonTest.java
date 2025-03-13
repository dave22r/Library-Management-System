package persistence;

import model.Borrower;
import model.Book;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkBook(String title, String author, String genre, double rating, Book book, boolean borrowed, ArrayList<Borrower> loanRecord,
                             String desc, int daysOverdue) {
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(genre, book.getGenre());
        assertEquals(rating, book.getRating());
        assertEquals(borrowed, book.isBorrowed());
        assertEquals(loanRecord, book.getLoanRecord());
        assertEquals(desc, book.getDesc());
        assertEquals(daysOverdue, book.getDaysOverdue());

    }

    protected void checkBorrower(String name, String id, int finesAccumulated, ArrayList<Book> borrowingHistory,Borrower borrower) {
        assertEquals(name, borrower.getName());
        assertEquals(id, borrower.getID(borrower));
        assertEquals(finesAccumulated, borrower.getFinesAccumulated());
        assertEquals(borrowingHistory, borrower.getBorrowingHistory());
    }
}