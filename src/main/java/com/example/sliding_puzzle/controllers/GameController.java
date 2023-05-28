package com.example.sliding_puzzle.controllers;

import com.example.sliding_puzzle.abstraction.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GameController {

    private static NbTurns nbTurns = new NbTurns();

    private static Level level = new Level();

    private static GameMouvement gameMouvement = new GameMouvement();

    private static SlidingGame slidingGame = new SlidingGame();

    private static Record record = new Record();

    private static ResetGame resetGame = new ResetGame();

    private static Resolve resolve = new Resolve();


    public void initNbTurns(Label labelNbTurns){
        GameController.nbTurns.setNbTurns(labelNbTurns);
    }

    public void initLevel(Button buttonTopLevel, Button buttonLowerLevel, Label numberLevel) {
        GameController.level.setButtonTopLevel(buttonTopLevel);
        GameController.level.setButtonLowerLevel(buttonLowerLevel);
        GameController.level.setNumberLevel(numberLevel);
    }

    public void initGameMouvement(SlidingGame slidingGame) {
        GameController.gameMouvement.setSlidingGame(slidingGame);
    }

    public void initSlidingGame(AnchorPane anchorPane) {
        GameController.slidingGame.setAnchorPane(anchorPane);
    }

    public void initRecord(Label recordLabel) {
        GameController.record.setRecordLabel(recordLabel);
        GameController.record.setRecordFile(GameController.getSlidingGame().getFileName());
    }

    public void initResetGame(Button resetGameRandom, Button resetGameMovingTile) {
        GameController.resetGame.setResetGame(resetGameRandom);
        GameController.resetGame.setResetMovingTile(resetGameMovingTile);
    }

    public void initResolve(Button resolve) {
        GameController.resolve.setResolve(resolve);
    }
    public static NbTurns getNbTurns() {
        return GameController.nbTurns;
    }

    public static Level getLevel() {
        return GameController.level;
    }

    public static void setNbTurns(NbTurns nbTurns) {
        GameController.nbTurns = nbTurns;
    }

    public static void setLevel(Level level) {
        GameController.level = level;
    }

    public static GameMouvement getGameMouvement() {
        return gameMouvement;
    }

    public static void setGameMouvement(GameMouvement gameMouvement) {
        GameController.gameMouvement = gameMouvement;
    }

    public static SlidingGame getSlidingGame() {
        return slidingGame;
    }

    public static void setSlidingGame(SlidingGame slidingGame) {
        GameController.slidingGame = slidingGame;
    }

    public static Record getRecord() {
        return record;
    }

    public static void setRecord(Record record) {
        GameController.record = record;
    }

    public static ResetGame getResetGame() {
        return resetGame;
    }

    public static void setResetGame(ResetGame resetGame) {
        GameController.resetGame = resetGame;
    }

    public static Resolve getResolve() {
        return resolve;
    }

    public static void setResolve(Resolve resolve) {
        GameController.resolve = resolve;
    }
}
