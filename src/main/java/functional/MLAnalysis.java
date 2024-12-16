package functional;

import java.util.*;
import java.util.stream.Collectors;

public class MLAnalysis {

    // Predict customer churn using a simple threshold-based rule (placeholder logic)
    public Map<String, Double> predictChurn(List<Map<String, String>> data) {
        Map<String, Double> churnPredictions = new HashMap<>();

        for (Map<String, String> row : data) {
            // Example logic: churn probability based on low total purchase amount
            double totalPurchase = Double.parseDouble(row.get("TotalPurchaseAmount"));
            double churnProbability = totalPurchase < 100 ? 0.8 : 0.2; // Placeholder threshold
            churnPredictions.put(row.get("CustomerID"), churnProbability);
        }

        System.out.println("Churn prediction completed.");
        return churnPredictions;
    }

    // Calculate Customer Lifetime Value (CLV) based on Recency, Frequency, Monetary (RFM) analysis
    public Map<String, Double> calculateCLV(List<Map<String, String>> data) {
        Map<String, Double> clvMap = new HashMap<>();

        for (Map<String, String> row : data) {
            double frequency = Double.parseDouble(row.get("PurchaseFrequency"));
            double monetary = Double.parseDouble(row.get("TotalPurchaseAmount"));
            double recencyScore = Double.parseDouble(row.get("Recency"));

            // Example CLV formula: (Frequency * Monetary) / Recency
            double clv = (frequency * monetary) / (recencyScore + 1); // Avoid division by zero
            clvMap.put(row.get("CustomerID"), clv);
        }

        System.out.println("Customer Lifetime Value calculation completed.");
        return clvMap;
    }

    // Generate product recommendations based on collaborative filtering (placeholder logic)
    public List<String> generateRecommendations(String customerID, Map<String, List<String>> purchaseHistory) {
        List<String> recommendations = new ArrayList<>();

        // Find similar customers based on purchase history
        List<String> currentCustomerPurchases = purchaseHistory.get(customerID);
        if (currentCustomerPurchases == null) {
            System.out.println("No purchase history available for customer ID: " + customerID);
            return recommendations;
        }

        for (Map.Entry<String, List<String>> entry : purchaseHistory.entrySet()) {
            if (!entry.getKey().equals(customerID)) {
                for (String product : entry.getValue()) {
                    if (!currentCustomerPurchases.contains(product)) {
                        recommendations.add(product);
                    }
                }
            }
        }

        // Remove duplicates and return top recommendations
        recommendations = recommendations.stream().distinct().limit(5).collect(Collectors.toList());
        System.out.println("Recommendations generated for customer ID: " + customerID);
        return recommendations;
    }

    // Analyze trends using placeholder time-series analysis logic
    public Map<String, Double> analyzeTrends(List<Map<String, String>> data) {
        Map<String, Double> trends = new HashMap<>();

        // Example: Calculate average monthly sales for each month
        for (Map<String, String> row : data) {
            String month = row.get("PurchaseDate").split("-")[1]; // Extract month from "YYYY-MM-DD"
            double purchaseAmount = Double.parseDouble(row.get("TotalPurchaseAmount"));
            trends.put(month, trends.getOrDefault(month, 0.0) + purchaseAmount);
        }

        System.out.println("Trend analysis completed.");
        return trends;
    }
}

