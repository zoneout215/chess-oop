package com.romanov.piece;

import com.romanov.CoordinatesShift;

import java.util.HashSet;
import java.util.Set;

public interface IRook {
    default  Set<CoordinatesShift> getRookMoves() {
        Set<CoordinatesShift> result = new HashSet<>();
        // left to right
        for (int horizontalShift = -7; horizontalShift <= 7 ; horizontalShift++) {
            if (horizontalShift == 0) continue;
            result.add( new CoordinatesShift(horizontalShift, 0));
        }
        // bottom top
        for (int verticalShift = -7; verticalShift <= 7; verticalShift++) {
            if (verticalShift == 0) continue;
            result.add( new CoordinatesShift(0, verticalShift));
        }
        return result;
    }
}
