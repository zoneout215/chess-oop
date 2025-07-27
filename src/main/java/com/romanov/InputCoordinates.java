package com.romanov;

import com.romanov.board.Board;
import com.romanov.board.BoardFactory;
import com.romanov.board.Move;
import com.romanov.board.MoveFactory;
import com.romanov.piece.BoardConsoleRenderer;
import com.romanov.piece.King;
import com.romanov.piece.Piece;
import java.util.Scanner;
import java.util.Set;

public class InputCoordinates {
    private static final  Scanner scanner = new Scanner(System.in);


    public static Coordinates input(){
        while(true){
            System.out.println("Please enter coordinates (ex. a1)");
            String line = scanner.nextLine();

            if (line.length() != 2){
                System.out.println("Invalid format");
                continue;
            }
            char fileChar = line.charAt(0);
            char rankChar =  line.charAt(1);

            if (!Character.isLetter(fileChar)){
                System.out.println("Invalid format");
            }
            if (!Character.isDigit(rankChar)){
                System.out.println("Invalid format");
            }
            int rank = Character.getNumericValue(rankChar);
            if (rank < 1 || rank > 8) {
                System.out.println("Invalid format");
            }
            File file = File.fromChar(fileChar);
            if (file == null){
                System.out.println("Invalid format");
                continue;
            }
            return new Coordinates(file, rank);
        }
    }

    public static Coordinates inputPieceCoordinatesForColour(Colour colour, Board board) {
        while (true) {
            System.out.println("Enter coordinates for a piece to move");
            Coordinates coordinates = input();
            if (board.isSquareEmpty(coordinates)) {
                System.out.println("Empty square");
                continue;
            }
            Piece piece = board.getPiece(coordinates);
            if (piece.colour != colour) {
                System.out.println("Wrong colour");
                continue;
            }
            Set<Coordinates> availableMoveSquares = piece.getAccessibleSquares(board);
            if (availableMoveSquares.isEmpty()) {
                System.out.println("Blocked pieces");
                continue;
            }
            return coordinates;
        }
    }

    public static Coordinates inputAvailableSquare(Set<Coordinates> coordinates) {
        while(true){
            System.out.println("Write the target coordinate for you move");
            Coordinates input = input();
            if(!coordinates.contains(input)){
                System.out.println("Non-available");
                continue;
            }
            return input;
        }
    }
    
    private static boolean checkIfKingUnderAttack(Board board, Colour colour, Move move){
        Board copy = (new BoardFactory()).copy(board);
        copy.makeMove(move);
        // supposition that the King is on the board
        Piece king = copy.getPiecesByColour(colour).stream().filter(piece -> piece instanceof King).findFirst().get();
        return copy.isSquareAttackedByColour(king.coordinates, colour.opposite());
    }

    public static Move inputMove(Board board, Colour colour, BoardConsoleRenderer renderer){
        while(true){
            // input coordinates
            Coordinates sourceCoordinates = InputCoordinates.inputPieceCoordinatesForColour(
                    colour, board);
            Piece piece = board.getPiece(sourceCoordinates);
            
            // check input
            Set<Coordinates> accessibleSquares = piece.getAccessibleSquares(board);
            renderer.render(board, piece);
            Coordinates targetCoordinates = InputCoordinates.inputAvailableSquare(accessibleSquares);
            Move move = MoveFactory.createMove(sourceCoordinates, targetCoordinates, board, piece);

            if (checkIfKingUnderAttack(board, colour, move)) {
                System.out.println("Your king is under attack! Either block or move the king!");
                continue;
            }
            return move;
        }
      }

}
