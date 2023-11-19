package org.tutoring.maze;

import java.util.*;

/**
 * This class is a factory class to create maze instances.
 *
 * @author qazxsw1240
 */
public class MazeGenerator {
    private final int row;
    private final int column;

    /**
     * Creates a maze generator which creates the mazes the row and column
     * values of which are the given arguments.
     *
     * @param row    The row value of the mazes.
     * @param column The column value of the mazes.
     *
     * @throws IllegalArgumentException If the row and column values are not odd
     *                                  numbers.
     */
    public MazeGenerator(int row, int column) {
        if (row < 0 || (row & 1) != 1) {
            throw new IllegalArgumentException("row must be a positive odd number: " + row);
        }
        if (column < 0 || (column & 1) != 1) {
            throw new IllegalArgumentException("column must be a positive odd number: " + column);
        }
        this.row = row;
        this.column = column;
    }

    private static void initializeTiles(char[][] cells) {
        for (char[] rowCells : cells) {
            Arrays.fill(rowCells, Maze.WALL);
        }
    }

    /**
     * Creates a new maze with the row and column values of this generator.
     *
     * @return A new maze created randomly.
     */
    public String createMaze() {
        return createMaze(System.currentTimeMillis());
    }

    /**
     * Creates a new maze with the row and column values of this generator in
     * the given random seed.
     *
     * @return A new maze created randomly.
     */
    public String createMaze(long seed) {
        char[][] cells = new char[this.row][this.column];
        Random random = new Random(seed);
        initializeTiles(cells);
        fillCells(cells, random, Coordinate.UNIT_COORDINATE);
        return createMaze(cells);
    }

    private String createMaze(char[][] chars) {
        String[] lines = new String[chars.length];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new String(chars[i]);
        }
        return String.join("\n", lines);
    }

    private void fillCells(char[][] cells, Random random, Coordinate coordinate) {
        cells[coordinate.getRow()][coordinate.getColumn()] = Maze.ROAD;
        List<Coordinate> coordinates = getNeighbors(coordinate);
        Collections.shuffle(coordinates, random);
        for (Coordinate coord : coordinates) {
            int coordRow = coord.getRow();
            int coordColumn = coord.getColumn();
            if (cells[coordRow][coordColumn] == Maze.ROAD) {
                continue;
            }
            connectCells(cells, coordinate, coord);
            fillCells(cells, random, coord);
        }
    }

    private void connectCells(char[][] cells, Coordinate c1, Coordinate c2) {
        if (c1.getRow() == c2.getRow()) {
            int row = c1.getRow();
            int lower = Math.min(c1.getColumn(), c2.getColumn());
            int upper = Math.max(c1.getColumn(), c2.getColumn());
            for (int i = lower; i <= upper; i++) {
                cells[row][i] = Maze.ROAD;
            }
            return;
        }
        if (c1.getColumn() == c2.getColumn()) {
            int column = c1.getColumn();
            int lower = Math.min(c1.getRow(), c2.getRow());
            int upper = Math.max(c1.getRow(), c2.getRow());
            for (int i = lower; i <= upper; i++) {
                cells[i][column] = Maze.ROAD;
            }
            return;
        }
        throw new IllegalStateException(String.format("Cannot connect %s to %s", c1, c2));
    }

    private List<Coordinate> getNeighbors(Coordinate coordinate) {
        List<Coordinate> coordinates = new ArrayList<>(MazeGeneratorUtils.NEIGHBOR_OFFSET_SIZE);
        for (int i = 0; i < MazeGeneratorUtils.NEIGHBOR_OFFSET_SIZE; i++) {
            int newRow = coordinate.getRow() + MazeGeneratorUtils.NEIGHBOR_ROW_OFFSETS[i];
            int newColumn = coordinate.getColumn() + MazeGeneratorUtils.NEIGHBOR_COLUMN_OFFSETS[i];
            if (isRowRange(newRow) && isColumnRange(newColumn)) {
                coordinates.add(new Coordinate(newRow, newColumn));
            }
        }
        return coordinates;
    }

    private boolean isRowRange(int row) {
        return 0 <= row && row < this.row;
    }

    private boolean isColumnRange(int column) {
        return 0 <= column && column < this.column;
    }

    private static class MazeGeneratorUtils {
        private static final int NEIGHBOR_OFFSET_SIZE = 4;
        private static final int[] NEIGHBOR_ROW_OFFSETS = new int[]{-2, 2, 0, 0};
        private static final int[] NEIGHBOR_COLUMN_OFFSETS = new int[]{0, 0, -2, 2};
    }
}
