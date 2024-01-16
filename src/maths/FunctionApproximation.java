package maths;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.stat.regression.SimpleRegression;
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


        taylorSeries(inputArray, outputArray);

//        linearRegression(inputArray, outputArray);
//
//        inputArray = inputList.stream().filter(a -> a<0.58).mapToDouble(Double::doubleValue).toArray();
//        outputArray = outputList.stream().filter(a-> a!= 100.0).mapToDouble(Double::doubleValue).toArray();
//
//        for(int i = 0; i< inputArray.length; i++){
//            System.out.println(inputArray[i] + " --> " + outputArray[i]);
//        }
//
//        taylorSeries(inputArray, outputArray);
//
//        linearRegression(inputArray, outputArray);


    }

    private static void linearRegression(double[] inputArray, double[] outputArray) {
        SimpleRegression regression = new SimpleRegression();
        for (int i = 0; i < inputArray.length; i++) {
            regression.addData(inputArray[i], outputArray[i]);
        }

        // Print regression results
        System.out.println("Intercept: " + regression.getIntercept());
        System.out.println("Slope: " + regression.getSlope());

        // Example: Evaluate the linear regression model at x = 0.25
        double result = regression.predict(0.25);
        System.out.println("Approximation at x = 0.25: " + result);
    }


    private static void taylorSeries(double[] inputArray, double[] outputArray) {
        // Get the coefficients for the polynomial function
        double[] coefficients = getPolynomialCoefficients(inputArray, outputArray);

        // Print the coefficients
        System.out.println("Polynomial Coefficients: " + Arrays.toString(coefficients));
        writeCoefficientsToFile("polynomial_coefficients.txt", coefficients);
        // Use the coefficients to create a PolynomialFunction
        PolynomialFunction polynomialFunction = new PolynomialFunction(coefficients);

        // Example: Evaluate the function at x = 6.0
        double result = polynomialFunction.value(0.1);
        System.out.println("Approximation at x = 0.43: " + result);
    }


    private static double[] getPolynomialCoefficients(double[] inputSet, double[] outputSet) {
        WeightedObservedPoints points = new WeightedObservedPoints();

        // Add observed data points
        for (int i = 0; i < inputSet.length; i++) {
            points.add(inputSet[i], outputSet[i]);
        }

        // Choose the degree of the polynomial (e.g., 2 for quadratic)
        int degree = 120;

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
