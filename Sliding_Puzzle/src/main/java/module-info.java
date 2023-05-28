module com.example.sliding_puzzle {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                        requires org.kordamp.bootstrapfx.core;
            
    opens com.example.sliding_puzzle to javafx.fxml;
    exports com.example.sliding_puzzle;
    exports com.example.sliding_puzzle.controllers;
    opens com.example.sliding_puzzle.controllers to javafx.fxml;
}