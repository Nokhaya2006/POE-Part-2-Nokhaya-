package com.mycompany.chatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    private Message validMessage1;
    private Message validMessage2;
    private Message longMessage;
    private Message exact250Message;
    private Message singleWordMsg;

    @BeforeEach
    void setUp() {
        validMessage1 = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        validMessage2 = new Message(2, "+27718693002", "Hi Keegan, did you receive the payment?");
        longMessage = new Message(3, "+27718693002", "A".repeat(251));
        exact250Message = new Message(4, "+27718693002", "A".repeat(250));
        singleWordMsg = new Message(6, "+27718693002", "Hello");
    }

    @Test
    void testMessageLengthSuccess() {
        assertTrue(validMessage1.checkMessageLength());
    }

    @Test
    void testMessageLengthFailure() {
        assertFalse(longMessage.checkMessageLength());
    }

    @Test
    void testMessageLengthExactly250() {
        assertTrue(exact250Message.checkMessageLength());
    }

    @Test
    void testRecipientCellSuccess() {
        assertTrue(validMessage1.checkRecipientCell());
    }

    @Test
    void testRecipientCellFailureNoPlus() {
        Message badRecipient = new Message(1, "27718693002", "Hello");
        assertFalse(badRecipient.checkRecipientCell());
    }

    @Test
    void testRecipientCellFailureTooShort() {
        Message badRecipient = new Message(1, "+27123", "Hello");
        assertFalse(badRecipient.checkRecipientCell());
    }

    @Test
    void testRecipientCellFailureLetters() {
        Message badRecipient = new Message(1, "+27ABC12345", "Hello");
        assertFalse(badRecipient.checkRecipientCell());
    }

    @Test
    void testMessageHashCorrect() {
        String hash = validMessage1.getMessageHash();
        String expectedPrefix = validMessage1.getMessageID().substring(0, 2);

        assertNotNull(hash);
        assertTrue(hash.startsWith(expectedPrefix));
        assertTrue(hash.contains(":1:"));
        assertTrue(hash.contains("HI"));
        assertTrue(hash.endsWith("TONIGHT"));
    }

    @Test
    void testMessageHashSingleWord() {
        String hash = singleWordMsg.getMessageHash();
        assertNotNull(hash);
        assertTrue(hash.endsWith("HELLOHELLO"));
    }

    @Test
    void testMessageHashMultipleMessages() {
        String[] recipients = {"+27718693002", "+27834561234", "+27612345678"};
        String[] texts = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hi Keegan, did you receive the payment?",
            "Good morning everyone have a great day"
        };

        for (int i = 0; i < recipients.length; i++) {
            Message msg = new Message(i + 1, recipients[i], texts[i]);
            String hash = msg.getMessageHash();
            assertNotNull(hash);
            assertFalse(hash.isEmpty());
            assertTrue(hash.matches("[0-9]{2}:[0-9]+:[A-Z]+"));
        }
    }

    @Test
    void testMessageHashWithSpaces() {
        Message msg = new Message(7, "+27718693002", "Hello      world");
        String hash = msg.getMessageHash();
        assertNotNull(hash);
    }

    @Test
    void testMessageIDCreated() {
        String id = validMessage1.getMessageID();
        assertNotNull(id);
        assertTrue(validMessage1.checkMessageID());
        assertTrue(id.length() <= 10);
        assertFalse(id.isEmpty());
    }

    @Test
    void testSentMessageSend() {
        String result = validMessage1.sentMessage(1);
        assertEquals("Message successfully sent.", result);
    }

    @Test
    void testSentMessageDiscard() {
        String result = validMessage2.sentMessage(2);
        assertEquals("Press 0 to delete the message.", result);
    }

    @Test
    void testSentMessageStore() {
        String result = validMessage1.sentMessage(3);
        assertEquals("Message successfully stored.", result);
    }

    @Test
    void testSentMessageInvalidOption() {
        String result = validMessage1.sentMessage(99);
        assertEquals("Invalid option.", result);
    }

    @Test
    void testSentMessageNegativeOption() {
        String result = validMessage1.sentMessage(-1);
        assertEquals("Invalid option.", result);
    }

    @Test
    void testPrintMessage() {
        String output = validMessage1.printMessage();
        assertNotNull(output);
        assertTrue(output.contains(validMessage1.getMessageID()));
        assertTrue(output.contains(validMessage1.getMessageHash()));
        assertTrue(output.contains("Recipient"));
        assertTrue(output.contains("+27718693002"));
        assertTrue(output.contains("Message"));
        assertTrue(output.contains("Hi Mike, can you join us for dinner tonight?"));
    }

    @Test
    void testStoreMessage() {
        assertDoesNotThrow(() -> {
            validMessage1.storeMessage();
        });
    }

    @Test
    void testMessageIDNotNull() {
        assertNotNull(validMessage1.getMessageID());
    }

    @Test
    void testMessageHashNotEmpty() {
        String hash = validMessage1.getMessageHash();
        assertFalse(hash.isEmpty());
    }

    @Test
    void testPrintMessageNotEmpty() {
        String output = validMessage1.printMessage();
        assertFalse(output.isEmpty());
    }

    @Test
    void testDifferentMessagesGenerateDifferentHashes() {
        String hash1 = validMessage1.getMessageHash();
        String hash2 = validMessage2.getMessageHash();
        assertNotEquals(hash1, hash2);
    }

    @Test
    void testDifferentMessagesGenerateDifferentIDs() {
        String id1 = validMessage1.getMessageID();
        String id2 = validMessage2.getMessageID();
        assertNotEquals(id1, id2);
    }

    @Test
    void testStoreMessageMultipleTimes() {
        assertDoesNotThrow(() -> {
            validMessage1.storeMessage();
            validMessage1.storeMessage();
        });
    }

    @Test
    void testMessageHashUppercase() {
        String hash = validMessage1.getMessageHash();
        assertEquals(hash, hash.toUpperCase());
    }

    @Test
    void testRecipientWithSpacesFails() {
        Message msg = new Message(1, " +27718693002 ", "Hello");
        assertFalse(msg.checkRecipientCell());
    }

    @Test
    void testNullLikeMessage() {
        Message msg = new Message(1, "+27718693002", "null");
        assertTrue(msg.checkMessageLength());
    }

    @Test
    void testVeryShortMessage() {
        Message msg = new Message(1, "+27718693002", "H");
        assertTrue(msg.checkMessageLength());
        assertNotNull(msg.getMessageHash());
    }
}