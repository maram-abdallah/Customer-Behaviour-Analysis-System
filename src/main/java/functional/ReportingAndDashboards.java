package functional;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class ReportingAndDashboards {

    // Generate a report in the specified format
    public void generateReport(String format, Map<String, Object> metrics) {
        switch (format.toLowerCase()) {
            case "pdf":
                generatePDFReport(metrics);
                break;
            case "excel":
                generateExcelReport(metrics);
                break;
            default:
                System.out.println("Unsupported report format: " + format);
        }
    }

    // Create a basic dashboard with a bar chart
    public void createDashboard(Map<String, Integer> metrics) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        metrics.forEach((key, value) -> dataset.addValue(value, "Value", key));

        JFreeChart barChart = ChartFactory.createBarChart(
                "Dashboard Metrics", "Category", "Value",
                dataset
        );

        ChartFrame chartFrame = new ChartFrame("Dashboard", barChart);
        chartFrame.pack();
        chartFrame.setVisible(true);

        System.out.println("Dashboard created.");
    }

    // Export a dashboard to an image file (placeholder logic)
    public void exportDashboard(String filePath) {
        System.out.println("Dashboard exported to: " + filePath);
        // Logic for exporting dashboard as an image file can be implemented here
    }

    // Helper method to generate a PDF report (placeholder logic)
    private void generatePDFReport(Map<String, Object> metrics) {
        System.out.println("Generating PDF report...");
        metrics.forEach((key, value) -> System.out.println(key + ": " + value));
        System.out.println("PDF report generation completed.");
        // Use libraries like iText or Apache PDFBox for actual PDF report generation
    }

    // Helper method to generate an Excel report
    private void generateExcelReport(Map<String, Object> metrics) {
        System.out.println("Generating Excel report...");

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Report");

            int rowNum = 0;
            for (Map.Entry<String, Object> entry : metrics.entrySet()) {
                sheet.createRow(rowNum).createCell(0).setCellValue(entry.getKey());
                sheet.getRow(rowNum).createCell(1).setCellValue(entry.getValue().toString());
                rowNum++;
            }

            try (FileOutputStream out = new FileOutputStream("report.xlsx")) {
                workbook.write(out);
            }

            System.out.println("Excel report saved as 'report.xlsx'.");
        } catch (IOException e) {
            System.out.println("Error generating Excel report: " + e.getMessage());
        }
    }
}
