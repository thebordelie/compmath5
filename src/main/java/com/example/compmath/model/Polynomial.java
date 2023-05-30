package com.example.compmath.model;

import com.example.compmath.model.equation.AbstractFunction;
import lombok.Data;

import java.util.ArrayList;

public class Polynomial {

    private ArrayList<Block> functions;
    private ArrayList<Double> coefficients;
    public String information;

    public Polynomial(String information) {
        functions = new ArrayList<>();
        coefficients = new ArrayList<>();
        this.information = information;
    }

    public void addBlock(double coefficient, ArrayList<AbstractFunction> functions) {
        this.functions.add(new Block(coefficient, functions));
        this.coefficients.add(coefficient);
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getInformation() {
        return information;
    }

    public double calculateValue(double x) {
        double value = 0;
        for (Block block : functions) {
            value += block.calculateValue(x);
        }
        return value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        functions.forEach(function -> {
            builder.append(function.toString());
        });
        return builder.toString();
    }

    @Data
    public class Block {
        private double coefficient;
        private ArrayList<AbstractFunction> functions;

        public Block(double coefficient, ArrayList<AbstractFunction> functions) {
            this.coefficient = coefficient;
            this.functions = functions;
        }

        public double calculateValue(double x) {
            double value = 1;
            for (AbstractFunction function : functions) {
                value *= function.calculateValue(x);
            }
            return coefficient * value;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (coefficient == 0) return "";
            if (coefficient > 0) builder.append(" + ");
            else builder.append(" - ");
            builder.append(String.format("%.3f", Math.abs(coefficient))).append(" * ");
            for (int function = 0; function < functions.size(); function++) {
                builder.append(" (").append(functions.get(function)).append(" ) ");
                if (function != functions.size() - 1) builder.append(" * ");
            }
            return builder.toString();
        }
    }


}
