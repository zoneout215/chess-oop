package main.com.romanov.piece;

import main.com.romanov.*;
import main.com.romanov.board.Board;
import main.com.romanov.board.BoardUtils;

import java.util.List;

public abstract class LongRangePiece extends Piece{
    public LongRangePiece(Colour colour, Coordinates coordinates) {
        super(colour, coordinates);
    }

    @Override
    protected boolean isSquareAvailable(Coordinates coordinates, Board board){
        boolean result = super.isSquareAvailable(coordinates, board);
        if (result){
            return isSquareAvailableForAttack(coordinates, board);
        } else  {
            return false;
        }
    }

    @Override
    protected boolean isSquareAvailableForAttack(Coordinates coordinates, Board board) {
        List<Coordinates> coordinatesBetween;
        if (this.coordinates.file == coordinates.file) {
            coordinatesBetween = BoardUtils.getVerticalCoordinatesBetween(
                    this.coordinates, coordinates);
        } else if (this.coordinates.rank.equals(coordinates.rank))  {
            coordinatesBetween = BoardUtils.getHorizontalCoordinatesBetween(
                    this.coordinates, coordinates);
        } else {
            coordinatesBetween = BoardUtils.getDiagonalsCoordinatesBetween(
                    this.coordinates, coordinates);
        }

        for (Coordinates c : coordinatesBetween){
            if (!board.isSquareEmpty(c)){
                return false;
            }
        }
        return true;
    }
}
