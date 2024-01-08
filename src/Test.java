import java.util.*;

public class Test {

    public static int n = 5;
    static String[][] arr = {
            {"A", "A", "B", "B", "D"},
            {"A", "A", "B", "B", "D"},
            {"F", "A", "F", "B", "A"},
            {"F", "A", "B", "C", "D"},
            {"C", "A", "D", "F", "D"},
    };

    public static List<List<int[]>> findCuster(String[][] arr){
        List<List<int[]>> clusters = new ArrayList<>();
        List<int[]> cluster = new ArrayList<>();
        boolean[][] visited = new boolean[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                cluster = new ArrayList<>();
                findC(arr, i, j, arr[i][j], cluster, visited);
                if(cluster.size()>= 5)
                    clusters.add(cluster);

            }
        }
        return clusters;
    }

    private static void findC(String[][] arr, int i, int j, String s, List<int[]> cluster,boolean[][] visited) {
        if(i < 0 || i >= n || j < 0 || j >= n || !arr[i][j].equals(s) || visited[i][j]){
            return;
        }
        cluster.add(new int[] {i, j});
        visited[i][j] = true;

        findC(arr, i + 1,j, s, cluster, visited);
        findC(arr, i - 1,j, s, cluster, visited);
        findC(arr, i,j - 1, s, cluster, visited);
        findC(arr, i,j + 1, s, cluster, visited);


    }

    public static void main(String[] args) {
        List<List<int[]>> c = findCuster(arr);

        for(List<int[]> cluster : c){
            for(int[] pair : cluster) {
                System.out.print(" [" + pair[0] + " " + pair[1]+ "] ");
            }
            System.out.println();
        }
    }
}
