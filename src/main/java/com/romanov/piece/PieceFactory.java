package com.romanov.piece;

import com.romanov.Colour;
import com.romanov.Coordinates;

public class PieceFactory {
    public Piece fromFenChar(char fenChar, Coordinates coordinates){
        return switch (fenChar) {
            case 'p' -> new Pawn(Colour.BLACK, coordinates);
            case 'P' -> new Pawn(Colour.WHITE, coordinates);
            case 'r' -> new Rook(Colour.BLACK, coordinates);
            case 'R' -> new Rook(Colour.WHITE, coordinates);
            case 'n' -> new Knight(Colour.BLACK, coordinates);
            case 'N' -> new Knight(Colour.WHITE, coordinates);
            case 'b' -> new Bishop(Colour.BLACK, coordinates);
            case 'B' -> new Bishop(Colour.WHITE, coordinates);
            case 'q' -> new Queen(Colour.BLACK, coordinates);
            case 'Q' -> new Queen(Colour.WHITE, coordinates);
            case 'k' -> new King(Colour.BLACK, coordinates);
            case 'K' -> new King(Colour.WHITE, coordinates);
            default -> throw new RuntimeException("Unknown FEN char!");
        };
    }
}
