package com.romanov;

import com.romanov.board.Board;
import com.romanov.board.BoardFactory;
import com.romanov.board.Move;
import com.romanov.piece.King;
import com.romanov.piece.Piece;
import java.util.List;
import java.util.Set;

public class CheckmateGameStateChecker extends GameStateChecker {
    @Override
    public GameState check(Board board) {
        // suppose that King is on the board
        Colour colour = board.getColourToMove();
        Piece king = board.getPiecesByColour(colour).stream().filter(
                piece -> piece instanceof King).findFirst().get();
        boolean s1 = board.isSquareAttackedByColour(king.coordinates, colour.opposite());
        if (!board.isSquareAttackedByColour(king.coordinates, colour.opposite())){
            return GameState.ONGOING;
        }

        List<Piece> pieces = board.getPiecesByColour(colour);
        for(Piece piece : pieces) {
            Set<Coordinates> accessibleSquares = piece.getAccessibleSquares(board);

            for (Coordinates coordinates : accessibleSquares) {
                Board clone = new BoardFactory().copy(board);
                clone.makeMove(new Move(piece.coordinates, coordinates));
                Piece clonedKing = clone.getPiecesByColour(colour).stream().filter(
                        p -> p instanceof King).findFirst().get();
                if (!clone.isSquareAttackedByColour(clonedKing.coordinates, colour.opposite())){
                    return GameState.ONGOING;
                }
            }
        }
        if (colour == Colour.WHITE){
            return GameState.CHECKMATE_TO_WHITE;
        } else
            return GameState.CHECKMATE_TO_BLACK;
    }
}