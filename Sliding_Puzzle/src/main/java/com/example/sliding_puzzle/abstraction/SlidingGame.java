package com.example.sliding_puzzle.abstraction;

import com.example.sliding_puzzle.controllers.GameController;
import com.example.sliding_puzzle.controllers.GameMouvement;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class SlidingGame {

    private GridPane slidingGame;

    private String fileName;

    private AnchorPane anchorPane;


    public SlidingGame() {
        this.fileName = "src/levels/level1.txt";
        this.slidingGame = new GridPane();
    }

    public String getFileName() {
        return fileName;
    }

    public GridPane getSlidingGame() {
        return this.slidingGame;
    }

    public void setSlidingGame(GridPane slidingGame) {
        this.slidingGame = slidingGame;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public void createSlidingGame() {
        ArrayList<Integer> listNumberButton = this.listNumberButton();

        GameController.getLevel().chooseLevel();

        Label labelRecord = GameController.getRecord().getRecordLabel();
        int recordFile = GameController.getRecord().getFileLevelRecord();
        labelRecord.setText(Integer.toString(recordFile));


        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            int i=0;
            int increment = 0;
            while ((line = br.readLine()) != null) {
                RowConstraints row = new RowConstraints();
                this.slidingGame.getRowConstraints().add(row);
                for (int j = 0; j < line.length(); j++) {
                    char caractere = line.charAt(j);
                    ColumnConstraints column = new ColumnConstraints();
                    this.slidingGame.getColumnConstraints().add(column);
                    if(caractere == '/'){
                        LabelTile labelTile = new LabelTile();
                        GridPane.setRowIndex(labelTile.getLabelTile(), i);
                        GridPane.setColumnIndex(labelTile.getLabelTile(), j);
                        this.slidingGame.getChildren().add(labelTile.getLabelTile());
                    } else {
                        increment++;
                        int chooseRandomNumber = 0;
                        if (caractere == '+') {
                            increment++;
                            Random random = new Random();
                            int index = random.nextInt(listNumberButton.size());
//                            while (listNumberButton.get(index) == increment) {
//                                index = random.nextInt(listNumberButton.size());
//                            }
                            chooseRandomNumber = listNumberButton.get(index);
                            listNumberButton.remove(index);

                            Collections.sort(listNumberButton);
                        }
                        Tile tile = this.createTile(caractere, chooseRandomNumber);
                        if(tile != null) {
                            GridPane.setRowIndex(tile.getTile(), i);
                            GridPane.setColumnIndex(tile.getTile(), j);
                            this.slidingGame.getChildren().add(tile.getTile());
                        }
                    }
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnchorPane.setRightAnchor(this.slidingGame, 100.0);
        AnchorPane.setBottomAnchor(this.slidingGame, 100.0);
        AnchorPane.setTopAnchor(this.slidingGame,100.0);
        AnchorPane.setLeftAnchor(this.slidingGame, 100.0);


        this.slidingGame.setAlignment(Pos.CENTER);
        this.anchorPane.getChildren().add(this.slidingGame);
    }

    public ArrayList<Integer> listNumberButton() {
        ArrayList<Integer> listSommePlus = new ArrayList<Integer>();
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            int sommePlus = 0;
            String line;
            while ((line = br.readLine()) != null) {
                for (int j = 0; j < line.length(); j++) {
                    char caractere = line.charAt(j);
                    if(caractere == '+') {
                        sommePlus++;
                        listSommePlus.add(sommePlus);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listSommePlus;
    }

    public Tile createTile(char caracter, int chooseRandomNumber) {
        switch (caracter) {
            case '+':
                NumberTile numberTile = new NumberTile(Integer.toString(chooseRandomNumber));
                numberTile.tileOnAction();
                return numberTile;
            case '-':
                EmptyTile emptyTile = new EmptyTile();
                emptyTile.tileOnAction();
                return emptyTile;
            default:
                return null;
        }

    }

    public int getGridColumn() {
        int maxColumns = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Record")) {
                    break;  // Sortir de la boucle si on rencontre la ligne contenant "Record"
                }
                int lineColumns = line.length();
                if (lineColumns > maxColumns) {
                    maxColumns = lineColumns;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxColumns;
    }

    public int getGridRow() {
        int lineCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Record")) {
                    break;  // Sortir de la boucle si on rencontre la ligne contenant "Record"
                }
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineCount;

    }

    public Button getButtonByCoordinates(int row, int col) {
        for (Node node : this.slidingGame.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (GridPane.getRowIndex(button) == row && GridPane.getColumnIndex(button) == col) {
                    return button;
                }
            }
        }
        return null;
    }

    public void clearGridPane(){
        GameController.getNbTurns().resetNbTurns();
        this.slidingGame.getChildren().clear();
        this.anchorPane.getChildren().remove(this.slidingGame);
    }


}
