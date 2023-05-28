package com.example.sliding_puzzle.abstraction;

import javafx.scene.control.Label;

public class LabelTile {

    private Label labelTile;

    public LabelTile() {
        this.labelTile = new Label("");
        this.labelTileStyle();
    }

    public Label getLabelTile() {
        return this.labelTile;
    }

    public void setLabelTile(Label labelTile) {
        this.labelTile = labelTile;
    }

    // Label Style
    public void labelTileStyle() {
        this.labelTile.setStyle("-fx-background-color:grey;");
    }
}
