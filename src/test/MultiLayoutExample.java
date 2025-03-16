package test;

import javax.swing.*;
import java.awt.*;

public class MultiLayoutExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Multi-Layout Example");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        // Set main layout
        frame.setLayout(new BorderLayout());

        // ===== Top Panel (FlowLayout) =====
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Del Luna Hotel Dashboard");
        topPanel.add(titleLabel);

        // ===== Center Panel (GridLayout) =====
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JLabel balanceLabel = new JLabel("Balance: $100", JLabel.CENTER);
        JButton topUpButton = new JButton("Top Up");

        centerPanel.add(balanceLabel);
        centerPanel.add(topUpButton);

        // ===== Bottom Panel (FlowLayout) =====
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton homeButton = new JButton("Home");
        JButton bookingButton = new JButton("Book Room");
        bottomPanel.add(homeButton);
        bottomPanel.add(bookingButton);

        // ===== Add Panels to Frame =====
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
