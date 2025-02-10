package week2.library_management;

// Main.java
public class Main {
    public static void main(String[] args) {
        LibraryManager libManager = new LibraryManager();

        // Initialize library system
        Book book1 = new Book("The Lord of the Rings", "J.R.R. Tolkien", "978-06182602");
        Book book2 = new Book("Pride and Prejudice", "Jane Austen", "978-01414395");
        Book book3 = new Book("1984", "George Orwell", "978-0451524935");
        libManager.addBook(book1);
        libManager.addBook(book2);
         libManager.addBook(book3);

        User user1 = new User("Alice", "U1001");
        User user2 = new User("Bob", "U1002");
        libManager.addUser(user1);
        libManager.addUser(user2);

        // Demonstrate borrowing, returning, and reserving books
        try {
            libManager.borrowBook("978-06182602", "U1001");
            libManager.borrowBook("978-01414395", "U1001");
            libManager.returnBook("978-06182602", "U1001");
            libManager.reserveBook("978-0451524935", "U1002");
            //Try to borrow same book
            libManager.borrowBook("978-06182602", "U1002");


        } catch (BookNotFoundException | UserNotFoundException | MaxBooksAllowedException e) {
            System.err.println("Error: " + e.getMessage());
        }

        // Demonstrate searching
        Book foundBook = libManager.searchBook("Pride");
        if (foundBook != null) {
            System.out.println("Found book: " + foundBook);
        } else {
            System.out.println("Book not found.");
        }

        //Demonstrate Multithreading

        Runnable task1 = () -> {
            try {
                libManager.borrowBook("978-0451524935", "U1001");
            } catch (BookNotFoundException | UserNotFoundException | MaxBooksAllowedException e) {
                System.err.println("Error in thread 1: " + e.getMessage());
            }
        };

        Runnable task2 = () -> {
            try {
                libManager.returnBook("978-0451524935", "U1001");
            } catch (BookNotFoundException | UserNotFoundException e) {
                System.err.println("Error in thread 2: " + e.getMessage());
            }
        };

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }


    }
}
