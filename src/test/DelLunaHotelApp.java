package ewallet.java;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DelLunaHotelApp {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public DelLunaHotelApp() {
        // Initialize Frame
        frame = new JFrame("Del Luna Hotel App");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Set CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add different pages
        mainPanel.add(createLoginPage(), "Login");
        mainPanel.add(createDashboardPage(), "Dashboard");
        mainPanel.add(createBookingPage(), "Booking");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Login Page
    private JPanel createLoginPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Welcome to Del Luna Hotel!", JLabel.CENTER);
        JButton loginButton = new JButton("Login");

        // Switch to Dashboard on login
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "Dashboard"));

        panel.add(label, BorderLayout.CENTER);
        panel.add(loginButton, BorderLayout.SOUTH);
        return panel;
    }

    // Dashboard Page
    private JPanel createDashboardPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel balanceLabel = new JLabel("Balance: $100", JLabel.CENTER);
        JButton topUpButton = new JButton("Top Up");
        JButton bookButton = new JButton("Book a Room");

        // Navigate to Booking Page
        bookButton.addActionListener(e -> cardLayout.show(mainPanel, "Booking"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(topUpButton);
        buttonPanel.add(bookButton);

        panel.add(balanceLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Booking Page
    private JPanel createBookingPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Select Your Room", JLabel.CENTER);
        JButton backButton = new JButton("Back to Dashboard");

        // Navigate back to Dashboard
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Dashboard"));

        panel.add(label, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);
        return panel;
    }

    public static void main(String[] args) {
        new DelLunaHotelApp();
    }
}
