package user;

import functional.DataIngestion;
import functional.ReportingAndDashboards;

import java.util.Map;

public class Administrator extends User {

    public Administrator(String userID, String username) {
        super(userID, username);
    }

    @Override
    public void performRole() {
        System.out.println("Administrator role: Manage datasets and generate reports.");
    }

    // Upload dataset
    public void uploadDataset(String filePath, DataIngestion dataIngestion) {
        System.out.println("Administrator uploading dataset: " + filePath);
        dataIngestion.importData(filePath);
    }

    // Clean dataset
    public void cleanDataset(DataIngestion dataIngestion) {
        System.out.println("Administrator cleaning dataset...");
        dataIngestion.cleanData();
    }

    // Store dataset
    public void storeDataset(DataIngestion dataIngestion, String dbUrl, String tableName) {
        System.out.println("Storing dataset into database...");
        dataIngestion.storeData(dbUrl, tableName);
    }

    // Generate Reports
    public void generateReport(ReportingAndDashboards reporting, String format, Map<String, Object> metrics) {
        System.out.println("Administrator generating report in " + format + " format...");
        reporting.generateReport(format, metrics);
    }
}

