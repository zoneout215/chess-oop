package com.romanov.piece;

import com.romanov.Colour;
import com.romanov.Coordinates;
import com.romanov.CoordinatesShift;

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
