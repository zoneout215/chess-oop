package com.romanov.board;

import com.romanov.Colour;
import com.romanov.Coordinates;
import com.romanov.File;
import com.romanov.piece.PieceFactory;
import java.util.Map;

public class BoardFactory {

    private PieceFactory pieceFactory = new PieceFactory();
    public Board fromFEN(String fen){
        Board board = new Board(fen);
        String[] parts = fen.split(" ");
        String piecePositions = parts[0];
        String activeColour = parts[1];
        String[] fenRows = piecePositions.split("/");
        String castelsAvailability = parts[2];

        // Set active colour from FEN
        board.colourToMove = activeColour.equals("w") ? Colour.WHITE : Colour.BLACK;

        for (int i = 0; i < fenRows.length; i++){
            String row = fenRows[i];
            int rank = 8 - i;
            int fileIndex = 0;
            for(int j = 0; j < row.length(); j++){
                char fenChar = row.charAt(j);

                if (Character.isDigit(fenChar)) {
                    fileIndex += Character.getNumericValue(fenChar);
                } else {
                    File file = File.values()[fileIndex];
                    Coordinates coordinates = new Coordinates(file, rank);
                    board.setPiece(coordinates,
                            pieceFactory.fromFenChar(fenChar, coordinates));
                    fileIndex++;
                }
            }
        }
        parseCastlingRights(castelsAvailability, board);
        return board;
    }

    public Board copy(Board source) {
        Board clone = fromFEN(source.startingFen);
        for (Move move : source.historyMoves){
            clone.makeMove(move);
        }
        return clone;
    }

    
    public void parseCastlingRights(String castlingString, Board board) {
        // Create mapping from FEN chars to your keys
        Map<Character, String> fenToKey = Map.of(
            'K', "WHITE_KINGSIDE",
            'Q', "WHITE_QUEENSIDE", 
            'k', "BLACK_KINGSIDE",
            'q', "BLACK_QUEENSIDE"
        );
        
        // Reset all to false
        board.castlingRights.replaceAll((k, v) -> false);
        
        // Set available castling rights
        for (char c : castlingString.toCharArray()) {
            if (fenToKey.containsKey(c)) {
                board.castlingRights.put(fenToKey.get(c), true);
            }
        }
    }
    
}
