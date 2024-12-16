package functional;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class DataIngestion {
    private List<Map<String, String>> dataset; // Stores the dataset as a list of key-value pairs (column:value)

    public DataIngestion() {
        this.dataset = new ArrayList<>();
    }

    // Import dataset from CSV or TXT file
    public boolean importData(String filePath) {
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

            String[] headers = headerLine.split(",");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> row = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    row.put(headers[i], values[i]);
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

    // Clean and preprocess the dataset
    public void cleanData() {
        System.out.println("Cleaning data...");
        handleMissingValues();
        removeOutliers();
        normalizeNumericalData();
        System.out.println("Data cleaning completed.");
    }

    // Store cleaned data in SQLite database
    public void storeData(String databaseUrl, String tableName) {
        try (Connection conn = DriverManager.getConnection(databaseUrl)) {
            // Create table if not exists
            createTable(conn, tableName);

            // Insert cleaned data
            for (Map<String, String> row : dataset) {
                String sql = buildInsertQuery(tableName, row);
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.executeUpdate();
                }
            }

            System.out.println("Data stored successfully in the database.");
        } catch (SQLException e) {
            System.out.println("Error storing data in the database: " + e.getMessage());
        }
    }

    // Handle missing values by removing rows with missing data
    private void handleMissingValues() {
        dataset = dataset.stream()
                .filter(row -> row.values().stream().noneMatch(String::isEmpty))
                .collect(Collectors.toList());
        System.out.println("Removed rows with missing values.");
    }

    // Detect and remove outliers (example: remove rows with negative values)
    private void removeOutliers() {
        dataset = dataset.stream()
                .filter(row -> row.values().stream().noneMatch(value -> isNegativeNumber(value)))
                .collect(Collectors.toList());
        System.out.println("Removed rows with outliers.");
    }

    // Normalize numerical data (scaling between 0 and 1)
    private void normalizeNumericalData() {
        // Assuming numerical columns are "ProductPrice" and "Quantity"
        List<String> numericalColumns = Arrays.asList("ProductPrice", "Quantity");
        for (String column : numericalColumns) {
            List<Double> values = dataset.stream()
                    .map(row -> Double.parseDouble(row.get(column)))
                    .collect(Collectors.toList());

            double min = Collections.min(values);
            double max = Collections.max(values);

            for (Map<String, String> row : dataset) {
                double originalValue = Double.parseDouble(row.get(column));
                double normalizedValue = (originalValue - min) / (max - min);
                row.put(column, String.valueOf(normalizedValue));
            }
        }
        System.out.println("Normalized numerical data.");
    }

    // Helper method to create a table in the database
    private void createTable(Connection conn, String tableName) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (";
        for (String column : dataset.get(0).keySet()) {
            sql += column + " TEXT, ";
        }
        sql = sql.substring(0, sql.length() - 2) + ");"; // Remove last comma and add closing parenthesis
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    // Helper method to build an SQL insert query
    private String buildInsertQuery(String tableName, Map<String, String> row) {
        String columns = String.join(", ", row.keySet());
        String values = row.values().stream().map(value -> "'" + value + "'").collect(Collectors.joining(", "));
        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ");";
    }

    // Helper method to get the file extension
    private String getFileExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
    }

    // Helper method to check if a string is a negative number
    private boolean isNegativeNumber(String value) {
        try {
            return Double.parseDouble(value) < 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
