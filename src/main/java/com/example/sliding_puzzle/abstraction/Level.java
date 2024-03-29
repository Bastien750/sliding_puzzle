package com.example.sliding_puzzle.abstraction;

import com.example.sliding_puzzle.controllers.GameController;
import com.example.sliding_puzzle.controllers.GameMouvement;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class Level {

    private Button buttonTopLevel;
    private Button buttonLowerLevel;
    private Label numberLevel;

    private ShowAlert showAlert;


    public Button getButtonTopLevel() {
        return buttonTopLevel;
    }

    public void setButtonTopLevel(Button buttonTopLevel) {
        this.buttonTopLevel = buttonTopLevel;
    }

    public Button getButtonLowerLevel() {
        return buttonLowerLevel;
    }

    public void setButtonLowerLevel(Button buttonLowerLevel) {
        this.buttonLowerLevel = buttonLowerLevel;
    }

    public Label getNumberLevel() {
        return numberLevel;
    }

    public void setNumberLevel(Label numberLevel) {
        this.numberLevel = numberLevel;
    }

    // Can choose level
    public void chooseLevel() {
        this.buttonTopLevel.setOnAction(e -> {
            if(this.levelAccess()) {
                this.topLevel();
            } else {
                this.showAlert = new ShowAlert("Impossible", "You must first pass the level" + this.getIntLevelNumber());
            }
        });

        this.buttonLowerLevel.setOnAction(e -> {
            this.lowerLevel();
        });
    }

    // Go to the nextLevel
    public void topLevel() {
        int level = this.getIntLevelNumber();
        if(level < 10) {
            level+=1;
            this.numberLevel.setText("#" + level);
            String fileName = "src/levels/level" + level + ".txt";
            GameController.getSlidingGame().clearGridPane();
            GameController.getSlidingGame().setFileName(fileName);
            GameController.getSlidingGame().createSlidingGame();
        }
    }

    // Go the lower level
    public void lowerLevel() {
        int level = this.getIntLevelNumber();
        if(level > 1) {
            level-=1;
            this.numberLevel.setText("#" + level);
            String fileName = "src/levels/level" + level + ".txt";
            GameController.getSlidingGame().clearGridPane();
            GameController.getSlidingGame().setFileName(fileName);
            GameController.getSlidingGame().createSlidingGame();
        }
    }

    // Get the current level number
    public int getIntLevelNumber() {
        String numericValue = this.numberLevel.getText().replaceAll("\\D+", "");
        int level = Integer.parseInt(numericValue);
        return level;
    }

    // Verify if you can access in the next level
    public boolean levelAccess() {
        int record = GameController.getRecord().getFileLevelRecord();
        if(record == 0) {
            return false;
        }
        return true;
    }

}
