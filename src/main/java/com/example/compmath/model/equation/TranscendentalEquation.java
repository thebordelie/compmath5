package com.example.compmath.model.equation;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TranscendentalEquation extends AbstractFunction {
    private String type;
    private AbstractFunction coefficient;
    private AbstractFunction argument;
    private double coefficientDouble;

    public TranscendentalEquation(String type, AbstractFunction coefficient, AbstractFunction argument) {
        if (type.equals("ln") || type.equals("cos") || type.equals("sin") || type.equals("tg") || type.equals("ctg") || type.equals("e") || type.equals("x")) {
            this.coefficient = coefficient;
            this.type = type;
            this.argument = argument;
        } else this.type = "und";

    }

    public TranscendentalEquation(String type, double coefficient, AbstractFunction argument) {
        if (type.equals("ln") || type.equals("cos") || type.equals("sin") || type.equals("tg") || type.equals("ctg") || type.equals("e") || type.equals("x")) {
            this.coefficientDouble = coefficient;
            this.type = type;
            this.argument = argument;
        } else this.type = "und";

    }

    public TranscendentalEquation(String type, AbstractFunction coefficient, double coefficientDouble, AbstractFunction argument) {
        if (type.equals("ln") || type.equals("cos") || type.equals("sin") || type.equals("tg") || type.equals("ctg") || type.equals("e") || type.equals("x")) {
            this.coefficientDouble = coefficientDouble;
            this.coefficient = coefficient;
            this.type = type;
            this.argument = argument;
        } else this.type = "und";

    }

    @Override
    public double calculateValue(double x) {
        double value = 0;
        double argumentValue = argument.calculateValue(x);
        double coefficientValue = coefficientDouble;
        if (coefficient != null) coefficientValue = coefficientValue * coefficient.calculateValue(x);
        switch (type) {
            case "ln":
                value += coefficientValue * Math.log(argumentValue);
                break;
            case "cos":
                value += coefficientValue * Math.cos(argumentValue);
                break;
            case "sin":
                value += coefficientValue * Math.sin(argumentValue);
                break;
            case "tg":
                value += coefficientValue * Math.tan(argumentValue);
                break;
            case "ctg":
                value += coefficientValue / Math.tan(argumentValue);
                break;
            case "e":
                value += coefficientValue * Math.exp(argumentValue);
                break;
            case "x":
                value += coefficientValue * Math.pow(x, argumentValue);
                break;
        }
        return value;
    }

    @Override
    public ArrayList<AbstractFunction> findDeclarative() {
        TranscendentalEquation equation = new TranscendentalEquation("cos", new AlgebraicFunction(1, 0, 4), new AlgebraicFunction(1, 1, 0));
        ArrayList<AbstractFunction> functions = new ArrayList<>();

        if (coefficient == null) {
            switch (type) {
                case "ln":

                    break;
                case "cos":
                    equation = new TranscendentalEquation("sin", argument.findDeclarative().get(0), -1 * coefficientDouble, argument);
                    break;
                case "sin":
                    break;
                case "tg":
                    break;
                case "ctg":
                    break;
            }
            functions.add(equation);

        } else {
            AbstractFunction functionDeclarative = coefficient.findDeclarative().get(0);
            AbstractFunction argumentDeclarative = argument.findDeclarative().get(0);
            switch (type) {
                case "ln":

                    break;
                case "cos":
                    TranscendentalEquation equation1 = new TranscendentalEquation("cos", functionDeclarative, coefficientDouble, argument);
                    TranscendentalEquation equation2 = new TranscendentalEquation("sin", argumentDeclarative, coefficientDouble, argument);
                    functions.add(equation1);
                    functions.add(equation2);
                    break;
                case "sin":
                    equation1 = new TranscendentalEquation("sin", functionDeclarative, coefficientDouble, argument);
                    equation2 = new TranscendentalEquation("cos", argumentDeclarative, coefficientDouble, argument);
                    functions.add(equation1);
                    functions.add(equation2);
                    break;
                case "tg":
                    break;
                case "ctg":
                    break;
            }

        }

        return functions;
    }

    @Override
    public ArrayList<Double> getCoefficients() {
        return new ArrayList<>(List.of(coefficientDouble));
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (coefficientDouble != 0) {
            if (coefficientDouble > 0) builder.append(" + ");
            else builder.append(" - ");
        }
        if (Math.abs(coefficientDouble) != 1)
            builder.append(String.format("%f", Math.abs(coefficientDouble))).append(" * ");
        if (coefficient != null) builder.append(" (").append(coefficient).append(") * ");

        switch (type) {
            case "ln":
                builder.append("ln(").append(argument.toString()).append(" ) ");
                break;
            case "cos":
                builder.append("cos(").append(argument.toString()).append(" ) ");
                break;
            case "sin":
                builder.append("sin(").append(argument.toString()).append(" ) ");
                break;
            case "tg":
                builder.append("tg(").append(argument.toString()).append(" ) ");
                break;
            case "ctg":
                builder.append("ctg(").append(argument.toString()).append(" ) ");
                break;
            case "e":
                builder.append("e^(").append(argument.toString()).append(" ) ");
                break;
            case "x":
                builder.append("x^(").append(argument.toString()).append(" ) ");
                break;

        }
        return builder.toString();
    }
}
