/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp; // 

import java.util.regex.Pattern;

public class Login {
    private String storedUsername;
    private String storedPassword;
    private String firstName;
    private String lastName;

    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        if (password == null) return false;
        boolean hasMinLength = password.length() >= 8;
        boolean hasCapital = Pattern.matches(".*[A-Z].*", password);
        boolean hasNumber = Pattern.matches(".*\\d.*", password);
        boolean hasSpecialChar = Pattern.matches(".*[^A-Za-z0-9].*", password);
        return hasMinLength && hasCapital && hasNumber && hasSpecialChar;
    }

    public boolean checkCellPhoneNumber(String phone) {
        return phone != null && Pattern.matches("^\\+27[678]\\d{8}$", phone);
    }

    public String registerUser(String username, String password, String phone, String firstName, String lastName) {
        this.storedUsername = username;
        this.storedPassword = password;
        this.firstName = firstName;
        this.lastName = lastName;
        return "The user has been registered successfully.";
    }

    public boolean loginUser(String username, String password) {
        return username.equals(storedUsername) && password.equals(storedPassword);
    }

    public String returnLoginStatus(boolean isLoggedIn) {
        return isLoggedIn ? "Welcome " + firstName + " " + lastName : "Login failed.";
    }
}