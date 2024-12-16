package functional;

import java.util.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;
import org.jfree.data.general.DefaultPieDataset;

public class DataAnalysis {

    // Method to calculate descriptive statistics
    public Map<String, Double> calculateStatistics(List<Double> data) {
        Map<String, Double> statistics = new HashMap<>();

        if (data == null || data.isEmpty()) {
            System.out.println("No data available for analysis.");
            return statistics;
        }

        double sum = data.stream().mapToDouble(Double::doubleValue).sum();
        double mean = sum / data.size();
        double variance = data.stream().mapToDouble(value -> Math.pow(value - mean, 2)).sum() / data.size();
        double stdDev = Math.sqrt(variance);

        statistics.put("Mean", mean);
        statistics.put("Variance", variance);
        statistics.put("Standard Deviation", stdDev);
        System.out.println("Descriptive statistics calculated.");
        return statistics;
    }

    // Method to generate bar chart
    public void createBarChart(Map<String, Integer> data, String title, String xAxisLabel, String yAxisLabel) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        data.forEach((key, value) -> dataset.addValue(value, "Frequency", key));

        JFreeChart barChart = ChartFactory.createBarChart(
                title, xAxisLabel, yAxisLabel, dataset,
                PlotOrientation.VERTICAL, false, true, false);

        ChartFrame chartFrame = new ChartFrame(title, barChart);
        chartFrame.pack();
        chartFrame.setVisible(true);
        System.out.println("Bar chart created.");
    }

    // Method to generate pie chart
    public void createPieChart(Map<String, Integer> data, String title) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        data.forEach(dataset::setValue);

        JFreeChart pieChart = ChartFactory.createPieChart(
                title, dataset, true, true, false);

        ChartFrame chartFrame = new ChartFrame(title, pieChart);
        chartFrame.pack();
        chartFrame.setVisible(true);
        System.out.println("Pie chart created.");
    }

    // Method for market basket analysis (simple example using association rules)
    public Map<String, String> performAssociationRuleMining(List<List<String>> transactions) {
        Map<String, String> rules = new HashMap<>();

        if (transactions == null || transactions.isEmpty()) {
            System.out.println("No transactions available for market basket analysis.");
            return rules;
        }

        // Simple example of creating rules based on item co-occurrence
        for (List<String> transaction : transactions) {
            for (String itemA : transaction) {
                for (String itemB : transaction) {
                    if (!itemA.equals(itemB)) {
                        rules.put(itemA, itemB);
                    }
                }
            }
        }

        System.out.println("Market basket analysis completed.");
        return rules;
    }

    // Method for clustering (example: K-Means Clustering)
    public Map<Integer, List<List<Double>>> runClustering(List<List<Double>> data, int k) {
        Map<Integer, List<List<Double>>> clusters = new HashMap<>();

        if (data == null || data.isEmpty()) {
            System.out.println("No data available for clustering.");
            return clusters;
        }

        // Placeholder for K-Means clustering logic
        // Assume each cluster is represented by an integer and contains a list of points
        for (int i = 0; i < k; i++) {
            clusters.put(i, new ArrayList<>());
        }

        System.out.println("Clustering completed (placeholder logic).");
        return clusters;
    }
}
