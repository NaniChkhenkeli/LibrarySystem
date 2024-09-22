package org.example;

import java.util.Scanner;

public class Exit implements IOOperation {

    Scanner s;
    Database database;
    User user;

    @Override
    public void oper(Database database, User user) {
        this.database = database;
        this.user = user;
        System.out.println("\nAre you sure that you want to quit?\n" +
                "1. Yes\n2. Main Menu");
        s = new Scanner(System.in);
        int choice = s.nextInt();
        s.nextLine();

        if (choice == 1) {
            System.out.println("0. Exit\n1. Login\n2. New User");
            int option = s.nextInt();
            s.nextLine(); // Consume the newline character left by nextInt()

            switch (option) {
                case 1:
                    login();
                    break;
                case 2:
                    newUser();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    System.exit(0); // Exit the program
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        } else {
            user.menu(database, user); // Go back to the main menu
        }
    }

    private void login() {
        System.out.println("Enter phone number:");
        String phoneNumber = s.nextLine();
        System.out.println("Enter email:");
        String email = s.nextLine();
        int n = database.login(phoneNumber, email);
        if (n != -1) {
            User loggedInUser = database.getUser(n);
            loggedInUser.menu(database, loggedInUser);
        } else {
            System.out.println("User doesn't exist!");
        }
    }

    private void newUser() {
        System.out.println("Enter name:");
        String name = s.nextLine();
        System.out.println("Enter phone number:");
        String phoneNumber = s.nextLine();
        System.out.println("Enter email:");
        String email = s.nextLine();
        System.out.println("1. Admin\n2. Normal User");

        int userType;
        try {
            userType = Integer.parseInt(s.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, assuming Normal User");
            userType = 2;
        }

        User newUser;
        if (userType == 1) {
            newUser = new Admin(name, email, phoneNumber);
        } else {
            newUser = new NormalUser(name, email, phoneNumber);
        }
        database.addUser(newUser);
        newUser.menu(database, newUser);
    }
}
