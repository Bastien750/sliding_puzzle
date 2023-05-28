package com.example.sliding_puzzle.abstraction;

import javafx.scene.control.Button;

public class EmptyTile extends Tile{


    // Constructor
    public EmptyTile() {
        super();
        super.getTile().setText("");
        this.emptyTileStyle();
    }

    // Style emptyTile
    public void emptyTileStyle() {
        super.getTile().setStyle("-fx-background-color:black;");
    }
    public void setEmptyTile(Button emptyTile) {
        this.setEmptyTile(emptyTile);
    }

}
