module org.stefy.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.stefy.client to javafx.fxml;
    exports org.stefy.client;
}