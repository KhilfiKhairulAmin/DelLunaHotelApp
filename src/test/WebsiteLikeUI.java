package test;

import javax.swing.*;
import java.awt.*;

public class WebsiteLikeUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Del Luna Hotel App");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Using BorderLayout

        // === HEADER (Navigation Bar) ===
        JPanel header = new JPanel();
        header.setBackground(Color.DARK_GRAY);
        header.setPreferredSize(new Dimension(800, 50));
        JLabel title = new JLabel("Del Luna Hotel");
        title.setForeground(Color.WHITE);
        header.add(title);

        // === SIDEBAR (Navigation Menu) ===
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.LIGHT_GRAY);
        sidebar.setPreferredSize(new Dimension(150, 500));
        sidebar.setLayout(new GridLayout(5, 1, 5, 5));
        sidebar.add(new JButton("Home"));
        sidebar.add(new JButton("Bookings"));
        sidebar.add(new JButton("Profile"));
        sidebar.add(new JButton("Settings"));
        sidebar.add(new JButton("Logout"));

        // === MAIN CONTENT (Dynamic Pages) ===
        JPanel content = new JPanel();
        content.setBackground(Color.WHITE);
        content.add(new JLabel("Welcome to Del Luna Hotel!"));

        // === FOOTER ===
        JPanel footer = new JPanel();
        footer.setBackground(Color.GRAY);
        footer.setPreferredSize(new Dimension(800, 40));
        JLabel footerText = new JLabel("© 2025 Del Luna Hotel. All rights reserved.");
        footer.add(footerText);

        // === ADDING COMPONENTS TO FRAME ===
        frame.add(header, BorderLayout.NORTH);
        frame.add(sidebar, BorderLayout.WEST);
        frame.add(content, BorderLayout.CENTER);
        frame.add(footer, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}

