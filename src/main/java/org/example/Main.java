package org.example;

import java.util.Scanner;

public class Main {
    static Database database;
    static Scanner s;

    public static void main(String[] args) {
        database = new Database();
        System.out.println("Welcome to Library Management System!");
        int num = -1;

        s = new Scanner(System.in);

        while (num != 0) {
            System.out.println(
                    "0. Exit\n" +
                            "1. Login\n" +
                            "2. New User");

            num = s.nextInt();
            s.nextLine();

            switch (num) {
                case 1:
                    login();
                    break;
                case 2:
                    newUser();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Error! Invalid option.");
            }
        }
    }

    private static void login() {
        System.out.println("Enter phone number:");
        String phoneNumber = s.nextLine();
        System.out.println("Enter email:");
        String email = s.nextLine();
        int n = database.login(phoneNumber, email);
        if (n != -1) {
            User user = database.getUser(n);
            user.menu(database, user);
        } else {
            System.out.println("User doesn't exist!");
        }
    }

    private static void newUser() {
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

        User user;
        if (userType == 1) {
            user = new Admin(name, email, phoneNumber);
        } else {
            user = new NormalUser(name, email, phoneNumber);
        }
        database.addUser(user);
        user.menu(database, user);
    }
}
