import utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationTest {
    private static List<String> initialFruits = new ArrayList<>();
    private static final int GRID_SIZE = 7;

    private static final int N = 1000;
    private static String[][] fruitGrid;
    private static List<List<String>> clusters;
    public static void main(String[] args) {
        initializeGrid();
        int counter = 0;
        int LOOP = 100000;
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
            clusters = findCluster();
            if (checkClusters(clusters, "B") ) {
                counter++;
            }
            if(p == 250000 || p== 500000 || p ==750000)
                System.out.println(p);
        }

        double percentage = ((double) counter / LOOP) * 100;
        System.out.println(percentage);

    }

    private static boolean checkClusters(List<List<String>> clusters, String target) {
        for(List<String> cluster : clusters){
            if(cluster.get(0).equals(target) && cluster.size() >= 5)
                return true;
        }
        return false;
    }


    private static List<List<String>> findCluster() {
        List<List<String>> clusters = new ArrayList<>();
        boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
        List<String> cluster = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                cluster = new ArrayList<>();


                exploreCluster(i, j, fruitGrid[i][j], visited, cluster);
                if (cluster.size() == 5 ) {
                    clusters.add(cluster);
                }

            }
        }

        return clusters;
    }


    private static boolean containsN(String[][] matrix, String target, int m){
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
    private static void exploreCluster(int row, int col, String targetFruit, boolean[][] visited, List<String> currentCluster) {
        if (row < 0 || row >= GRID_SIZE || col < 0 || col >= GRID_SIZE || visited[row][col] || !fruitGrid[row][col].equals(targetFruit)) {
            return;
        }

        visited[row][col] = true;

        currentCluster.add(fruitGrid[row][col]);

        exploreCluster(row + 1, col, targetFruit, visited, currentCluster); // Down
        exploreCluster(row - 1, col, targetFruit, visited, currentCluster); // Up
        exploreCluster(row, col + 1, targetFruit, visited, currentCluster); // Right
        exploreCluster(row, col - 1, targetFruit, visited, currentCluster); // Left
    }



    private static void initializeGrid() {
        fruitGrid = new String[GRID_SIZE][GRID_SIZE];

        for(int i = 0; i < N*Probabilities.ORANGE; i ++){
            initialFruits.add(Constants.ORANGE);
        }
        for(int i = 0; i < N*Probabilities.WATERMELON; i ++){
            initialFruits.add(Constants.WATERMELON);
        }
        for(int i = 0; i < N*Probabilities.CHERRIES; i++) {
            initialFruits.add(Constants.CHERRIES);
        }
        for(int i = 0; i < N*Probabilities.BERRIES; i++){
            initialFruits.add(Constants.BERRIES);
        }
        for(int i = 0; i < N*Probabilities.BANANA; i++){
            initialFruits.add(Constants.BANANA);
        }
        for(int i = 0; i < N*Probabilities.AVOCADO; i ++){
            initialFruits.add(Constants.AVOCADO);
        }
        for(int i = 0; i < N*Probabilities.STRAWBERRY; i ++){
            initialFruits.add(Constants.STRAWBERRY);
        }
        for(int i = 0; i < N*Probabilities.MANGO; i ++){
            initialFruits.add(Constants.MANGO);
        }
    }
}
