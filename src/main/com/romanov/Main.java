package main.com.romanov;

import main.com.romanov.board.Board;
import main.com.romanov.piece.BoardConsoleRenderer;
import main.com.romanov.board.BoardFactory;

public class Main {
    public static void main(String[] args) {
        Board board = (new BoardFactory()).fromFEN(
                "4k3/p2p2p1/8/1P2P3/5p1P/3KP3/6P1/8 w - - 0 1"
//            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
//            "8/8/1N2r3/3k4/7B/2K5/1R3Q2/4p3 w - - 0 1r"
            //"1n1qk2r/8/4b3/2N3P1/P5P1/R3B3/1P5P/1N2K3 w k - 0 1"
             //"1n1qk2r/8/8/2N3P1/P5P1/4B3/1P5P/RN1QK3 w HQka - 0 1"
            // default rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
            //1nbqk2r/8/8/6P1/P1B3P1/8/1P5P/RN1QK3 w Qk - 0 1

        );
        BoardConsoleRenderer renderer = new BoardConsoleRenderer();
        Game game = new Game(board);
        game.gameLoop();
        }
    }
