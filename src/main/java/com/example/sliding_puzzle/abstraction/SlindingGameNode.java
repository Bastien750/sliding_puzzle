package com.example.sliding_puzzle.abstraction;

import java.util.List;

public class SlindingGameNode {

    private List<List<Integer>> board;
    private int cost;
    private int level;
    private SlindingGameNode parent;
    private List<int[]> zeroPositions;

    private int[] movedTilePosition;
    private int[] emptyTilePosition;


    public SlindingGameNode(List<List<Integer>> board, int level, int cost, SlindingGameNode parent, List<int[]> zeroPositions) {
        this.board = board;
        this.level = level;
        this.cost = cost;
        this.parent = parent;
        this.zeroPositions = zeroPositions;
    }


    public int getCost() {
        return cost;
    }

    public int getLevel() {
        return level;
    }

    public List<List<Integer>> getBoard() {
        return board;
    }

    public SlindingGameNode getParent() {
        return parent;
    }

    public List<int[]> getZeroPositions() {
        return zeroPositions;
    }
}
