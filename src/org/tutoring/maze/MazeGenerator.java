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

    private static void initializeTiles(MazeTile[][] tiles) {
        for (MazeTile[] rowTiles : tiles) {
            Arrays.fill(rowTiles, MazeTile.WALL);
        }
    }

    /**
     * Returns the row value of the mazes which this generator creates.
     *
     * @return The row value.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Returns the column value of the mazes which this generator creates.
     *
     * @return The column value.
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Creates a new maze with the row and column values of this generator.
     *
     * @return A new maze created randomly.
     */
    public Maze createMaze() {
        return createMaze(System.currentTimeMillis());
    }

    /**
     * Creates a new maze with the row and column values of this generator in
     * the given random seed.
     *
     * @return A new maze created randomly.
     */
    public Maze createMaze(long seed) {
        MazeTile[][] tiles = new MazeTile[this.row][this.column];
        Random random = new Random(seed);
        initializeTiles(tiles);
        fillTiles(tiles, random, 0, 0);
        return new Maze(tiles, 0, 0);
    }

    private void fillTiles(MazeTile[][] tiles, Random random, int row, int column) {
        tiles[row][column] = MazeTile.ROAD;
        List<Coordinate> coordinates = getNeighbors(row, column);
        Collections.shuffle(coordinates, random);
        for (Coordinate coordinate : coordinates) {
            int coordRow = coordinate.getRow();
            int coordColumn = coordinate.getColumn();
            if (tiles[coordRow][coordColumn] == MazeTile.ROAD) {
                continue;
            }
            connectTiles(tiles, row, column, coordRow, coordColumn);
            fillTiles(tiles, random, coordRow, coordColumn);
        }
    }

    private void connectTiles(MazeTile[][] tiles, int c1row, int c1column, int c2row, int c2column) {
        if (c1row == c2row) {
            int lower = Math.min(c1column, c2column);
            int upper = Math.max(c1column, c2column);
            for (int i = lower; i <= upper; i++) {
                tiles[c1row][i] = MazeTile.ROAD;
            }
            return;
        }
        if (c1column == c2column) {
            int lower = Math.min(c1row, c2row);
            int upper = Math.max(c1row, c2row);
            for (int i = lower; i <= upper; i++) {
                tiles[i][c1column] = MazeTile.ROAD;
            }
            return;
        }
        throw new IllegalStateException(String.format("Cannot connect (%d, %d) to (%d, %d)", c1row, c1column, c2row, c2column));
    }

    private List<Coordinate> getNeighbors(int row, int column) {
        List<Coordinate> coordinates = new ArrayList<>(MazeGeneratorUtils.NEIGHBOR_OFFSET_SIZE);
        for (int i = 0; i < MazeGeneratorUtils.NEIGHBOR_OFFSET_SIZE; i++) {
            int newRow = row + MazeGeneratorUtils.NEIGHBOR_ROW_OFFSETS[i];
            int newColumn = column + MazeGeneratorUtils.NEIGHBOR_COLUMN_OFFSETS[i];
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
