package com.example.sliding_puzzle.abstraction;

import com.example.sliding_puzzle.controllers.GameController;
import com.example.sliding_puzzle.controllers.GameMouvement;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class SlidingGame {

    private GridPane slidingGame;

    private String fileName;

    private AnchorPane anchorPane;

    private PriorityQueue<SlindingGameNode> openList;
    private Set<String> closedSet;
    private List<List<Integer>> goalState;
    private int numRows;
    private int numCols;


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


    // Function that create the GridPane(Slinding Game) using .txt fils  and display in the application
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

    // Function who has the list number clicked
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

    // Funtion who create a tile according to his symbol
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

    // Get column of the slidingGame
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

    // Get row of the slidingGame
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

    // Get buttons by his coordinates
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

    // Clear the gridPane
    public void clearGridPane(){
        GameController.getNbTurns().resetNbTurns();
        this.slidingGame.getChildren().clear();
        this.anchorPane.getChildren().remove(this.slidingGame);
    }

    // Count the number of + int the .txt file
    public int nbPlus() {
        int nbPlus = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Record")) {
                    break;  // Sortir de la boucle si on rencontre la ligne contenant "Record"
                }
                for(char c : line.toCharArray()) {
                    if(c == '+') {
                        nbPlus++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nbPlus;
    }

    public List<Integer> halfFinalBoard() {
        List<Integer> halfFinalBoard = new ArrayList<>();
        int nbPlus = 0;
        int nbemptyCase = 0;
        int increment = 0;
        List<Integer> listBlockCase = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Record")) {
                    break;  // Sortir de la boucle si on rencontre la ligne contenant "Record"
                }
                for(char c : line.toCharArray()) {
                    switch (c) {
                        case '+':
                            nbPlus++;
                            break;
                        case '-':
                            nbemptyCase++;
                            break;
                        case '/':
                            listBlockCase.add(increment);
                            break;
                        default:
                            break;
                    }
                    increment++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=1; i<=nbPlus; i++) {
            halfFinalBoard.add(i);
        }

        for(int i=1; i<=nbemptyCase; i++) {
            halfFinalBoard.add(0);
        }

        for(int i=0; i<listBlockCase.size(); i++) {
            halfFinalBoard.add(listBlockCase.get(i), -1);
        }
        return halfFinalBoard;
    }

    public List<List<Integer>> finalBoard() {
        System.out.println("half " + this.halfFinalBoard());
        List<Integer> halfFinalBoard = this.halfFinalBoard();
        List<List<Integer>> finalBoard = new ArrayList<>();
        int row = this.getGridRow();

        for(int i=0; i<halfFinalBoard.size(); i+= row) {
            List<Integer> finalBoardSubList = new ArrayList<>();
            for(int j=i; j<i+row; j++) {
                finalBoardSubList.add(halfFinalBoard.get(j));
            }
            finalBoard.add(finalBoardSubList);
        }

        return finalBoard;
    }


    public List<List<Integer>> initalBoard() {
        int gridRow = this.getGridRow();
        int gridColumn = this.getGridColumn();
        List<List<Integer>> initialBoard = new ArrayList<>();
//        System.out.println(gridRow + " | " + gridColumn);
        for(int row = 0; row < gridRow; row++) {
            List<Integer> initialBoard2 = new ArrayList<>();
            for(int column = 0; column < gridColumn; column++) {
                if(this.isLabel(row, column)){
                    initialBoard2.add(-1);
                } else {
                    Button buttonText = this.getButtonByCoordinates(row, column);
                    String buttonNumber = buttonText.getText();
                    if(buttonText.getText() == ""){
                        initialBoard2.add(0);
                    } else {
                        initialBoard2.add(Integer.parseInt(buttonNumber));
                    }
                }

            }

            initialBoard.add(initialBoard2);
        }

        return initialBoard;
    }

    public boolean isLabel(int row, int col){
        for (Node node : this.slidingGame.getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                if (GridPane.getRowIndex(label) == row && GridPane.getColumnIndex(label) == col) {
                    return true;
                }
            }
        }
        return false;
    }


}
