package com.grupa.projektowa.labirynt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AntAlgorithm {
    private int[][] maze;
    private int rows;
    private int cols;
    private int numAnts;
    private double[][] pheromones;
    private Random random;
    private List<int[]> bestPath; // Najkrótsza ścieżka
    private double pheromoneWeight;
    private double shortestDistance; // Długość najkrótszej ścieżki
    private double pheromoneEvaporationRate; // Współczynnik parowania feromonów

    public AntAlgorithm(int[][] maze, int numAnts, double pheromoneWeight, double pheromoneEvaporationRate) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
        this.numAnts = numAnts;
        this.pheromones = new double[rows][cols];
        this.random = new Random();
        this.bestPath = null;
        this.pheromoneWeight = pheromoneWeight;
        this.shortestDistance = Double.POSITIVE_INFINITY;
        this.pheromoneEvaporationRate = pheromoneEvaporationRate;
    }

    public int[][] findShortestPath() {
        for (int ant = 0; ant < numAnts; ant++) {
            List<int[]> path = generatePath();
            double distance = calculatePathDistance(path);

            if (distance < shortestDistance) {
                shortestDistance = distance;
                bestPath = path;
            }

            updatePheromones(path, distance);
        }

        // Parowanie feromonów na całej planszy
        evaporatePheromones();

        // Aktualizacja feromonów na najlepszej ścieżce
        updatePheromones(bestPath, shortestDistance);

        return convertPathToArray(bestPath);
    }

    private List<int[]> generatePath() {
        int[] start = findStart();
        int[] exit = findExit();
        List<int[]> path = new ArrayList<>();
        int[] current = start;
        path.add(current);

        while (!Arrays.equals(current, exit)) {
            int[] next = getNextMove(current);
            path.add(next);
            current = next;

            // Przycinanie (pruning) - jeśli ścieżka jest dłuższa niż dotychczas znaleziona najkrótsza ścieżka, przerwij generowanie
            if (bestPath != null && path.size() >= bestPath.size()) {
                return path;
            }
        }

        return path;
    }

    private int[] findStart() {
        return findElement(2);
    }

    private int[] findExit() {
        return findElement(3);
    }

    private int[] findElement(int element) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == element) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private int[] getNextMove(int[] current) {
        List<int[]> possibleMoves = new ArrayList<>();
        int[] deltas = {-1, 0, 1, 0, -1};

        for (int i = 0; i < 4; i++) {
            int newRow = current[0] + deltas[i];
            int newCol = current[1] + deltas[i + 1];

            if (isValidMove(newRow, newCol)) {
                possibleMoves.add(new int[]{newRow, newCol});
            }
        }

        if (possibleMoves.isEmpty()) {
            return current;  // Stuck, can't move anywhere
        }

        double[] probabilities = calculateMoveProbabilities(current, possibleMoves);
        int moveIndex = chooseMoveIndex(probabilities);
        return possibleMoves.get(moveIndex);
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols && maze[row][col] != 0;
    }

    private double[] calculateMoveProbabilities(int[] current, List<int[]> possibleMoves) {
        double[] probabilities = new double[possibleMoves.size()];
        double totalPheromones = 0.0;

        for (int[] move : possibleMoves) {
            int row = move[0];
            int col = move[1];
            double pheromone = pheromones[row][col];
            double heuristic = calculateHeuristic(move, findExit());
            probabilities[possibleMoves.indexOf(move)] = pheromone * Math.pow(heuristic, pheromoneWeight);
            totalPheromones += probabilities[possibleMoves.indexOf(move)];
        }

        if (totalPheromones == 0.0) {
            Arrays.fill(probabilities, 1.0 / possibleMoves.size());
        } else {
            for (int i = 0; i < probabilities.length; i++) {
                probabilities[i] /= totalPheromones;
            }
        }

        return probabilities;
    }

    private int chooseMoveIndex(double[] probabilities) {
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0.0;

        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (randomValue <= cumulativeProbability) {
                return i;
            }
        }

        return probabilities.length - 1;
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

    private void updatePheromones(List<int[]> path, double distance) {
        double pheromoneDeposit = pheromoneWeight / distance;

        for (int[] point : path) {
            int row = point[0];
            int col = point[1];
            pheromones[row][col] += pheromoneDeposit;
        }
    }

    private void evaporatePheromones() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                pheromones[i][j] *= (1 - pheromoneEvaporationRate);
            }
        }
    }

    private double calculateHeuristic(int[] point1, int[] point2) {
        int dx = point2[0] - point1[0];
        int dy = point2[1] - point1[1];
        return Math.sqrt(dx * dx + dy * dy);
    }

    private int[][] convertPathToArray(List<int[]> path) {
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = maze[i][j];
            }
        }

        for (int[] position : path) {
            int row = position[0];
            int col = position[1];
            result[row][col] = 4; // Oznaczamy najkrótszą ścieżkę liczbą 4
        }

        return result;
    }
}
