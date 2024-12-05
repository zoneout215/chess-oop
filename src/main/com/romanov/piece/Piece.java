package main.com.romanov.piece;

import main.com.romanov.Colour;
import main.com.romanov.Coordinates;
import main.com.romanov.CoordinatesShift;
import main.com.romanov.board.Board;

import java.util.HashSet;
import java.util.Set;

abstract public class Piece {
    public final Colour colour;
    public Coordinates coordinates;

    public Piece(Colour colour, Coordinates coordinates) {
        this.colour = colour;
        this.coordinates = coordinates;
    }
    public Set<Coordinates> getAccessibleSquares(Board board) {
        Set<Coordinates> result = new HashSet<>();

        for (CoordinatesShift shift : getPieceMoves()) {
            if (coordinates.canShift(shift)) {
                Coordinates newCoordinates = coordinates.shift(shift);
                if (isSquareAvailable(newCoordinates, board)) {
                    result.add(newCoordinates);
                }
            }
        }
        return result;
    }

    protected boolean isSquareAvailable(Coordinates  coordinates, Board board) {
        return (board.isSquareEmpty(coordinates) || board.getPiece(coordinates).colour != colour);

    }
    protected abstract Set<CoordinatesShift> getPieceMoves();
    protected  Set<CoordinatesShift> getPieceAttacks(){
        return getPieceMoves();
    }
    public Set<Coordinates> getAttackedSquares(Board board) {
        Set<CoordinatesShift> pieceAttacks =  getPieceAttacks();
        Set<Coordinates> result = new HashSet<>();

        for (CoordinatesShift pieceAttack : pieceAttacks){
            if (coordinates.canShift(pieceAttack)){
                Coordinates shiftedCoordinates= coordinates.shift(pieceAttack);
                if (isSquareAvailableForAttack(shiftedCoordinates, board)){
                  result.add(shiftedCoordinates);
                }
            }
        }
        return result;
    }

    protected boolean isSquareAvailableForAttack(Coordinates shiftedCoordinates, Board board){
        return true;
    }
}

