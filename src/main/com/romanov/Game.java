package main.com.romanov;

import main.com.romanov.board.Board;
import main.com.romanov.piece.BoardConsoleRenderer;
import main.com.romanov.board.Move;

import java.util.List;

public class Game {
    private final Board board;
    private final BoardConsoleRenderer renderer = new BoardConsoleRenderer();
    private final List<GameStateChecker> checkers = List.of(
            new StaleMateGameStateChecker(),
            new CheckmateGameStateChecker()
    );

    Game(Board board){this.board = board;}

    public void gameLoop(){
        Colour colorToMove = Colour.WHITE;
        GameState gameState = determineGameState(board, colorToMove);
        while(gameState == GameState.ONGOING) {
            renderer.render(board);
            if(colorToMove == Colour.WHITE){
                System.out.println("Whites' turn");
            } else{
                System.out.println("Blacks' turn");
            }
            Move move = InputCoordinates.inputMove(board, colorToMove, renderer);

            // make move
            board.makeMove(move);
            // pass move
            colorToMove = colorToMove.opposite();
            gameState = determineGameState(board, colorToMove);
        }
        renderer.render(board);
        System.out.println("Game ended with an outcome of " + gameState);
    }


    private GameState determineGameState(Board board, Colour colour) {
        for(GameStateChecker checker : checkers){
            GameState state = checker.check(board, colour);
            if (state != GameState.ONGOING){
                return state;
            }
        }
    return GameState.ONGOING;
    }
}

