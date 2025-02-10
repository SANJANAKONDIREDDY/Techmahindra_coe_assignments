package week2.library_management;


import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String userID;
    private List<Book> borrowedBooks;

    public User(String name, String userID) {
        this.name = name;
        this.userID = userID;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", userID='" + userID + '\'' +
                ", borrowedBooks=" + borrowedBooks +
                '}';
    }
}