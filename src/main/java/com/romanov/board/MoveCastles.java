package com.romanov.board;

import com.romanov.Coordinates;

public class MoveCastles {
    public final Coordinates kingFrom, kingTo, rookFrom, rookTo;

    public MoveCastles(Coordinates kingFrom,
                       Coordinates kingTo,
                       Coordinates rookFrom,
                       Coordinates rookTo) {
        this.kingFrom = kingFrom;
        this.kingTo = kingTo;
        this.rookFrom = rookFrom;
        this.rookTo = rookTo;
    }
}
