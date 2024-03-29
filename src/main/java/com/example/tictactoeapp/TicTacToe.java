package com.example.tictactoeapp;
import java.util.Random;

import java.util.Objects;

public class TicTacToe {
    public String[][] matrix;
    public Tree gameTree;
    public TicTacToe(){
        this.matrix = new String[][]{{"", "", ""}, {"", "", ""}, {"", "", ""}};
        this.gameTree = new Tree();
    }

    public void NullTreeRoot(){
        this.gameTree.root = null;
    }

    private void CreateGameTree(){
        for(int i = 0; i < this.matrix.length; i++){
            for(int j = 0; j < this.matrix[i].length; j++){
                if(Objects.equals(this.matrix[i][j], "X")){
                    String[][] possibilityToAdd = new String[3][3];
                    CopyMatrixTo(this.matrix, possibilityToAdd);
                    // copy matrix 2d array
                    Possibility firstMove = new Possibility(Turn.PLAYER, possibilityToAdd);
                    this.gameTree.addNodeToRoot(firstMove);
                    CreateGameTree(this.gameTree.root, firstMove, Turn.CPU);
                    break;
                }
            }
        }
    }
    private void CreateGameTree(TreeNode root, Possibility possibility, Turn newTurn){
        for(int i = 0; i < possibility.matrix.length; i++){
            for(int j = 0; j < possibility.matrix[i].length; j++){
                if(Objects.equals(possibility.matrix[i][j], "")){
                    String[][] possibilityToAdd = new String[3][3];
                    CopyMatrixTo(possibility.matrix, possibilityToAdd);
                    if(newTurn == Turn.CPU){
                        possibilityToAdd[i][j] = "O";
                    } else {
                        possibilityToAdd[i][j] = "X";
                    }
                    Possibility move = new Possibility(newTurn, possibilityToAdd);
                    TreeNode newNode = new TreeNode(move);
                    root.addBranch(newNode);
                    if(newTurn == Turn.CPU){
                        if(checkIfEitherPlayerOrCpuWinsOrDraw(possibilityToAdd, "O")){
                            return;
                        }
                        CreateGameTree(newNode, move, Turn.PLAYER);
                    } else {
                        if(checkIfEitherPlayerOrCpuWinsOrDraw(possibilityToAdd, "X")){
                            return;
                        }
                        CreateGameTree(newNode, move, Turn.CPU);
                    }
                }
            }
        }
    }

    public int getCpuMoveRowAndCol(TreeNode node, Turn turn) {
        TreeNode root = node;

        if (checkIfEitherPlayerOrCpuWinsOrDraw(root.data.matrix, "O")) {
            root.data.score = -1 * (countNoOfEmptyCells(root.data.matrix) + 1);
            return -1 * (countNoOfEmptyCells(root.data.matrix) + 1);
        } else if (checkIfEitherPlayerOrCpuWinsOrDraw(root.data.matrix, "X")) {
            root.data.score = 1 * (countNoOfEmptyCells(root.data.matrix) + 1);
            return 1 * (countNoOfEmptyCells(root.data.matrix) + 1);
        } else if (checkIfGameIsTie(root.data.matrix)) {
            root.data.score = -1 * (countNoOfEmptyCells(root.data.matrix) + 1);
            return -1 * (countNoOfEmptyCells(root.data.matrix) + 1);
        }
        int bestScore;

        if (turn == Turn.PLAYER) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < root.branches.size(); i++) {
                TreeNode currentBranch = root.branches.get(i);
                int score = getCpuMoveRowAndCol(currentBranch, Turn.CPU);
                if (score > bestScore) {
                    bestScore = score;
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < root.branches.size(); i++) {
                TreeNode currentBranch = root.branches.get(i);
                int score = getCpuMoveRowAndCol(currentBranch, Turn.PLAYER);
                if (score < bestScore) {
                    bestScore = score;
                }
            }
        }
        node.data.score = bestScore * (countNoOfEmptyCells(root.data.matrix) + 1);
        return bestScore;
    }

    public int[] findBestMove() {
        if(this.gameTree.root.data.turn == Turn.CPU){
            for(int i = 0; i < this.gameTree.root.branches.size(); i++){
                TreeNode currentBranch = this.gameTree.root.branches.get(i);
                if(checkIfMatricesAreEquals(this.matrix, currentBranch.data.matrix)){
                    this.gameTree.root = currentBranch;
                    break;
                }
            }
        }
        String[][] matrixWithNewMove = null;
        int minimumScore = this.gameTree.root.branches.get(0).data.score;
        for(int i = 0; i < this.gameTree.root.branches.size(); i++){
            TreeNode currentBranch = this.gameTree.root.branches.get(i);
            if(currentBranch.data.score < minimumScore){
                minimumScore = currentBranch.data.score;
            }
        }
        for(int i = 0; i < this.gameTree.root.branches.size(); i++){
            TreeNode currentBranch = this.gameTree.root.branches.get(i);
            if(currentBranch.data.score == minimumScore){
                matrixWithNewMove = currentBranch.data.matrix;
                this.gameTree.root = currentBranch;
                break;
            }
        }
        if(matrixWithNewMove != null){
            return getTheNewMove(this.matrix, matrixWithNewMove);
        }

        return null;
    }

    public int countNoOfEmptyCells(String[][] matrix){
        int count = 0;
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                if(Objects.equals(matrix[i][j], "")){
                    count++;
                }
            }
        }
        return count;
    }

    public int[] chooseRandomEmptyCell(String[][] matrix) {
        Random random = new Random();
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Iterate until an empty cell is found
        while (true) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);
            if (matrix[row][col].equals(" ")) { // Replace " " with your empty cell marker
                return new int[] {row, col};
            }
        }
    }

    public int[] getTheNewMove(String[][] matrix1, String[][] matrix2){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(!Objects.equals(matrix1[i][j],matrix2[i][j])){
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public boolean checkIfMatricesAreEquals(String[][] matrix1, String[][] matrix2){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(!Objects.equals(matrix1[i][j],matrix2[i][j])){
                    return false;
                }
            }
        }
        return true;
    }

    public void updateMatrix(String[][] matrix){
        this.matrix = matrix;
    }
    private boolean checkIfEitherPlayerOrCpuWinsOrDraw(String[][] matrix, String match){
        if(Objects.equals(matrix[0][0], match) && Objects.equals(matrix[0][1], match) && Objects.equals(matrix[0][2], match)){
            return true;
        } else if(Objects.equals(matrix[1][0], match) && Objects.equals(matrix[1][1], match) && Objects.equals(matrix[1][2], match)){
            return true;
        } else if(Objects.equals(matrix[2][0], match) && Objects.equals(matrix[2][1], match) && Objects.equals(matrix[2][2], match)){
            return true;
        } else if(Objects.equals(matrix[0][0], match) && Objects.equals(matrix[1][0], match) && Objects.equals(matrix[2][0], match)){
            return true;
        } else if(Objects.equals(matrix[0][1], match) && Objects.equals(matrix[1][1], match) && Objects.equals(matrix[2][1], match)){
            return true;
        } else if(Objects.equals(matrix[0][2], match) && Objects.equals(matrix[1][2], match) && Objects.equals(matrix[2][2], match)){
            return true;
        } else if(Objects.equals(matrix[0][0], match) && Objects.equals(matrix[1][1], match) && Objects.equals(matrix[2][2], match)){
            return true;
        } else if(Objects.equals(matrix[0][2], match) && Objects.equals(matrix[1][1], match) && Objects.equals(matrix[2][0], match)){
            return true;
        } else {
            return false;
        }
    }
    private boolean checkIfGameIsTie(String[][] matrix) {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(Objects.equals(matrix[i][j], "")){
                    return false;
                }
            }
        }
        return true;
    }

    private void CopyMatrixTo(String[][] from, String[][] to){
        for(int i = 0; i < from.length; i++){
            for(int j = 0; j < from[i].length; j++){
                to[i][j] = from[i][j];
            }
        }
    }

    public void Start(){
        // create a game tree
        this.CreateGameTree();
    }
}
