module DelLunaHotelApp {
	requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens DelLunaHotelApp to javafx.fxml;
    exports DelLunaHotelApp;
}
