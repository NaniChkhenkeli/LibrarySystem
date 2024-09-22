package org.example;

import java.util.Scanner;

public class AddBook implements IOOperation {
    @Override
    public void oper(Database database, User user) {

        Scanner s = new Scanner(System.in);
        Book book = new Book();
        System.out.println("Enter book name: ");
        String name = s.next();
        if(database.getBook(name)> -1) {
            System.out.println("There is a book with this name!");
            user.menu(database,user);
        } else {
            book.setName(s.next());
        }
        book.setName(s.next());
        System.out.println("Enter book author: ");
        book.setAuthor(s.next());
        System.out.println("Enter book publisher: ");
        book.setPublisher(s.next());
        System.out.println("Enter book collection ");
        book.setAddress(s.next());
        System.out.println("Enter qty: ");
        book.setQty(s.nextInt());
        System.out.println("Enter price: ");
        book.setPrice(s.nextDouble());
        System.out.println("Enter borrowing copies: ");
        book.setBrwcopies(s.nextInt());


        database.addBook(book);

        System.out.println("Book added successfully!");
      //  s.close();
        user.menu(database, user);
    }
}
