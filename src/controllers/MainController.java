package controllers;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainController {
	private Stage stage;
	
    @FXML
    private Circle appLogoContainer;
    
    @FXML
    private VBox stackPaneContainer; // Holds all StackPane elements

    private StackPane selectedPane;
    
    @FXML
    private StackPane homeStack;
    
    @FXML
    private BorderPane mainPane;  // The main layout container

    @FXML
    private StackPane exploreStack;
    
    @FXML
    private StackPane rewardsStack;

    @FXML
    private StackPane bookingsStack;
    
    @FXML
    private StackPane accountStack;

    @FXML
    private StackPane logoutStack;

    @FXML
    private StackPane homePane;
    
    @FXML
    private StackPane explorePane;
    
    @FXML
    private StackPane rewardsPane;
    
    @FXML
    private StackPane bookingsPane;
    
    @FXML
    private StackPane accountPane;
    
    @FXML
    private StackPane logoutPane;
    
    private StackPane curPane;
    
    public void initialize() {
        // Load the image 	
        Image image = new Image(getClass().getResource("/resources/icon.jpg").toExternalForm(), false);
        appLogoContainer.setFill(new ImagePattern(image));
        
        selectedPane = homeStack;
        switchTo(homePane);
        int count = 0, except1 = 1, except2 = 6;
        selectedPane.setStyle("-fx-background-color: #d5acd1;");
        for (var node : stackPaneContainer.getChildren()) {
        	count++;
        	if (count == except1 || count == except2) {
        		continue;
        	}
            if (node instanceof StackPane stackPane) {
                stackPane.setOnMouseClicked(this::handleStackClick);
                
            }
        }
        
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public Stage getStage() {
        return stage;
    }
    
    private void handleStackClick(MouseEvent event) {
        StackPane clickedPane = (StackPane) event.getSource();
        
        if (selectedPane == clickedPane) {
        	return;
        }
        if (selectedPane != null) {
            selectedPane.setStyle(""); // Reset previous
        }

        clickedPane.setStyle("-fx-background-color: #d5acd1;"); // Highlight selected
        selectedPane = clickedPane;
        
        handleSidebarClick(event);
    }
    
    // Handle StackPane clicks (set this in SceneBuilder)
    @FXML
    private void handleSidebarClick(MouseEvent event) {
        StackPane clickedStack = (StackPane) event.getSource(); // Get clicked StackPane
        if (clickedStack == homeStack) {
            switchTo(homePane);
        } else if (clickedStack == exploreStack) {
            switchTo(explorePane);
        } else if (clickedStack == rewardsStack) {
        	switchTo(rewardsPane);
        } else if (clickedStack == bookingsStack) {
        	switchTo(bookingsPane);
        } else if (clickedStack == accountStack) {
        	switchTo(accountPane);
        } else if (clickedStack == logoutStack) {
        	switchTo(logoutPane);
        }
    }

    // Method to switch views dynamically
    private void switchTo(StackPane panel) {
    	panel.setVisible(true); // Show the new panel
        if (curPane != null) {
        	curPane.setVisible(false); // Hide the previous panel
        }
        curPane = panel; // Update current panel
    }
}
