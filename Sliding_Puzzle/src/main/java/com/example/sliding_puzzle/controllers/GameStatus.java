package com.example.sliding_puzzle.controllers;

import com.example.sliding_puzzle.abstraction.SlidingGame;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Collections;

public class GameStatus {

    private static GameMouvement gameMouvement = new GameMouvement();
    public boolean checkWin(SlidingGame slidingGame) {
        int gridRow = slidingGame.getGridRow();
        int gridColumn = slidingGame.getGridColumn();
        int increment = 999;
        ArrayList<Integer> resultList = new ArrayList<Integer>();
        for(int row = 0; row < gridRow; row++) {
            for(int column = 0; column < gridColumn; column++) {
                if(slidingGame.getButtonByCoordinates(row,column) != null) {
                    if(slidingGame.getButtonByCoordinates(row, column).getText() != "") {
                        resultList.add(Integer.parseInt(slidingGame.getButtonByCoordinates(row,column).getText()));
                    } else {
                        resultList.add(increment++);
                    }
                }
            }
        }

        ArrayList<Integer> sortedList = new ArrayList<Integer>();
        sortedList.addAll(resultList);
        Collections.sort(sortedList);
        System.out.println(sortedList);
        System.out.println(resultList);
        if(resultList.equals(sortedList)) {
            System.out.println("true");
            return true;
        }
        System.out.println("false");
        return false;
    }
}
