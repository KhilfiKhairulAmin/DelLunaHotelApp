package ewallet.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainLunaWallet extends JFrame {
    private double balance = 50.00; // Initial balance
    private JLabel balanceLabel; // Label to show balance

    public MainLunaWallet() {
        // 🔹 Set up JFrame (window)
        setTitle("Luna Wallet Dashboard");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🔹 Create balance label
        balanceLabel = new JLabel("Balance: RM " + balance, SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // 🔹 Create Top Up button
        JButton topUpButton = new JButton("Top Up RM10");
        topUpButton.setFont(new Font("Arial", Font.PLAIN, 14));

        // 🔹 Add action listener to button
        topUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                topUp(10); // Increase balance by RM10
            }
        });

        // 🔹 Add components to JFrame
        add(balanceLabel, BorderLayout.CENTER);
        add(topUpButton, BorderLayout.SOUTH);

        // 🔹 Make it visible
        setVisible(true);
    }

    // 🔹 Method to increase balance
    private void topUp(double amount) {
        balance += amount;
        balanceLabel.setText("Balance: RM " + balance);
        JOptionPane.showMessageDialog(this, "Successfully topped up RM" + amount + "!");
    }

    // 🔹 Main method to run the dashboard
    public static void main(String[] args) {
        new MainLunaWallet();
    }
}