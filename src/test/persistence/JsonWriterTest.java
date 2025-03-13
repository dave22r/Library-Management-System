package persistence;

import model.Book;
import model.Borrower;
import model.Library;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Library library = new Library(new ArrayList<Book>(), new ArrayList<Borrower>());
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyLibrary() {
        try {
            Library library = new Library(new ArrayList<Book>(), new ArrayList<Borrower>());
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLibrary.json");
            writer.open();
            writer.write(library);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLibrary.json");
            library = reader.read();
            assertEquals(0, library.getBooks().size());
            assertEquals(0, library.getBorrowers().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLibrary() {
        try {

            Library library = new Library(new ArrayList<Book>(), new ArrayList<Borrower>());
            library.addBook(new Book("The Silent Patient", "Alex Michaelides", "Mystery", false, 4.2, new ArrayList<>(),
                    "The Silent Patient is a shocking psychological thriller of a woman's act "
                            + "of violence against her husband and of the "
                            + "therapist obsessed with uncovering her motive.", 9));
            library.addBook(new Book("The Chain", "Adrian McKinty", "Mystery", false, 3.8, new ArrayList<>(),
                    "When a mother is targeted by a dangerous group of masterminds,"
                            + " she must commit a crime to save her kidnapped daughter -- or risk losing her forever", 0));
            library.addBorrower(new Borrower("John Doe", "00001", new ArrayList<Book>(), 22));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLibrary.json");
            writer.open();
            writer.write(library);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLibrary.json");
            library = reader.read();

            List<Book> books = library.getBooks();
            List<Borrower> borrowers = library.getBorrowers();

            assertEquals(2, books.size());
            assertEquals(1, borrowers.size());


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
//JFrame frame;
//JPanel mainPanel;
//
//
//Scanner scanner = new Scanner(System.in);
//
//
//
//
//
//
//String borrowerId = scanner.nextLine();
//
//// Check if borrower exists
//Borrower borrower = library.getBorrowerById(borrowerId);
//
//
//
//
//
//
//

//public void run() {
//    frame = new JFrame("Library Application");
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.setSize(300, 200);
//    JPanel mainPanel = new JPanel();
//    mainPanel.setLayout(new FlowLayout());
//    JLabel label = new JLabel("Enter Borrower ID: ");
//    JTextField borrowerID = new JTextField();
//    Borrower borrower = library.getBorrowerById(borrowerID.getText());
//    ArrayList<String> books = library.showAvailableBooks();
//    for (String book: books) {
//        mainPanel.add(new JLabel(book));
//    }






