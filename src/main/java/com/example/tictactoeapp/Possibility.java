package com.example.tictactoeapp;

public class Possibility {
    public Turn turn;
    public String[][] matrix;
    public Possibility(Turn turn, String[][] matrix){
        this.turn = turn;
        this.matrix = matrix;
    }
}
