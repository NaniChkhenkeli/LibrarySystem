package org.example;

import org.checkerframework.checker.units.qual.A;

import java.util.Scanner;

public class Admin extends User {

    public Admin(String name) {
        super(name);
        this.operations = new IOOperation[] {
                new AddBook(),
                new AddBook(),
                new DeleteBook(),
                new Search(),
                new DeleteBook(),
                new ViewOrders(),
                new Exit()
    };
    }


    public Admin(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber);
        this.operations = new IOOperation[] {
                new AddBook(),
                new AddBook(),
                new DeleteBook(),
                new Search(),
                new DeleteBook(),
                new ViewOrders(),
                new Exit()
        };
    }

    @Override
    public void menu(Database database, User user) {
        System.out.println("1. View Books");
        System.out.println("2. Add Book");
        System.out.println("3. Delete Book");
        System.out.println("4. Search");
        System.out.println("5. Delete all data");
        System.out.println("6. View Orders");
        System.out.println("7. Exit");

        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        this.operations[n - 1].oper(database, user);
        s.close();
    }

    public String toString() {
        return name + "<N/>" + email + "<N/>" + phoneNumber + "<N/>" + "admin";

    }

}
