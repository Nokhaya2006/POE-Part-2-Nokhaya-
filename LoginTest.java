package com.mycompany.chatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    private Login login;

    @BeforeEach
    public void setUp() {
        login = new Login();
    }

    @Test
    public void testCheckUserName_Valid() {
        assertTrue(login.checkUserName("K_lo"));
        assertTrue(login.checkUserName("a_b"));
    }

    @Test
    public void testCheckUserName_Invalid_TooLong() {
        assertFalse(login.checkUserName("User_Name"));
    }

    @Test
    public void testCheckUserName_Invalid_NoUnderscore() {
        assertFalse(login.checkUserName("Kalo"));
    }

    @Test
    public void testCheckPasswordComplexity_Valid() {
        assertTrue(login.checkPasswordComplexity("Ch@t123!"));
    }

    @Test
    public void testCheckPasswordComplexity_Invalid() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCheckCellPhoneNumber_Valid() {
        assertTrue(login.checkCellPhoneNumber("+27612345678"));
        assertTrue(login.checkCellPhoneNumber("+27712345678"));
        assertTrue(login.checkCellPhoneNumber("+27812345678"));
    }

    @Test
    public void testCheckCellPhoneNumber_Invalid() {
        assertFalse(login.checkCellPhoneNumber("0612345678"));
        assertFalse(login.checkCellPhoneNumber("+27512345678"));
        assertFalse(login.checkCellPhoneNumber("+2761234567"));
    }

    @Test
    public void testRegistrationAndSuccessfulLogin() {
        String regMessage = login.registerUser("O_ra", "Ch@t123!", "+27612345678", "Oratile", "Name");
        assertNotNull(regMessage);

        boolean loginSuccess = login.loginUser("O_ra", "Ch@t123!");
        assertTrue(loginSuccess);

        String statusMessage = login.returnLoginStatus(loginSuccess);
        assertNotNull(statusMessage);
    }

    @Test
    public void testLogin_Failure_WrongPassword() {
        login.registerUser("O_ra", "Ch@t123!", "+27612345678", "Oratile", "Name");
        boolean loginSuccess = login.loginUser("O_ra", "WrongPass123");
        assertFalse(loginSuccess);
    }
}