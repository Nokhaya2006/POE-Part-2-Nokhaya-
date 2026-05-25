/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp;

/**
 *
 * @author Oratile
 */
import java.util.Scanner;
import java.util.regex.Pattern;

public class ChatApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login login = new Login();

        // REGISTER
        System.out.println("--- CREATE YOUR ACCOUNT ---");

        System.out.print("Enter First Name: ");
        String firstName = input.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = input.nextLine();

        String username;
        while (true) {
            System.out.print("Enter Username: ");
            username = input.nextLine();
            if (login.checkUserName(username)) break;
            System.out.println("Invalid username. Ensure it contains '_' and is <=5 chars. Try again.");
        }

        String password;
        while (true) {
            System.out.print("Enter Password: ");
            password = input.nextLine();
            if (login.checkPasswordComplexity(password)) break;
            System.out.println("Invalid password. Please check the requirements above.");
        }

        String phone;
        while (true) {
            System.out.print("Enter Phone Number: ");
            phone = input.nextLine();
            if (login.checkCellPhoneNumber(phone)) break;
            System.out.println("Invalid cell number. Must be +27 followed by 9 digits starting with 6,7,8. Try again.");
        }

        System.out.println(login.registerUser(username, password, phone, firstName, lastName));

        // LOGIN
        System.out.println("--- SIGN IN ---");
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.print("Username: ");
            String u = input.nextLine();

            System.out.print("Password: ");
            String p = input.nextLine();

            loggedIn = login.loginUser(u, p);
            System.out.println(login.returnLoginStatus(loggedIn));

            if (!loggedIn) {
                System.out.println("Please try again.\n");
            }
        }

        System.out.println("\nYou are now logged in. Goodbye!");
        input.close();
    }
}