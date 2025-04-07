package main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import auth.SignUpController;

import java.io.IOException;

import functions.Logger;

public class Main extends Application {
	
    @Override
    public void start(Stage primaryStage) throws Exception {

    	// Initialize window
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/auth/SignUp.fxml"));
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
        Logger logger = Logger.getInstance();
        
        // Configure logger
        try {
            logger.addOutput(new Logger.FileOutput("logs/application.log"));
        } catch (IOException e) {
            logger.error("Failed to create file logger", e);
        }
        
        // Importing stylesheets
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        // Get window resolution (excluding task bar)
        Screen screen = Screen.getPrimary();
        Rectangle2D visualBounds = screen.getVisualBounds();
        
        // Configure windows resolution
        primaryStage.setWidth(visualBounds.getMaxX());
        primaryStage.setHeight(visualBounds.getMaxY());
        logger.info("Resolution: " + (int)visualBounds.getMaxX() + " x " + (int)visualBounds.getMaxY() + " px");
        
        // Set window title
        primaryStage.setResizable(true);
        primaryStage.setTitle("LunaWallet Dashboard");
        
        // Set window to visible
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
