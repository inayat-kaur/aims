package myapp;

import myapp.user.User;

public class App {

    //The main method creates a user object and askes it to login
    public static void main(String[] args) {
        do{
            System.out.println("Welcome to AIMS! Kindly Login");
            User user = new User();
            boolean lg = user.login();
            user = null;
        }while(true);
    }
}