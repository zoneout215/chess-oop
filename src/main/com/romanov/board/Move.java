package main.com.romanov.board;

import main.com.romanov.Coordinates;

public class Move {
    public final Coordinates from, to;

    public Move(Coordinates from, Coordinates to){
        this.from = from;
        this.to = to;
    }
}
