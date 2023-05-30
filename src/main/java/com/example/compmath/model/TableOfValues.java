package com.example.compmath.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TableOfValues {
    private int tableSize;
    private ArrayList<Point> points;

    public TableOfValues(Double[] xValues, Double[] yValues, int tableSize) {
        if (xValues.length != yValues.length) return;
        this.points = new ArrayList<>();
        for (int counter = 0; counter < tableSize; counter++) {
            Point point = new Point(xValues[counter], yValues[counter]);
            this.points.add(point);
        }
        Collections.sort(points);
        this.tableSize = tableSize;
    }

    public TableOfValues(ArrayList<Double> xValues, ArrayList<Double> yValues, int tableSize) {
        if (xValues.size() != yValues.size() || xValues.size() != tableSize) return;
        this.points = new ArrayList<>();
        for (int counter = 0; counter < tableSize; counter++) {
            Point point = new Point(xValues.get(counter), yValues.get(counter));
            this.points.add(point);
        }
        this.tableSize = tableSize;
    }

    public List<Double> getXValues() {
        return points.stream().map(point -> point.X).collect(Collectors.toList());
    }

    public List<Double> getYValues() {
        return points.stream().map(point -> point.Y).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        ArrayList<Double> xValues = new ArrayList<>();
        ArrayList<Double> yValues = new ArrayList<>();
        for (Point point : points) {
            xValues.add(point.X);
            yValues.add(point.Y);
        }
        return createHeader() + "X " + createLine(xValues) + "Y " + createLine(yValues);
    }

    private String createHeader() {
        StringBuilder builder = new StringBuilder();
        StringBuilder builder1 = new StringBuilder();
        builder.append("№ |");
        for (int i = 0; i < tableSize; i++) {
            builder.append(String.format("%5s%-6s|", " ", i + 1));
            builder1.append("=".repeat(12));

        }

        builder.append("\n").append(builder1).append("\n");
        return builder.toString();
    }

    private String createLine(ArrayList<Double> values) {
        StringBuilder builder = new StringBuilder();
        StringBuilder builder1 = new StringBuilder();
        for (double value : values) {
            builder.append(String.format("|%3s%-8s", " ", String.format("%.3f", value)));
            builder1.append("-".repeat(12));
        }

        builder.append("|\n").append(builder1).append("\n");
        return builder.toString();
    }

    @AllArgsConstructor
    @Data
    public class Point implements Comparable<Point> {
        double X;
        double Y;

        @Override
        public int compareTo(Point o) {
            return Double.compare(this.X, o.X);
        }
    }
}
