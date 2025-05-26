import javax.swing.*;
import java.util.ArrayList;

public class QuickChatApp {

    static ArrayList<Message> messages = new ArrayList<>();

    public static void main(String[] args) {
        String regUsername, regPassword;

        // Registration with format checks
        while (true) {
            regUsername = JOptionPane.showInputDialog("Register - Enter a username:\n(Under 5 chars, includes '_' and capital letter)");
            if (regUsername == null) return;

            if (!isValidUsername(regUsername)) {
                JOptionPane.showMessageDialog(null, "Invalid username. Must be under 5 characters, contain '_' and a capital letter.");
            } else break;
        }

        while (true) {
            regPassword = JOptionPane.showInputDialog("Register - Enter a password:\n(8+ chars, includes capital, number, special char)");
            if (regPassword == null) return;

            if (!isValidPassword(regPassword)) {
                JOptionPane.showMessageDialog(null, "Invalid password. Must be at least 8 characters, include a capital letter, number, and special character.");
            } else break;
        }

        JOptionPane.showMessageDialog(null, "Registration successful! Please log in.");

        // Login with format and value checks
        String loginUsername;
        while (true) {
            loginUsername = JOptionPane.showInputDialog("Login - Enter username:");
            if (loginUsername == null) return;

            if (!isValidUsername(loginUsername)) {
                JOptionPane.showMessageDialog(null, "Invalid username format.");
                continue;
            }

            if (!loginUsername.equals(regUsername)) {
                JOptionPane.showMessageDialog(null, "Username not recognized.");
                continue;
            }

            break;
        }

        String loginPassword;
        while (true) {
            loginPassword = JOptionPane.showInputDialog("Login - Enter password:");
            if (loginPassword == null) return;

            if (!isValidPassword(loginPassword)) {
                JOptionPane.showMessageDialog(null, "Invalid password format.");
                continue;
            }

            if (!loginPassword.equals(regPassword)) {
                JOptionPane.showMessageDialog(null, "Incorrect password.");
                continue;
            }

            break;
        }

        JOptionPane.showMessageDialog(null, "Welcome to QuickChat, " + loginUsername + "!");
        boolean running = true;

        while (running) {
            String menu = JOptionPane.showInputDialog("""
                    Choose an option:
                    1) Send Messages
                    2) Show recently sent messages
                    3) Quit
                    """);

            if (menu == null) break;

            switch (menu) {
                case "1" -> {
                    int totalMessages = 0;
                    boolean validInput = false;

                    while (!validInput) {
                        String input = JOptionPane.showInputDialog("Enter number of messages to send:");
                        if (input == null) return;

                        try {
                            totalMessages = Integer.parseInt(input);
                            if (totalMessages <= 0) {
                                JOptionPane.showMessageDialog(null, "Please enter a number greater than 0.");
                            } else {
                                validInput = true;
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
                        }
                    }

                    for (int i = 0; i < totalMessages; i++) {
                        Message msg = new Message();
                        msg.sendMessage();
                        messages.add(msg);
                    }

                    JOptionPane.showMessageDialog(null, "Total messages sent: " + messages.size());
                }

                case "2" -> {
                    JOptionPane.showMessageDialog(null, "Coming Soon.");
                }

                case "3" -> {
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    running = false;
                }

                default -> {
                    JOptionPane.showMessageDialog(null, "Invalid option. Please choose 1, 2, or 3.");
                }
            }
        }
    }

    public static boolean isValidUsername(String username) {
        return username.length() < 5 && username.contains("_") && username.matches(".*[A-Z].*");
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }
}
