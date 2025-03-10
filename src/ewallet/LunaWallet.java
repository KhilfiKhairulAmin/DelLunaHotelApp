/**
 * File: LunaWallet.java
 * Author: Khilfi
 * Description:
 * The Luna Wallet is a component of Del Luna Hotel app.
 * The purpose of the e-wallet is for transactions.
 * User can top-up money inside the e-wallet and obtain points for future use.
 */
package ewallet;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import util.DateUtil;
import javax.swing.*;

import ewallet.LunaWalletDB.BalanceLimitExceeded;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles user interface for LunaWallet
 */
public class LunaWallet {
	private LunaWalletDB wallet;
	private JFrame frame;
	private JPanel mainPanel;
	private CardLayout cardLayout;
	private enum Page { DASHBOARD, TOP_UP, VIEW_TRANSACTIONS, SETTINGS, UPDATE_PIN, UPDATE_SECURITY_QUESTION, DEACTIVATE_WALLET, EXIT };
	
	public static void main(String[] args) {
		new LunaWallet("1");
	}
	
	LunaWallet(String UID) {
		
		try (LunaWalletDB temp = new LunaWalletDB(UID)) {
			wallet = temp;
			
		} catch (LunaWalletDB.UidNotFound e) {
			// Ask user to activate LunaWallet
		}
		
		frame = new JFrame("Del Luna Hotel App");
		frame.setSize(500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		// Set CardLayout
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		
		// Add different page
		mainPanel.add(createDashboardPage(), Page.DASHBOARD.toString());
		mainPanel.add(createTopUpPage(), Page.TOP_UP.toString());
		
		frame.add(mainPanel);
		frame.setVisible(true);
	}
	
	private JPanel createDashboardPage() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JLabel label = new JLabel("Welcome to Del Luna Hotel!", JLabel.CENTER);
		
		JPanel buttonPanel = new JPanel();
		JButton topUpButton = new JButton("Top Up");
		JButton viewTransactionsButton = new JButton("View Transactions");
		JButton settingsButton = new JButton("Settings");
		
		topUpButton.addActionListener(e -> cardLayout.show(mainPanel, Page.TOP_UP.toString()));
		viewTransactionsButton.addActionListener(e -> cardLayout.show(mainPanel, Page.VIEW_TRANSACTIONS.toString()));
		settingsButton.addActionListener(e -> cardLayout.show(mainPanel, Page.SETTINGS.toString()));
		
		panel.add(label, BorderLayout.CENTER);
		buttonPanel.add(topUpButton);
		buttonPanel.add(viewTransactionsButton);
		buttonPanel.add(settingsButton);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel createTopUpPage() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		double curBalance = wallet.getBalance();
		
		JLabel label = new JLabel(Double.toString(curBalance), JLabel.CENTER);
		
		JButton rm10Button = new JButton("10");
		JButton rm20Button = new JButton("20");
		JButton rm50Button = new JButton("50");
		JButton rm100Button = new JButton("100");
		JButton customAmount = new JButton("Custom");
		JButton backButton = new JButton("Back");
		
		rm10Button.addActionListener(e -> {
			try {
				wallet.topUpBalance(10);
				label.setText(Double.toString(wallet.getBalance()));
			} catch (BalanceLimitExceeded e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		rm20Button.addActionListener(e -> {
			try {
				wallet.topUpBalance(20);
				label.setText(Double.toString(wallet.getBalance()));
			} catch (BalanceLimitExceeded e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		rm50Button.addActionListener(e -> {
			try {
				wallet.topUpBalance(50);
				label.setText(Double.toString(wallet.getBalance()));
			} catch (BalanceLimitExceeded e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		rm100Button.addActionListener(e -> {
			try {
				wallet.topUpBalance(100);
				label.setText(Double.toString(wallet.getBalance()));
			} catch (BalanceLimitExceeded e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		customAmount.addActionListener(e -> {
			try {
				wallet.topUpBalance(1_000_000);
				label.setText(Double.toString(wallet.getBalance()));
			} catch (BalanceLimitExceeded e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		backButton.addActionListener(e -> cardLayout.show(mainPanel, Page.DASHBOARD.toString()));
		
		JPanel buttonPanel1 = new JPanel();
		buttonPanel1.add(rm10Button);
		buttonPanel1.add(rm20Button);
		buttonPanel1.add(rm50Button);
		buttonPanel1.add(rm100Button);
		buttonPanel1.add(customAmount);
		
		JPanel buttonPanel2 = new JPanel();
		buttonPanel2.add(backButton);
		
		panel.add(label, BorderLayout.CENTER);
		panel.add(buttonPanel1, BorderLayout.SOUTH);
		panel.add(buttonPanel2, BorderLayout.EAST);
		
		return panel;
	}
	
	void ViewTransactions() {
		
	}
	
	void Settings() {
		
	}
	
	void UpdatePIN() {
		
	}
	
	void UpdateSecurityQuestion() {
		
	}
	
	void DeactivateWallet() {
		
	}
	
	
}

/**
 * Handles database operations for LunaWallet
 */
class LunaWalletDB implements AutoCloseable {
	private String UID = "";
	private String pinHash;
	private double balance;
	private int lunaPoints;
	private String securityQuestion;
	private String securityAnswerHash;
	private String dateCreated;
	private String lastUpdated;
	private static final String FILE_PATH = "Wallet.txt";
	private static final double BALANCE_LIMIT = 100_000;
	
	/**
	 * Loads user LunaWallet based on UID
	 * @param UID
	 * @throws UidNotFound 
	 */
	LunaWalletDB(String UID) throws UidNotFound {
		try (Scanner sc = new Scanner(new File(FILE_PATH))) {
			
			boolean isHeader = true;
			while (sc.hasNextLine()) {
				
				String line = sc.nextLine();
				
				if (isHeader) {
					isHeader = false;
					continue;
				}
				
				String[] data = line.split(",");
				
				
				String uid = data[0];
				
				if (!uid.equals(UID)) {
					continue;
				}
				this.UID = uid;
				this.pinHash = data[1];
				this.balance = Double.parseDouble(data[2]);
				this.lunaPoints = Integer.parseInt(data[3]);
				this.securityQuestion = data[4];
				this.securityAnswerHash = data[5];
				this.dateCreated = data[6];
				this.lastUpdated = data[7];
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (!this.UID.equals(UID)) {
			throw new UidNotFound(UID);
		}
	}
	
	/**
	 * Top up balance amount
	 * @param amount
	 * @throws BalanceLimitExceeded 
	 */
	void topUpBalance(double amount) throws BalanceLimitExceeded {
		if (balance + amount > BALANCE_LIMIT) {
			throw new BalanceLimitExceeded();
		}
		balance += amount;
	}
	
	/**
	 * Deduct money from Wallet
	 * @param amount
	 * @throws NotEnoughBalance 
	 */
	void deductBalance(double amount) throws NotEnoughBalance {
		if (balance - amount < 0) {
			throw new NotEnoughBalance();
		}
		balance -= amount;
	}
	
	/**
	 * Returns amount of balance inside Wallet
	 * @return
	 */
	double getBalance() {
		return balance;
	}
	
    public static class UidNotFound extends Exception {
		private static final long serialVersionUID = -4102964319935793290L;

		public UidNotFound(String UID) {
            super("UID " + UID + " doesn't exist");
        }
    }
    
    public static class BalanceLimitExceeded extends Exception {
		private static final long serialVersionUID = -5197016556609704353L;

		public BalanceLimitExceeded() {
    		super("Balance limit exceeded RM " + BALANCE_LIMIT);
    	}
    }
    
    public static class NotEnoughBalance extends Exception {
		private static final long serialVersionUID = -9058636885723273681L;

		public NotEnoughBalance() {
    		super("Not enough balance");
    	}
    }
    
    @Override
    public void close() {}
}
