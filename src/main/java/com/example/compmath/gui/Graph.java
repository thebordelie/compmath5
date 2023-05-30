package com.example.compmath.gui;

import com.example.compmath.model.Polynomial;
import com.example.compmath.model.TableOfValues;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Graph extends JFrame {

    public Graph(String applicationTitle, String chartTitle, TableOfValues table, ArrayList<Polynomial> equations) {
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "X",
                "Y",
                null,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(890, 560));
        final XYPlot plot = createPlot(xylineChart);
        XYDataset dataset = createDataset(table, equations);
        plot.setDataset(dataset);

        XYSplineRenderer r0 = new XYSplineRenderer();
        for (int equation = 0; equation < equations.size(); equation++) {
            r0.setSeriesShapesVisible(equation + 1, false);
        }
        plot.setRenderer(r0);
        setContentPane(chartPanel);
    }

    private XYDataset createDataset(TableOfValues table, ArrayList<Polynomial> equations) {
        final XYSeriesCollection dataset = new XYSeriesCollection();
        final XYSeries tableValues = new XYSeries("Таблица");

//        for (TableOfValues.Point point : table.getPoints()) {
//            tableValues.add(point.getX(), point.getY());
//        }
//        dataset.addSeries(tableValues);
        for (Polynomial equation : equations) {
            final XYSeries equationValue = new XYSeries(equation.getInformation());
            for (double x = table.getPoints().get(0).getX() - 0.1; x <= table.getPoints().get(table.getTableSize() - 1).getX() + 0.1; x += 0.1) {
                equationValue.add(x, equation.calculateValue(x));
            }
            dataset.addSeries(equationValue);
        }
        return dataset;
    }

    private XYPlot createPlot(JFreeChart chart) {


        chart.setBackgroundPaint(Color.white);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(232, 232, 232));

        plot.setDomainGridlinePaint(Color.gray);
        plot.setRangeGridlinePaint(Color.gray);

        // Определение отступа меток делений
        plot.setAxisOffset(new RectangleInsets(1.0, 1.0, 1.0, 1.0));

        // Скрытие осевых линий и меток делений
        ValueAxis axis = plot.getDomainAxis();
        axis.setAxisLineVisible(false);    // осевая линия

        // Настройка NumberAxis
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAxisLineVisible(false);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        return plot;
    }
}

