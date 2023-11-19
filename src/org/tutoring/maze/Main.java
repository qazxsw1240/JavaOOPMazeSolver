package org.tutoring.maze;

public class Main {
    public static void main(String[] args) {
        MazeGenerator generator = new MazeGenerator(51, 101);
        String maze = generator.createMaze();
        System.out.println(maze);
    }
}
