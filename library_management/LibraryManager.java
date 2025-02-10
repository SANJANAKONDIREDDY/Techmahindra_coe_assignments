package week2.library_management;
import java.io.*;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;

public class LibraryManager extends LibrarySystem {
    private static final int MAX_BOOKS_PER_USER = 3;
    private final Lock lock = new ReentrantLock();

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void borrowBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException, MaxBooksAllowedException {
        lock.lock(); // Thread safety
        try {
            Book book = findBookByISBN(ISBN);
            User user = findUserByID(userID);

            if (book == null) {
                throw new BookNotFoundException("Book with ISBN " + ISBN + " not found.");
            }
            if (user == null) {
                throw new UserNotFoundException("User with ID " + userID + " not found.");
            }
            if (user.getBorrowedBooks().size() >= MAX_BOOKS_PER_USER) {
                throw new MaxBooksAllowedException("User " + userID + " has reached the maximum allowed books.");
            }
             if (!book.isAvailable()) {
                throw new BookNotFoundException("Book with ISBN " + ISBN + " is currently not available.");
            }

            book.setAvailable(false);
            user.getBorrowedBooks().add(book);
            System.out.println("Book " + ISBN + " borrowed by user " + userID);

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void returnBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException {
        lock.lock(); // Thread safety
        try {
            Book book = findBookByISBN(ISBN);
            User user = findUserByID(userID);

            if (book == null) {
                throw new BookNotFoundException("Book with ISBN " + ISBN + " not found.");
            }
            if (user == null) {
                throw new UserNotFoundException("User with ID " + userID + " not found.");
            }

            if (user.getBorrowedBooks().removeIf(b -> b.getISBN().equals(ISBN))) {
                book.setAvailable(true);
                System.out.println("Book " + ISBN + " returned by user " + userID);
            } else {
                System.out.println("User " + userID + " did not borrow book " + ISBN);
            }


        } finally {
            lock.unlock();
        }
    }

    @Override
    public void reserveBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException {
        //Implement reservation logic here
        lock.lock();
         try {
            Book book = findBookByISBN(ISBN);
            User user = findUserByID(userID);

             if (book == null) {
                throw new BookNotFoundException("Book with ISBN " + ISBN + " not found.");
            }
            if (user == null) {
                throw new UserNotFoundException("User with ID " + userID + " not found.");
            }

            System.out.println("Book " + ISBN + " reserved by user " + userID);

         } finally {
            lock.unlock();
        }
    }

    private Book findBookByISBN(String ISBN) {
        for (Book book : books) {
            if (book.getISBN().equals(ISBN)) {
                return book;
            }
        }
        return null;
    }

    private User findUserByID(String userID) {
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }
        return null;
    }
  //In LibraryManager.java

    public void saveData(String bookFile, String userFile) {
        try (ObjectOutputStream bookOutputStream = new ObjectOutputStream(new FileOutputStream(bookFile));
             ObjectOutputStream userOutputStream = new ObjectOutputStream(new FileOutputStream(userFile))) {

            bookOutputStream.writeObject(this.books);
            userOutputStream.writeObject(this.users);

            System.out.println("Library data saved successfully.");

        } catch (IOException e) {
            System.err.println("Error saving library data: " + e.getMessage());
        }
    }

    public void loadData(String bookFile, String userFile) {
        try (ObjectInputStream bookInputStream = new ObjectInputStream(new FileInputStream(bookFile));
             ObjectInputStream userInputStream = new ObjectInputStream(new FileInputStream(userFile))) {

            this.books = (List<Book>) bookInputStream.readObject();
            this.users = (List<User>) userInputStream.readObject();

            System.out.println("Library data loaded successfully.");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading library data: " + e.getMessage());
            // If files not found, initialize with empty lists
            this.books = new ArrayList<>();
            this.users = new ArrayList<>();
        }
    }


}
