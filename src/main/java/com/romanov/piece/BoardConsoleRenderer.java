package com.romanov.piece;

import com.romanov.Colour;
import com.romanov.Coordinates;
import com.romanov.File;
import com.romanov.board.Board;
import static java.util.Collections.emptySet;
import java.util.Set;


public class
BoardConsoleRenderer {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE_PIECE_COLOR = "\u001B[97m";
    public static final String ANSI_BLACK_PIECE_COLOR = "\u001B[30m";

    public static final String ANSI_WHITE_SQUARE_BACKGROUND = "\u001B[47m";

    public static final String ANSI_BLACK_SQUARE_BACKGROUND = "\u001B[0;100m";
    public static final String ANSI_HIGHLIGHTED_SQUARE_COLOUR = "\u001B[45m";

    public void render(Board board, Piece pieceToMove) {

        Set<Coordinates> accessibleSquares = emptySet();
        if (pieceToMove != null){
            accessibleSquares = pieceToMove.getAccessibleSquares(board);
        }

        for (int rank = 8; rank >= 1; rank--) {
            String line = "";
            for (File file : File.values()) {
                Coordinates coordinates = new Coordinates(file, rank);
                boolean isHighlighted = accessibleSquares.contains(coordinates);
                if (board.isSquareEmpty(coordinates)) {
                    line += getSpringForEmptySquare(coordinates, isHighlighted);
                } else
                    line += getPieceSprite(board.getPiece(coordinates), isHighlighted);
            }
            line +=  ANSI_RESET;
            System.out.println(line);
        }
    }

    public void render(Board board) {
        render(board, null);
    }

    private String colorizeSprite(
            String sprite, Colour pieceColour,
            boolean isSquareDark, boolean isHighlighted ) {
        String result = sprite;
        if (pieceColour == Colour.WHITE) {
            result = ANSI_WHITE_PIECE_COLOR + result;
        } else {
            result = ANSI_BLACK_PIECE_COLOR + result;
        }
        if (isHighlighted){
            result = ANSI_HIGHLIGHTED_SQUARE_COLOUR + result;
        } else if (isSquareDark) {
            result = ANSI_BLACK_SQUARE_BACKGROUND + result;
        } else {
            result = ANSI_WHITE_SQUARE_BACKGROUND + result;
        }
        return result;
    }

    private String selectUnicodeSpriteForPiece(Piece piece){
        return switch (piece.getClass().getSimpleName()) {
            case "Pawn" -> " ♟ ";
            case "Rook" -> " ♜ ";
            case "Knight" -> " ♞ ";
            case "Bishop" -> " ♝ ";
            case "King" -> " ♔ ";
            case "Queen" -> " ♕ ";
            default -> "";
        };
    }
    private String getSpringForEmptySquare(Coordinates coordinates, boolean isHighlighted) {
        return colorizeSprite("   ", Colour.WHITE, Board.isSquareDark(coordinates), isHighlighted);
    }

    private String getPieceSprite(Piece piece, boolean isHighlighted) {
        return colorizeSprite(
                selectUnicodeSpriteForPiece(piece),
                piece.colour,
                Board.isSquareDark(piece.coordinates),
                isHighlighted
        );
    }

}

