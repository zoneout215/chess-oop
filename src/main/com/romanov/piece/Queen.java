package main.com.romanov.piece;

import main.com.romanov.Colour;
import main.com.romanov.Coordinates;
import main.com.romanov.CoordinatesShift;

import java.util.HashSet;
import java.util.Set;

public class Queen extends LongRangePiece implements IBishop, IRook{
    public Queen(Colour colour, Coordinates coordinates) {
        super(colour, coordinates);
    }
    @Override
    protected Set<CoordinatesShift> getPieceMoves(){
        Set<CoordinatesShift> moves = getBishopMoves();
        moves.addAll(getRookMoves());
        return moves;
    }
}
