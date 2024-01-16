package maths;

import models.Fruit;
import utils.Constants;

import java.util.HashMap;

public class Probabilities {

    public static double ORANGE = 0.1;
    public static double AVOCADO = 0.1;
    public static double WATERMELON = 0.1;
    public static double BERRIES = 0.1;
    public static double BANANA = 0.25;
    public static double CHERRIES = 0.2;

    public static double STRAWBERRY = 0.05;
    public static double MANGO = 0.1;

    public static HashMap<String, HashMap<Integer, Double>> multiplier = initHashMap();

    private static HashMap<String, HashMap<Integer, Double>> initHashMap() {
        HashMap<String, HashMap<Integer, Double>> table = new HashMap<>();
        table.put(Constants.BANANA, new HashMap<>());
        table.put(Constants.CHERRIES, new HashMap<>());
        table.put(Constants.STRAWBERRY, new HashMap<>());
        table.put(Constants.MANGO, new HashMap<>());
        table.put(Constants.ORANGE, new HashMap<>());
        table.put(Constants.AVOCADO, new HashMap<>());
        table.put(Constants.WATERMELON, new HashMap<>());
        table.put(Constants.BERRIES, new HashMap<>());

        table.get(Constants.BANANA).put(5, 0.4);
        table.get(Constants.BANANA).put(6, 0.8);
        table.get(Constants.BANANA).put(7, 2.0);
        table.get(Constants.BANANA).put(8, 5.0);
        table.get(Constants.BANANA).put(9, 10.0);
        table.get(Constants.BANANA).put(10, 15.0);
        table.get(Constants.BANANA).put(11, 30.0);
        table.get(Constants.BANANA).put(12, 30.0);
        table.get(Constants.BANANA).put(13, 50.0);
        table.get(Constants.BANANA).put(14, 50.0);
        table.get(Constants.BANANA).put(15, 100.0);
        /////////////////////////////////
        table.get(Constants.CHERRIES).put(5, 0.7);
        table.get(Constants.CHERRIES).put(6, 1.5);
        table.get(Constants.CHERRIES).put(7, 4.0);
        table.get(Constants.CHERRIES).put(8, 12.0);
        table.get(Constants.CHERRIES).put(9, 25.0);
        table.get(Constants.CHERRIES).put(10, 50.0);
        table.get(Constants.CHERRIES).put(11, 120.0);
        table.get(Constants.CHERRIES).put(12, 120.0);
        table.get(Constants.CHERRIES).put(13, 200.0);
        table.get(Constants.CHERRIES).put(14, 200.0);
        table.get(Constants.CHERRIES).put(15, 250.0);
        /////////////////////////////////
        table.get(Constants.STRAWBERRY).put(5, 2.0);
        table.get(Constants.STRAWBERRY).put(6, 8.0);
        table.get(Constants.STRAWBERRY).put(7, 25.0);
        table.get(Constants.STRAWBERRY).put(8, 75.0);
        table.get(Constants.STRAWBERRY).put(9, 150.0);
        table.get(Constants.STRAWBERRY).put(10, 250.0);
        table.get(Constants.STRAWBERRY).put(11, 500.0);
        table.get(Constants.STRAWBERRY).put(12, 500.0);
        table.get(Constants.STRAWBERRY).put(13, 700.0);
        table.get(Constants.STRAWBERRY).put(14, 700.0);
        table.get(Constants.STRAWBERRY).put(15, 800.0);
        /////////////////////////////////
        table.get(Constants.MANGO).put(5, 1.0);
        table.get(Constants.MANGO).put(6, 4.0);
        table.get(Constants.MANGO).put(7, 12.0);
        table.get(Constants.MANGO).put(8, 36.0);
        table.get(Constants.MANGO).put(9, 75.0);
        table.get(Constants.MANGO).put(10, 125.0);
        table.get(Constants.MANGO).put(11, 250.0);
        table.get(Constants.MANGO).put(12, 250.0);
        table.get(Constants.MANGO).put(13, 350.0);
        table.get(Constants.MANGO).put(14, 350.0);
        table.get(Constants.MANGO).put(15, 500.0);
        /////////////////////////////////
        table.get(Constants.ORANGE).put(5, 1.0);
        table.get(Constants.ORANGE).put(6, 4.0);
        table.get(Constants.ORANGE).put(7, 12.0);
        table.get(Constants.ORANGE).put(8, 36.0);
        table.get(Constants.ORANGE).put(9, 75.0);
        table.get(Constants.ORANGE).put(10, 125.0);
        table.get(Constants.ORANGE).put(11, 250.0);
        table.get(Constants.ORANGE).put(12, 250.0);
        table.get(Constants.ORANGE).put(13, 350.0);
        table.get(Constants.ORANGE).put(14, 350.0);
        table.get(Constants.ORANGE).put(15, 500.0);
        /////////////////////////////////
        table.get(Constants.AVOCADO).put(5, 1.0);
        table.get(Constants.AVOCADO).put(6, 4.0);
        table.get(Constants.AVOCADO).put(7, 12.0);
        table.get(Constants.AVOCADO).put(8, 36.0);
        table.get(Constants.AVOCADO).put(9, 75.0);
        table.get(Constants.AVOCADO).put(10, 125.0);
        table.get(Constants.AVOCADO).put(11, 250.0);
        table.get(Constants.AVOCADO).put(12, 250.0);
        table.get(Constants.AVOCADO).put(13, 350.0);
        table.get(Constants.AVOCADO).put(14, 350.0);
        table.get(Constants.AVOCADO).put(15, 500.0);
        /////////////////////////////////
        table.get(Constants.WATERMELON).put(5, 1.0);
        table.get(Constants.WATERMELON).put(6, 4.0);
        table.get(Constants.WATERMELON).put(7, 12.0);
        table.get(Constants.WATERMELON).put(8, 36.0);
        table.get(Constants.WATERMELON).put(9, 75.0);
        table.get(Constants.WATERMELON).put(10, 125.0);
        table.get(Constants.WATERMELON).put(11, 250.0);
        table.get(Constants.WATERMELON).put(12, 250.0);
        table.get(Constants.WATERMELON).put(13, 350.0);
        table.get(Constants.WATERMELON).put(14, 350.0);
        table.get(Constants.WATERMELON).put(15, 500.0);
        /////////////////////////////////
        table.get(Constants.BERRIES).put(5, 1.0);
        table.get(Constants.BERRIES).put(6, 4.0);
        table.get(Constants.BERRIES).put(7, 12.0);
        table.get(Constants.BERRIES).put(8, 36.0);
        table.get(Constants.BERRIES).put(9, 75.0);
        table.get(Constants.BERRIES).put(10, 125.0);
        table.get(Constants.BERRIES).put(11, 250.0);
        table.get(Constants.BERRIES).put(12, 250.0);
        table.get(Constants.BERRIES).put(13, 350.0);
        table.get(Constants.BERRIES).put(14, 350.0);
        table.get(Constants.BERRIES).put(15, 500.0);
        /////////////////////////////////
        return table;





    }

    public static double findMultiplayer(Fruit fruit, int size) {
        //System.out.println(size);
        if(size >= 15){
            return multiplier.get(fruit.symbol).get(15);
        }else
            return multiplier.get(fruit.symbol).get(size);

    }

//    public static final double probability(String f){
//        sw
//    }

}
