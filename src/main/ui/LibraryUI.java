package ui;


import model.*;

import model.Event;
import persistence.JsonReader;

import persistence.JsonWriter;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.util.Scanner;

import java.util.*;

import java.lang.String;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;




import model.Library;

import model.Book;

import model.Borrower;

import java.util.Scanner;

import java.util.*;

import java.lang.String;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



import model.Library;

import model.Book;

import model.Borrower;

import persistence.JsonReader;

import persistence.JsonWriter;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.util.Scanner;

import java.util.*;

import java.lang.String;

public class LibraryUI {
    //TO//DO: Implement quit button (done)
    //TO//DO: RE-Implement Console functionality (done)
    //TO//DO: Write requires/modifies/effects clauses for all methods (done)
    //TODO: Readme edit with the instructor section
    //TO//DO: Jacoco coverage  (done)
    //TO//DO: thank you image when donating pops up (done)

    private static final String JSON_STORE = "./data/library.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel label;

    public LibraryUI() {
        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);
    }

    private Library library = initializeLibrary();

    Scanner scanner = new Scanner(System.in);

    String borrowerId = scanner.nextLine();

    Borrower borrowerConsole = library.getBorrowerById(borrowerId);

    // Check if borrower exists
    Borrower borrower;



//    MODIFIES: mainPanel, Frame, label
//    EFFECTS: Initializes and runs the library application interface.


    public void run() {
        frame = new JFrame("Library Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 3));
        label = new JLabel("Enter Borrower ID: ");
        mainPanel.add(label);
        JTextField borrowerID = new JTextField();
        JButton enterButton = new JButton("Enter"); // Add a button for the user to click after entering the ID

        mainPanel.add(borrowerID);
        mainPanel.add(enterButton);






        ArrayList<String> books = library.showAvailableBooks();
        for (String book: books) {
            mainPanel.add(new JLabel(book));
        }
        enterButtonMaker(enterButton, borrowerID);




        authenticator();
        frame.add(mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

    //helpers for run:

    // EFFECTS: Authenticates the borrower and displays appropriate information or prompts.
    private void authenticator() {
        // Call the method in an appropriate context
        if (authenticateBorrower(borrower)) {
            displayBorrowerInfo(borrower);

            boolean keepGoing = true;
            String command = null;

            while (keepGoing) {
                displayMenu();
                command = scanner.nextLine();
                command = command.toLowerCase();

                if (command.equals("0")) {
                    System.out.println("Okay, quitting :( ");
                    keepGoing = false;
                } else {
                    processCommand(command);
                }









            }

        } else {
            System.out.println(".......");
        }
    }



    // EFFECTS: Sets up action listener for the enter button.

    private void enterButtonMaker(JButton enterButton, JTextField borrowerID) {
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the borrower ID entered by the user
                String id = borrowerID.getText();
                // Check if the borrower exists
                borrower = library.getBorrowerById(id);

                // If the borrower exists, display the menu
                if (authenticateBorrower(borrower)) {
                    enterButton.setVisible(false);
                    updatePanel();

                } else if (authenticateBorrower(borrowerConsole)) {
                    displayConsoleMenu();
                } else {
                    label.setText("Invalid borrower ID, quitting");
                    System.out.println("Invalid borrower ID, quitting");
                    System.exit(0);
                }
            }
        });
    }

    //
    //



    //EFFECTS: Processes the user command and executes the corresponding action.

    private void processCommand(String command) {


        if (command.equals("1")) {
            donateConsole();
        } else if (command.equals("2")) {
            borrowConsole();

        } else if (command.equals("3")) {
            bookReturnConsole();
        } else if (command.equals("4")) {
            finePay();
        } else if (command.equals("5")) {
            searchBook();
        } else if (command.equals("6")) {
            getHistory();
        } else if (command.equals("7")) {
            showBooks();
        } else if (command.equals("8")) {
            saveLibraryConsole();
        } else if (command.equals("9")) {
            loadLibraryConsole();
        } else {
            System.out.println("invalid command");
        }


    }



    //EFFECTS: Prints the borrowing history of the current borrower.

    private void getHistory() {
        System.out.println("here's your borrowing history: ");
        ArrayList<Book> history = borrower.getBorrowingHistory();
        for (Book b: history) {
            System.out.println(b.getTitle());
        }
    }

    //REQUIRES: Valid JSON library data.
    //MODIFIES: library
    //EFFECTS: Loads library data from a JSON file to the console.

    private void loadLibraryConsole() {
        try {

            library = jsonReader.read();

            System.out.println("Library data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading library data from file: " + e.getMessage());
        }
    }

    //REQUIRES: Library data.
    //MODIFIES: library.json, mainPanel
    //EFFECTS: Saves the library data to a JSON file.

    private void saveLibrary() {
        JPanel savePanel = new JPanel();
        JLabel messageLabel = new JLabel();
        messageLabel.setVisible(false);
        savePanel.add(messageLabel);
        try {

            jsonWriter.open();
            jsonWriter.write(library);
            jsonWriter.close();
            messageLabel.setText("Library data saved successfully.");
            messageLabel.setVisible(true);
        } catch (FileNotFoundException e) {
            messageLabel.setText("Error saving library data to file: " + e.getMessage());
            messageLabel.setVisible(true);
        }

        mainPanel.add(savePanel);
        mainPanel.revalidate();
        mainPanel.repaint();


    }


    //REQUIRES: Valid JSON Library data.
    //MODIFIES: library.json
    //EFFECTS: Saves the library data to library.json

    private void saveLibraryConsole() {
        try {
            jsonWriter.open();
            jsonWriter.write(library);
            jsonWriter.close();
            System.out.println("Library data saved successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Error saving library data to file: " + e.getMessage());
        }
    }

    //REQUIRES: Valid JSON library data.
    //MODIFIES: library, mainPanel
    //EFFECTS: Loads library data from library.json to GUI
    private void loadLibrary() {
        JPanel loadPanel = new JPanel();
        JLabel messageLabel = new JLabel();
        messageLabel.setVisible(false);

        loadPanel.add(messageLabel);
        try {

            library = jsonReader.read();

            messageLabel.setText("Library data loaded successfully.");
            messageLabel.setVisible(true);
            updatePanel();
        } catch (IOException e) {
            messageLabel.setText("Error loading library data from file: " + e.getMessage());
            messageLabel.setVisible(true);
        }

        mainPanel.add(loadPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    //EFFECTS: Prints information about all books in the library to the console.
    private void showBooks() {
        for (String s : library.showBooksInfo()) {
            System.out.println(s);

        }
    }





    //REQUIRES: Valid user input on the console.

    //EFFECTS: Searches for a book by title and displays its availability and information on the console.
    private void searchBook() {
        System.out.println("Enter title of the book you wish to search for: ");
        String title = scanner.nextLine();
        Book b = library.searchByTitle(title);
        if (b != null) {
            System.out.println("Yes, you can find this book at our library!");
            if (b.isBorrowed()) {
                System.out.println("But unfortunately, it has been borrowed!");
            } else {
                System.out.println(b.displayInfo());
                System.out.println("You can borrow this book if you'd like by clicking 2. Look at the menu below!");
            }




        } else {
            System.out.println("Unfortunately, we don't have this book at our library yet!");
        }

    }


    //REQUIRES: Book title, author, and genre inputs on the console
    //MODIFIES: library.
    //EFFECTS: Donates a book to the library.


    private void donateConsole() {
        System.out.println("Enter title of the book: ");
        String title = scanner.nextLine();

        System.out.println("Enter Author's name: ");
        String author = scanner.nextLine();
        Book b1 = library.searchByTitle(title);
        if (b1 != null && b1.getAuthor().equals(author)) {
            System.out.println("The library already has this book, but we'll still take it :)");
            library.addBook(new Book(b1.getTitle(), b1.getAuthor(), b1.getGenre(),
                    b1.isBorrowed(), b1.getRating(), b1.getLoanRecord(), b1.getDesc(), 0));
        } else {
            System.out.println("Enter Genre of book: ");
            String genre = scanner.nextLine();
            System.out.println("Enter a short description of the book: ");
            String desc = scanner.nextLine();

            Book newBook = new Book(title, author, genre, false, 0.0, new ArrayList<Borrower>(), desc, 0);
            System.out.println(borrower.donateBook(newBook, library));
        }

        System.out.println("inventory");
        System.out.println(library.showAvailableBooks());


        System.out.println("succesfully donated, thanks!");
    }





    //REQUIRES: Book title, author, genre, and description inputs in text fields.
    //MODIFIES: Library, mainPanel
    //EFFECTS: Donates a book to the library.



    private void donate() {

        JPanel donatePanel = new JPanel();

        // Create the title text field
        JTextField bookTitle = new JTextField(20);
        JLabel titleLabel = new JLabel("Enter the title of the book: ");
        donatePanel.add(titleLabel);
        donatePanel.add(bookTitle);

        //Create author field
        JLabel authorLabel = setupAuthorLabel(donatePanel);
        JTextField bookAuthor = setupAuthorTextField(donatePanel);



        //Create Genre field
        JLabel genreLabel = setupGenreLabel(donatePanel);
        JTextField bookGenre = setupGenreTextField(donatePanel);



        //Create description label

        JLabel descLabel = new JLabel("Enter description of the book: ");
        JTextField bookDesc = new JTextField(20);
        donatePanel.add(descLabel);
        donatePanel.add(bookDesc);
        descLabel.setVisible(false);
        bookDesc.setVisible(false);



        // Create a button for donating the book
        JButton donateButton = new JButton("Donate");
        donatePanel.add(donateButton);
        donateButton.setVisible(false);

        donateHelper2(bookTitle, authorLabel, bookAuthor, genreLabel, bookGenre, descLabel, bookDesc, donateButton);


        // Add action listener to the donate button
        donateHelper(donateButton, bookTitle, bookAuthor, bookDesc, bookGenre);

        // Display the donate panel in the main panel
        mainPanel.add(donatePanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    //HELPERS FOR DONATE():

    // EFFECTS: Sets up action listener for the donate button.

    private void donateHelper(JButton donateButton, JTextField bookTitle, JTextField bookAuthor,
                              JTextField bookDesc, JTextField bookGenre) {
        donateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bookTitle.getText() != "" && bookAuthor.getText() != ""
                        && bookGenre.getText() != "" && bookDesc.getText() != "") {
                    label.setText(library.addBook(new Book(bookTitle.getText(), bookAuthor.getText(),
                            bookGenre.getText(), true, 0.0,
                            new ArrayList<Borrower>(), bookDesc.getText(), 0)));
                }

                updatePanel();
                showThankYouImage();




            }
        });


    }

    // EFFECTS: Sets up action listeners for book input fields.

    private void donateHelper2(JTextField bookTitle, JLabel authorLabel, JTextField bookAuthor, JLabel genreLabel,
                               JTextField bookGenre, JLabel descLabel, JTextField bookDesc, JButton donateButton) {
        bookTitle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authorLabel.setVisible(true);
                bookAuthor.setVisible(true);

                bookAuthor.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        genreLabel.setVisible(true);
                        bookGenre.setVisible(true);

                        bookGenre.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                descLabel.setVisible(true);
                                bookDesc.setVisible(true);
                                descActionHelper(bookDesc, donateButton);


                            }
                        });
                    }
                });
            }
        });

    }

    // EFFECTS: Sets up action listener for book description field.

    private void descActionHelper(JTextField bookDesc, JButton donateButton) {
        bookDesc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                donateButton.setVisible(true);
            }
        });
    }

    // EFFECTS: Sets up author label in donation panel.

    private JLabel setupAuthorLabel(JPanel donatePanel) {
        JLabel authorLabel = new JLabel("Enter the author of the book: ");

        donatePanel.add(authorLabel);

        authorLabel.setVisible(false);
        return authorLabel;

    }

    // EFFECTS: Sets up author text field in donation panel.

    private JTextField setupAuthorTextField(JPanel donatePanel) {
        JTextField bookAuthor = new JTextField(20);
        donatePanel.add(bookAuthor);
        bookAuthor.setVisible(false);
        return bookAuthor;


    }

    // EFFECTS: Sets up genre label in donation panel.

    private JLabel setupGenreLabel(JPanel donatePanel) {
        JLabel genreLabel = new JLabel("Enter genre of the book: ");

        donatePanel.add(genreLabel);

        genreLabel.setVisible(false);

        return genreLabel;

    }

    // EFFECTS: Sets up genre text field in donation panel.

    private JTextField setupGenreTextField(JPanel donatePanel) {
        JTextField bookGenre = new JTextField(20);
        donatePanel.add(bookGenre);
        bookGenre.setVisible(false);
        return bookGenre;


    }

    //
    //






    private void updatePanel() {

        mainPanel.removeAll();
        displayMenu();
        JPanel booksinLibrary = new JPanel();

        ArrayList<String> books = library.showAvailableBooks();
        booksinLibrary.add(new JLabel("Books Available: "));


        for (String book: books) {
            if (book != books.get(books.size() - 1)) {
                booksinLibrary.add(new JLabel(book  + ','));
                booksinLibrary.add(new JLabel("\n"));
            } else {
                booksinLibrary.add(new JLabel(book));
                booksinLibrary.add(new JLabel("\n"));

            }
        }
        mainPanel.add(booksinLibrary);

        mainPanel.revalidate();
        mainPanel.repaint();

        frame.add(mainPanel);



    }



    //REQUIRES: Book title input on the console.
    //MODIFIES: library.
    //EFFECTS: Allows a borrower to borrow an available book from the library.

    private void borrowConsole() {
        System.out.println(library.showAvailableBooks());
        System.out.println("Enter title of book you wish to borrow: ");
        String title = scanner.nextLine();
        Book b = library.searchByTitle(title);
        if (library.searchByTitle(title) != null) {
            if (library.searchByTitle(title).isAvailable()) {
                borrower.borrowBook(b);

                library.searchByTitle(title).setBorrowed(true);
                System.out.println("Book successfully borrowed");
                System.out.println("Current Inventory at Library: ");
                System.out.println(library.showAvailableBooks());
            }
        } else {
            System.out.println("this book is not available to borrow at the library at this time");
        }

    }

    //REQUIRES: Book title input in the text field.
    //MODIFIES: library, mainPanel
    //EFFECTS: Allows a borrower to borrow an available book from the library.


    private void borrow() {
        JLabel errorLabel = new JLabel(" ");

        JPanel borrowPanel = new JPanel();
        JTextField bookTitle = new JTextField(20);
        JLabel titleLabel = new JLabel("Enter the title of the book: ");

        // Entering title of the book
        borrowPanel.add(titleLabel);
        borrowPanel.add(bookTitle);
        borrowHelper(bookTitle, errorLabel, borrowPanel);




        mainPanel.add(borrowPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    //borrow helper:

    // EFFECTS: Sets up action listener for the book title field to handle borrowing a book.

    private void borrowHelper(JTextField bookTitle, JLabel errorLabel, JPanel borrowPanel) {
        bookTitle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Book b = library.searchByTitle(bookTitle.getText());

                if (b != null) {
                    if (b.isAvailable()) {
                        if (borrower != null) { // Check if borrower is initialized
                            label.setText(borrower.borrowBook(b));
                            updatePanel();
                        } else {
                            errorLabel.setText("Borrower not initialized.");
                            borrowPanel.add(errorLabel);

                        }
                    } else {
                        errorLabel.setText("This book is not available to borrow at the library at this time");
                        borrowPanel.add(errorLabel);
                    }
                } else {
                    errorLabel.setText("Book with title \"" + bookTitle.getText() + "\" not found.");
                    borrowPanel.add(errorLabel);
                }
            }
        });
    }

    //
    //



    //REQUIRES: Book title input from the console
    //MODIFIES: library
    //EFFECTS: Allows a borrower to return a borrowed book to the library.

    private void bookReturnConsole() {
        System.out.println(library.showAvailableBooks());
        System.out.println("Enter title of the book you wish to return: ");
        String title = scanner.nextLine();
        Book b = library.searchByTitle(title);

        System.out.println(borrower.returnBook(b));

        System.out.println("Current inventory of the library: ");
        System.out.println(library.showAvailableBooks());

    }



    //REQUIRES: Book title input from the text field.
    //MODIFIES: library, mainPanel
    //EFFECTS: Allows a borrower to return a borrowed book to the library.


    private void bookReturn() {
        JPanel returnPanel = new JPanel();
        JLabel messageLabel = new JLabel("");
        JTextField bookTitle = new JTextField(20);
        JLabel titleLabel = new JLabel("Enter the title of the book: ");
        returnPanel.add(titleLabel);
        returnPanel.add(bookTitle);

        returnPanel.add(messageLabel);
        bookTitle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book b = library.searchByTitleReturn(bookTitle.getText());
                if (b != null && b.isBorrowed()) {
                    messageLabel.setText(borrower.returnBook(b));

                    updatePanel();
                } else {
                    messageLabel.setText("Book isn't available to return");


                }

            }
        });






        mainPanel.add(returnPanel);
        mainPanel.revalidate();
        mainPanel.repaint();





    }

    //REQUIRES: Book title and overdue days from console
    //MODIFIES: book
    //EFFECTS: Allows a borrower to pay fines for an overdue book.

    private void finePay() {
        System.out.println("What was the book you'd like to pay fines for: ");
        String title = scanner.nextLine();
        Book b = library.searchByTitle(title);

        if (b != null && b.isBorrowed()) {
            System.out.println("How many days overdue is the book? ");
            String days = scanner.nextLine();
            int daysOverdue = Integer.parseInt(days);
            b.setDaysOverdue(daysOverdue);
            System.out.println(library.calculateFines(borrower, b));
            System.out.println("enter amt you'd like to pay: ");
            String amt = scanner.nextLine();
            int amount = Integer.parseInt(amt);
            System.out.println(borrower.payFines(amount));


        }  else {
            System.out.println("This book was never borrowed.");

        }












    }


    //EFFECTS: Displays a thank you GIF in a dialog box.

    private void showThankYouImage() {
        // Create a new dialog to display the thank you image
        JDialog thankYouDialog = new JDialog(frame, "Thank You!");

        // Load the thank you image
        ImageIcon thankYouIcon = new ImageIcon("/Users/dave/Desktop/project_g0d5l/lib/THANK YOU IMAGE.gif");
        JLabel imageLabel = new JLabel(thankYouIcon);

        // Add the image label to the dialog
        thankYouDialog.add(imageLabel);

        // Set dialog properties
        thankYouDialog.setSize(300, 200);
        thankYouDialog.setLocationRelativeTo(frame);
        thankYouDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Display the dialog
        thankYouDialog.setVisible(true);
    }

    //REQUIRES: Borrower object.

    //EFFECTS: Checks if the borrower is valid

    private boolean authenticateBorrower(Borrower borrower) {
        if (borrower != null) {

            return true;
        } else {
            return false;
        }
    }


    //REQUIRES: Borrower object.
    //Effects: Displays information about the borrower.

    private void displayBorrowerInfo(Borrower borrower) {
        JLabel welcomeMessage = new JLabel("Welcome " + borrower.getName()
                + "! You have $" + borrower.getFinesAccumulated() + " fines.");
        JLabel borrowingHistory = new JLabel("Your borrowing history: " + borrower.getBorrowingHistory());

        mainPanel.add(welcomeMessage);
        mainPanel.add(borrowingHistory);
        System.out.println("Welcome " + borrower.getName()
                + "! You have $" + borrower.getFinesAccumulated() + " fines.");
        System.out.println("Your borrowing history: " + borrower.getBorrowingHistory());
    }

    // EFFECTS: Sets up action listener for the donate button.

    private void donateButtonMaker(JButton donateButton) {
        donateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                donate();
            }
        });
    }

    // EFFECTS: Sets up action listener for the quit button.

    private void quitButtonMaker(JButton quitButton) {
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printEventLogToConsole();

                frame.dispose();
            }
        });
    }

    // EFFECTS: Sets up action listener for the saveLibrary button.

    private void saveButtonMaker(JButton saveButton) {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveLibrary();
            }
        });
    }

    // EFFECTS: Sets up action listener for the loadLibrary button.


    private void loadButtonMaker(JButton loadButton) {
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadLibrary();
            }
        });
    }

    // EFFECTS: Sets up action listener for the borrow button.


    private void borrowButtonMaker(JButton borrowButton) {
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrow();
            }
        });
    }

    // EFFECTS: Sets up action listener for the return a book button.



    private void returnButtonMaker(JButton returnButton) {
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookReturn();
            }
        });
    }

    // EFFECTS: Sets up action listener for the switch to UI button.

    private void switchUIButtonMaker(JButton switchUIButton) {
        switchUIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runConsoleGUI();
            }
        });
    }










    //EFFECTS: Displays the main menu of the library application.


    private void displayMenu() {


        JButton donateButton = new JButton("Donate a book");
        mainPanel.add(donateButton);
        JButton borrowButton = new JButton("Borrow a Book");
        mainPanel.add(borrowButton);
        JButton returnButton = new JButton("Return a Book");
        mainPanel.add(returnButton);
        JButton saveButton = new JButton("Save the Library");
        mainPanel.add(saveButton);
        JButton loadButton = new JButton("Load the Library");
        mainPanel.add(loadButton);
        JButton quitButton = new JButton("Quit");
        mainPanel.add(quitButton);
        JButton switchUIButton = new JButton("Switch to Console");
        mainPanel.add(switchUIButton);
        displayBookInfo();


        donateButtonMaker(donateButton);
        quitButtonMaker(quitButton);
        saveButtonMaker(saveButton);
        loadButtonMaker(loadButton);
        borrowButtonMaker(borrowButton);
        returnButtonMaker(returnButton);
        switchUIButtonMaker(switchUIButton);



        frame.setVisible(true);







    }

    private static Library initializeLibrary() {
        // Initialize library with some borrowers and books
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Borrower> borrowers = new ArrayList<>();

        // Add some books
        books.add(new Book("The Silent Patient", "Alex Michaelides", "Mystery", false, 4.2, new ArrayList<>(),
                "The Silent Patient is a shocking psychological thriller of a woman's act "
                        + "of violence against her husband and of the "
                        + "therapist obsessed with uncovering her motive.", 9));
        books.add(new Book("The Chain", "Adrian McKinty", "Mystery", false, 3.8, new ArrayList<>(),
                "When a mother is targeted by a dangerous group of masterminds,"
                        + " she must commit a crime to save her kidnapped daughter -- or risk losing her forever", 0));
        books.add(new Book("Shoe Dog: A Memoir by the Creator of Nike",
                "Phil Knight", "Autobiography", false, 3.8, new ArrayList<>(),
                "Shoe Dog is a memoir by Nike co-founder Phil Knight. The memoir chronicles the history of "
                        + "Nike from its founding as Blue Ribbon Sports and its early "
                        + "challenges to its evolution into one of the world's most "
                        + "recognized and profitable companies. "
                        + "It also highlights certain parts of Phil Knight's life.", 0));
        books.add(new Book("Book5", "Author5", "Genre5", false, 3.8, new ArrayList<>(), "Description5", 0));


        // Add some borrowers
        borrowers.add(new Borrower("Alex", "00001", new ArrayList<>(), 0));
        borrowers.add(new Borrower("John", "00002", new ArrayList<>(), 0));

        return new Library(books, borrowers);
    }

    // EFFECTS: Sets up console UI for the app.


    private void runConsoleGUI() {


        // Call the method in an appropriate context
        if (authenticateBorrower(borrowerConsole)) {
            displayBorrowerInfo(borrowerConsole);

            boolean keepGoing = true;
            String command = null;

            while (keepGoing) {
                displayConsoleMenu();
                command = scanner.nextLine();
                command = command.toLowerCase();

                if (command.equals("0")) {
                    System.out.println("Okay, quitting :( ");
                    keepGoing = false;
                } else {
                    processCommand(command);
                }









            }

        } else {
            System.out.println("Invalid Borrower ID, exiting.......");
        }



    }

    //EFFECTS: Runs the library application in console mode.

    private void displayConsoleMenu() {
        boolean keepGoing = true;
        System.out.println("1. Donate a Book");
        System.out.println("2. Borrow a Book");
        System.out.println("3. Return a Book");
        System.out.println("4. Pay fine for a Book");
        System.out.println("5. Search for a book by title");
        System.out.println("6. Check your borrowing history");
        System.out.println("7. List all books in the library");
        System.out.println("8. Save the Library");
        System.out.println("9. Load the library");
        System.out.println("0. Quit");
    }
    // REQUIRES: library has valid book information
    // MODIFIES: mainPanel
    // EFFECTS: Creates a button to display information for all books in the library when clicked

    private void displayBookInfo() {
        JButton displayButton = new JButton("Display information on all books");

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> info = library.showBooksInfo();
                StringBuilder infoText = new StringBuilder();
                for (String inf : info) {
                    infoText.append(inf).append("\n\n");
                }

                JTextArea textArea = new JTextArea(infoText.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);

                JOptionPane.showMessageDialog(null, scrollPane,
                        "Book Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        mainPanel.add(displayButton);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void printEventLogToConsole() {
        try {
            EventLog log = EventLog.getInstance();
            Iterator<Event> iterator = log.iterator();

            System.out.println("Event Log:");
            while (iterator.hasNext()) {
                Event event = iterator.next();
                System.out.println(event.getDate() + "\n" + event.getDescription() + "\n");
            }
        } catch (Exception e) {
            System.err.println("Error printing event log: " + e.getMessage());
        }
    }



}