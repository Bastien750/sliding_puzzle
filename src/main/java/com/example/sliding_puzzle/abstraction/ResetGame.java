package com.example.sliding_puzzle.abstraction;

import com.example.sliding_puzzle.controllers.GameController;
import com.example.sliding_puzzle.controllers.GameMouvement;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ResetGame {

    private Button resetGameRandom;

    private Button resetMovingTile;

    private GameMouvement gameMouvement = new GameMouvement();

    private boolean autoCompletion = false;

    public Button getResetGame() {
        return resetGameRandom;
    }


    public void setResetGame(Button resetGame) {
        this.resetGameRandom = resetGame;
    }

    public Button getResetMovingTile() {
        return resetMovingTile;
    }

    public void setResetMovingTile(Button resetMovingTile) {
        this.resetMovingTile = resetMovingTile;
    }

    public boolean isAutoCompletion() {
        return autoCompletion;
    }

    public void setAutoCompletion(boolean autoCompletion) {
        this.autoCompletion = autoCompletion;
    }

    // Reset the game by shuffling the tile numbers
    public void resetRandom() {
        this.resetGameRandom.setOnAction(e -> {
            this.autoCompletion = true;
            GameController.getSlidingGame().clearGridPane();
            GameController.getSlidingGame().createSlidingGame();
        });
    }

    // Reset the game by moving the tiles
    public void resetMovingTile() {
        this.resetMovingTile.setOnAction(e-> {
            this.startReset();
        });
    }


    // Get button by coordinates
    public Button getButtonsCoord(int x, int y) {
        Node node = null;
        for (Node child : GameController.getSlidingGame().getSlidingGame().getChildren()) {
            Integer row = GridPane.getRowIndex(child);
            Integer col = GridPane.getColumnIndex(child);
            if (row != null && col != null && row == x && col == y) {
                node = child;
                break;
            }
        }

        if (node instanceof Button) {
            Button button = (Button) node;
            // Button Find
            System.out.println("Button find : " + button.getText());
            return button;
        } else {
            System.out.println("No button found at the specified coordinates.");
            return null;
        }
    }


    // Start the timer to moving tile randomly
    private void startReset() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            this.resetMovingTileShuffle();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Duration duration = Duration.seconds(15);
        timeline.setOnFinished(event -> {
            timeline.stop();
            System.out.println("Shuffling stopped after 15 seconds");
        });

        Timeline stopTimeline = new Timeline(new KeyFrame(duration, event -> timeline.stop()));
        stopTimeline.play();
    }

    // Function that move the tile
    public void resetMovingTileShuffle() {
        Random random = new Random();
        for(int i = 0; i < 1; i++) {
            int rowIndex = 0;
            int  columnIndex = 0;
            for (Node node : GameController.getSlidingGame().getSlidingGame().getChildren()) {
                if (node instanceof Button) {
                    Button emptyButton = (Button) node;
                    if (emptyButton.getText() == "") {
                        // Le bouton avec le texte recherché a été trouvé
                        rowIndex = GridPane.getRowIndex(emptyButton);
                        columnIndex = GridPane.getColumnIndex(emptyButton);
                        System.out.println("r" + rowIndex + "c" + columnIndex);

                        int randomX = random.nextInt(3) - 1;
                        int randomY = random.nextInt(3) - 1;


                        while ((randomX == randomY) || (randomX == -1 && randomY == 1) || (randomX == 1 && randomY == -1)) {
                            randomX = random.nextInt(3) - 1;
                            randomY = random.nextInt(3) - 1;
                        }

                        if(randomX == 0) {
                            randomY = random.nextInt(2) * 2 - 1;
                        } else if(randomY == 0){
                            randomX = random.nextInt(2) * 2 - 1;
                        }


                        System.out.println(randomX +" | " + randomY);
                        System.out.println("Button cord" + (rowIndex + randomX) + " | " + (columnIndex + randomY));


                        if(columnIndex + randomY > GameController.getSlidingGame().getGridColumn() - 1) {
                            randomY -= 2;
                        }
                        if(rowIndex + randomX > GameController.getSlidingGame().getGridRow() - 1) {
                            randomX -= 2;
                        }
                        if(columnIndex + randomY < 0) {
                            randomY += 2;
                        }
                        if(rowIndex + randomX < 0) {
                            randomX+=2;
                        }

                        if (columnIndex + randomY > GameController.getSlidingGame().getGridColumn() - 1
                                || rowIndex + randomX > GameController.getSlidingGame().getGridRow() - 1
                                || columnIndex + randomY < 0
                                || rowIndex + randomX < 0
                        ) {
                            continue;
                        } else {
                            Button buttonAdj = this.getButtonsCoord(rowIndex + randomX, columnIndex + randomY);
                            if(buttonAdj != null) {
                                System.out.println("j :" + (rowIndex + randomX) + " k :" + (columnIndex + randomY));
                                this.autoCompletion = true;
                                gameMouvement.selector(emptyButton);
                                gameMouvement.selector(buttonAdj);
                                this.gameMouvement.getListButtonClicked().clear();
                                GameController.getNbTurns().resetNbTurns();
                            }
                        }

                    }
                }
            }

        }
        this.autoCompletion = false;
    }
}