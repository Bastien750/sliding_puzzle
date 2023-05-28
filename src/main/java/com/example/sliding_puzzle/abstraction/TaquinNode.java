package com.example.sliding_puzzle.abstraction;

import java.util.*;

class TaquinNode {
    private List<List<Integer>> board;
    private int cost;
    private int level;
    private TaquinNode parent;
    private List<int[]> zeroPositions;

    public TaquinNode(List<List<Integer>> board, int level, int cost, TaquinNode parent, List<int[]> zeroPositions) {
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

    public TaquinNode getParent() {
        return parent;
    }

    public List<int[]> getZeroPositions() {
        return zeroPositions;
    }
}