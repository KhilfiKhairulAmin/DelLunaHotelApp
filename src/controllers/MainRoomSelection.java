package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainRoomSelection extends Application{
	 @Override
	    public void start(@SuppressWarnings("exports") Stage primaryStage) throws Exception {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/selectingRooms.fxml"));
	        Parent root = fxmlLoader.load();
	        Scene scene = new Scene(root);
	        primaryStage.setTitle("Room Selection");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }
  
}
