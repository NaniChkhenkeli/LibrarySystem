package org.example;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Borrowing {

    LocalDate start;
    LocalDate finish;
    Book book;
    User user;

    // Formatter to format LocalDate to String in "yyyy-MM-dd" format
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Borrowing(Book book, User user) {
        this.start = LocalDate.now();   // Borrowing starts today
        this.finish = start.plusDays(14); // Borrowing ends after 14 days
        this.book = book;
        this.user = user;
    }

    public Borrowing(LocalDate start, LocalDate finish, Book book, User user) {
        this.start = start;
        this.finish = finish;
        this.book = book;
        this.user = user;
    }

    // Get the start date formatted as "yyyy-MM-dd"
    public String getStart() {
        return formatter.format(start);
    }

    // Get the finish date formatted as "yyyy-MM-dd"
    public String getFinish() {
        return formatter.format(finish);
    }

    // Calculate the number of days left until the borrowing period expires
    public int getDaysLeft() {
        return Period.between(LocalDate.now(), finish).getDays(); // Recalculate every time
    }

    // Getter for the borrowed book
    public Book getBook() {
        return book;
    }

    // Setter for the borrowed book
    public void setBook(Book book) {
        this.book = book;
    }

    // Getter for the user who borrowed the book
    public User getUser() {
        return user;
    }

    // Setter for the user
    public void setUser(User user) {
        this.user = user;
    }

    // Overriding toString method for default display of Borrowing object
    @Override
    public String toString() {
        return "Borrowing time: " + getStart() + "\nExpiry date: " + getFinish() + "\nDays left: " + getDaysLeft();
    }

    // Another toString method, using a different format
    public String toString2() {
        return getStart() + "<N/>" + getFinish() + "<N/>" + getDaysLeft() + "<N/>" +
                book.getName() + "<N/>" + user.getName() + "<N/>";
    }
}
