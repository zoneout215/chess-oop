package com.zhukovsd.piece;

import com.zhukovsd.Color;
import com.zhukovsd.Coordinates;

import java.util.Set;

public class Rook extends Piece {
    public Rook(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        return null;
    }
}
