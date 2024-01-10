import java.util.*;

public class Test {

    public static int n = 5;
    public static int N = 1000;

    public static double chance = 0;

    static String[][] allClusters = new String[n][n];
    static String[][] arr = {
            {"A", "A", "A", "A", "D"},
            {"B", "A", "B", "B", "D"},
            {"F", "C", "F", "B", "A"},
            {"F", "C", "B", "C", "D"},
            {"C", "F", "D", "F", "D"},
    };

    public static List<List<int[]>> findCuster(String[][] arr) {
        List<List<int[]>> clusters = new ArrayList<>();
        List<int[]> cluster = new ArrayList<>();
        boolean[][] visited = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cluster = new ArrayList<>();
                findC(arr, i, j, arr[i][j], cluster, visited);
                if (cluster.size() >= 5)
                    clusters.add(cluster);

            }
        }
        return clusters;
    }

    private static void findC(String[][] arr, int i, int j, String s, List<int[]> cluster, boolean[][] visited) {
        if (i < 0 || i >= n || j < 0 || j >= n || !arr[i][j].equals(s) || visited[i][j]) {
            return;
        }
        cluster.add(new int[]{i, j});
        visited[i][j] = true;

        findC(arr, i + 1, j, s, cluster, visited);
        findC(arr, i - 1, j, s, cluster, visited);
        findC(arr, i, j - 1, s, cluster, visited);
        findC(arr, i, j + 1, s, cluster, visited);


    }

    public static void main(String[] args) {
//        List<List<int[]>> c = findCuster(arr);
//
//        for(List<int[]> cluster : c){
//            for(int[] pair : cluster) {
//                System.out.print(" [" + pair[0] + " " + pair[1]+ "] ");
//            }
//            System.out.println();
//        }


        int[][] matrix = new int[7][7];
        List<int[][]> clusters = findAllClusters(matrix, 5);
        System.out.println("5 clusters 7x7");
        System.out.println(clusters.size());
       // System.out.println(chance);
//
//        matrix = new int[7][7];
//        clusters = findAllClusters(matrix, 6);
//        System.out.println("6 clusters 7x7");
//        System.out.println(clusters.size());
//        matrix = new int[7][7];
//        clusters = findAllClusters(matrix, 7);
//        System.out.println("7 clusters 7x7");
//        System.out.println(clusters.size());
//        matrix = new int[7][7];
//        clusters = findAllClusters(matrix, 8);
//        System.out.println("8 clusters 7x7");
//        System.out.println(clusters.size());
//        clusters = findAllClusters(matrix, 9);
//        System.out.println("9 clusters 7x7");
//        System.out.println(clusters.size());
//        clusters = findAllClusters(matrix, 10);
//        System.out.println("10 clusters 7x7");
//        System.out.println(clusters.size());
//        clusters = findAllClusters(matrix, 11);
//        System.out.println("11 clusters 7x7");
//        System.out.println(clusters.size());
//        clusters = findAllClusters(matrix, 12);
//        System.out.println("12 clusters 7x7");
//        System.out.println(clusters.size());
//        clusters = findAllClusters(matrix, 13);
//        System.out.println("13 clusters 7x7");
//        System.out.println(clusters.size());
//        clusters = findAllClusters(matrix, 14);
//        System.out.println("14 clusters 7x7");
//        System.out.println(clusters.size());
//        clusters = findAllClusters(matrix, 15);
//        System.out.println("15 clusters 7x7");
//        System.out.println(clusters.size());
    }


    public static List<int[][]> findAllClusters(int[][] matrix, int target) {
        List<int[][]> clusters = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                findClusters(deepCopy(matrix), i, j, target, clusters);
            }


        }
        return clusters;
    }

    private static void findClusters(int[][] matrix, int row, int col, int remaining, List<int[][]> clusters) {
        // Base case: If remaining is 0, we found a valid cluster
        if (remaining == 0) {

            int[][] arr = deepCopy(matrix);
            //arr[row][col] = 1;
            // Check for equality of array contents
            boolean containsDuplicate = false;
            for (int[][] existingArray : clusters) {
                if (areArraysEqual(existingArray, arr)) {
                    containsDuplicate = true;
                    break;
                }
            }

            // If the array is not a duplicate, add it to the list
            if (!containsDuplicate) {
                clusters.add(arr);
                printMatrix(arr);
                //chance += distribute(arr);
            }

            return;
        }

        if (row < 0 || col < 0 || row >= matrix.length || col >= matrix.length)
            return;
        if (matrix[row][col] == 1)
            return;

        matrix[row][col] = 1;  // 1 represents the banana symbol

        // Recur to the next cell with reduced remaining symbols
        findClusters(matrix, row, col + 1, remaining - 1, clusters);
        findClusters(matrix, row, col - 1, remaining - 1, clusters);
        findClusters(matrix, row + 1, col, remaining - 1, clusters);
        findClusters(matrix, row - 1, col, remaining - 1, clusters);
        // Backtrack: Undo the current move
        matrix[row][col] = 0;

    }

    public static boolean areArraysEqual(int[][] arr1, int[][] arr2) {
        if (arr1.length != arr2.length || arr1[0].length != arr2[0].length) {
            return false;
        }

        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1[i].length; j++) {
                if (arr1[i][j] != arr2[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        int rows = original.length;
        int columns = original[0].length;

        int[][] copy = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            // Using System.arraycopy for a deep copy of inner arrays
            System.arraycopy(original[i], 0, copy[i], 0, columns);
        }

        return copy;
    }


    private static double distribute(int[][] matrix) {
        double sum = 1;
        Random rand = new Random();
        List<Integer> bananas = new ArrayList<>();
        for (int i = 0; i < 250; i++) {
            bananas.add(1);

        }
        List<Integer> rest = new ArrayList<>();
        for (int i = 0; i < 750; i++) {
            rest.add(0);

        }

//        List<Integer> prototype = new ArrayList<>(l);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 1) {
                    sum *= (double) bananas.size() / (double) (bananas.size() + rest.size());
                    //System.out.println(sum);
                    bananas.removeLast();
                } else {
                    double prob = (double) bananas.size() / (double) (bananas.size() + rest.size());
                    //System.out.println(prob);
                    if (rand.nextDouble() < prob) {
                        bananas.removeLast();
                        sum *= (double) bananas.size() / (double) (bananas.size() + rest.size());
                    } else {
                        sum *= (1 - (double) bananas.size() / (double) (bananas.size() + rest.size()));
                        rest.removeLast();
                    }
                }
            }
        }

        return sum;
    }

    private static void printMatrix(int[][] matrix) {
        System.out.println("Found cluster:");
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
