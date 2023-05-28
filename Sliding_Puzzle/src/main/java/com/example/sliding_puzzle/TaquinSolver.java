package com.example.sliding_puzzle;

import java.util.*;

class TaquinSolver {
    private PriorityQueue<TaquinNode> openList;
    private Set<String> closedSet;
    private List<List<Integer>> goalState;
    private int numRows;
    private int numCols;

    public TaquinSolver(List<List<Integer>> goalState) {
        this.goalState = goalState;
        this.numRows = goalState.size();
        this.numCols = goalState.get(0).size();
        openList = new PriorityQueue<>(Comparator.comparingInt(TaquinNode::getCost));
        System.out.println(this.numCols + " | " + this.numRows );

        closedSet = new HashSet<>();
    }

    public void solve(List<List<Integer>> initialBoardList) {
        TaquinNode root = new TaquinNode(initialBoardList, 0, 0, null, findZeroPositions(initialBoardList));
        openList.add(root);

        while (!openList.isEmpty()) {
            TaquinNode currentNode = openList.poll();
            closedSet.add(currentNode.getBoard().toString());

            if (currentNode.getBoard().equals(goalState)) {
                printSolution(currentNode);
                return;
            }

            generateSuccessors(currentNode);
        }

        System.out.println("A solution could not be found.");
    }

    private void generateSuccessors(TaquinNode node) {
        List<List<Integer>> board = node.getBoard();
        int level = node.getLevel();
        List<int[]> zeroPositions = node.getZeroPositions();

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};  // Up, Down, Left, Right

        for (int[] zeroPos : zeroPositions) {
            int zeroRow = zeroPos[0];
            int zeroCol = zeroPos[1];

            for (int[] direction : directions) {
                int newRow = zeroRow + direction[0];
                int newCol = zeroCol + direction[1];

                if (isValidPosition(newRow, newCol)) {
                    List<List<Integer>> newBoard = deepCopy(board);
                    int temp = newBoard.get(zeroRow).get(zeroCol);
                    newBoard.get(zeroRow).set(zeroCol, newBoard.get(newRow).get(newCol));
                    newBoard.get(newRow).set(newCol, temp);

                    if (!closedSet.contains(newBoard.toString())) {
                        int cost = calculateCost(newBoard, goalState) + level + 1;
                        TaquinNode newNode = new TaquinNode(newBoard, level + 1, cost, node, findZeroPositions(newBoard));
                        openList.add(newNode);
                    }
                }
            }
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols;
    }

    private List<int[]> findZeroPositions(List<List<Integer>> board) {
        List<int[]> zeroPositions = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (board.get(i).get(j) == 0) {
                    zeroPositions.add(new int[]{i, j});
                }
            }
        }

        return zeroPositions;
    }

    private int calculateCost(List<List<Integer>> currentState, List<List<Integer>> goalState) {
        int cost = 0;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (!currentState.get(i).get(j).equals(goalState.get(i).get(j))) {
                    cost++;
                }
            }
        }

        return cost;
    }

    private List<List<Integer>> deepCopy(List<List<Integer>> list) {
        List<List<Integer>> copy = new ArrayList<>();

        for (List<Integer> sublist : list) {
            copy.add(new ArrayList<>(sublist));
        }

        return copy;
    }

    private void printSolution(TaquinNode node) {
        List<TaquinNode> path = new ArrayList<>();
        TaquinNode currentNode = node;

        while (currentNode != null) {
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }

        for (int i = path.size() - 1; i >= 0; i--) {
            TaquinNode step = path.get(i);
            List<List<Integer>> board = step.getBoard();

            System.out.println("Step " + (path.size() - i));
            for (List<Integer> row : board) {
                for (int num : row) {
                    System.out.print(num + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private List<List<Integer>> convertArrayToList(int[][] array) {
        List<List<Integer>> list = new ArrayList<>();

        for (int[] row : array) {
            List<Integer> sublist = new ArrayList<>();
            for (int num : row) {
                sublist.add(num);
            }
            list.add(sublist);
        }

        return list;
    }
}