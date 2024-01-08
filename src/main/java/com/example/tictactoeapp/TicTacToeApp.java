package com.example.tictactoeapp;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

public class TicTacToeApp extends Application {

    private String currentPlayer = "X";
    private String[][] matrix = new String[][]{{"", "", ""}, {"", "", ""}, {"", "", ""}};
    TicTacToe myGame;
    private boolean playerFirstMoveCompleted = false;
    private Button[][] buttons = new Button[3][3];
    private Scene ticTacToeScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tic Tac Toe");

        myGame = new TicTacToe(matrix);
        // Create start screen
        StackPane startLayout = createStartLayout(primaryStage);
        Scene startScene = new Scene(startLayout, 300, 200);

        // Create Tic Tac Toe game screen
        GridPane gridPane = createGridPane();
        addButtonsToGrid(gridPane);
        ticTacToeScene = new Scene(gridPane, 300, 300);

        // Set start screen as the initial scene
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    private StackPane createStartLayout(Stage primaryStage) {
        StackPane startLayout = new StackPane();
        Button startButton = new Button("Start");
        startButton.setOnAction(event -> primaryStage.setScene(ticTacToeScene));
        startLayout.getChildren().add(startButton);
        return startLayout;
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }

    private void addButtonsToGrid(GridPane gridPane) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = createButton();
                buttons[row][col] = button;

                int finalRow = row;
                int finalCol = col;

                button.setOnAction(event -> handleButtonClick(finalRow, finalCol));

                gridPane.add(button, col, row);
            }
        }
    }

    private Button createButton() {
        Button button = new Button();
        button.setMinSize(100, 100);
        button.setStyle("-fx-font-size: 2em; -fx-background-radius: 0;");
        return button;
    }

    private void handleButtonClick(int row, int col) {
        Button button = buttons[row][col];

        if (button.getText().isEmpty()) {
            if(!playerFirstMoveCompleted){
                matrix[row][col] = currentPlayer;
                myGame.updateMatrix(matrix);
                myGame.Start();
                playerFirstMoveCompleted = true;
                button.setText(currentPlayer);
                int bestMove = myGame.findBestMove(myGame.gameTree.root);
                int[] indexes = myGame.checkIfMatricesAreEqual(this.matrix, this.myGame.gameTree.root.branches.get(bestMove).data.matrix);
                matrix[indexes[0]][indexes[1]] = "O";
                myGame.updateMatrix(matrix);
                Button cpuButton = buttons[indexes[0]][indexes[1]];
                cpuButton.setText("O");
                return;
            }
            matrix[row][col] = currentPlayer;
            myGame.updateMatrix(matrix);
            myGame.NullTreeRoot();
            myGame.Start();
            button.setText(currentPlayer);
//            int bestMove = myGame.findBestMove(myGame.gameTree.root);
//            int[] indexes = myGame.checkIfMatricesAreEqual(this.matrix, this.myGame.gameTree.root.branches.get(bestMove).data.matrix);
//            matrix[indexes[0]][indexes[1]] = "O";
//            myGame.updateMatrix(indexes[0], indexes[1], "0");
//            Button cpuButton = buttons[indexes[0]][indexes[1]];
//            cpuButton.setText("O");

        }
    }

    private boolean checkForWinner(int row, int col) {
        // Implement your logic to check for a winner
        // You need to check the row, column, and diagonals
        // Return true if there is a winner, false otherwise
        return false;
    }

    private void announceWinner() {
        // Implement the code to announce the winner
        // You can use an Alert or any other way to display the winner
    }
}