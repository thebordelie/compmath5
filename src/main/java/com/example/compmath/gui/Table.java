package com.example.compmath.gui;

import com.example.compmath.model.TableOfValues;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Table extends JFrame {
    private DefaultTableModel model;

    public Table(TableOfValues values) {
        setTitle("Разности");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(950, 600);
        model = new DefaultTableModel();

        List<Double> xValues = values.getXValues();
        List<Double> yValues = values.getYValues();

        HashMap<Integer, ArrayList<Double>> table = new HashMap<>();
        ArrayList<Double> difference = new ArrayList<>(yValues);
        table.put(0, difference);

        for (int rank = 1; rank < xValues.size(); rank++) {
            difference = new ArrayList<>();
            for (int y_i = 0; y_i < xValues.size() - rank; y_i++) {
                difference.add(table.get(rank - 1).get(y_i + 1) - table.get(rank - 1).get(y_i));
            }
            table.put(rank, difference);
        }
        model.addColumn("xi");
        for (int rank = 0; rank < table.size(); rank++) {
            model.addColumn("y^" + rank);
        }
        for (int rank = 0; rank < table.size(); rank++) {
            String[] dataSet = new String[table.size() - rank + 1];
            dataSet[0] = String.format("%.3f", xValues.get(rank));
            for (int value = 0; value < table.size() - rank; value++) {
                dataSet[value + 1] = String.format("%.3f", table.get(value).get(rank));
            }
            model.addRow(dataSet);
        }
        JTable jTable = new JTable(model);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        getContentPane().add(new JScrollPane(jTable), BorderLayout.CENTER);
        setVisible(true);
    }
}
