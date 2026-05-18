import model.DataFile;
import pipeline.DataPipeline;
import pipeline.SequentialStrategy;
import processor.loader.CsvLoaderProcessor;
import processor.normalization.NormalizationProcessor;
import processor.classification.ClassificationProcessor;
import processor.validation.ValidationProcessor;
import processor.report.ReportProcessor;

public class Main {

    public static void main(String[] args) {

        String csvPath = "data/car_sales.csv";
        DataFile dataFile = new DataFile("car_sales.csv");

        // usa a estrategia sequencial - padrao Strategy
        DataPipeline pipeline = new DataPipeline(new SequentialStrategy());
        pipeline.addProcessor(new CsvLoaderProcessor(csvPath));
        pipeline.addProcessor(new NormalizationProcessor());
        pipeline.addProcessor(new ClassificationProcessor());
        pipeline.addProcessor(new ValidationProcessor());
        pipeline.addProcessor(new ReportProcessor());

        pipeline.run(dataFile);
    }
}