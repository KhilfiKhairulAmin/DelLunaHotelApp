package auth;

import javafx.event.ActionEvent;  // Event handling package

import javafx.fxml.FXML;          // FXML packages
import javafx.fxml.FXMLLoader;

import javafx.scene.control.*;    // UI components package

import javafx.scene.Parent;       // Scene management packages
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import model.Guest;
import model.GuestDB;

import java.io.IOException;       // Error handling package
import java.util.List;
import java.util.regex.Pattern;   // Input validation package


public class ForgotPasswordController {

	/* @FXML annotation is used to link UI components with attributes of the class to handle UI updates & input retrieval */
	
	// These are fields entered by the user
    @FXML private TextField emailField;
    @FXML private TextField pinField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    
    // These are fields to display error messages
    @FXML private Label emailError;
    @FXML private Label pinError;
    @FXML private Label passwordError;
    @FXML private Label confirmPasswordError;

    // These are regular expressions for input validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$");
    
    /**
     * Handle Sign Up button click event
     * @param event
     */
    @FXML private void handleResetPassword(ActionEvent event) {
        // Reset error messages
        clearErrors();
        
        // Validate inputs
        boolean isValid = true;
        
        // Phone validation
        if (phoneField.getText().isEmpty()) {
            phoneError.setText("Phone is required");
            isValid = false;
        } else if (!PHONE_PATTERN.matcher(phoneField.getText()).matches()) {
            phoneError.setText("Invalid phone format");
            isValid = false;
        }
        
        // Password validation
        if (passwordField.getText().isEmpty()) {
            passwordError.setText("Password is required");
            isValid = false;
        } else if (!PASSWORD_PATTERN.matcher(passwordField.getText()).matches()) {
            passwordError.setText("Password must be 8+ chars with uppercase, lowercase, number and special char");
            isValid = false;
        }
        
        // If all valid, proceed to sign up
        if (isValid) {
            try {
            	// Get a copy of all guests
            	List<Guest> guests = GuestDB.getAllGuests();
            	
            	// Checks if email is unique by removing guests that doesn't have the same email as the entered email
            	guests.removeIf(guest -> !guest.getEmail().equals(emailField.getText()));
            	
            	if (guests.size() == 0) {  // No one has the same email as the entered one
            		Guest newGuest = new Guest(null, nameField.getText(), emailField.getText(), phoneField.getText());            		
            		GuestDB.addGuest(newGuest, passwordField.getText());
            	}
            	else {  // Someone already took the email
            		throw new Exception("Email already taken");
            	}
                
                // For now, just show success and go to home page
                showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully!");
                loadHomePage(event);
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to create account: " + e.getMessage());
            }
        }
    }
    
    @FXML private void handleSendPin(ActionEvent event) {
    	
    	boolean isValid = true;
    	
        // Email validation
        if (emailField.getText().isEmpty()) {
            emailError.setText("Email is required");
            isValid = false;
        } else if (!EMAIL_PATTERN.matcher(emailField.getText()).matches()) {
            emailError.setText("Invalid email format");
            isValid = false;
        }
        
        
    }

    /**
     * Handle Log In link click event
     * @param event
     */
    @FXML private void switchToSignIn(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/auth/SignIn.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1366, 720));
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load sign in page");
            e.printStackTrace();
        }
    }

    /**
     * Reset error fields to default text
     */
    private void clearErrors() {
        nameError.setText("");
        emailError.setText("");
        phoneError.setText("");
        passwordError.setText("");
    }

    /**
     * Transition to Homepage
     * @param event
     * @throws IOException
     */
    private void loadHomePage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/Main.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1366, 720));
    }

    /**
     * Show a pop up box to alert the user
     * @param alertType
     * @param title Title of the pop up window
     * @param message Message of the pop up window
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
