package persistence;

import model.Book;
import model.Borrower;
import model.Library;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Library library = reader.read();
            fail("IOException expected");
            assertEquals(0, library.getBooks().size());
            assertEquals(0, library.getBorrowers().size());
            library.addBook(new Book("The Silent Patient", "Alex Michaelides", "Mystery", false, 4.2, new ArrayList<>(),
                    "The Silent Patient is a shocking psychological thriller of a woman's act "
                            + "of violence against her husband and of the "
                            + "therapist obsessed with uncovering her motive.", 9));
            library.addBook(new Book("The Chain", "Adrian McKinty", "Mystery", false, 3.8, new ArrayList<>(),
                    "When a mother is targeted by a dangerous group of masterminds,"
                            + " she must commit a crime to save her kidnapped daughter -- or risk losing her forever", 0));
            library.addBorrower(new Borrower("John Doe", "00001", new ArrayList<Book>(), 22));

            assertEquals(2, library.getBooks().size());
            assertEquals(1, library.getBorrowers().size());


        } catch (IOException e) {
            // pass
        }
    }



    }

