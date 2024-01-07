module com.example.tictactoeapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tictactoeapp to javafx.fxml;
    exports com.example.tictactoeapp;
}