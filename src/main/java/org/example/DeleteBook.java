package org.example;

import java.util.Scanner;

public class DeleteBook implements IOOperation {

    @Override
    public void oper(Database database, User user) {

        Scanner s = new Scanner(System.in);
        System.out.println("Enter book name: ");
        String bookname = s.next();


        int i = database.getBook(bookname);
        if(i > -1) {
            database.deleteBook(i);
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Book doesn't exist!");
        }
        s.close();
        user.menu(database,user);
    }

}
