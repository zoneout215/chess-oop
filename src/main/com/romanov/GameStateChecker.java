package main.com.romanov;

import main.com.romanov.board.Board;

public abstract class  GameStateChecker {
    public abstract GameState check(Board board);
}

