package ewallet.java;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {
    @FXML
    private Button myButton;

    @FXML
    private void handleButtonClick() {
        myButton.setText("Clicked!");
    }
}
