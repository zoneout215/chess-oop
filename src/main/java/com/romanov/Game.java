package com.romanov;

import com.romanov.board.Board;
import com.romanov.board.Move;
import com.romanov.piece.BoardConsoleRenderer;
import java.util.List;

public class Game {
    private final Board board;
    private final BoardConsoleRenderer renderer = new BoardConsoleRenderer();
    private final List<GameStateChecker> checkers = List.of(
            new StaleMateGameStateChecker(),
            new CheckmateGameStateChecker()
//            new CaslesGameStateChecker()
    );

    Game(Board board){this.board = board;}

    public void gameLoop(){
        Colour colorToMove = Colour.WHITE; // add reading from FEN
        GameState gameState = determineGameState(board);
        while(gameState == GameState.ONGOING) {
            renderer.render(board);
            if(board.getColourToMove() == Colour.WHITE){
                System.out.println("Whites' turn");
            } else{
                System.out.println("Blacks' turn");
            }
            Move move = InputCoordinates.inputMove(board, board.getColourToMove(), renderer);

            // make move
            board.makeMove(move);
            // pass move
            board.setActiveColour(colorToMove.opposite());
            gameState = determineGameState(board);
        }
        renderer.render(board);
        System.out.println("Game ended with an outcome of " + gameState);
    }


    private GameState determineGameState(Board board) {
        for(GameStateChecker checker : checkers){
            GameState state = checker.check(board);
            if (state != GameState.ONGOING){
                return state;
            }
        }
    return GameState.ONGOING;
    }
}

