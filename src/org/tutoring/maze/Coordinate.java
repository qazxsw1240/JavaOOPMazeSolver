package org.tutoring.maze;

/**
 * This class represents a coordinate which contains a row value and a column
 * value.
 *
 * @author qazxsw1240
 */
public class Coordinate {
    /**
     * A unit coordinate instance the row and column values of which are (0, 0).
     */
    public static final Coordinate UNIT_COORDINATE = new Coordinate(0, 0);

    private final int row;
    private final int column;

    /**
     * Creates a coordinate instance with the given arguments.
     *
     * @param row    The row value.
     * @param column The column value.
     */
    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Gets the row value.
     *
     * @return The row value.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Gets the column value.
     *
     * @return The column value.
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Returns a new coordinate the row value of which is the sum of the row
     * value of this coordinate and the given offset.
     *
     * @param offset The offset which will be added to the row value of this
     *               coordinate.
     *
     * @return A new coordinate the row value of which is the sum of the row
     *     value of this coordinate and the given offset.
     */
    public Coordinate addRowOffset(int offset) {
        return new Coordinate(this.row + offset, this.column);
    }

    /**
     * Returns a new coordinate the column value of which is the sum of the
     * column value of this coordinate and the given offset.
     *
     * @param offset The offset which will be added to the column value of this
     *               coordinate.
     *
     * @return A new coordinate the column value of which is the sum of the
     *     column value of this coordinate and the given offset.
     */
    public Coordinate addColumnOffset(int offset) {
        return new Coordinate(this.row, this.column + offset);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Coordinate)) {
            return false;
        }
        Coordinate castObj = (Coordinate) obj;
        return this.row == castObj.row && this.column == castObj.column;
    }

    @Override
    public String toString() {
        return "(row:" + this.row + ", column:" + this.column + ")";
    }
}
