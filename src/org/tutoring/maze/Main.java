package org.tutoring.maze;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        MazeGenerator generator = new MazeGenerator(51, 51);
        Maze maze = generator.createMaze();
        MazeVisualizer visualizer = new MazeVisualizer(List.of(maze));
        visualizer.start();
    }
}
