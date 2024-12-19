package functional;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import weka.associations.Apriori;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class DataAnalysis {

    // Descriptive Statistics
    public void calculateDescriptiveStatistics(List<Double> data) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        data.forEach(stats::addValue);

        System.out.println("Mean: " + stats.getMean());
        System.out.println("Median: " + calculateMedian(data));
        System.out.println("Standard Deviation: " + stats.getStandardDeviation());
        System.out.println("Variance: " + stats.getVariance());
        System.out.println("Range: " + (stats.getMax() - stats.getMin()));
    }

    private double calculateMedian(List<Double> data) {
        List<Double> sortedData = data.stream().sorted().collect(Collectors.toList());
        int size = sortedData.size();
        return size % 2 == 0 ? (sortedData.get(size / 2 - 1) + sortedData.get(size / 2)) / 2 : sortedData.get(size / 2);
    }

    // Frequency Distribution for Categorical Data
    public void frequencyDistribution(List<String> categories) {
        Map<String, Long> frequency = categories.stream()
                .collect(Collectors.groupingBy(category -> category, Collectors.counting()));
        frequency.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    // Bar Chart Visualization
    public void createBarChart(Map<String, Integer> data, String title, String categoryAxis, String valueAxis) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        data.forEach(dataset::addValue);

        JFreeChart barChart = ChartFactory.createBarChart(
                title, categoryAxis, valueAxis, dataset, PlotOrientation.VERTICAL, true, true, false);

        displayChart(barChart, title);
    }

    // Pie Chart Visualization
    public void createPieChart(Map<String, Integer> data, String title) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        data.forEach(dataset::setValue);

        JFreeChart pieChart = ChartFactory.createPieChart(title, dataset, true, true, false);
        displayChart(pieChart, title);
    }

    // Display JFreeChart in Swing window
    private void displayChart(JFreeChart chart, String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
    }

    // Market Basket Analysis using Weka's Apriori Algorithm
    public void marketBasketAnalysis(String filePath) {
        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(filePath);
            Instances data = source.getDataSet();

            Apriori apriori = new Apriori();
            apriori.buildAssociations(data);
            System.out.println("Market Basket Analysis Rules: ");
            System.out.println(apriori);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Customer Segmentation using K-Means Clustering
    public void customerSegmentation(String filePath, int numClusters) {
        try {
            // Load dataset
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(filePath);
            Instances data = source.getDataSet();

            // Ensure that the dataset is numeric and ready for clustering
            if (data.classIndex() == -1) {
                data.setClassIndex(data.numAttributes() - 1); // Set last column as class
            }

            // Build K-Means clustering model
            SimpleKMeans kMeans = new SimpleKMeans();
            kMeans.setNumClusters(numClusters); // Set number of clusters
            kMeans.buildClusterer(data);

            // Display cluster assignments
            System.out.println("Cluster Centers:");
            for (double[] center : kMeans.getClusterCentroids()) {
                System.out.println(Arrays.toString(center));
            }

            System.out.println("\nCluster Assignments:");
            for (int i = 0; i < data.numInstances(); i++) {
                System.out.println("Instance " + i + " -> Cluster " + kMeans.clusterInstance(data.instance(i)));
            }

            System.out.println("\nK-Means clustering completed successfully!");

        } catch (Exception e) {
            System.err.println("Error performing customer segmentation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}