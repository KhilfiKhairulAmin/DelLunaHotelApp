module DelLunaHotelApp {
	requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens controllers to javafx.fxml;
    opens main to javafx.fxml;

    exports main;
    exports controllers;
}
