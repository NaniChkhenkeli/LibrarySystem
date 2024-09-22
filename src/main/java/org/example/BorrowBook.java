package org.example;

import javax.xml.crypto.Data;
import java.util.Scanner;

public class BorrowBook implements IOOperation {

    @Override
    public void oper(Database database, User user) {

        Scanner s = new Scanner(System.in);
        System.out.println("Enter book name: ");
        String bookname = s.next();

        int i = database.getBook(bookname);
        if(i > -1) {
            Book book = database.getBook(i);
            if(book.getBrwcopies() > 1) {
                Borrowing borrowing = new Borrowing(book, user);
                book.setBrwcopies(book.getBrwcopies()-1);
                database.borrowBook(borrowing, book, i);
                System.out.println("You must return the book before 14 days from now\n +" +
                        "Expiry date: " + borrowing.getFinish() + "\nEnjoy!\n");
            } else {
                System.out.println("this book isn't available for borrowing");
            }
        } else {
            System.out.println("Book doesn't exist!\n");
        }
        user.menu(database, user);


    }
}
