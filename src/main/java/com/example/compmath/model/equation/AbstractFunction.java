package com.example.compmath.model.equation;

import java.util.ArrayList;

abstract public class AbstractFunction {

    abstract public double calculateValue(double x);

    abstract public ArrayList<AbstractFunction> findDeclarative();

    abstract public ArrayList<Double> getCoefficients();


}
