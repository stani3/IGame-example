package maths;

import utils.Constants;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class SimulationTest {
    public  List<String> initialFruits = new ArrayList<>();
    public  final int GRID_SIZE = 7;

    public  final int N = 10000;
    public  String[][] fruitGrid;

    public static int LOOP = 100000;

    private static final int NUM_THREADS = 8;
    public  List<List<String>> clusters;
    public static void main(String[] args) {
        //findRandomProbabilities();
        SimulationTest t = new SimulationTest();
        double p = t.simulateFruitsParallel(Constants.BANANA, 0.43);


    }

    private static void findRandomProbabilities() {
        Random rand = new Random();
        for(int i= 0; i < 50; i++){
            double probability = rand.nextDouble();
            System.out.println(probability);
            if(probability > 0.58){
                writeToFile(probability, 100.0);
            }else {
                SimulationTest t = new SimulationTest();
                //double p = t.simulateFruits(Constants.BANANA);
                double p = t.simulateFruitsParallel(Constants.BANANA, probability);
                writeToFile(probability, p);
            }
        }
    }

    private static void writeToFile(double banana, double p) {
        try (FileWriter writer = new FileWriter("output.txt", true)) {
            // Append input and output to the file
            writer.write("Input: " + banana + " Output: " + p + "\n");
            System.out.println("Data appended to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public double simulateFruitsParallel(String target, double probability) {

        initializeGridParalel(probability);
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        List<Callable<Integer>> tasks = new ArrayList<>();

        for (int t = 0; t < NUM_THREADS; t++) {
            final int threadIndex = t;
            tasks.add(() -> simulateFruitsInRange(target, threadIndex * (LOOP / NUM_THREADS), (threadIndex + 1) * (LOOP / NUM_THREADS)));
        }


        try {
            List<Future<Integer>> futures = executorService.invokeAll(tasks);

            int totalCounter = 0;
            for (Future<Integer> future : futures) {
                totalCounter += future.get();
            }
            System.out.println(totalCounter);
            double percentage = ((double) totalCounter / LOOP) * 100;
            System.out.println(percentage);

            return percentage;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return 0.0;
        } finally {
            executorService.shutdown();
        }
    }

    private int simulateFruitsInRange(String target, int start, int end) {

        int counter = 0;
        List<List<String>> localClusters = new ArrayList<>();
        Random random = new Random();
        String[][] grid = deepCopy(fruitGrid);

        for (int p = start; p < end; p++) {
            // Your existing loop code here
            // ...

            List<String> f = new ArrayList<>(initialFruits);
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    //int s = f.size();
                    //System.out.println(s);
                    int r = random.nextInt(f.size());
                    String fr = new String(f.get(r));
                    grid[i][j] = fr;
//                    for (int k = 0; k < 10 && f.size() > 0; k++) {
//                        f.remove(fr);
//                    }
                    f.remove(fr);

                }
            }
            localClusters = findCluster(target, grid);
            if (checkClusters(localClusters, target) ) {
                counter++;
            }
            if(p == 250000 || p== 500000 || p ==750000)
                System.out.println(p);

        }

        return counter;
    }


    public  double simulateFruits(String target) {
        initializeGrid();
        int counter = 0;

        Random random = new Random();
        for(int p = 0; p < LOOP; p++) {


            List<String> f = new ArrayList<>(initialFruits);
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    //int s = f.size();
                    //System.out.println(s);
                    int r = random.nextInt(f.size());
                    String fr = new String(f.get(r));
                    fruitGrid[i][j] = fr;
//                    for (int k = 0; k < 10 && f.size() > 0; k++) {
//                        f.remove(fr);
//                    }
                    f.remove(fr);

                }
            }
            clusters = findCluster(target, fruitGrid);
            if (checkClusters(clusters, target) ) {
                counter++;
            }
            if(p == 250000 || p== 500000 || p ==750000)
                System.out.println(p);
        }
        //System.out.println(counter);
        double percentage = ((double) counter / LOOP) * 100;
        System.out.println(percentage);
        return percentage;
    }

    public static boolean checkClusters(List<List<String>> clusters, String target) {
        for(List<String> cluster : clusters){
            if(cluster.get(0).equals(target) && cluster.size() >= 5)
                return true;
        }
        return false;
    }


    public List<List<String>> findCluster(String target, String[][] grid) {
        List<List<String>> clusters = new ArrayList<>();
        boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
        List<String> cluster = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                cluster = new ArrayList<>();


                exploreCluster(i, j, grid[i][j], visited, cluster, grid);
                if (cluster.size() >= 5 ) {
                    clusters.add(cluster);
                }

            }
        }

        return clusters;
    }


    public static boolean containsN(String[][] matrix, String target, int m){
        int count = 0;
        for(int p = 0; p < matrix.length; p++){
            for(int q = 0; q < matrix[p].length; q++){
                if(matrix[p][q].equals(target))
                    count++;


            }
        }
        //System.out.println(count);
        return count == m;

    }
    public  void exploreCluster(int row, int col, String targetFruit, boolean[][] visited, List<String> currentCluster, String[][] grid) {
        if (row < 0 || row >= GRID_SIZE || col < 0 || col >= GRID_SIZE || visited[row][col] || !grid[row][col].equals(targetFruit)) {
            return;
        }

        visited[row][col] = true;

        currentCluster.add(grid[row][col]);

        exploreCluster(row + 1, col, targetFruit, visited, currentCluster, grid); // Down
        exploreCluster(row - 1, col, targetFruit, visited, currentCluster,grid); // Up
        exploreCluster(row, col + 1, targetFruit, visited, currentCluster, grid); // Right
        exploreCluster(row, col - 1, targetFruit, visited, currentCluster,grid); // Left
        visited[row][col] = false;
    }


    public static String[][] deepCopy(String[][] original) {
        if (original == null) {
            return null;
        }

        int rows = original.length;
        int columns = original[0].length;

        String[][] copy = new String[rows][columns];

        for (int i = 0; i < rows; i++) {
            // Using System.arraycopy for a deep copy of inner arrays
            System.arraycopy(original[i], 0, copy[i], 0, columns);
        }

        return copy;
    }


    public void initializeGridParalel(double probability){
        fruitGrid = new String[GRID_SIZE][GRID_SIZE];


        for(int i = 0; i < N* probability; i ++){
            initialFruits.add(Constants.BANANA);
        }

        int F = (int) ((N- N*probability)/7);
        System.out.println(F);


        for(int i = 0; i <  F; i ++){
            initialFruits.add(Constants.ORANGE);
        }

        for(int i = 0; i < F; i ++){
            initialFruits.add(Constants.WATERMELON);
        }
        for(int i = 0; i <  F; i ++){
            initialFruits.add(Constants.AVOCADO);
        }
        for(int i = 0; i <  F; i ++){
            initialFruits.add(Constants.MANGO);
        }
        for(int i = 0; i <  F; i ++){
            initialFruits.add(Constants.CHERRIES);
        }
        for(int i = 0; i < F; i ++){
            initialFruits.add(Constants.BERRIES);
        }

        for(int i = 0; i < F; i ++){
            initialFruits.add(Constants.STRAWBERRY);
        }
        while (initialFruits.size() < N) {
            initialFruits.add(Constants.STRAWBERRY);
        }

       // System.out.println(initialFruits.size());

    }
    public  void initializeGrid() {
        fruitGrid = new String[GRID_SIZE][GRID_SIZE];

        for(int i = 0; i < N* Probabilities.ORANGE; i ++){
            initialFruits.add(Constants.ORANGE);
        }
        for(int i = 0; i < N* Probabilities.WATERMELON; i ++){
            initialFruits.add(Constants.WATERMELON);
        }
        for(int i = 0; i < N* Probabilities.CHERRIES; i++) {
            initialFruits.add(Constants.CHERRIES);
        }
        for(int i = 0; i < N* Probabilities.BERRIES; i++){
            initialFruits.add(Constants.BERRIES);
        }
        for(int i = 0; i < N* Probabilities.BANANA; i++){
            initialFruits.add(Constants.BANANA);
        }
        for(int i = 0; i < N* Probabilities.AVOCADO; i ++){
            initialFruits.add(Constants.AVOCADO);
        }
        for(int i = 0; i < N* Probabilities.STRAWBERRY; i ++){
            initialFruits.add(Constants.STRAWBERRY);
        }
        for(int i = 0; i < N* Probabilities.MANGO; i ++){
            initialFruits.add(Constants.MANGO);
        }
    }


}
