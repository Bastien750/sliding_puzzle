package com.example.sliding_puzzle.abstraction;

import com.example.sliding_puzzle.controllers.GameController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.*;

public class Resolve {

    private PriorityQueue<SlindingGameNode> openList;
    private Set<String> closedSet;
    private List<List<Integer>> goalState;
    private int numRows;
    private int numCols;

    private Button resolve;

    private ShowAlert showAlert;

    public Button getResolve() {
        return resolve;
    }

    public void setResolve(Button resolve) {
        this.resolve = resolve;
    }

    // Event of the button resolve
    public void resolveSlidingGame() {
        this.resolve.setOnAction(e -> {
            this.resolve();
            this.showAlert = new ShowAlert("Level cleared ", "Level solved with autocomplete ");
            GameController.getSlidingGame().clearGridPane();
            GameController.getSlidingGame().createSlidingGame();
        });
    }

    // Function that resolve the game
    public void resolve() {
        this.goalState = GameController.getSlidingGame().finalBoard();
        this.numRows = GameController.getSlidingGame().finalBoard().size();
        this.numCols = GameController.getSlidingGame().finalBoard().get(0).size();
        openList = new PriorityQueue<>(Comparator.comparingInt(SlindingGameNode::getCost));
        closedSet = new HashSet<>();
        System.out.println("final " + GameController.getSlidingGame().finalBoard());
        System.out.println("inital " + GameController.getSlidingGame().initalBoard());
        System.out.println(this.goalState);


        this.solve(GameController.getSlidingGame().initalBoard());
    }

    public void solve(List<List<Integer>> initialBoardList) {
        SlindingGameNode root = new SlindingGameNode(initialBoardList, 0, 0, null, findZeroPositions(initialBoardList));
        openList.add(root);

        while (!openList.isEmpty()) {
            SlindingGameNode currentNode = openList.poll();
            closedSet.add(currentNode.getBoard().toString());

            if (currentNode.getBoard().equals(goalState)) {
//                startShuffling(currentNode);
                printSolution(currentNode);
                return;
            }

            generateSuccessors(currentNode);
        }

        System.out.println("A solution could not be found.");
    }

    // Function that generates successors
    private void generateSuccessors(SlindingGameNode node) {
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
                        SlindingGameNode newNode = new SlindingGameNode(newBoard, level + 1, cost, node, findZeroPositions(newBoard));

                        if (node.getParent() != null && node.getParent().getBoard().equals(newNode.getBoard())) {
                            int[] movedTileCoordinates = getMovedTileCoordinates(node.getBoard(), newNode.getBoard());
                            int[] emptyTileCoordinates = getEmptyTileCoordinates(node.getBoard(), newNode.getBoard());
                            int movedRow = movedTileCoordinates[0];
                            int movedCol = movedTileCoordinates[1];
                            int emptyRow = emptyTileCoordinates[0];
                            int emptyCol = emptyTileCoordinates[1];
                            System.out.println("Moved Tile Coordinates: (" + movedRow + ", " + movedCol + ")");
                            System.out.println("Empty Tile Coordinates: (" + emptyRow + ", " + emptyCol + ")");
                        }

                        openList.add(newNode);
                    }
                }
            }
        }
    }

    // Function that return if is a validPosition
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols;
    }

    // Function that find empty case
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

    // Function that calculate Cose
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

    // Function that copy list
    private List<List<Integer>> deepCopy(List<List<Integer>> list) {
        List<List<Integer>> copy = new ArrayList<>();

        for (List<Integer> sublist : list) {
            copy.add(new ArrayList<>(sublist));
        }

        return copy;
    }

    // Function that print in the console step by step the solution and resolve the game in the applicaition
    private void printSolution(SlindingGameNode node) {
        List<SlindingGameNode> path = new ArrayList<>();
        SlindingGameNode currentNode = node;

        List<Button> modifiedButtons = new ArrayList<>();
        List<List<Integer>> listCoord = new ArrayList<>();
        List<Button> coord = new ArrayList<>();


        while (currentNode != null) {
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }

        List<List<Integer>> currentBoard = new ArrayList<>();
        List<List<Integer>> oldBoard = new ArrayList<>();

        Button targetButton1 = null;
        Button targetButton2 = null;

        for (int i = path.size() - 1; i >= 0; i--) {
            SlindingGameNode step = path.get(i);
//            System.out.println("okkk" + path.get(i).getBoard());
            currentBoard = path.get(i).getBoard();
            if(i != 0) {
                oldBoard = path.get(i-1).getBoard();
//                System.out.println("t" + path.get(i-1).getBoard());
            }


            for (int k = 0; k < currentBoard.size(); k++) {
                for (int j = 0; j < currentBoard.get(k).size(); j++) {
                    if (currentBoard.get(k).get(j) != oldBoard.get(k).get(j)) {
                        System.out.println("La valeur à l'indice [" + k + "][" + j + "] a changé.");
                        System.out.println("Ancienne valeur : " + currentBoard.get(k).get(j));
                        System.out.println("Nouvelle valeur : " + oldBoard.get(k).get(j));

                        coord.add(this.getButtonFromCoordinates(k,j));
//                        System.out.println("Button " + this.getButtonFromCoordinates(j,k).getText());

//                        System.out.println("cal" + (k * currentBoard.get(k).size() + j));
//                        System.out.println("ccord" + coord);
                    }

                    if(coord.size() > 1) {
                        System.out.println("buttoncord" + coord);
                        swapButtons(coord.get(0), coord.get(1));
                        coord.clear();
                    }
                }

            }



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


    // Function who return a button by it's coordinate
    private Button getButtonFromCoordinates(int x, int y) {
        for (Node node : GameController.getSlidingGame().getSlidingGame().getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (GridPane.getRowIndex(button) == x && GridPane.getColumnIndex(button) == y) {
                    return button;
                }
            }
        }
        return null;
    }





    private int[] getMovedTileCoordinates(List<List<Integer>> currentState, List<List<Integer>> parentState) {
        int numRows = currentState.size();
        int numCols = currentState.get(0).size();

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int currentTile = currentState.get(i).get(j);
                int parentTile = parentState.get(i).get(j);
                if (currentTile != parentTile) {
                    return new int[]{i, j};
                }
            }
        }

        return null;
    }

    private int[] getEmptyTileCoordinates(List<List<Integer>> currentState, List<List<Integer>> parentState) {
        int numRows = currentState.size();
        int numCols = currentState.get(0).size();

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int currentTile = currentState.get(i).get(j);
                int parentTile = parentState.get(i).get(j);
                if (currentTile == 0 && parentTile != 0) {
                    return new int[]{i, j};
                }
            }
        }

        return null;
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

    public boolean swapButtons(Button button1, Button button2) {

        int button1Column = GridPane.getColumnIndex(button1);
        int button1Row = GridPane.getRowIndex(button1);
        int button2Column = GridPane.getColumnIndex(button2);
        int button2Row = GridPane.getRowIndex(button2);

        GameController.getSlidingGame().getSlidingGame().getChildren().removeAll(button1, button2); // Retire les boutons du GridPane

        GameController.getSlidingGame().getSlidingGame().add(button1, button2Column, button2Row);
        GameController.getSlidingGame().getSlidingGame().add(button2, button1Column, button1Row);

        return true;
    }
}
