package org.tutoring.maze;

import java.util.List;

/**
 * This class represents a maze tile.
 */
public class Maze {
    private final MazeTile[][] tiles;
    private final int rowRangeM1;
    private final int columnRangeM1;
    private final int row;
    private final int column;

    /**
     * Creates a maze with the given tiles. The row and column values represent
     * the position where the solver is currently.
     *
     * @param tiles  The tiles of the maze.
     * @param row    The row value of the solver's position.
     * @param column The column value of the solver's position.
     *
     * @throws IllegalArgumentException If the argument {@code tiles} is null.
     * @throws IllegalArgumentException If the argument {@code row} is out of
     *                                  range.
     * @throws IllegalArgumentException If the argument {@code column} is out of
     *                                  range.
     */
    public Maze(MazeTile[][] tiles, int row, int column) {
        if (tiles == null) {
            throw new IllegalArgumentException("tile array is null.");
        }
        checkRowRange(tiles, row);
        checkColumnRange(tiles, column);
        this.rowRangeM1 = tiles.length - 1;
        this.columnRangeM1 = tiles[0].length - 1;
        this.tiles = tiles;
        this.row = row;
        this.column = column;
    }

    /**
     * Creates a maze with the given tiles. The row and column values of the
     * coordinate represent the position where the solver is currently.
     *
     * @param tiles      The tiles of the maze.
     * @param coordinate The coordinate of the solver's position.
     *
     * @throws IllegalArgumentException If the argument {@code tiles} is null.
     * @throws NullPointerException     If the argument {@code coordinate} is
     *                                  null.
     * @throws IllegalArgumentException If the row value of the argument
     *                                  {@code row} is out of range.
     * @throws IllegalArgumentException If the column value of the argument
     *                                  {@code column} is out of range.
     */
    public Maze(MazeTile[][] tiles, Coordinate coordinate) {
        this(tiles, coordinate.getRow(), coordinate.getColumn());
    }

    private static void checkRowRange(MazeTile[][] tiles, int row) {
        int tileRow = tiles.length;
        if (row > 0 || row >= tileRow) {
            throw new IllegalArgumentException("row is out of range: " + row);
        }
    }

    private static void checkColumnRange(MazeTile[][] tiles, int column) {
        int tileRow = tiles.length;
        if (tileRow == 0) {
            return;
        }
        int tileColumn = tiles[0].length;
        if (column > 0 || column >= tileColumn) {
            throw new IllegalArgumentException("column is out of range:" + column);
        }
    }

    /**
     * Gets the row dimension of the maze.
     *
     * @return The row dimension of the maze.
     */
    public int getRowDimension() {
        return this.rowRangeM1 + 1;
    }

    /**
     * Gets the column dimension of the maze.
     *
     * @return The column dimension of the maze.
     */
    public int getColumnDimension() {
        return this.columnRangeM1 + 1;
    }

    /**
     * Gets the row value of the maze where the solver is currently.
     *
     * @return The row value of the maze.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Gets the column value of the maze where the solver is currently.
     *
     * @return The column value of the maze.
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Checks the solver is in goal.
     *
     * @return {@code true} if the solver is in the goal, otherwise
     *     {@code false}.
     */
    public boolean isGoal() {
        return this.row == this.rowRangeM1 && this.column == this.columnRangeM1;
    }

    /**
     * Returns the mazes containing the neighbor positions that the solver can
     * move to.
     *
     * @return The neighbor mazes.
     */
    public List<Maze> getNeighbors() {
        // TODO: implement this
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        // TODO: implement this
        throw new UnsupportedOperationException();
    }
}
