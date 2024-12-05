package main.com.romanov.piece;

import main.com.romanov.*;

import java.util.HashSet;
import java.util.List;
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
