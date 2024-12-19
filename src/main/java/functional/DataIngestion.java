package functional;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DataIngestion {
    private List<Map<String, String>> dataset; // Dynamic dataset storage
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/datasets/customer_behavior.db";

    public DataIngestion() {
        this.dataset = new ArrayList<>();
    }

    // Import dataset (supports both CSV and TXT)
    public boolean importData(String filePath, String delimiter) {
        String fileExtension = getFileExtension(filePath);

        if (!fileExtension.equals("csv") && !fileExtension.equals("txt")) {
            System.out.println("Unsupported file format. Please provide a CSV or TXT file.");
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                System.out.println("The file is empty.");
                return false;
            }

            String[] headers = headerLine.split(delimiter);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(delimiter);
                if (values.length != headers.length) continue;

                Map<String, String> row = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    row.put(headers[i].trim(), values[i].trim());
                }
                dataset.add(row);
            }

            System.out.println("Data imported successfully. Total rows: " + dataset.size());
            return true;
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return false;
        }
    }

    // Clean data: Remove rows with missing values
    public void cleanData() {
        System.out.println("Cleaning data...");
        dataset.removeIf(row -> row.values().stream().anyMatch(String::isEmpty));
        System.out.println("Data cleaning completed. Remaining rows: " + dataset.size());
    }

    // Store data into the database
    public void storeData(String tableName) {
        if (dataset.isEmpty()) {
            System.out.println("Dataset is empty. Nothing to store.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            createTable(connection, tableName);

            for (Map<String, String> row : dataset) {
                String sql = buildInsertQuery(tableName, row);
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.executeUpdate();
                }
            }
            System.out.println("Data stored successfully in the table: " + tableName);
        } catch (SQLException e) {
            System.out.println("Error storing data in the database: " + e.getMessage());
        }
    }

    // Helper method to dynamically create a table
    private void createTable(Connection connection, String tableName) throws SQLException {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (");
        for (String column : dataset.get(0).keySet()) {
            sql.append(column).append(" TEXT, ");
        }
        sql.delete(sql.length() - 2, sql.length()).append(");");

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql.toString());
            System.out.println("Table '" + tableName + "' created or already exists.");
        }
    }

    // Helper method to build dynamic insert queries
    private String buildInsertQuery(String tableName, Map<String, String> row) {
        String columns = String.join(", ", row.keySet());
        String values = row.values().stream()
                .map(value -> "'" + value.replace("'", "''") + "'")
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        return "INSERT OR IGNORE INTO " + tableName + " (" + columns + ") VALUES (" + values + ");";
    }

    // Helper to get file extension
    private String getFileExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
    }

    // Test the DataIngestion class
    public static void main(String[] args) {
        DataIngestion ingestion = new DataIngestion();
        String csvPath = "src/main/resources/datasets/sample_data.csv";
        String txtPath = "src/main/resources/datasets/sample_data.txt";
        String tableName = "CustomerData";

        System.out.println("=== Importing CSV File ===");
        if (ingestion.importData(csvPath, ",")) {
            ingestion.cleanData();
            ingestion.storeData(tableName);
        }

        System.out.println("\n=== Importing TXT File ===");
        if (ingestion.importData(txtPath, "\\t")) { // Assuming tab-delimited TXT file
            ingestion.cleanData();
            ingestion.storeData(tableName);
        }
    }
}