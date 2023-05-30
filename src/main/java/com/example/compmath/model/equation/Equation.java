package com.example.compmath.model.equation;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;

@Data
public class Equation {
    private ArrayList<AbstractFunction> equation;
    private double rightValue;
    private String information;

    public Equation(double rightValue, ArrayList<AbstractFunction> equation) {
        this.equation = equation;
        this.rightValue = rightValue;
    }

    public Equation(double rightValue, ArrayList<AbstractFunction> equation, String information) {
        this.equation = equation;
        this.rightValue = rightValue;
        this.information = information;
    }

    public Equation(double rightValue, AbstractFunction... functions) {
        this.rightValue = rightValue;
        equation = new ArrayList<>();
        equation.addAll(Arrays.asList(functions));
    }

    public Equation(double rightValue, String information, AbstractFunction... functions) {
        this.rightValue = rightValue;
        equation = new ArrayList<>();
        equation.addAll(Arrays.asList(functions));
        this.information = information;
    }

    public double calculateEquationValue(double x) {
        double answer = 0;
        for (AbstractFunction function : equation) answer += function.calculateValue(x);
        return answer;
    }

    public Equation findDerivative() {
        Equation equation;
        ArrayList<AbstractFunction> functions = new ArrayList<>();
        for (AbstractFunction function : this.equation) {
            functions.addAll(function.findDeclarative());
        }
        equation = new Equation(0, functions);
        return equation;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (AbstractFunction function : equation) {
            builder.append(function.toString());
        }
        return builder.toString();
    }
}
