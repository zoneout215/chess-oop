package main.com.romanov.piece;

import main.com.romanov.board.Board;
import main.com.romanov.Colour;
import main.com.romanov.Coordinates;
import main.com.romanov.CoordinatesShift;
import main.com.romanov.board.BoardUtils;
import main.com.romanov.board.Move;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.abs;

public class Pawn extends Piece {
    public Pawn(Colour colour, Coordinates coordinates){
        super(colour, coordinates);
    }
    @Override
    protected Set<CoordinatesShift> getPieceMoves(){
        Set<CoordinatesShift> results = new HashSet<>();
        if (colour == Colour.WHITE) {
            results.add(new CoordinatesShift(0, 1));
            if (coordinates.rank == 2){
                results.add(new CoordinatesShift(0, 2));
            }
            // attacking squares
            results.add(new CoordinatesShift(-1, 1));
            results.add(new CoordinatesShift(1, 1));
        } else {
            results.add(new CoordinatesShift(0, -1));
            if (coordinates.rank == 7){
                results.add(new CoordinatesShift(0, -2));
            }
            // attacking squares
            results.add(new CoordinatesShift(1, -1));
            results.add(new CoordinatesShift(-1, -1));
        }
        return results;
    }


    @Override
    protected Set<CoordinatesShift> getPieceAttacks() {
        // classic attacks
        Set<CoordinatesShift> results = new HashSet<>();
        if (colour == Colour.WHITE) {
            results.add(new CoordinatesShift(-1, 1));
            results.add(new CoordinatesShift(1, 1));
        } else {
            results.add(new CoordinatesShift(1, -1));
            results.add(new CoordinatesShift(-1, -1));
        }
        return results;
    }

    @Override
    protected boolean isSquareAvailable(Coordinates coordinates, Board board){
        boolean result = super.isSquareAvailable(coordinates, board);
        if (result){
            return isSquareAvailableForAttack(coordinates, board) || isSquareAvailableForMove(coordinates, board);
        } else {
            return false;
        }
    }

    @Override
    protected boolean isSquareAvailableForAttack(Coordinates coordinates, Board board){
        // EnPassant
        if (!board.historyMoves.isEmpty()) {
            Move lastMove = board.historyMoves.getLast();
            Piece lastMovedPiece = board.getPiece(lastMove.to);
            int distanceToAttackedPawn = abs(lastMove.to.file.ordinal() - this.coordinates.file.ordinal());
            boolean enPassantCondition = (
                    lastMovedPiece instanceof Pawn
                    && abs(lastMove.from.rank - lastMove.to.rank) == 2
                    && (distanceToAttackedPawn == 1)
                    && lastMove.from.file.ordinal() == coordinates.file.ordinal()
                    && lastMovedPiece.colour != this.colour
            );
            if (enPassantCondition){
                if (colour == Colour.WHITE && lastMove.to.rank== 5){
                    return true;
                } else if (colour == Colour.BLACK && lastMove.to.rank == 4){
                    return true;
                } else {
                    return false;
                }
            }
        };
        return false;
    }

    protected boolean isSquareAvailableForMove(Coordinates coordinates, Board board) {
        if (this.coordinates.file == coordinates.file){
            int rankShift = abs(this.coordinates.rank - coordinates.rank);

            if (rankShift == 2) {
                List<Coordinates> coordinatesBetween = BoardUtils.getVerticalCoordinatesBetween(
                    this.coordinates, coordinates);
                return (board.isSquareEmpty(coordinatesBetween.getFirst())) && board.isSquareEmpty(coordinates);
            } else {

                return (board.isSquareEmpty(coordinates));
            }
        } else {
            if (board.isSquareEmpty(coordinates)){
                return false;
            } else {
                return board.getPiece(coordinates).colour != colour;
            }
        }
    }
}
