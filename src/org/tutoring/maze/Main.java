package org.tutoring.maze;

public class Main {
    public static void main(String[] args) {
        MazeGenerator generator = new MazeGenerator(11, 21);
        System.out.println(generator.createMaze());
    }
}
