package com.romanov.piece;

import com.romanov.Colour;
import com.romanov.Coordinates;
import com.romanov.CoordinatesShift;
import com.romanov.board.Board;
import com.romanov.board.BoardUtils;
import com.romanov.board.Move;
import static java.lang.Math.abs;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        if (!board.historyMoves.isEmpty()) {
            Move lastMove = board.historyMoves.getLast();
            if (board.isEnPassantAvaliable(this, coordinates)){
                if (colour == Colour.WHITE && lastMove.to.rank== 5){
                    return true;
                } else if (colour == Colour.BLACK && lastMove.to.rank == 4){
                    return true;
                } else {
                    return false;
                }
            }
        }
       // Classical attacks are handled by getPieceAttacks method 
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
