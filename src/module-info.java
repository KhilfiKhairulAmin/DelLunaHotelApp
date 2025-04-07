module DelLunaHotelApp {
	requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens auth to javafx.fxml;
    opens main to javafx.fxml;
    exports main;
}
