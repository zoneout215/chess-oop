package com.romanov.board;

import com.romanov.Colour;
import com.romanov.Coordinates;
import com.romanov.File;
import com.romanov.piece.*;

import static java.lang.Math.abs;

public class MoveFactory {
    public static Move createMove(Coordinates from, Coordinates to, Board board, Piece piece) {
        // Detect castling
        // TODO add the check of the availability of castling, if the king or neeed squares
        //  needed are under attack. or if there are pieces in between
//        if (isCastling(from, to, board)) {
//            return createCastlingMove(from, to, board);
//        }
        if (board.isEnPassantAvaliable(piece, to)){
            return new MoveEnPassant(from, to);
        }
        // Default to standard move
        return new MoveStandard(from, to);
    }
    
    private static MoveCastles createCastlingMove(Coordinates kingFrom, Coordinates kingTo, Board board) {
        // deside which way to castle 
        boolean kingSide = kingTo.file.ordinal() > kingFrom.file.ordinal(); // pass instead of definition
        int rank;
        File fileFrom, fileTo;
        if (kingSide){
            fileFrom = File.H;
            fileTo = File.F;
        } else{
            fileFrom = File.A;
            fileTo = File.D;
        }

        if (board.colourToMove == Colour.WHITE){
            rank = 1;
        } else{
            rank = 8;
        }
        Coordinates rookFrom = new Coordinates(fileFrom, rank);
        Coordinates rookTo = new Coordinates(fileTo, rank); 
        return new MoveCastles(kingFrom, kingTo, rookFrom, rookTo);
    }

//    public static isCastling(Coordinates from,Coordinates to, Board board){
//        piece instanceof King && Math.abs(from.file.ordinal() - to.file.ordinal()) == 2
//    }
}