package com.romanov.board;


import com.romanov.Colour;
import com.romanov.Coordinates;
import com.romanov.File;
import com.romanov.piece.*;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Board {
    HashMap<Coordinates, Piece> pieces = new HashMap<>();
    final String startingFen;
    public List<Move> historyMoves = new ArrayList<>();
    public Board(String startingFen){this.startingFen = startingFen;}
    public Colour colourToMove;
    public HashMap<String, Boolean> castlingRights = new HashMap<>(Map.of(
            "WHITE_KINGSIDE", true,
            "WHITE_QUEENSIDE", true,
            "BLACK_KINGSIDE", true,
            "BLACK_QUEENSIDE", true
        ));

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
    public Colour getColourToMove() {return this.colourToMove;}
    public List<Piece> getPiecesByColour(Colour colour){
        List<Piece> result = new ArrayList<>();
        for (Piece piece: pieces.values()){
            if (piece.colour == colour){
                result.add(piece);
            }
        }
        return result;
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
            
            if (lastMovedPiece instanceof Pawn && lastMovedPiece.colour != pawn.colour) {
                // Check if enemy pawn moved 2 squares
                boolean movedTwoSquares = abs(lastMove.from.rank - lastMove.to.rank) == 2;
                
                // Check if pawns are on adjacent files and same rank
                boolean adjacentFiles = abs(lastMove.to.file.ordinal() - pawn.coordinates.file.ordinal()) == 1;
                boolean sameRank = lastMove.to.rank == pawn.coordinates.rank;
                
                // Check if target square is behind the enemy pawn
                boolean correctTargetSquare = to.file.ordinal() == lastMove.to.file.ordinal();
                
                // Check target rank based on pawn color
                boolean correctTargetRank;
                if (pawn.colour == Colour.WHITE) {
                    correctTargetRank = to.rank == lastMove.to.rank + 1; // White moves up
                } else {
                    correctTargetRank = to.rank == lastMove.to.rank - 1; // Black moves down
                }
                
                return movedTwoSquares && adjacentFiles && sameRank && 
                    correctTargetSquare && correctTargetRank;
            }
        }
        return false;
    }

    public boolean isCastlingKingSideAvailable(Colour colour){
        String key = colour == Colour.WHITE ? "WHITE_KINGSIDE" : "BLACK_KINGSIDE";
        if (!castlingRights.getOrDefault(key, false)) {
            return false;
        }
       // Check if squares between king and rook are empty
        int rank = colour == Colour.WHITE ? 1 : 8;
        Coordinates[] squaresToCheck = {
            new Coordinates(File.F, rank),
            new Coordinates(File.G, rank), 
        };
        
        // Check if any square has a piece (should be empty for castling)
        for (Coordinates coord : squaresToCheck) {
            if (!isSquareEmpty(coord)) {
                return false;  // Path is blocked
            }
        }
        
        return true;
    }

    public boolean isCastlingQueenSideAvailable(Colour colour) {
        // Check castling rights first
        String key = colour == Colour.WHITE ? "WHITE_QUEENSIDE" : "BLACK_QUEENSIDE";
        if (!castlingRights.getOrDefault(key, false)) {
            return false;
        }
        
        // Check if squares between king and rook are empty
        int rank = colour == Colour.WHITE ? 1 : 8;
        Coordinates[] squaresToCheck = {
            new Coordinates(File.B, rank),  // b1/b8
            new Coordinates(File.C, rank),  // c1/c8
            new Coordinates(File.D, rank)   // d1/d8
        };
        
        // Check if any square has a piece (should be empty for castling)
        for (Coordinates coord : squaresToCheck) {
            if (!isSquareEmpty(coord)) {
                return false;  // Path is blocked
            }
        }
        
        return true;
    }

    public void removeCastlingKingSide(){
        if (colourToMove == Colour.WHITE) {
            this.castlingRights.put("K", false);
        } else {
            this.castlingRights.put("k", false);
        }
    }
    public void removeCastlingQueenSide(){
        if (colourToMove == Colour.WHITE) {
            this.castlingRights.put("Q", false);
        } else {
            this.castlingRights.put("q", false);
        }
    }

    public void makeMove(Move move){
        move.execute(this);
        historyMoves.add(move);
    }
}

