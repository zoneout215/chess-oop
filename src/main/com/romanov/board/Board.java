package main.com.romanov.board;


import main.com.romanov.Colour;
import main.com.romanov.Coordinates;
import main.com.romanov.piece.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static java.lang.Math.abs;

public class Board {
    HashMap<Coordinates, Piece> pieces = new HashMap<>();

    final String startingFen;
    public List<Move> historyMoves = new ArrayList<>();
    public Board(String startingFen){this.startingFen = startingFen;}

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

                            // remove a piece a coordinate.to - 1
        //else remove a piece a coordinate.to + 1
        //           historyMoves.add(move);
        // }
        // problems

        // if (enPassantPossible){
        // 1. first problem -> check if en passant possible:
        // how to solve:
        //  - check the previous move --> Problem: how to know the previous move was done by pawn
        // -  check the coordinate of the near pawn
        // -  check if the moved piece is Pawn
        // -  overload getPieceMoves in Pawn, to pass historical moves
        // - passing Move to the Pawn is hard, since usage of getPieceMoves is not unique
        // - another possibility is to use isSquare available forAttack since it has Board and thus history moves
        // - we need to override the isSquareAvailableForAttack in Pawn as we did in LongRangePiece
        // 2. second problem -> how to eat a peace from the square which is different from the square to move
        // how to solve:
        // -  use isEnPassantMethod
        // - if yes then use Overloaded method
        //}

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
        int n = 3;
        return enPassantCondition && (move.to.file.ordinal() == lastMove.to.file.ordinal());
    }
}

