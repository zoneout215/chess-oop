package com.romanov.board;

import com.romanov.Coordinates;
import com.romanov.File;
import com.romanov.piece.*;

public class MoveStandard extends Move{
    public MoveStandard(Coordinates from, Coordinates to){
        super(from, to);
    }
    @Override
    public void execute(Board board){
        Piece piece = board.getPiece(from);
        board.setPiece(to, piece);
        board.removePiece(from);
        if (piece instanceof King){
            board.removeCastlingKingSide();
            board.removeCastlingQueenSide();
        }
        if (piece instanceof Rook && piece.coordinates.file == File.H){
            board.removeCastlingKingSide();
        } else if (piece instanceof Rook && piece.coordinates.file == File.A){
            board.removeCastlingQueenSide();
        }
    };    
}
