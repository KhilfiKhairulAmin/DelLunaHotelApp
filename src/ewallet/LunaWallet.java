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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles user interface for LunaWallet
 */
public class LunaWallet {
	private LunaWalletDB wallet;
	enum Page { DASHBOARD, TOP_UP, VIEW_TRANSACTIONS, SETTINGS, UPDATE_PIN, UPDATE_SECURITY_QUESTION, DEACTIVATE_WALLET, EXIT };
	
	LunaWallet(String UID) {
		
		try (LunaWalletDB temp = new LunaWalletDB(UID)) {
			wallet = temp;
			
		} catch (LunaWalletDB.UidNotFound e) {
			// Ask user to activate LunaWallet
		}
	}
	
	void main() {
		Page curPage = Page.DASHBOARD;
		while (curPage != Page.EXIT) {
			switch (curPage) {
			case DASHBOARD:
				new Dashboard();
				break;
			case TOP_UP:
				TopUp();
				break;
			case VIEW_TRANSACTIONS:
				ViewTransactions();
				break;
			case SETTINGS:
				Settings();
				break;
			case UPDATE_PIN:
				UpdatePIN();
				break;
			case UPDATE_SECURITY_QUESTION:
				UpdateSecurityQuestion();
				break;
			case DEACTIVATE_WALLET:
				DeactivateWallet();
				break;
			case EXIT:
				return;
			}
		}
	}
	
	class Dashboard extends JFrame {
		private JLabel balanceLabel;
		
		public Dashboard() {
			// Set up JFrame
			setTitle("Luna Wallet Dashboard");
			setSize(300, 200);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(new BorderLayout());
		}
	}
	
	void TopUp() {
		
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
