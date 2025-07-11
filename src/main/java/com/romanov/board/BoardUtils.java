package com.romanov.board;

import com.romanov.Coordinates;
import com.romanov.File;

import java.util.ArrayList;
import java.util.List;

public class BoardUtils {
    // допущение что клетки лежат на одной диагонали1
    public static List<Coordinates> getDiagonalsCoordinatesBetween(Coordinates source, Coordinates target){
        int fileShift = source.file.ordinal() < target.file.ordinal() ? 1 : -1;
        int rankShift = source.rank < target.rank ? 1 : -1;
        List<Coordinates> results = new ArrayList<>();
        for (
            int fileIndex = source.file.ordinal() + fileShift,
            rankIndex = source.rank + rankShift;
            fileIndex != target.file.ordinal() && rankIndex != target.rank;
            fileIndex += fileShift, rankIndex += rankShift
        ) {
            results.add(new Coordinates(File.values()[fileIndex], rankIndex));
        }
        return results;
    }
    // допущение что клетки лежат на одной вертикали
    public static List<Coordinates> getVerticalCoordinatesBetween(Coordinates source, Coordinates target){
        int rankShift = source.rank < target.rank ? 1 : -1;
        List<Coordinates> results = new ArrayList<>();
        for (
                int rankIndex = source.rank + rankShift;
                rankIndex != target.rank;
                rankIndex += rankShift
        ) {
            results.add(new Coordinates(source.file, rankIndex));
        }
        return results;
    }
    // допущение что клетки лежат на одной горизонтали

    public static List<Coordinates> getHorizontalCoordinatesBetween(Coordinates source, Coordinates target){
        int fileShift = source.file.ordinal() < target.file.ordinal() ? 1 : -1;
        List<Coordinates> results = new ArrayList<>();
        for (
                int fileIndex = source.file.ordinal() + fileShift;
                fileIndex != target.file.ordinal();
                fileIndex += fileShift
        ) {
            results.add(new Coordinates(File.values()[fileIndex], source.rank));
        }
        return results;
    }
}
