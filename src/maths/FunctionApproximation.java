package maths;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import utils.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FunctionApproximation {



    public static void main(String[] args) {
        List<Double> inputList = new ArrayList<>();
        List<Double> outputList = new ArrayList<>();

        // Read data from file
        try (BufferedReader br = new BufferedReader(new FileReader("output.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Parse input and output values from each line
                String[] parts = line.split("\\s+");
                double input = Double.parseDouble(parts[1]);
                double output = Double.parseDouble(parts[3]);

                // Add values to the lists
                inputList.add(input);
                outputList.add(output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert lists to arrays
        double[] inputArray = inputList.stream().mapToDouble(Double::doubleValue).toArray();
        double[] outputArray = outputList.stream().mapToDouble(Double::doubleValue).toArray();


        // Get the coefficients for the polynomial function
        double[] coefficients = getPolynomialCoefficients(inputArray, outputArray);

        // Print the coefficients
        System.out.println("Polynomial Coefficients: " + Arrays.toString(coefficients));
        writeCoefficientsToFile("polynomial_coefficients.txt", coefficients);
        // Use the coefficients to create a PolynomialFunction
        PolynomialFunction polynomialFunction = new PolynomialFunction(coefficients);

        // Example: Evaluate the function at x = 6.0
        double result = polynomialFunction.value(0.25);
        System.out.println("Approximation at x = 0.43: " + result);

    }



    private static double[] getPolynomialCoefficients(double[] inputSet, double[] outputSet) {
        WeightedObservedPoints points = new WeightedObservedPoints();

        // Add observed data points
        for (int i = 0; i < inputSet.length; i++) {
            points.add(inputSet[i], outputSet[i]);
        }

        // Choose the degree of the polynomial (e.g., 2 for quadratic)
        int degree = 100;

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
