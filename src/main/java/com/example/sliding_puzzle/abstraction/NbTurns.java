package com.example.sliding_puzzle.abstraction;

import com.example.sliding_puzzle.GameApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;


public class NbTurns {

    private Label nbTurns;

    public void setNbTurns(Label nbTurns) {
        this.nbTurns = nbTurns;
    }

    public Label getNbTurns() {
        return this.nbTurns;
    }

    // Increment number of turns
    public void incrementTurn()  {
        int currentNbTurns = Integer.parseInt(this.nbTurns.getText()) + 1;
        this.nbTurns.setText(Integer.toString(currentNbTurns));
    }

    // Reset number of turns on 0
    public void resetNbTurns() {
        this.nbTurns.setText("0");
    }

}
