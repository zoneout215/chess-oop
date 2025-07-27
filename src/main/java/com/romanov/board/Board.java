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
    
    public boolean isEnPassantAvaliable(Piece pawn, Coordinates to){
        if (!historyMoves.isEmpty()) {
            Move lastMove = historyMoves.getLast();
            Piece lastMovedPiece = getPiece(lastMove.to);
            int fileDifferenceToAttackedPawn = abs(lastMove.to.file.ordinal() - to.file.ordinal());
            boolean enPassantCondition = (
                    lastMovedPiece instanceof Pawn
                            && (fileDifferenceToAttackedPawn == 1) // Pawns are on the adjacent files
                            && abs(lastMove.to.rank - pawn.coordinates.rank) == 1 // The attacking and attacked pawns are on the same rank
                            && abs(lastMove.from.rank - lastMove.to.rank) == 2 // Passed pawn moved 2 squares
                            && lastMove.from.file.ordinal() == to.file.ordinal() // Passing pawn moves behind the passed Pawn
                            && lastMovedPiece.colour != colourToMove // Pawns are the opposite colour
            );
            return enPassantCondition;
        }
        return false;
    }
}

