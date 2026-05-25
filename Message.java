/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

/**
 *
 * @author hi
 */
import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    public Message(int messageNumber, String recipient, String message) {
        this.messageNumber = messageNumber;
        this.recipient     = recipient;
        this.message       = message;
        this.messageID     = generateMessageID();
        this.messageHash   = createMessageHash();
    }

    // Generates a unique ID for each message
    private String generateMessageID() {
        Random rand = new Random();
        int part1 = rand.nextInt(9000) + 1000;
        int part2 = rand.nextInt(9000) + 1000;
        return part1 + "" + part2;
    }

    // ID must be 10 digits or fewer
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Recipient must follow the same +27 format used in registration
    public boolean checkRecipientCell() {
        return recipient.matches("^\\+27[678]\\d{8}$");
    }

    // Message must not exceed 250 characters
    public boolean checkMessageLength() {
        return message.length() <= 250;
    }

    // Creates a short hash to identify the message
    public String createMessageHash() {
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord  = words[words.length - 1].toUpperCase();
        return messageID.substring(0, 2) + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    // Returns a status based on what the user chose to do with the message
    public String sentMessage(int choice) {
        switch (choice) {
            case 1: return "Message successfully sent.";
            case 2: return "Press 0 to delete the message.";
            case 3: return "Message successfully stored.";
            default: return "Invalid option.";
        }
    }

    // Displays the full details of a message
    public String printMessage() {
        return "Message ID : " + messageID +
               "\nHash       : " + messageHash +
               "\nRecipient  : " + recipient +
               "\nMessage    : " + message;
    }

    // Getters
    public String getMessage()     { return message;     }
    public String getRecipient()   { return recipient;   }
    public String getMessageHash() { return messageHash; }
    public String getMessageID()   { return messageID;   }

    // Saves the message details to a JSON file
    public void storeMessage() {
        try {
            FileWriter writer = new FileWriter("storedMessages.json", true);
            writer.write("{\n");
            writer.write("\"MessageID\":\"" + messageID + "\",\n");
            writer.write("\"MessageHash\":\"" + messageHash + "\",\n");
            writer.write("\"Recipient\":\"" + recipient + "\",\n");
            writer.write("\"Message\":\"" + message + "\"\n");
            writer.write("}\n");
            writer.close();
        } catch (Exception e) {
            System.out.println("Error saving message");
        }
    }

    // Main method — runs the message feature directly
    public static void main(String[] args) {

        java.util.Scanner input = new java.util.Scanner(System.in);
        ArrayList<Message> messageList = new ArrayList<>();
        boolean running = true;

        System.out.println("==============================================");
        System.out.println("         Welcome To QuickChat                ");
        System.out.println("==============================================");

        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1) Send Messages");
            System.out.println("2) View Sent Messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            String option = input.nextLine().trim();

            switch (option) {

                case "1":
                    int total = 0;
                    while (true) {
                        System.out.print("How many messages would you like to send? ");
                        try {
                            total = Integer.parseInt(input.nextLine().trim());
                            if (total > 0) break;
                            System.out.println("Please enter a number greater than 0.");
                        } catch (NumberFormatException e) {
                            System.out.println("Numbers only please.");
                        }
                    }

                    int i = 1;
                    while (i <= total) {
                        System.out.println("\n--- Message " + i + " of " + total + " ---");

                        // Get recipient number
                        String cellNumber;
                        while (true) {
                            System.out.print("Enter recipient cell number: ");
                            cellNumber = input.nextLine().trim();
                            if (cellNumber.matches("^\\+27[678]\\d{8}$")) break;
                            System.out.println("Invalid cell number. Must be +27 followed by 9 digits starting with 6,7,8. Try again.");
                        }

                        // Get message text
                        String messageText;
                        while (true) {
                            System.out.print("Enter your message (max 250 characters): ");
                            messageText = input.nextLine();
                            if (messageText.trim().isEmpty()) {
                                System.out.println("Message cannot be empty.");
                            } else if (messageText.length() > 250) {
                                System.out.println("Message is too long. " + messageText.length() + " characters entered. Please shorten it.");
                            } else {
                                break;
                            }
                        }

                        // Create the message object
                        Message msg = new Message(i, cellNumber, messageText);

                        if (!msg.checkMessageID()) {
                            System.out.println("Warning: message ID exceeded the allowed length.");
                        }

                        // Ask what to do with the message
                        System.out.println("\nWhat would you like to do with this message?");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message to send later");
                        System.out.print("Choose (1/2/3): ");

                        int action = 0;
                        while (true) {
                            try {
                                action = Integer.parseInt(input.nextLine().trim());
                                if (action >= 1 && action <= 3) break;
                                System.out.print("Enter 1, 2, or 3 only: ");
                            } catch (NumberFormatException e) {
                                System.out.print("Numbers only. Enter 1, 2, or 3: ");
                            }
                        }

                        if (action == 1) {
                            messageList.add(msg);
                            System.out.println("Message successfully sent.");
                            System.out.println("Message Hash: " + msg.getMessageHash());
                            msg.storeMessage();
                            i++;
                        } else if (action == 2) {
                            System.out.println("Press 0 to delete the message.");
                            System.out.println("Message deleted. Please redo this message.");
                        } else if (action == 3) {
                            messageList.add(msg);
                            System.out.println("Message successfully stored.");
                            System.out.println("Message Hash: " + msg.getMessageHash());
                            msg.storeMessage();
                            i++;
                        }
                    }
                    break;

                case "2":
                    System.out.println("Coming Soon.");
                    break;

                case "3":
                    System.out.println("\nGoodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("That is not a valid option. Please enter 1, 2, or 3.");
            }
        }

        input.close();
    }
}
