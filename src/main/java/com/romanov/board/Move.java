package com.romanov.board;

import com.romanov.Coordinates;

public abstract class Move {
    public final Coordinates from, to;

    public Move(Coordinates from, Coordinates to){
        this.from = from;
        this.to = to;
    }

    public abstract void execute(Board board);
}