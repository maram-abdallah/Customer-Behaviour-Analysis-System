package user;

import functional.DataAnalysis;
import functional.MLAnalysis;
import functional.ReportingAndDashboards;

import java.util.Map;

public class MarketingDataAnalyst extends User {

    public MarketingDataAnalyst(String userID, String username) {
        super(userID, username);
    }

    @Override
    public void login() {
        System.out.println("Marketing Analyst " + username + " logged in.");
    }

    @Override
    public void logout() {
        System.out.println("Marketing Analyst " + username + " logged out.");
    }

    // Perform descriptive statistics
    public void performDescriptiveAnalysis(DataAnalysis dataAnalysis, Map<String, Integer> data) {
        System.out.println("Performing descriptive statistics...");
        Map<String, Double> stats = dataAnalysis.calculateStatistics(data.values().stream().mapToDouble(Integer::doubleValue).boxed().toList());
        stats.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    // Generate visualizations
    public void generateVisualizations(DataAnalysis dataAnalysis, Map<String, Integer> metrics) {
        System.out.println("Generating bar chart...");
        dataAnalysis.createBarChart(metrics, "Sales Metrics", "Categories", "Sales");
    }

    // Perform churn prediction
    public void runChurnPrediction(MLAnalysis mlAnalysis, List<Map<String, String>> dataset) {
        System.out.println("Running churn prediction...");
        Map<String, Double> churnPredictions = mlAnalysis.predictChurn(dataset);
        churnPredictions.forEach((key, value) -> System.out.println("Customer " + key + " Churn Probability: " + value));
    }

    // Perform customer segmentation
    public void performSegmentation(DataAnalysis dataAnalysis, List<List<Double>> data) {
        System.out.println("Performing customer segmentation...");
        Map<Integer, List<List<Double>>> clusters = dataAnalysis.runClustering(data, 3);
        clusters.forEach((key, value) -> System.out.println("Cluster " + key + ": " + value));
    }

    // Generate reports
    public void generateReport(ReportingAndDashboards reporting, String format, Map<String, Object> metrics) {
        System.out.println("Generating report in " + format + " format...");
        reporting.generateReport(format, metrics);
    }
}
