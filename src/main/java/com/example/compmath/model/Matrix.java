package com.example.compmath.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Matrix {
    private ArrayList<Line> lines;
    private int matrixSize;

    public Matrix(int matrixSize) {
        this.lines = new ArrayList<>();
        this.matrixSize = matrixSize;
    }

    public void addLinetoLine(Line line1, int lineNumber2) {
        Line line2 = lines.get(lineNumber2);
        for (int column = 0; column < matrixSize - 1; column++) {
            line2.getCoefficients().set(column, line2.getCoefficients().get(column) + line1.getCoefficients().get(column));

        }
        line2.setRightValue(line2.getRightValue()+line1.getRightValue());

    }

    public Line multiplyLine(int lineNumber, double value) {
        try {
            Line newLine = (Line) lines.get(lineNumber).clone();
            newLine.multiplyCoefficients(value);
            return newLine;

        } catch (Throwable e) {
            return null;
        }

    }

    public void addLine(double rightValue, ArrayList<Double> coefficients) {
        if (matrixSize != coefficients.size() + 1) return;
        lines.add(new Line(rightValue, coefficients));
    }

    public double getValue(int line, int column) {
        if (line >= matrixSize || column >= matrixSize) return Double.NEGATIVE_INFINITY;
        return lines.get(line).getCoefficients().get(column);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Line line: lines) {
            builder.append(line.getCoefficients()).append(line.rightValue).append("\n");
        }
        return builder.toString();
    }

    @Data
    public class Line implements Cloneable {
        private ArrayList<Double> coefficients;
        private double rightValue;

        public Line(double rightValue, ArrayList<Double> coefficients) {
            this.coefficients = coefficients;
            this.rightValue = rightValue;
        }

        public void multiplyCoefficients(double value) {
            for (int counter = 0; counter < coefficients.size(); counter++) {
                coefficients.set(counter, coefficients.get(counter) * value);
            }
            rightValue*=value;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            ArrayList<Double> newValues = new ArrayList<>(coefficients);
            return new Line(rightValue, newValues);
        }
    }

}
