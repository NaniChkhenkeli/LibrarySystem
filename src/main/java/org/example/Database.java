package org.example;

import data.Books;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Database {
    ArrayList<User> users = new ArrayList<>();
    ArrayList<String> usernames = new ArrayList<>();
    ArrayList<Book> books = new ArrayList<>();
    ArrayList<String> booknames = new ArrayList<>();
    ArrayList<Order> orders = new ArrayList<Order>();
    ArrayList<Borrowing> borrowings = new ArrayList<Borrowing>();



    private File userfile = new File("C:\\Library MGMT System\\Data\\Users");
    private File booksfile = new File("C:\\Library MGMT System\\Data\\Books");
    private File folder = new File("C:\\Library MGMT System\\Data");
    private File ordersfile = new File("C:\\Library MGMT System\\Data\\Orders");
    private File borrowingsfile = new File("C:\\Library MGMT System\\Data\\Borrowings");


    public Database() {
     //   URL userfileUrl = Main.class.getResource("/Users.txt"); // Adjust if the file has a different name
     //   URL booksfileUrl = Main.class.getResource("/Books.txt"); // Adjust if the file has a different name
        if(!folder.exists()) {
            folder.mkdirs();
        }
        if(!userfile.exists()) {
            try {
                userfile.createNewFile();
            } catch (Exception e) {}
        }
        if(!booksfile.exists()) {
            try {
                booksfile.createNewFile();
            } catch (Exception e) {}
        }
        if(!ordersfile.exists()) {
            try {
                ordersfile.createNewFile();
            } catch (Exception e) {}
        }

        if(!borrowingsfile.exists()) {
            try {
                borrowingsfile.createNewFile();
            } catch (Exception e) {}
        }

        getUsers();
        getBooks();
        getOrders();
        getBorrowings();
    }

    public void addUser(User s) {
        users.add(s);
        usernames.add(s.getName());
        saveUsers();
    }

    public int login(String phoneNumber, String email) {
        int n = -1;
        for (User s : users) {
            if (s.getPhonenumber().matches(phoneNumber) && s.getEmail().matches(email)) {
                n = users.indexOf(s);
                break;
            }
        }
        return n;
    }



    public void addBook(Book book) {
        books.add(book);
        booknames.add(book.getName());
        saveBooks();
    }

    public User getUser(int n) {
        return users.get(n);
    }

    private void getUsers() {
        StringBuilder text1 = new StringBuilder();
        try (BufferedReader br1 = new BufferedReader(new FileReader(userfile))) {
            String s1;
            while ((s1 = br1.readLine()) != null) {
                text1.append(s1);
            }
        } catch (IOException e) {
            System.err.println("Error reading users file: " + e.toString());
        }

        if (!text1.toString().isEmpty()) {
            String[] a1 = text1.toString().split("<NewUser/>");
            for (String s : a1) {
                String[] a2 = s.split("<N/>");
                if (a2.length > 3 && a2[3].matches("Admin")) {
                    User user = new Admin(a2[0], a2[1], a2[2]);
                    addUser(user);
                    user.menu(this, user);
                } else {
                    User user = new NormalUser(a2[0], a2[1], a2[2]);
                    addUser(user);
                }
            }
        }
    }

    private void saveUsers() {
        StringBuilder text1 = new StringBuilder();
        for (User user : users) {
            text1.append(user.toString());
            text1.append("<NewUser/>");
        }
        try (PrintWriter pw = new PrintWriter(userfile)) {
            pw.print(text1.toString());
            pw.close();
            System.err.println("Data Saved");
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private void saveBooks() {
        StringBuilder text1 = new StringBuilder();
        for (Book book : books) {
            text1.append(book.toString2()).append("<NewBook/>\n");
        }
        try (PrintWriter pw = new PrintWriter(booksfile)) {
            pw.print(text1.toString());
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.toString());
        }
    }

    private void getBooks() {
        StringBuilder text1 = new StringBuilder();
        try (BufferedReader br1 = new BufferedReader(new FileReader(booksfile))) {
            String s1;
            while ((s1 = br1.readLine()) != null) {
                text1.append(s1);
            }
        } catch (IOException e) {
            System.err.println("Error reading books file: " + e.toString());
        }

        if (!text1.toString().isEmpty()) {
            String[] a1 = text1.toString().split("<NewBook/>");
            for (String s : a1) {
                Book book = parseBook(s);
                books.add(book);
                booknames.add(book.getName());
            }
        }
    }



    public Book parseBook(String s) {
        String[] a = s.split("<N/>");
        Book book = new Book();
        book.setName(a[0]);
        book.setAuthor(a[1]);
        book.setPublisher(a[2]);
        book.setAddress(a[3]);
        book.setQty(Integer.parseInt(a[4]));
        book.setPrice(Double.parseDouble(a[5]));
        book.setBrwcopies(Integer.parseInt(a[6]));
        return book;
    }

    public ArrayList<Book> getAllBooks() {
        return books;
    }

    public int getBook(String bookname) {
        int i = -1;
        for(Book book : books) {
            if(book.getName().matches(bookname)) {
                i = books.indexOf(book);
            }
        }
        return i;
    }

    public Book getBook(int i) {
        return books.get(i);
    }

    public void deleteBook(int i) {
        books.remove(i);
        booknames.remove(i);
        saveBooks();
    }

    public void deleteAllData() {
        if(!userfile.exists()) {
            try {
                userfile.delete();
                userfile.createNewFile();
            } catch (Exception e) {}
        }
        if(!booksfile.exists()) {
            try {
                booksfile.delete();

            } catch (Exception e) {}
        }

        if(!ordersfile.exists()) {
            try {
                ordersfile.delete();
            } catch (Exception e) {}
        }

        if(!borrowingsfile.exists()) {
            try {
                borrowingsfile.delete();
            } catch (Exception e) {}
        }
    }

    public void addOrder(Order order, Book book, int bookindex) {
        orders.add(order);
        books.set(bookindex, book);
        saveOrders();
        saveBooks();
    }


    private void saveOrders() {
        String text1 = "";
        for (Order order : orders) {
            text1 = text1 + order.toString2() + "<NewOrder/>\n";
        }
        try {
            PrintWriter pw = new PrintWriter(ordersfile);
            pw.print(text1);
            pw.close();
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private void getOrders() {
        String text1 = "";
        try (BufferedReader br1 = new BufferedReader(new FileReader(ordersfile))) {
            String s1;
            while ((s1 = br1.readLine()) != null) {
                text1 = text1 + s1;
            }
            br1.close();
        } catch (IOException e) {
            System.err.println(e.toString());
        }

        if (!text1.matches("") || !text1.isEmpty()) {
            String[] a1 = text1.split("<NewOrder/>");
            for (String s : a1) {
                Order order = parseOrder(s);
                orders.add(order);
            }
        }
    }

    private User getUserByName(String name) {
        User u = new NormalUser("");
        for (User user : users) {
            if (user.getName().matches(name)) {
                u = user;
                break;
            }
        }
        return u;
    }

    private Order parseOrder(String s) {
        String[] a = s.split("<N/>");
        Order order = new Order(books.get(getBook(a[0])), getUserByName(a[1]),
                Double.parseDouble(a[2]), Integer.parseInt(a[3]));
        return order;
    }

    public ArrayList<Order> getAllOrders() {
        return orders;
    }

    private void saveBorrowings() {
        String text1 = "";
        for (Borrowing borrowing : borrowings) {
            text1 = text1 + borrowing.toString2() + "<NewBorrowing/>\n";
        }
        try {
            PrintWriter pw = new PrintWriter(borrowingsfile);
            pw.print(text1);
            pw.close();
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private void getBorrowings() {
        String text1 = "";
        try (BufferedReader br1 = new BufferedReader(new FileReader(borrowingsfile))) {
            String s1;
            while ((s1 = br1.readLine()) != null) {
                text1 = text1 + s1;
            }
            br1.close();
        } catch (IOException e) {
            System.err.println(e.toString());
        }

        if (!text1.matches("") || !text1.isEmpty()) {
            String[] a1 = text1.split("<NewBorrowing/>");
            for (String s : a1) {
            Borrowing borrowing = parseBorrowing(s);
            borrowings.add(borrowing);
            }
        }
    }

    private Borrowing parseBorrowing(String s) {
        String [] a = s.split("<N/>");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(a[0], formatter);
        LocalDate finish = LocalDate.parse(a[1], formatter);
        Book book = getBook(getBook(a[3]));
        User user = getUserByName(a[4]);
        Borrowing brw = new Borrowing(start, finish, book, user);
        return brw;
    }

    public void borrowBook(Borrowing brw, Book book, int bookindex) {
        borrowings.add(brw);
        books.set(bookindex, book);
        saveBorrowings();
        saveBooks();
    }


    public ArrayList<Borrowing> getBrws() {
        return borrowings;
    }


    public void returnBook(Borrowing b, Book book, int bookindex) {
        borrowings.remove(b);
        books.set(bookindex, book);
        saveBorrowings();
        saveBooks();
    }

}



