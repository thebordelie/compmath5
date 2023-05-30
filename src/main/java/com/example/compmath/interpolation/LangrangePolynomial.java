package com.example.compmath.interpolation;

import com.example.compmath.model.Polynomial;
import com.example.compmath.model.TableOfValues;
import com.example.compmath.model.equation.AbstractFunction;
import com.example.compmath.model.equation.AlgebraicFunction;

import java.util.ArrayList;
import java.util.List;

public class LangrangePolynomial implements InterpolationFunction{
    @Override
    public Polynomial interpolate(TableOfValues values, double value) {
        Polynomial polynomial1 = new Polynomial("Многочлен Лангранжа");
        List<Double> xValues = values.getXValues();
        List<Double> yValues = values.getYValues();
        for (int polynomial = 0; polynomial < xValues.size(); polynomial++) {
            double coefficient = yValues.get(polynomial);
            ArrayList<AbstractFunction> functions = new ArrayList<>();
            for (int currentPolynomial = 0; currentPolynomial < xValues.size(); currentPolynomial++) {
                if (currentPolynomial == polynomial) continue;
                functions.add(new AlgebraicFunction(1, 1, -xValues.get(currentPolynomial)));
                coefficient *= 1 / (xValues.get(polynomial) - xValues.get(currentPolynomial));
            }
            polynomial1.addBlock(coefficient, functions);
        }
        polynomial1.setInformation("Многочлен Лангранжа:\n Значение " + String.format("%.3f", polynomial1.calculateValue(value)));
        return polynomial1;
    }
}
