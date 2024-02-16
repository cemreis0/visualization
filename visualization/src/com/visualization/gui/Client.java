package com.visualization.gui;

import com.visualization.util.DataHandler;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class Client extends JFrame {
    private DataHandler dataHandler;

    public static void main(String[] args) {
        Client client = new Client();
    }

    public Client() {
        super("Turkey Data");
        dataHandler = new DataHandler();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 1000);

        JPanel panel = new JPanel(new GridLayout(1, 1));

        // extract data for Turkey from the deaths file
        File deathsFile = new File("data/terrorism-deaths.csv");
        List<String> deathsData = dataHandler.extractDataFromFile(deathsFile, "Turkey");

        // extract data for Turkey from the attacks file
        File attacksFile = new File("data/terrorist-attacks.csv");
        List<String> attacksData = dataHandler.extractDataFromFile(attacksFile, "Turkey");

        DefaultCategoryDataset combinedDataset = combineDatasets(deathsData, attacksData);

        // create chart
        JFreeChart combinedChart = ChartFactory.createLineChart(
                "Number of Terrorist Attacks and Deaths from Terrorism in Turkey between 1970-2021",
                "Year",
                "Count",
                combinedDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // customizations
        CategoryPlot plot = combinedChart.getCategoryPlot();
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED); // Set color for deaths
        renderer.setSeriesPaint(1, Color.BLUE); // Set color for attacks
        plot.setRenderer(renderer);
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        // set preferred size for the chart panel
        ChartPanel chartPanel = new ChartPanel(combinedChart);

        // Add chart panel to the frame
        panel.add(chartPanel, BorderLayout.CENTER);
        add(panel);

        setVisible(true);
    }

    private DefaultCategoryDataset createCategoryDataset(List<String> data, String seriesName) {
        DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();

        for (String line : data) {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                categoryDataset.addValue(Integer.parseInt(parts[3]), seriesName, parts[2]);
            }
        }

        return categoryDataset;
    }

    private DefaultCategoryDataset combineDatasets(List<String> deathsData, List<String> attacksData) {
        DefaultCategoryDataset combinedDataset = new DefaultCategoryDataset();

        // Add deaths data to the combined dataset
        for (String line : deathsData) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                combinedDataset.addValue(Integer.parseInt(parts[3]), "Deaths", parts[2]);
            }
        }

        // Add attacks data to the combined dataset
        for (String line : attacksData) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                combinedDataset.addValue(Integer.parseInt(parts[3]), "Attacks", parts[2]);
            }
        }

        return combinedDataset;
    }
}
