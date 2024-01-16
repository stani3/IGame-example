import maths.Probabilities;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;


public class Main extends JFrame {

    private static final int GRID_SIZE = 7;
    private static final int SCALE = 2;
    private static final int SQUARE_SIZE = 32 * SCALE;

    private static final int N = 1000;

    private static double bankRoll = 1000;
    private static double bet = 5;

    private Fruit[][] fruitGrid;
    private List<Fruit> initialFruits = new ArrayList<>();
    private List<List<int[]>> clusters;
    JLabel bankRollLabel;
    JTextArea textArea = new JTextArea();


    public Main() {

        setTitle("models.Fruit Grid");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        fruitGrid = new Fruit[GRID_SIZE][GRID_SIZE];



        for(int i = 0; i < N* Probabilities.ORANGE; i ++){
            initialFruits.add(new Orange());
        }
        for(int i = 0; i < N*Probabilities.WATERMELON; i ++){
            initialFruits.add(new Watermelon());
        }
        for(int i = 0; i < N*Probabilities.CHERRIES; i++) {
            initialFruits.add(new Cherries());
        }
        for(int i = 0; i < N*Probabilities.BERRIES; i++){
            initialFruits.add(new Berries());
        }
        for(int i = 0; i < N*Probabilities.BANANA; i++){
            initialFruits.add(new Banana());
        }
        for(int i = 0; i < N*Probabilities.AVOCADO; i ++){
            initialFruits.add(new Avocado());
        }
        for(int i = 0; i < N*Probabilities.STRAWBERRY; i ++){
            initialFruits.add(new Strawberry());
        }
        for(int i = 0; i < N*Probabilities.MANGO; i ++){
            initialFruits.add(new Mango());
        }

        initializeFruitGrid();


        init();
    }

    private void init() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(Color.WHITE);

                highlightClusters(g);
                drawGrid(g);

            }
        };

        JButton spinButton = new JButton("Spin");
        spinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bankRoll-=bet;
                bankRollLabel.setText("Bank Roll: $" + bankRoll);
                initializeFruitGrid();
                panel.repaint();

            }
        });

        // Set layout manager for the frame
        setLayout(new BorderLayout());

        // Set button size
        Dimension buttonSize = new Dimension(150, 50);
        spinButton.setPreferredSize(buttonSize);

        // Add a bit of space above the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Adjust the space as needed
        buttonPanel.add(spinButton);

        // Add components to the frame
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        bankRollLabel = new JLabel("Bank Roll: $" + bankRoll);
        bankRollLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bankRollLabel.setForeground(Color.BLACK);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

        // Add the bank roll label to the label panel
        labelPanel.add(bankRollLabel);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setForeground(Color.BLACK);
        textArea.setBackground(Color.WHITE);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Add the JTextArea to a JScrollPane for scrolling
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(200, 200));

        // Add the JScrollPane to the label panel
        labelPanel.add(scrollPane);

        // Add the label panel to the frame
        add(labelPanel, BorderLayout.EAST);


        setVisible(true);
    }

    private void highlightClusters(Graphics g) {
        //printC(clusters);
        g.setColor(Color.YELLOW);

        for (List<int[]> cluster : clusters) {
            for(int[] pair : cluster) {
                int row = pair[0];
                int col = pair[1];
                g.fillRect(row * SQUARE_SIZE, col * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }
    private void initializeFruitGrid() {
        Random random = new Random();
        List<Fruit> f = new ArrayList<>(initialFruits);
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                //int s = f.size();
                //System.out.println(s);
                int r = random.nextInt(f.size());
                Fruit fr = f.get(r);
                fruitGrid[i][j] = f.get(r);
                for(int k = 0; k < 10 && f.size() > 0; k++){
                    f.remove(fr);
                }

            }
        }
        clusters = findCluster();
        double win;
        for(List<int[]> cluster : clusters){
            win = bet * Probabilities.findMultiplayer(fruitGrid[cluster.getFirst()[0]][cluster.getFirst()[1]], cluster.size());
            textArea.setText(
                    textArea.getText() + "\n" + cluster.size() + " " + fruitGrid[cluster.getFirst()[0]][cluster.getFirst()[1]].symbol
                    + " " + Probabilities.findMultiplayer(fruitGrid[cluster.getFirst()[0]][cluster.getFirst()[1]], cluster.size())
                            + " payout: " + win + "\n");

            bankRoll += bet * Probabilities.findMultiplayer(fruitGrid[cluster.getFirst()[0]][cluster.getFirst()[1]], cluster.size());
        }

//        for(int[] cluster : clusters){
//            System.out.println(cluster[0] + " C " + cluster[1]);
//        }
    }

    private void drawGrid(Graphics g) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                BufferedImage resizedImage = resizeImage(fruitGrid[i][j].getImage(), SQUARE_SIZE, SQUARE_SIZE);
                g.drawImage(resizedImage, i * SQUARE_SIZE, j * SQUARE_SIZE, this);
            }
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return resizedImage;
    }


    private List<List<int[]>> findCluster() {
        List<List<int[]>> clusters = new ArrayList<>();
        boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
        List<int[]> cluster = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                    cluster = new ArrayList<>();


                    exploreCluster(i, j, fruitGrid[i][j], visited, cluster);
                    if (cluster.size() >= 5) {
                        clusters.add(cluster);
                    }

            }
        }

        return clusters;
    }

    private void exploreCluster(int row, int col, Fruit targetFruit, boolean[][] visited, List<int[]> currentCluster) {
        if (row < 0 || row >= GRID_SIZE || col < 0 || col >= GRID_SIZE || visited[row][col] || !fruitGrid[row][col].equals(targetFruit)) {
            return;
        }

        visited[row][col] = true;
        int[] arr = new int[]{row, col};
        currentCluster.add(arr);

        exploreCluster(row + 1, col, targetFruit, visited, currentCluster); // Down
        exploreCluster(row - 1, col, targetFruit, visited, currentCluster); // Up
        exploreCluster(row, col + 1, targetFruit, visited, currentCluster); // Right
        exploreCluster(row, col - 1, targetFruit, visited, currentCluster); // Left
        //visited[row][col] = false;
    }


    private void printC(List<List<int[]>> c){

        for(List<int[]> cluster : c){
            for(int[] pair : cluster) {
                System.out.print(" [" + pair[0] + " "  + pair[1]+ "] " + fruitGrid[pair[0]][pair[1]].symbol + " ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
