package user;

import functional.DataAnalysis;
import functional.MLAnalysis;
import functional.ReportingAndDashboards;

import java.util.List;
import java.util.Map;

public class MarketingDataAnalyst extends User {

    public MarketingDataAnalyst(String userID, String username) {
        super(userID, username);
    }

    @Override
    public void performRole() {
        System.out.println("Marketing Data Analyst role: Analyze data and generate insights.");
    }

    // Descriptive Analysis
    public void performDescriptiveAnalysis(DataAnalysis dataAnalysis, Map<String, Integer> data) {
        System.out.println("Performing descriptive analysis...");
        Map<String, Double> stats = dataAnalysis.calculateStatistics(data);
        stats.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    // Generate Visualizations
    public void generateVisualizations(DataAnalysis dataAnalysis, Map<String, Integer> data) {
        System.out.println("Generating visualizations...");
        dataAnalysis.createBarChart(data, "Category Distribution", "Category", "Value");
    }

    // Run Churn Prediction
    public void runChurnPrediction(MLAnalysis mlAnalysis, List<Map<String, String>> data) {
        System.out.println("Running churn prediction...");
        Map<String, Double> predictions = mlAnalysis.predictChurn(data);
        predictions.forEach((customerID, churnProb) -> 
            System.out.println("Customer " + customerID + " churn probability: " + churnProb));
    }

    // Perform Segmentation
    public void performSegmentation(DataAnalysis dataAnalysis, List<List<Double>> data) {
        System.out.println("Performing customer segmentation...");
        List<List<Double>> clusters = dataAnalysis.runClustering(data, 3);
        clusters.forEach(cluster -> System.out.println("Cluster: " + cluster));
    }

    // Generate Dashboard
    public void generateDashboard(ReportingAndDashboards reporting, Map<String, Integer> metrics) {
        System.out.println("Generating dashboard...");
        reporting.createDashboard(metrics);
    }
}
