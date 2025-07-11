package com.romanov.piece;

import com.romanov.Colour;
import com.romanov.Coordinates;
import com.romanov.CoordinatesShift;

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

public class Knight extends Piece {
    public Knight(Colour colour, Coordinates coordinates){
        super(colour, coordinates);
    }

    @Override
    protected Set<CoordinatesShift> getPieceMoves(){
        return new HashSet<>(Arrays.asList(
            // Up
            new CoordinatesShift(-1,2 ),
            new CoordinatesShift(1, 2),
            // Right
            new CoordinatesShift(2,1),
            new CoordinatesShift(2, -1),
            // Down
            new CoordinatesShift(1, -2),
            new CoordinatesShift(-1,-2),
            // Left
            new CoordinatesShift(-2, -1),
            new CoordinatesShift(-2, 1)
        ));
    }
}
