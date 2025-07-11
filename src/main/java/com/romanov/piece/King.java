package com.romanov.piece;

import com.romanov.Colour;
import com.romanov.Coordinates;
import com.romanov.CoordinatesShift;
import com.romanov.board.Board;
import java.util.HashSet;
import java.util.Set;

public class King extends Piece{
    public King(Colour colour, Coordinates coordinates){
        super(colour, coordinates);
    }
    @Override
    protected Set<CoordinatesShift> getPieceMoves(){
        Set<CoordinatesShift> result = new HashSet<>();
        for (int fileShift = -1; fileShift <= 1; fileShift++) {
            for (int rankShift = -1; rankShift <= 1; rankShift++) {
                if ((fileShift == 0) && (rankShift == 0)){
                    continue;
                }
                result.add(new CoordinatesShift(fileShift, rankShift));
            }
        }
        return result;
    }

    @Override
    protected boolean isSquareAvailable(Coordinates coordinates, Board board) {
        boolean result = super.isSquareAvailable(coordinates, board);
        if (result){
            return !board.isSquareAttackedByColour(coordinates, colour.opposite());
        }
        return false;
    }

}
