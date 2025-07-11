package main.com.romanov.piece;

import main.com.romanov.board.Board;
import main.com.romanov.Colour;
import main.com.romanov.Coordinates;
import main.com.romanov.CoordinatesShift;

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
