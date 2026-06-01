import java.util.Arrays;
import java.util.List;
import pipeline.PipelineExecutor;

public class Main {

    public static void main(String[] args) throws Exception {

        // lista de arquivos a processar em paralelo
        List<String> arquivos = Arrays.asList(
            "data/car_sales.csv",
            "data/car_sales_2.csv",
            "data/car_sales_3.csv"
        );

        // 3 threads - uma por arquivo
        PipelineExecutor executor = new PipelineExecutor(3);
        executor.executar(arquivos, "");

        System.out.println("Processamento paralelo concluido!");
    }
}