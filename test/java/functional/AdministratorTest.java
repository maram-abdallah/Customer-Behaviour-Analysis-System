package user;

import functional.DataIngestion;
import functional.ReportingAndDashboards;

import java.util.HashMap;
import java.util.Map;

public class AdministratorTest {
    public static void main(String[] args) {
        // Initialize Administrator
        Administrator admin = new Administrator("Admin001", "AdminUser");

        // Initialize Functional Classes
        DataIngestion dataIngestion = new DataIngestion();
        ReportingAndDashboards reporting = new ReportingAndDashboards();

        // Test Login and Logout
        admin.login();
        admin.logout();

        // Test Dataset Management
        String filePath = "src/main/resources/datasets/sample_data.csv";
        admin.uploadDataset(filePath, dataIngestion);
        admin.cleanDataset(dataIngestion);

        String databaseUrl = "jdbc:sqlite:src/main/resources/datasets/customer_behavior.db";
        String tableName = "CustomerData";
        admin.storeDataset(dataIngestion, databaseUrl, tableName);

        // Test Report Generation
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("Total Sales", 1000);
        metrics.put("Churn Rate", 0.12);
        admin.generateReport(reporting, "PDF", metrics);
    }
}
