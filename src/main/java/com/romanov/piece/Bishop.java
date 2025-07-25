package com.romanov.piece;

import com.romanov.*;
import java.util.Set;

public class Bishop extends LongRangePiece implements IBishop{
    public Bishop(Colour colour, Coordinates coordinates) {
        super(colour, coordinates);
    }

    @Override
    protected Set<CoordinatesShift> getPieceMoves(){
        return getBishopMoves();
    }

}
