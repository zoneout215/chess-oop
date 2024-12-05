package main.com.romanov.piece;

import main.com.romanov.CoordinatesShift;

import java.util.HashSet;
import java.util.Set;

public interface IBishop {
    default Set<CoordinatesShift> getBishopMoves() {
        Set<CoordinatesShift> result = new HashSet<>();
        // bottom left to top right
        for (int Diagonal = -7; Diagonal <= 7 ; Diagonal++) {
            if (Diagonal == 0) continue;
            result.add( new CoordinatesShift(Diagonal, Diagonal));
        }
        // top left to bottom right
        for (int backDiagonal = -7; backDiagonal <= 7; backDiagonal++) {
            if (backDiagonal == 0) continue;
            result.add( new CoordinatesShift(backDiagonal, -backDiagonal));
        }
        return result;
    }
}

