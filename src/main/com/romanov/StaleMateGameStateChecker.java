package main.com.romanov;

import main.com.romanov.board.Board;
import main.com.romanov.piece.Piece;

import java.util.List;
import java.util.Set;

public class StaleMateGameStateChecker extends GameStateChecker {
    @Override
    public GameState check(Board board, Colour colour) {
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
