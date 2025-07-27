package com.romanov.board;

import com.romanov.Coordinates;

public class MoveCastles extends Move {
    public final Coordinates rookFrom, rookTo;

    public MoveCastles(Coordinates kingFrom,
                       Coordinates kingTo,
                       Coordinates rookFrom,
                       Coordinates rookTo) {
        super(kingFrom, kingTo);

        this.rookFrom = rookFrom;
        this.rookTo = rookTo;
    }

    @Override
    public void execute(Board board){
        // King moves
        board.setPiece(to, board.getPiece(from));
        board.removePiece(from);
        // rookMoves
        board.setPiece(rookTo, board.getPiece(rookFrom));
        board.removePiece(rookFrom);
        board.removeCastlingKingSide();
        board.removeCastlingQueenSide();
    }
}

