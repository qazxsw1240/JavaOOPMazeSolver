package org.tutoring.maze;

import java.awt.*;
import java.util.List;

/**
 * This class represents a maze.
 */
public interface Maze {
    /**
     * A charset represent the roads in the mazes.
     */
    public static final char ROAD = '0';
    /**
     * A charset represent the walls in the mazes.
     */
    public static final char WALL = '1';
    /**
     * A charset represent the positions of the solvers in the mazes.
     */
    public static final char CURRENT = '2';

    /**
     * Gets the row dimension of the maze.
     *
     * @return The row dimension of the maze.
     */
    public int getRowDimension();

    /**
     * Gets the column dimension of the maze.
     *
     * @return The column dimension of the maze.
     */
    public int getColumnDimension();

    /**
     * Gets the row value of the maze where the solver is currently.
     *
     * @return The row value of the maze.
     */
    public int getRow();

    /**
     * Gets the column value of the maze where the solver is currently.
     *
     * @return The column value of the maze.
     */
    public int getColumn();

    /**
     * Checks the solver is in goal.
     *
     * @return {@code true} if the solver is in the goal, otherwise
     *     {@code false}.
     */
    public boolean isGoal();

    /**
     * Returns the mazes containing the neighbor positions that the solver can
     * move to.
     *
     * @return The neighbor mazes.
     */
    public List<Maze> getNeighbors();

    /**
     * Returns the array of the maze, the color of which represent its walls,
     * roads, and the solver's position.
     *
     * @return The color array representing the maze.
     */
    public Color[][] getColorArray();
}
