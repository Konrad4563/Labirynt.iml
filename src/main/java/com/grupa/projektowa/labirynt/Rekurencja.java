package com.grupa.projektowa.labirynt;

import java.util.ArrayList;
import java.util.List;

public class Rekurencja {
    private int[][] maze;
    private int startX;
    private int startY;
    private boolean[][] visited;
    private List<int[]> path;
    private List<int[]> bestPath;
    private double shortestDistance;

    public Rekurencja(int[][] maze, int startX, int startY) {
        this.maze = maze;
        this.startX = startX;
        this.startY = startY;
        this.visited = new boolean[maze.length][maze[0].length];
        this.path = new ArrayList<>();
        this.bestPath = null;
        this.shortestDistance = Double.POSITIVE_INFINITY;
    }

    public int[][] solve() {
        int[][] mazeWithPath = new int[maze.length][maze[0].length];
        explore(startX, startY);

        if (bestPath != null) {
            mazeWithPath = printPath(bestPath);
        } else {
            new AlertBox().wyswietl("Błąd labiryntu", "Nie znaleziono drogi", false);
        }

        return mazeWithPath;
    }

    private void explore(int x, int y) {
        if (x < 0 || x >= maze.length || y < 0 || y >= maze[0].length) {
            return; // out of bounds
        }

        if (visited[x][y] || maze[x][y] == 0) {
            return; // already visited or wall
        }

        if (maze[x][y] == 3) {
            path.add(new int[]{x, y});
            double distance = calculatePathDistance(path);

            if (distance < shortestDistance) {
                shortestDistance = distance;
                bestPath = new ArrayList<>(path);
            }

            path.remove(path.size() - 1);
            return; // found the end
        }

        visited[x][y] = true;
        path.add(new int[]{x, y});

        explore(x + 1, y); // go down
        explore(x, y + 1); // go right
        explore(x - 1, y); // go up
        explore(x, y - 1); // go left

        visited[x][y] = false; // mark as unvisited
        path.remove(path.size() - 1); // backtrack
    }

    private double calculatePathDistance(List<int[]> path) {
        double distance = 0.0;

        for (int i = 0; i < path.size() - 1; i++) {
            int[] current = path.get(i);
            int[] next = path.get(i + 1);
            distance += calculateEuclideanDistance(current, next);
        }

        return distance;
    }

    private double calculateEuclideanDistance(int[] point1, int[] point2) {
        int dx = point2[0] - point1[0];
        int dy = point2[1] - point1[1];
        return Math.sqrt(dx * dx + dy * dy);
    }

    private int[][] printPath(List<int[]> path) {
        int[][] mazeWithPath = new int[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                mazeWithPath[i][j] = maze[i][j];
            }
        }
        for (int[] pos : path) {
            mazeWithPath[pos[0]][pos[1]] = 4;
        }
        return mazeWithPath;
    }
}
