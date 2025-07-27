
package com.romanov.board;

import com.romanov.Coordinates;
import com.romanov.piece.Piece;

public class MoveEnPassant extends Move {

    public MoveEnPassant(Coordinates from, Coordinates to){
        super(from, to);
    }

    @Override
    public void execute(Board board){
        // Remove passed pawn
        board.removePiece(board.historyMoves.getLast().to);
        // Place passing pawn
        board.setPiece(to, board.getPiece(from));
        board.removePiece(from);
    }
    }
