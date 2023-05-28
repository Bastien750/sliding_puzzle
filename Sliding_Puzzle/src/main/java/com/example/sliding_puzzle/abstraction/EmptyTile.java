package com.example.sliding_puzzle.abstraction;

import javafx.scene.control.Button;

public class EmptyTile extends Tile{


    public EmptyTile() {
        super();
        super.getTile().setText("");
        this.emptyTileStyle();
    }

    public void emptyTileStyle() {
        super.getTile().setStyle("-fx-background-color:black;");
    }
    public void setEmptyTile(Button emptyTile) {
        this.setEmptyTile(emptyTile);
    }

}
