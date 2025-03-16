package ewallet.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserForm {
    public static void main(String[] args) {
        JFrame frame = new JFrame("User Registration Form");
        frame.setSize(350, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center window

        // ===== Create Panel with GridLayout =====
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        // ===== Form Fields =====
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JLabel genderLabel = new JLabel("Gender:");
        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> genderBox = new JComboBox<>(genders);

        JButton submitButton = new JButton("Submit");

        // ===== Add Components to Panel =====
        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(emailLabel);
        panel.add(emailField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(genderLabel);
        panel.add(genderBox);

        panel.add(new JLabel()); // Empty space
        panel.add(submitButton);

        // ===== Submit Button Action =====
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String gender = (String) genderBox.getSelectedItem();

                // Show input in a dialog
                JOptionPane.showMessageDialog(frame, 
                    "Name: " + name + "\nEmail: " + email + 
                    "\nPassword: " + password + "\nGender: " + gender, 
                    "Form Data", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // ===== Add Panel to Frame =====
        frame.add(panel);
        frame.setVisible(true);
    }
}
