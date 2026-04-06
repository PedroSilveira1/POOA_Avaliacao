import model.DataFile;
import pipeline.DataPipeline;
import processors.ClassificationProcessor;
import processors.CsvLoaderProcessor;
import processors.NormalizationProcessor;
import processors.ValidationProcessor;
import report.ReportGenerator;

public class Main {

    public static void main(String[] args) {

        String csvPath = "data/car_sales.csv";

        DataFile dataFile = new DataFile("car_sales.csv");

        // monta o pipeline na ordem definida: carga -> normalização -> classificação -> validação
        
        DataPipeline pipeline = new DataPipeline();
        pipeline.addProcessor(new CsvLoaderProcessor(csvPath));
        pipeline.addProcessor(new NormalizationProcessor());
        pipeline.addProcessor(new ClassificationProcessor());
        pipeline.addProcessor(new ValidationProcessor());

        DataFile resultado = pipeline.run(dataFile);

        new ReportGenerator().generate(resultado);
    }
}
