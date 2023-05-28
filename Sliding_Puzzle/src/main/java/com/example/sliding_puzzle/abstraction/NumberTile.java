package com.example.sliding_puzzle.abstraction;

import javafx.scene.control.Button;

public class NumberTile extends Tile{

    private String text;
    public NumberTile(String number) {
        super();
        this.text = number;
        super.getTile().setText(number);
        this.numberTileStyle();
    }

    public String getText() {
        return text;
    }

    public void numberTileStyle() {
        //
    }
    public void setText(String text) {
        this.text = text;
    }

}
