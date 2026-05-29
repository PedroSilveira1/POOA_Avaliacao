import java.util.List;
import model.DataFile;
import pipeline.DataPipeline;
import pipeline.Processor;
import pipeline.ProcessorScanner;
import pipeline.SequentialStrategy;

public class Main {

    public static void main(String[] args) throws Exception {

        String csvPath = "data/car_sales.csv";

        // scanner descobre e ordena os processadores automaticamente via reflection
        ProcessorScanner scanner = new ProcessorScanner(csvPath);
        List<Processor> processadores = scanner.descobrir();

        System.out.println("Processadores descobertos: " + processadores.size());
        System.out.println();

        DataFile dataFile = new DataFile("car_sales.csv");

        DataPipeline pipeline = new DataPipeline(new SequentialStrategy());
        for (Processor p : processadores) {
            pipeline.addProcessor(p);
        }

        pipeline.run(dataFile);
    }
}