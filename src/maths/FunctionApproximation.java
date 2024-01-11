package maths;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import utils.Constants;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FunctionApproximation {

    private static final int NUM_THREADS = 4;

    public static void main(String[] args) {
        // Sample input and output sets
        double[] inputSet = {0.1, 0.05, 0.2, 0.25};
        double[] outputSet = new double[4];
        String[] symbols = {Constants.AVOCADO, Constants.STRAWBERRY, Constants.CHERRIES, Constants.BANANA};

        // Create an ExecutorService with a fixed number of threads
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        // Divide the loop into tasks and submit them to the ExecutorService
        for (int i = 0; i < symbols.length; i += NUM_THREADS) {
            int endIndex = Math.min(i + NUM_THREADS, symbols.length);
            Runnable task = createTask(inputSet, outputSet, symbols, i, endIndex);
            executorService.submit(task);
        }

        // Shutdown the ExecutorService and wait for all tasks to complete
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Get the coefficients for the polynomial function
        double[] coefficients = getPolynomialCoefficients(inputSet, outputSet);

        // Print the coefficients
        System.out.println("Polynomial Coefficients: " + Arrays.toString(coefficients));
        writeCoefficientsToFile("polynomial_coefficients.txt", coefficients);

        // Use the coefficients to create a PolynomialFunction
        PolynomialFunction polynomialFunction = new PolynomialFunction(coefficients);

        // Example: Evaluate the function at x = 0.43
        double result = polynomialFunction.value(0.43);
        System.out.println("Approximation at x = 0.43: " + result);
    }

    private static Runnable createTask(double[] inputSet, double[] outputSet, String[] symbols, int startIndex, int endIndex) {
        return () -> {
            for (int i = startIndex; i < endIndex; i++) {
                SimulationTest test = new SimulationTest();
                outputSet[i] = test.simulateFruits(symbols[i]);
            }
        };
    }

    private static double[] getPolynomialCoefficients(double[] inputSet, double[] outputSet) {
        WeightedObservedPoints points = new WeightedObservedPoints();

        // Add observed data points
        for (int i = 0; i < inputSet.length; i++) {
            points.add(inputSet[i], outputSet[i]);
        }

        // Choose the degree of the polynomial (e.g., 2 for quadratic)
        int degree = 4;

        // Use PolynomialCurveFitter to fit the polynomial
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);

        // Get the fitted parameters (coefficients)
        double[] coefficients = fitter.fit(points.toList());

        return coefficients;
    }


    private static void writeCoefficientsToFile(String fileName, double[] coefficients) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write the coefficients to the file
            for (int i = 0; i < coefficients.length; i++) {
                writer.write("Coefficient " + i + ": " + coefficients[i] + "\n");
            }
            System.out.println("Coefficients written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
