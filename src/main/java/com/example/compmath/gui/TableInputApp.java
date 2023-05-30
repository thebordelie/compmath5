package com.example.compmath.gui;

import com.example.compmath.interpolation.LangrangePolynomial;
import com.example.compmath.interpolation.NewtonPolynomial;
import com.example.compmath.model.Polynomial;
import com.example.compmath.model.TableOfValues;
import com.example.compmath.model.equation.AlgebraicFunction;
import com.example.compmath.model.equation.Equation;
import com.example.compmath.model.equation.TranscendentalEquation;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TableInputApp extends JFrame {
    private DefaultTableModel model;
    private JSpinner spinner;
    private JTextField fileTextField;
    private TableOfValues tableOfValues;
    private JComboBox<String> modeComboBox;
    private JTextField value1TextField;
    private JTextField value2TextField;
    private JTextField value3TextField;

    public TableInputApp() {
        setTitle("Лабораторная работа №5");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1080, 600);


        model = new DefaultTableModel();
        model.addColumn("X");
        model.addColumn("Y");
        JTable table = new JTable(model);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        JButton addButton = new JButton("Добавить строку");
        addButton.addActionListener(e -> addRow());


        JButton deleteButton = new JButton("Удалить строку");
        deleteButton.addActionListener(e -> deleteRow());

        JButton clearButton = new JButton("Очистить таблицу");
        clearButton.addActionListener(e -> clearTable());

        JButton loadButton = new JButton("Загрузить данные из файла");
        loadButton.addActionListener(e -> loadFile());

        JButton processButton = new JButton("Интерполировать");
        processButton.addActionListener(e -> processData());

        JButton load = new JButton("Построить таблицу для заданной функции");
        load.addActionListener(e -> loadFunction());

        modeComboBox = new JComboBox<>();
        value1TextField = new JTextField();
        value2TextField = new JTextField();

        modeComboBox.addItem("sin(x)");
        modeComboBox.addItem("cos(x^2+2x)");

        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        modePanel.add(new JLabel("Уравнение: "));
        modePanel.add(modeComboBox);
        value3TextField = new JTextField();
        JPanel valuesPanel = new JPanel(new GridLayout(3, 2));
        JPanel valuesPanel1 = new JPanel(new GridLayout(2, 2));
        valuesPanel.add(new JLabel("X, для которого нужно посчитать значение:"));
        valuesPanel.add(value3TextField);

        valuesPanel.add(new JLabel("Шаг (Для уравнения): "));
        valuesPanel.add(value1TextField);

        valuesPanel.add(new JLabel("Через пробел, интервал для исследования (Для уравнения): "));
        valuesPanel.add(value2TextField);


        SpinnerModel spinnerModel = new SpinnerNumberModel(8, 0, Integer.MAX_VALUE, 1);
        spinner = new JSpinner(spinnerModel);
        spinner.addChangeListener(e -> setTableRowCount((int) spinner.getValue()));

        fileTextField = new JTextField();
        fileTextField.setEditable(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(processButton);

        JPanel spinnerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        spinnerPanel.add(new JLabel("Количество строк: "));
        spinnerPanel.add(spinner);
        setTableRowCount(8);

        JPanel filePanel = new JPanel(new BorderLayout());
        filePanel.add(new JLabel("File: "), BorderLayout.WEST);
        filePanel.add(fileTextField, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new BorderLayout());
        JPanel controlPanel1 = new JPanel(new BorderLayout());
        JPanel controlPanel2 = new JPanel(new GridLayout(2, 1));

        controlPanel1.add(buttonPanel, BorderLayout.EAST);
        controlPanel1.add(spinnerPanel, BorderLayout.WEST);
        controlPanel1.add(filePanel, BorderLayout.SOUTH);
        controlPanel.add(valuesPanel, BorderLayout.CENTER);
        controlPanel.add(modePanel, BorderLayout.NORTH);

        controlPanel.add(load, BorderLayout.SOUTH);

        controlPanel2.add(controlPanel);
        controlPanel2.add(controlPanel1);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        getContentPane().add(controlPanel2, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void clearTable() {
        int rowCount = model.getRowCount();
        setTableRowCount(0);
        setTableRowCount(rowCount);
    }

    private void addRow() {
        if (model.getRowCount() >= Integer.MAX_VALUE) return;
        model.addRow(new Object[]{"", ""});
    }

    private void addRow(String value1, String value2) {
        model.addRow(new Object[]{value1, value2});
    }

    private void deleteRow() {
        if (model.getRowCount() <= 0) return;
        model.removeRow(model.getRowCount() - 1);
    }

    private void setTableRowCount(int rowCount) {
        int currentRowCount = model.getRowCount();
        if (currentRowCount < rowCount) {
            for (int i = 0; i < rowCount - currentRowCount; i++) {
                model.addRow(new Object[]{"", ""});
            }
        } else if (currentRowCount > rowCount) {
            for (int i = currentRowCount - 1; i >= rowCount; i--) {
                model.removeRow(i);
            }
        }
    }

    private void loadFunction() {
        Equation equation;
        if (modeComboBox.getSelectedIndex() == 0) {
            equation = new Equation(0, new TranscendentalEquation("sin", 1, new AlgebraicFunction(1, 1, 0)));
        } else {
            equation = new Equation(0, new TranscendentalEquation("cos", 1, new AlgebraicFunction(2, 1, 2, 0)));
        }
        try {
            double step = Double.parseDouble(value1TextField.getText().replaceAll(",", "."));
            String[] border = value2TextField.getText().split(" ");
            double leftBorder = Double.parseDouble(border[0].replaceAll(",", "."));
            double rightBorder = Double.parseDouble(border[1].replaceAll(",", "."));
            if (rightBorder < leftBorder || step <= 0) throw new NumberFormatException();

            setTableRowCount(0);
            for (double point = leftBorder; point <= rightBorder; point += step) {
                addRow(String.format("%.3f", point), String.format("%.3f", equation.calculateEquationValue(point)));
            }
        } catch (Throwable e) {
            JOptionPane.showMessageDialog(TableInputApp.this, "Некорректно введены данные", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            fileTextField.setText(file.getAbsolutePath());
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line = reader.readLine();
                int tableSize = 0;
                setTableRowCount(0);
                while (line != null) {
                    String[] values = line.split(" ");
                    if (values.length != 2) throw new NumberFormatException();
                    addRow(values[0], values[1]);
                    tableSize++;
                    line = reader.readLine();
                }

            } catch (IOException e) {
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(TableInputApp.this, "Некорректно данные в файле", "Ошибка", JOptionPane.ERROR_MESSAGE);
                setTableRowCount(0);
            }
        }
    }

    private void processData() {
        int rowCount = model.getRowCount();
        ArrayList<Double> xValues = new ArrayList<>();
        ArrayList<Double> yValues = new ArrayList<>();
        try {
            if (rowCount <= 1) throw new NumberFormatException();
            for (int row = 0; row < rowCount; row++) {
                double x = Double.parseDouble(String.valueOf(model.getValueAt(row, 0)).replaceAll(",", "."));
                double y = Double.parseDouble(String.valueOf(model.getValueAt(row, 1)).replaceAll(",", "."));
                xValues.add(x);
                yValues.add(y);
            }
            if (new HashSet<>(xValues).size() != xValues.size())
                throw new NumberFormatException();
            TableOfValues values = new TableOfValues(xValues, yValues, rowCount);
            showResult(values);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(TableInputApp.this, "Некорректно введены данные", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showResult(TableOfValues values) {
        HashSet<Double> steps = new HashSet<>();
        List<Double> xValues = values.getXValues();
        for (int xNumber = 1; xNumber < xValues.size(); xNumber++) {
            steps.add((double) (Math.round((xValues.get(xNumber) - xValues.get(xNumber - 1)) * 1000)) / 1000);
        }
        double value = Double.parseDouble(value3TextField.getText().replaceAll(",", "."));
        Polynomial polynomial = new LangrangePolynomial().interpolate(values, value);
        Polynomial polynomial1;
        ArrayList<Polynomial> polynomials = new ArrayList<>();
        if (steps.size() != 1) {
            JOptionPane.showMessageDialog(TableInputApp.this, "Расстояние между x не одинаково, многочлен ньютона не будет построен", "Предупреждение", JOptionPane.INFORMATION_MESSAGE);

        } else {
            polynomial1 = new NewtonPolynomial().interpolate(values, value);
            Table table = new Table(values);
            table.pack();
            table.setDefaultCloseOperation(ApplicationFrame.DISPOSE_ON_CLOSE);
            RefineryUtilities.centerFrameOnScreen(table);
            table.setVisible(true);
            polynomials.add(polynomial1);
        }
        polynomials.add(polynomial);

        Graph graph = new Graph("Вывод", "Графики", values, polynomials);
        graph.pack();
        graph.setDefaultCloseOperation(ApplicationFrame.DISPOSE_ON_CLOSE);
        RefineryUtilities.centerFrameOnScreen(graph);
        graph.setVisible(true);
    }
}
