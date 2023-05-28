package com.example.sliding_puzzle;

import com.example.sliding_puzzle.abstraction.NbTurns;
import com.example.sliding_puzzle.abstraction.SlidingGame;
import com.example.sliding_puzzle.controllers.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameApplication extends Application {

    private SlidingGame slidingGame = new SlidingGame();
    private String fileName = "src/levels/level1.txt";

    private FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("hello-view.fxml"));

    private NbTurns nbTurns = new NbTurns();

    GameController gameController = new GameController();



    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = this.fxmlLoader.load();

        this.gameController.initNbTurns(this.getNbTurns());
        this.gameController.initLevel(this.getButtonTopLevel(), this.getButtonLowerLevel(), this.getNumberLevel());
        this.gameController.initSlidingGame(this.getAnchorePane());
        this.gameController.initGameMouvement(GameController.getSlidingGame());
        this.gameController.initRecord(this.getRecord());
        this.gameController.initResetGame(this.getResetGameRandom());
//        this.gameController.clearLevel()
        GameController.getSlidingGame().createSlidingGame();
        GameController.getResetGame().resetRandom();

        Scene scene = new Scene(root, 1200,800);

        // Affichage de la scène
        stage.setScene(scene);
        stage.show();
    }




    public Label getNbTurns() {
        Label labelNbTurns = (Label) fxmlLoader.getNamespace().get("nbTurns");
        return labelNbTurns;
    }

    public Button getButtonTopLevel() {
        Button buttonTopLevel = (Button) fxmlLoader.getNamespace().get("buttonTopLevel");
        return buttonTopLevel;
    }

    public Button getButtonLowerLevel() {
        Button buttonLowerLevel = (Button) fxmlLoader.getNamespace().get("buttonLowerLevel");
        return buttonLowerLevel;
    }

    public Label getNumberLevel() {
        Label numberLevel = (Label) fxmlLoader.getNamespace().get("numberLevel");
        return numberLevel;
    }

    public AnchorPane getAnchorePane() {
        AnchorPane anchorPane = (AnchorPane) fxmlLoader.getNamespace().get("gameBox");
        return anchorPane;
    }

    public Label getRecord() {
        Label labelRecord = (Label) fxmlLoader.getNamespace().get("record");
        return labelRecord;
    }

    public Button getResetGameRandom() {
        Button resetGameRandom = (Button) fxmlLoader.getNamespace().get("resetRandom");
        return resetGameRandom;
    }

    public static void main(String[] args) {
        launch();
    }


}



