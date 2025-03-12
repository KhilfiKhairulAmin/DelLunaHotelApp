package ewallet;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXExample extends Application {
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        Button btn = new Button("Click Me!");
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
