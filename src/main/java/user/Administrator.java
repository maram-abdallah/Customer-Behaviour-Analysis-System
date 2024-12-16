package user;

import functional.DataIngestion;
import functional.ReportingAndDashboards;

import java.util.Map;

public class Administrator extends User {

    public Administrator(String userID, String username) {
        super(userID, username);
    }

    @Override
    public void login() {
        System.out.println("Administrator " + username + " logged in.");
    }

    @Override
    public void logout() {
        System.out.println("Administrator " + username + " logged out.");
    }

    // Method to upload a dataset
    public void uploadDataset(String filePath, DataIngestion dataIngestion) {
        if (dataIngestion.importData(filePath)) {
            System.out.println("Dataset uploaded successfully.");
        } else {
            System.out.println("Failed to upload dataset.");
        }
    }

    // Method to clean datasets
    public void cleanDataset(DataIngestion dataIngestion) {
        System.out.println("Cleaning dataset...");
        dataIngestion.cleanData();
    }

    // Method to store datasets into the database
    public void storeDataset(DataIngestion dataIngestion, String databaseUrl, String tableName) {
        System.out.println("Storing dataset...");
        dataIngestion.storeData(databaseUrl, tableName);
    }

    // Method to generate reports
    public void generateReport(ReportingAndDashboards reporting, String format, Map<String, Object> metrics) {
        System.out.println("Generating report in " + format + " format...");
        reporting.generateReport(format, metrics);
    }
}
