package com.batalhaNaval;

import java.util.Arrays;
import java.util.Random;

public class BatalhaNavalPlayer {
    public String name;
    private int[][] board = new int[10][10];
    private int points = 0;


    public BatalhaNavalPlayer(String name) {
        for (int[] ints : board) {
            Arrays.fill(ints, 0);

        }

        Random random = new Random();
        for (int x = 0; x < 11; x++) {
            boolean hasShip = false;

            while (!hasShip) {
                int row = random.nextInt(10);
                int column = random.nextInt(10);

                if (board[row][column] == 1)
                    continue;
                board[row][column] = 1;
                hasShip = true;
            }
        }


        this.name = name;
    }

    public String drawBoard() {
        StringBuilder boardBuilder = new StringBuilder();

        boardBuilder.append("|   |");
        for (int row = 0; row < board.length; row++) {
            boardBuilder
                    .append(" ")
                    .append(row)
                    .append(" |");
        }


        boardBuilder
                .append("\n")
                .append("-".repeat(45))
                .append("\n");


        for (int row = 0; row < board.length; row++) {
            boardBuilder
                    .append("| ")
                    .append((char) (row + 65))
                    .append(" |")
                    .append(drawRow(board[row]))
                    .append("\n")
                    .append("-".repeat(45))
                    .append("\n")
            ;
        }
        return boardBuilder.toString();
    }

    public boolean isHit(char row, int column) {
        if (row < 65 || row > 74 || column < 0 || column > 9) {
            return false;
        }
        boolean hit = board[row - 65][column] == 1;
        board[row - 65][column] = hit ? 2 : 3;
        return hit;
    }

    public void addPoint() {
        points++;
    }

    public boolean isWin() {
        return points >= 11;
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return drawBoard();
    }

    private String drawRow(int[] boardRow) {
        StringBuilder rowBuilder = new StringBuilder();

        for (int column = 0; column < boardRow.length; column++) {
            rowBuilder
                    .append(" ")
                    .append(handleTypes(boardRow[column]))
                    .append(" |");
        }

        return rowBuilder.toString();

    }

    private String handleTypes(int value) {
        switch (value) {
            case 1:
                return "N";
            case 2:
                return "*";
            case 3:
                return "-";
            default:
                return " ";
        }
    }
}
