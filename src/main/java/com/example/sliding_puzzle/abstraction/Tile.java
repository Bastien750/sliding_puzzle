package com.example.sliding_puzzle.abstraction;

import com.example.sliding_puzzle.controllers.GameMouvement;
import javafx.scene.control.Button;

public class Tile {

    private Button tile;

    private static GameMouvement gameMouvement = new GameMouvement();


    public Tile(){
        this.tile = new Button();
        this.tileStyle();
    }

    public Button getTile() {
        return this.tile;
    }

    public void setTile(Button tile) {
        this.tile = tile;
    }

    public void tileStyle() {
        this.tile.setPrefSize(200,200);
    }

    public void tileOnAction() {
        this.tile.setOnAction(e->{
            Button buttonClicked = (Button) e.getSource();
            this.gameMouvement.selector(buttonClicked);
        });
    }




}
