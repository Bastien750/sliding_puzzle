package com.example.sliding_puzzle.abstraction;

import javafx.scene.control.Alert;

public class ShowAlert {

    private String title;

    private String content;

    public ShowAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
