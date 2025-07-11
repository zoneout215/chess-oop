package com.romanov;

import com.romanov.board.Board;
import com.romanov.piece.Piece;
import java.util.List;
import java.util.Set;

public class StaleMateGameStateChecker extends GameStateChecker {
    @Override
    public GameState check(Board board) {
        Colour colour = board.getColourToMove();
        List<Piece> pieces = board.getPiecesByColour(colour);

        for (Piece piece : pieces){
            Set<Coordinates> accessibleSquares = piece.getAccessibleSquares(board);
            if (!accessibleSquares.isEmpty()){
                return GameState.ONGOING;
            }
        }
        return GameState.STALEMATE;
    }
}
