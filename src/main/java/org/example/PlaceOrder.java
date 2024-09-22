package org.example;

import java.util.Scanner;

public class PlaceOrder implements IOOperation {
    @Override
    public void oper(Database database, User user) {
        Scanner s = new Scanner(System.in);

        System.out.println("\nEnter book name:");
        String bookName = s.nextLine();

        int bookIndex = database.getBook(bookName);
        if (bookIndex == -1) {
            System.out.println("Book doesn't exist!");
        } else {
            Book book = database.getBook(bookIndex);

            System.out.println("Enter quantity:");
            int qty = s.nextInt();

            if (qty > book.getQty()) {
                System.out.println("Not enough books in stock. Available quantity: " + book.getQty());
            } else {
                double totalPrice = book.getPrice() * qty;

                Order order = new Order(book, user, totalPrice, qty);

                book.setQty(book.getQty() - qty);
                database.addOrder(order, book, bookIndex);

                System.out.println("Order placed successfully!\n");
            }
        }

        user.menu(database, user); // Return to user menu
    }
}
