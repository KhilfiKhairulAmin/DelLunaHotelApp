module DelLunaHotelApp {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens DelLunaHotelApp to javafx.fxml;
    exports DelLunaHotelApp;
}
