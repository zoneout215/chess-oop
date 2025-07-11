package com.romanov.board;


import com.romanov.Colour;
import com.romanov.Coordinates;
import com.romanov.piece.*;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Board {
    HashMap<Coordinates, Piece> pieces = new HashMap<>();

    final String startingFen;
    public List<Move> historyMoves = new ArrayList<>();
    public Board(String startingFen){this.startingFen = startingFen;}
    public Colour colourToMove;

    public void setActiveColour(Colour colour){
        this.colourToMove = colour;
    }
    public void setPiece(Coordinates coordinates, Piece piece) {
        piece.coordinates = coordinates;
        pieces.put(coordinates, piece);
    }
    public void removePiece(Coordinates coordinates){
        pieces.remove(coordinates);
    }
    public Piece getPiece(Coordinates coordinates){
        return pieces.get(coordinates);
    }
    public Colour getColourToMove(){return this.colourToMove;}


    public List<Piece> getPiecesByColour(Colour colour){
        List<Piece> result = new ArrayList<>();
        for (Piece piece: pieces.values()){
            if (piece.colour == colour){
                result.add(piece);
            }
        }
        return result;
    }

    public void makeMove(Move move){

        // Castles
        // 1. Input should be either 0-0-0/0-0 o-o-o/o-o
        // 2. Move should move two pieces
        // 3. there should be check if there are pieces between a rook and a king
        // - method in King
        // 5. there should be a checking if a rook or a king have moved
        // King cannot castle if:
        // - he is under attack
        // - any of the squares between him and a rook is attacked

        if (!historyMoves.isEmpty() && isEnpassant(move, historyMoves.getLast())) {
            Piece piece = getPiece(move.from);
            removePiece(historyMoves.getLast().to);
            removePiece(move.from);
            setPiece(move.to, piece);
            historyMoves.add(move);
        } else {
                Piece piece = getPiece(move.from);
                removePiece(move.from);
                setPiece(move.to, piece);
                historyMoves.add(move);
            }
        }

    public static boolean isSquareDark(Coordinates coordinates){
        return (((coordinates.file.ordinal() + 1) + coordinates.rank) % 2)==0;
    }
    public boolean isSquareEmpty(Coordinates coordinates){
        return !pieces.containsKey(coordinates);
    }
    public boolean isSquareAttackedByColour(Coordinates coordinates, Colour colour) {
        List<Piece> pieces = getPiecesByColour(colour);
        for (Piece piece: pieces) {
            Set<Coordinates> attackedSquares = piece.getAttackedSquares(this);
            if (attackedSquares.contains(coordinates)){
                return true;
            }
        }
        return false;
    }
    public boolean isEnpassant(Move move, Move lastMove) {
        Piece lastMovedPiece = getPiece(lastMove.to);
        Piece pieceToMove = getPiece((move.from));
        int distanceToAttackedPawn = abs(lastMove.to.file.ordinal() - move.from.file.ordinal());
        boolean enPassantCondition = (
                lastMovedPiece instanceof Pawn
                        && abs(lastMove.from.rank - lastMove.to.rank) == 2
                        && (distanceToAttackedPawn == 1)
                        && lastMove.from.file.ordinal() == move.to.file.ordinal()
                        && lastMovedPiece.colour != pieceToMove.colour
        );
        return enPassantCondition && (move.to.file.ordinal() == lastMove.to.file.ordinal());
    }
}

