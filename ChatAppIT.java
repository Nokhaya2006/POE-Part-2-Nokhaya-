package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChatAppIT {

    @Test
    public void testMain() {

        assertDoesNotThrow(() -> {
            ChatApp.main(new String[]{});
        });
    }
}