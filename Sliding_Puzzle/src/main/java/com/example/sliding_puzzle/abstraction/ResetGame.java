package com.example.sliding_puzzle.abstraction;

import com.example.sliding_puzzle.controllers.GameController;
import javafx.scene.control.Button;

public class ResetGame {

    private Button resetGameRandom;

    public Button getResetGame() {
        return resetGameRandom;
    }

    public void setResetGame(Button resetGame) {
        this.resetGameRandom = resetGame;
    }

    public void resetRandom() {
        this.resetGameRandom.setOnAction(e -> {
            GameController.getSlidingGame().clearGridPane();
            GameController.getSlidingGame().createSlidingGame();
        });
    }
}
