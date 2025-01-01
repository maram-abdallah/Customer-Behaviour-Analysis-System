package main;

import functional.*;
import user.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Initialize Functional Classes
        DataIngestion dataIngestion = new DataIngestion();
        DataAnalysis dataAnalysis = new DataAnalysis();
        ReportingAndDashboards reporting = new ReportingAndDashboards();
        MLAnalysis mlAnalysis = new MLAnalysis(reporting);

        // Initialize User Roles
        Administrator admin = new Administrator("A001", "AdminUser");
        MarketingDataAnalyst analyst = new MarketingDataAnalyst("M001", "MarketingUser");

        // File and Database Configurations ,,,,, check path name !!!!!!!!!!!! aaaaaa maram 
        String csvFilePath = "src/main/resources/datasets/sample_data.csv";
        String tableName = "CustomerBehaviorData";
        String dbUrl = "jdbc:sqlite:src/main/resources/datasets/customer_behavior.db";

        // Administrator Workflow
        System.out.println("\n=== Administrator Workflow ===");
        admin.login();
        admin.uploadDataset(csvFilePath, dataIngestion);
        admin.cleanDataset(dataIngestion);
        admin.storeDataset(dataIngestion, dbUrl, tableName);

        Map<String, Object> adminMetrics = Map.of(
                "TotalRows", 1000,
                "CleanedRows", 950,
                "OutliersRemoved", 50
        );
        admin.generateReport(reporting, "pdf", adminMetrics);
        admin.logout();

        // Marketing Data Analyst Workflow
        System.out.println("\n=== Marketing Data Analyst Workflow ===");
        analyst.login();

        // Perform descriptive statistics
        List<Double> salesData = Arrays.asList(500.0, 450.0, 600.0, 700.0, 550.0);
        analyst.performDescriptiveAnalysis(dataAnalysis, salesData);

        // Generate visualizations
        Map<String, Integer> productMetrics = Map.of(
                "Electronics", 120,
                "Fashion", 80,
                "Home & Kitchen", 90
        );
        analyst.generateVisualizations(dataAnalysis, productMetrics);

        // Run machine learning models
        analyst.runChurnPrediction(mlAnalysis, csvFilePath);
        analyst.performSegmentation(dataAnalysis, csvFilePath);

        // Generate a report ,,,,, CHECK IT 
        Map<String, Object> analystMetrics = Map.of(
                "ChurnRate", "20%",
                "TopSegment", "High-Value Customers",
                "TopCategory", "Electronics"
        );
        analyst.generateReport(reporting, "txt", analystMetrics);

        analyst.logout();

        System.out.println("\n=== System Integration Completed Successfully ===");
    }
}
