package com.romanov.piece;

import com.romanov.*;
import java.util.Set;

public class Rook extends LongRangePiece implements IRook {
    public Rook(Colour colour, Coordinates coordinates){
        super(colour, coordinates);
    }
    @Override
    protected Set<CoordinatesShift> getPieceMoves(){
        return getRookMoves();
    }

}
