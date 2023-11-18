package org.tutoring.maze;

import java.util.List;

/**
 * This class offers the maze solving steps in a maze solving algorithm.
 */
public class MazeSolver {
    private final Maze maze;

    /**
     * Creates a maze solver with the given maze.
     *
     * @param maze The maze to solve.
     *
     * @throws IllegalArgumentException If the argument {@code maze} is null.
     */
    public MazeSolver(Maze maze) {
        if (maze == null) {
            throw new IllegalArgumentException("maze is null.");
        }
        this.maze = maze;
    }

    /**
     * Returns the maze solving steps that this solver proceeds.
     *
     * @return The maze solving steps.
     */
    public List<Maze> getMazeSolvingSteps() {
        // TODO: implement this...
        throw new UnsupportedOperationException();
    }
}
