package functional;

import weka.associations.Apriori;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.trees.RandomForest;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class MLAnalysis {

    private ReportingAndDashboards reporting;

    public MLAnalysis(ReportingAndDashboards reporting) {
        this.reporting = reporting;
    }

    // 1. Customer Churn Prediction
    public void predictChurn(String filePath) {
        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(filePath);
            Instances dataset = source.getDataSet();
            if (dataset.classIndex() == -1) dataset.setClassIndex(dataset.numAttributes() - 1);

            RandomForest model = new RandomForest();
            model.setNumTrees(100);
            model.buildClassifier(dataset);

            Evaluation eval = new Evaluation(dataset);
            eval.crossValidateModel(model, dataset, 10, new Random(1));

            Map<String, Object> results = Map.of(
                "Accuracy", eval.pctCorrect(),
                "Summary", eval.toSummaryString()
            );
            reporting.addChurnPredictionResults(results);

        } catch (Exception e) {
            System.err.println("Error predicting churn: " + e.getMessage());
        }
    }

    // 2. Customer Lifetime Value (CLV) Prediction
    public void predictCLV(String filePath) {
        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(filePath);
            Instances dataset = source.getDataSet();
            if (dataset.classIndex() == -1) dataset.setClassIndex(dataset.numAttributes() - 1);

            Remove remove = new Remove();
            remove.setAttributeIndices("1");
            remove.setInputFormat(dataset);
            Instances filteredData = Filter.useFilter(dataset, remove);

            LinearRegression model = new LinearRegression();
            model.buildClassifier(filteredData);

            Evaluation eval = new Evaluation(filteredData);
            eval.crossValidateModel(model, filteredData, 10, new Random(1));

            Map<String, Object> results = Map.of(
                "RegressionModel", model.toString(),
                "MAE", eval.meanAbsoluteError(),
                "RMSE", eval.rootMeanSquaredError()
            );
            reporting.addCLVResults(results);

        } catch (Exception e) {
            System.err.println("Error predicting CLV: " + e.getMessage());
        }
    }

    // 3. Personalized Recommendations
    public void generateRecommendations(String filePath, String customerID) {
        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(filePath);
            Instances data = source.getDataSet();

            Apriori apriori = new Apriori();
            apriori.buildAssociations(data);

            // Filter recommendations for the specific customer
            List<String> customerProducts = getCustomerProducts(data, customerID);
            List<String> recommendations = filterRecommendations(apriori, customerProducts);

            reporting.addRecommendations(customerID, recommendations);

        } catch (Exception e) {
            System.err.println("Error generating recommendations: " + e.getMessage());
        }
    }

    private List<String> getCustomerProducts(Instances data, String customerID) {
        // Mock logic for retrieving customer products
        return List.of("ProductA", "ProductB");
    }

    private List<String> filterRecommendations(Apriori apriori, List<String> customerProducts) {
        // Mock filtering logic based on Apriori rules
        return List.of("ProductC", "ProductD");
    }

    // 4. Time-Series Trend Analysis
    public void analyzeTrends(String filePath) {
        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(filePath);
            Instances dataset = source.getDataSet();

            Remove remove = new Remove();
            remove.setAttributeIndices("1");
            remove.setInputFormat(dataset);
            Instances numericData = Filter.useFilter(dataset, remove);

            SimpleKMeans kMeans = new SimpleKMeans();
            kMeans.setNumClusters(3);
            kMeans.buildClusterer(numericData);

            Map<String, Object> trends = Map.of(
                "Clusters", kMeans.getClusterCentroids()
            );
            reporting.addTrends(trends);

        } catch (Exception e) {
            System.err.println("Error analyzing trends: " + e.getMessage());
        }
    }
}


