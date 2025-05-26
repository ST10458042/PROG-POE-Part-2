import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class Message {
    private static int messageCount = 0;

    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;

    public void sendMessage() {
        messageID = String.format("%010d", (int) (Math.random() * 1_000_000_000));
        messageCount++;
        //Recepient number and it must start with +27
        recipient = JOptionPane.showInputDialog("Enter recipient number (e.g. +27797775739):");
        if (recipient == null || !recipient.matches("^\\+\\d{10,15}$")) {
            JOptionPane.showMessageDialog(null, "Invalid recipient number. Must start with '+' and contain 10 to 15 digits.");
            return;
        }

        messageText = JOptionPane.showInputDialog("Enter your message (max 250 characters):");
        if (messageText == null) return;

        if (messageText.length() > 250) {
            JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
            return;
        }
        //Message Hash and ID 
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0].toUpperCase() : "START";
        String lastWord = words.length > 1 ? words[words.length - 1].toUpperCase() : firstWord;

        messageHash = messageID.substring(0, 2) + "0" + messageCount + ":" + firstWord + lastWord;

        JOptionPane.showMessageDialog(null,
                "Message Sent!\n" +
                        "ID: " + messageID + "\n" +
                        "Recipient: " + recipient + "\n" +
                        "Message: " + messageText + "\n" +
                        "Hash: " + messageHash);

        String[] options = {"Send Message", "Disregard Message", "Store Message to Send Later"};
        int choice = JOptionPane.showOptionDialog(null,
                "Choose an option:", "Post-send Options",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        switch (choice) {
            case 0:
                JOptionPane.showMessageDialog(null, "Message sent.");
                break;
            case 1:
                JOptionPane.showMessageDialog(null, "Message disregarded.");
                break;
            case 2:
                saveToJson("messages.json");
                JOptionPane.showMessageDialog(null, "Message stored to send later.");
                break;
            default:
                JOptionPane.showMessageDialog(null, "No valid option selected.");
        }
    }

    void saveToJson(String fileName) {
        File file = new File(fileName);
        StringBuilder jsonEntry = new StringBuilder();
        jsonEntry.append("{\n")
                .append("  \"messageID\": \"").append(messageID).append("\",\n")
                .append("  \"recipient\": \"").append(escapeJson(recipient)).append("\",\n")
                .append("  \"messageText\": \"").append(escapeJson(messageText)).append("\",\n")
                .append("  \"messageHash\": \"").append(escapeJson(messageHash)).append("\"\n")
                .append("}");

        try {
            boolean appendComma = file.exists() && file.length() > 0;
            String existingContent = appendComma ? readJson(file) : "";

            try (FileWriter writer = new FileWriter(file)) {
                if (!appendComma) {
                    writer.write("[\n" + jsonEntry + "\n]");
                } else {
                    String trimmed = existingContent.trim();
                    if (trimmed.endsWith("]")) {
                        trimmed = trimmed.substring(0, trimmed.length() - 1);
                        writer.write(trimmed + ",\n" + jsonEntry + "\n]");
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to file:\n" + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String readJson(File file) {
        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
        } catch (IOException e) {
            return "";
        }
        return sb.toString();
    }

    private static String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r");
    }
}
