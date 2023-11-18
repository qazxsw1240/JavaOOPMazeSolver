package org.tutoring.maze;

/**
 * This enum class represents the tile of a maze.
 *
 * @author qazxsw1240
 */
public enum MazeTile {
    WALL('1'),
    ROAD('0');

    private final char code;

    private MazeTile(char code) {
        this.code = code;
    }

    /**
     * Get the char value of this tile.
     *
     * @return The char value.
     */
    public char toChar() {
        return this.code;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }
}
