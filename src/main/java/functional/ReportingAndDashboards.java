package functional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ReportingAndDashboards {

    // Generate a report (text or PDF) with metrics
    public void generateReport(String reportName, Map<String, Object> metrics, String format) {
        if (format.equalsIgnoreCase("txt")) {
            generateTextReport(reportName, metrics);
        } else if (format.equalsIgnoreCase("pdf")) {
            generatePDFReport(reportName, metrics);
        } else {
            System.err.println("Unsupported format. Please use 'txt' or 'pdf'.");
        }
    }

    // Generate a text-based report
    private void generateTextReport(String reportName, Map<String, Object> metrics) {
        String reportPath = "src/main/resources/reports/" + reportName + ".txt";
        try (FileWriter writer = new FileWriter(reportPath)) {
            writer.write("========== Customer Behavior Analysis Report ==========\n\n");
            for (Map.Entry<String, Object> entry : metrics.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
            System.out.println("Text report generated successfully: " + reportPath);
        } catch (IOException e) {
            System.err.println("Error generating text report: " + e.getMessage());
        }
    }

    // Generate a PDF-based report
    private void generatePDFReport(String reportName, Map<String, Object> metrics) {
        String reportPath = "src/main/resources/reports/" + reportName + ".pdf";
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setLeading(14.5f);
                contentStream.beginText();
                contentStream.setFont(org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA_BOLD, 14);
                contentStream.newLineAtOffset(50, 750);

                contentStream.showText("Customer Behavior Analysis Report");
                contentStream.newLine();
                contentStream.setFont(org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA, 12);

                // Write metrics to the PDF
                for (Map.Entry<String, Object> entry : metrics.entrySet()) {
                    contentStream.newLine();
                    contentStream.showText(entry.getKey() + ": " + entry.getValue());
                }
                contentStream.endText();
            }

            document.save(reportPath);
            System.out.println("PDF report generated successfully: " + reportPath);
        } catch (IOException e) {
            System.err.println("Error generating PDF report: " + e.getMessage());
        }
    }

    // Create a bar chart dashboard
    public void createBarChartDashboard(Map<String, Integer> data, String title, String categoryAxis, String valueAxis) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        data.forEach((key, value) -> dataset.addValue(value, categoryAxis, key));

        JFreeChart barChart = ChartFactory.createBarChart(
                title, categoryAxis, valueAxis, dataset, PlotOrientation.VERTICAL, true, true, false
        );

        displayChart(barChart, title);
    }

    // Create a pie chart dashboard
    public void createPieChartDashboard(Map<String, Integer> data, String title) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        data.forEach(dataset::setValue);

        JFreeChart pieChart = ChartFactory.createPieChart(
                title, dataset, true, true, false
        );

        displayChart(pieChart, title);
    }

    // Display the chart in a Swing window
    private void displayChart(JFreeChart chart, String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
    }

    // Integration Points for MLAnalysis
    public void addChurnPredictionResults(Map<String, Object> results) {
        System.out.println("=== Churn Prediction Results ===");
        results.forEach((key, value) -> System.out.println(key + ": " + value));
        generateReport("Churn_Prediction", results, "pdf");
    }

    public void addCLVResults(Map<String, Object> results) {
        System.out.println("=== Customer Lifetime Value (CLV) Results ===");
        results.forEach((key, value) -> System.out.println(key + ": " + value));
        generateReport("CLV_Analysis", results, "pdf");
    }

    public void addRecommendations(String customerID, List<String> recommendations) {
        System.out.println("=== Recommendations for Customer " + customerID + " ===");
        recommendations.forEach(System.out::println);

        Map<String, Object> results = Map.of(
                "CustomerID", customerID,
                "Recommendations", String.join(", ", recommendations)
        );
        generateReport("Recommendations_" + customerID, results, "pdf");
    }

    public void addTrends(Map<String, Object> trends) {
        System.out.println("=== Trend Analysis Results ===");
        trends.forEach((key, value) -> System.out.println(key + ": " + value));
        generateReport("Trend_Analysis", trends, "pdf");
    }
}

