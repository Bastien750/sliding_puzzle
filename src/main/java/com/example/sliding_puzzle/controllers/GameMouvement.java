package com.example.sliding_puzzle.controllers;

import com.example.sliding_puzzle.abstraction.NbTurns;
import com.example.sliding_puzzle.abstraction.ShowAlert;
import com.example.sliding_puzzle.abstraction.SlidingGame;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class GameMouvement {

    private ArrayList<Button> listButtonClicked = new ArrayList<>();

    private static GameStatus gameStatus = new GameStatus();

    private SlidingGame slidingGame;

    private ShowAlert showAlert;

//    private GameController gameController = new GameController();


    public SlidingGame getSlidingGame() {
        return slidingGame;
    }

    public void setSlidingGame(SlidingGame slidingGame) {
        this.slidingGame = slidingGame;
    }

    public ArrayList<Button> getListButtonClicked() {
        return listButtonClicked;
    }

    public void setListButtonClicked(ArrayList<Button> listButtonClicked) {
        this.listButtonClicked = listButtonClicked;
    }

    public void selector(Button buttonClicked) {
        this.listButtonClicked.add(buttonClicked);
        if(this.listButtonClicked.size() >= 2){
            if(this.listButtonClicked.get(0).getText() == ""){
                this.listButtonClicked.get(0).setStyle("-fx-background-color:black;");
            } else {
                this.listButtonClicked.get(0).setStyle("-fx-border-color: white; -fx-border-width: 0px;");
            }
            this.exchange();
            this.listButtonClicked.clear();
        } else {
            if(buttonClicked.getText() == ""){
                this.listButtonClicked.get(0).setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-background-color:black;");
            } else {
                buttonClicked.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
        }
    }



    public void exchange() {
        Button button1 = this.listButtonClicked.get(0);
        Button button2 = this.listButtonClicked.get(1);
        // Check that we have a single black square
        if((button1.getText() == "") ^ (button2.getText() == "")) {
            if (this.checkAdj(button1, button2)) {
                this.swap(button1, button2);
                GameController.getNbTurns().incrementTurn();
                if(this.gameStatus.checkWin(GameController.getGameMouvement().slidingGame)) {
                    if(!GameController.getResetGame().isAutoCompletion()) {
                        GameController.getRecord().updateRecord();
                        this.showAlert = new ShowAlert("Niveau supérieur", "Vous avez validé le niveau !");
                        GameController.getLevel().topLevel();
                        GameController.getNbTurns().resetNbTurns();
                        System.out.println("Gagné");
                    } else {
                        GameController.getSlidingGame().clearGridPane();
                        GameController.getSlidingGame().createSlidingGame();
                        this.showAlert = new ShowAlert("Reset Game", "Reset Game");
                    }
                }
            }


        }
    }

    public boolean checkAdj(Button button1, Button button2) {
        int Xbutton1 = GridPane.getRowIndex(button1);
        int Ybutton1 = GridPane.getColumnIndex(button1);
        int Xbutton2 = GridPane.getRowIndex(button2);
        int Ybutton2 = GridPane.getColumnIndex(button2);

        if((Xbutton2 == Xbutton1 + 1 && Ybutton2 == Ybutton1) || (Xbutton2 == Xbutton1 - 1 && Ybutton2 == Ybutton1) || (Xbutton2 == Xbutton1 && Ybutton2 == Ybutton1 + 1) || (Xbutton2 == Xbutton1 && Ybutton2 == Ybutton1 - 1)) {
            System.out.println("Oui");
            return true;
        }
        return false;
    }


    public void swap(Button button1, Button button2) {
        int row1 = GridPane.getRowIndex(button1);
        int col1 = GridPane.getColumnIndex(button1);
        int row2 = GridPane.getRowIndex(button2);
        int col2 = GridPane.getColumnIndex(button2);

        // Échange des positions des boutons dans le GridPane
        GridPane.setRowIndex(button1, row2);
        GridPane.setColumnIndex(button1, col2);
        GridPane.setRowIndex(button2, row1);
        GridPane.setColumnIndex(button2, col1);
    }
}
