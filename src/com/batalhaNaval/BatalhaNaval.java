package com.batalhaNaval;

import java.util.Random;
import java.util.Scanner;

public class BatalhaNaval {

    private BatalhaNavalPlayer player1 = new BatalhaNavalPlayer("Player");
    private BatalhaNavalPlayer player2 = new BatalhaNavalPlayer("Bot");

    private GameState gameState = GameState.STARTED;

    public BatalhaNaval() {

    }

    public void drawBoard(boolean player1) {
        StringBuilder header = new StringBuilder();

        header
                .append("-".repeat(45))
                .append("\n")
                .append(" ".repeat(18))
                .append("PLAYER ")
                .append(player1 ? 1 : 2)
                .append("\n")
                .append("-".repeat(45))
                .append("\n")
                .append(player1 ? this.player1 : player2)
        ;


        System.out.println(header);

    }

    public void play() {


        Scanner scanner = new Scanner(System.in);
        Random random = new Random();


        while (!gameState.equals(GameState.EXIT)) {

            switch (gameState) {
                case STARTED: {
                    System.out.println("Welcome to Batalha Naval, you are player 1");
                    System.out.println("Press x anytime to exit");
                    gameState = GameState.PLAYER1;
                }

                break;
                case PLAYER1: {


                    drawBoard(true);

                    System.out.printf("Player 1 points: %02d, player 2 points: %02d \n", player1.getPoints(), player2.getPoints());
                    System.out.println("Player 1, Insert an instruction");
                    String input = scanner.next().toUpperCase();
                    if (input.equalsIgnoreCase("x")) {
                        gameState = GameState.EXIT;
                    } else {
                        if (playerTurn(player1, player2, input)) {
                            gameState = GameState.WIN;
                        } else {
                            gameState = GameState.PLAYER2;
                        }
                    }


                }
                break;
                case PLAYER2: {

                    int row = random.nextInt(10) + 65;
                    int column = random.nextInt(10);

                    String input = String.format("%c%d", row, column);

                    if (playerTurn(player2, player1, input)) {
                        gameState = GameState.LOOSE;
                    } else {
                        gameState = GameState.PLAYER1;
                    }


                }
                break;
                case WIN: {

                    drawBoard(false);
                    System.out.println("Player 1 Won the game");
                    System.out.println("Do you want to play again?");

                    String input = scanner.next().toUpperCase();
                    if (input.equalsIgnoreCase("y")) {
                        gameState = GameState.STARTED;
                    } else {
                        gameState = GameState.EXIT;
                    }
                }
                break;
                case LOOSE: {
                    drawBoard(false);

                    System.out.println("Player 2 Won the game");
                    System.out.println("Do you want to play again?");

                    String input = scanner.next().toUpperCase();
                    if (input.equalsIgnoreCase("y")) {
                        gameState = GameState.STARTED;
                    } else {
                        gameState = GameState.EXIT;
                    }
                }
                break;
                default:
                    gameState = GameState.EXIT;
            }


        }
    }

    private boolean playerTurn(BatalhaNavalPlayer playingPlayer,
                               BatalhaNavalPlayer otherPlayer,
                               String input) {
        char row = input.charAt(0);
        int column = input.charAt(1) - 48;
        boolean hit = otherPlayer.isHit(row, column);

        System.out.printf("%s did%s hit on %s\n",playingPlayer.getName(), (hit ? "" : "n`t"), input);


        if (hit)
            playingPlayer.addPoint();

        return playingPlayer.isWin();

    }
}
