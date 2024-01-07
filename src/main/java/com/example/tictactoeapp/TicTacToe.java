package com.example.tictactoeapp;

import java.util.Objects;

public class TicTacToe {
    public String[][] matrix;
    public Tree gameTree;
    public TicTacToe(String[][] matrix){
        this.matrix = matrix;
        this.gameTree = new Tree();
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
                        if(checkIfEitherPlayerOrCpuWins(possibilityToAdd, "O")){
                            return;
                        }
                        CreateGameTree(newNode, move, Turn.PLAYER);
                    } else {
                        if(checkIfEitherPlayerOrCpuWins(possibilityToAdd, "X")){
                            return;
                        }
                        CreateGameTree(newNode, move, Turn.CPU);
                    }
                }
            }
        }
    }

    public LinearList getCpuMoveRowAndCol(TreeNode node){
        TreeNode root = node;
        for(int i = 0; i < root.branches.size(); i++){
            TreeNode currentBranch = root.branches.get(i);
            // check if matrix is equals
            if(checkIfMatricesAreEqual(this.matrix, currentBranch.data)){
                // go deep inside this tree and check in which condition a Cpu wins or Draws
                return checkIfCpuWinsOrDraws(currentBranch);
            } else {
                getCpuMoveRowAndCol(currentBranch);
            }
        }
        return null;
    }

    private LinearList checkIfCpuWinsOrDraws(TreeNode node){
        for(int i = 0; i < node.branches.size(); i++){
            TreeNode currentBranch = node.branches.get(i);
            if(checkIfEitherPlayerOrCpuWinsOrDraw(currentBranch.data.matrix, "O")){
                LinearList linearList = new LinearList();
                linearList.insertLast(currentBranch.data);
                return linearList;
            } else {
                LinearList linearList = checkIfCpuWinsOrDraws(currentBranch);
                if(linearList != null){
                    linearList.insertLast(currentBranch.data);
                }
            }
        }
        return null;
    }

    private boolean checkIfMatricesAreEqual(String[][] matrix1, Possibility possibility){
        for(int i = 0; i < matrix1.length; i++){
            for(int j = 0; j < matrix1[i].length; j++){
                if(!Objects.equals(matrix1[i][j], possibility.matrix[i][j])){
                    return false;
                }
            }
        }
        return true;
    }

    public void updateMatrix(int row, int col, String player){
        this.matrix[row][col] = player;
    }
    private boolean checkIfEitherPlayerOrCpuWins(String[][] matrix, String match) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (Objects.equals(matrix[i][0], match) && Objects.equals(matrix[i][1], match) && Objects.equals(matrix[i][2], match)) {
                return true; // Rows
            }
            if (Objects.equals(matrix[0][i], match) && Objects.equals(matrix[1][i], match) && Objects.equals(matrix[2][i], match)) {
                return true; // Columns
            }
        }

        // Check diagonals
        if (Objects.equals(matrix[0][0], match) && Objects.equals(matrix[1][1], match) && Objects.equals(matrix[2][2], match)) {
            return true; // Top-left to bottom-right diagonal
        }
        if (Objects.equals(matrix[0][2], match) && Objects.equals(matrix[1][1], match) && Objects.equals(matrix[2][0], match)) {
            return true; // Top-right to bottom-left diagonal
        }

        return false;
    }
    private boolean checkIfEitherPlayerOrCpuWinsOrDraw(String[][] matrix, String match) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (Objects.equals(matrix[i][0], match) && Objects.equals(matrix[i][1], match) && Objects.equals(matrix[i][2], match)) {
                return true; // Rows
            }
            if (Objects.equals(matrix[0][i], match) && Objects.equals(matrix[1][i], match) && Objects.equals(matrix[2][i], match)) {
                return true; // Columns
            }
        }

        // Check diagonals
        if (Objects.equals(matrix[0][0], match) && Objects.equals(matrix[1][1], match) && Objects.equals(matrix[2][2], match)) {
            return true; // Top-left to bottom-right diagonal
        }
        if (Objects.equals(matrix[0][2], match) && Objects.equals(matrix[1][1], match) && Objects.equals(matrix[2][0], match)) {
            return true; // Top-right to bottom-left diagonal
        }

        // Check for a draw
        boolean isDraw = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j].equals("")) {
                    isDraw = false;
                    break;
                }
            }
            if (!isDraw) {
                break;
            }
        }

        return isDraw;
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
