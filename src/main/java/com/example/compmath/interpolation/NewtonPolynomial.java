package com.example.compmath.interpolation;

import com.example.compmath.model.Polynomial;
import com.example.compmath.model.TableOfValues;
import com.example.compmath.model.equation.AbstractFunction;
import com.example.compmath.model.equation.AlgebraicFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewtonPolynomial implements InterpolationFunction {
    @Override
    public Polynomial interpolate(TableOfValues values, double value) {
        Polynomial polynomial1 = new Polynomial("Многочлен Ньютона");
        List<Double> xValues = values.getXValues();
        List<Double> yValues = values.getYValues();

        HashMap<Integer, ArrayList<Double>> table = new HashMap<>();
        ArrayList<Double> difference = new ArrayList<>(yValues);
        table.put(0, difference);

        int leftBorderNumber = 0;
        int rightBorderNumber = 0;
        boolean isLeft = true;

        for (int xValue = 1; xValue < xValues.size(); xValue++) {
            if (value >= xValues.get(xValue - 1) && value <= xValues.get(xValue)) {
                leftBorderNumber = xValue - 1;
                rightBorderNumber = xValue;
                break;
            }
        }

        double t = (value - xValues.get(leftBorderNumber)) / (xValues.get(1) - xValues.get(0));

        if ((xValues.get(rightBorderNumber) + xValues.get(leftBorderNumber)) / 2 < value) {
            isLeft = false;
            t = (value - xValues.get(rightBorderNumber)) / (xValues.get(1) - xValues.get(0));
        }
        if (rightBorderNumber == 0) {
            isLeft = false;
            rightBorderNumber = xValues.size() - 1;
        }
        for (int rank = 1; rank < xValues.size(); rank++) {
            difference = new ArrayList<>();
            for (int y_i = 0; y_i < xValues.size() - rank; y_i++) {
                difference.add(table.get(rank - 1).get(y_i + 1) - table.get(rank - 1).get(y_i));
            }
            table.put(rank, difference);
        }

        double h = xValues.get(1) - xValues.get(0);
        if (isLeft) {

            for (int rank = 0; rank < table.size() - leftBorderNumber; rank++) {
                double sum = table.get(rank).get(leftBorderNumber);
                ArrayList<AbstractFunction> functions = new ArrayList<>();
                for (int degree = 0; degree < rank; degree++) {
                    functions.add(new AlgebraicFunction(1, 1, -xValues.get(leftBorderNumber) - degree * h));
                    sum /= h;
                }
                sum /= getFactorial(rank);
                polynomial1.addBlock(sum, functions);
            }
        } else {
            int counter = 0;
            for (int rank = rightBorderNumber; rank >= 0; rank--) {
                double sum = table.get(counter).get(rank);
                ArrayList<AbstractFunction> functions = new ArrayList<>();
                for (int degree = 0; degree < counter; degree++) {
                    functions.add(new AlgebraicFunction(1, 1, -xValues.get(rightBorderNumber) + degree * h));
                    sum /= h;
                }
                sum /= getFactorial(counter);
                polynomial1.addBlock(sum, functions);
                counter++;
            }
        }
        polynomial1.setInformation("Многочлен Ньютона:\n Значение " + String.format("%.3f", polynomial1.calculateValue(value)));
        return polynomial1;
    }

    private int getFactorial(int value) {
        if (value == 0) return 1;
        int factorial = 1;
        for (int number = 1; number <= value; number++) factorial *= number;
        return factorial;
    }
}
