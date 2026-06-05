package pipeline;

import model.DataFile;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// gerencia o processamento paralelo de multiplos arquivos
// cada arquivo roda em uma thread separada ao mesmo tempo
public class PipelineExecutor {

    private int numThreads;

    public PipelineExecutor(int numThreads) {
        this.numThreads = numThreads;
    }

    public void executar(List<String> caminhos) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<?>> tarefas = new ArrayList<>();

        for (String caminho : caminhos) {
            // cada arquivo salva num banco separado
            String dbPath = caminho.replace(".csv", ".db");

            Future<?> tarefa = executor.submit(() -> {
                try {
                    DataFile dataFile = new DataFile(caminho);

                    ProcessorScanner scanner = new ProcessorScanner(caminho, dbPath);
                    List<Processor> processadores = scanner.descobrir();

                    DataPipeline pipeline = new DataPipeline(new SequentialStrategy());
                    for (Processor p : processadores) {
                        pipeline.addProcessor(p);
                    }

                    pipeline.run(dataFile);

                } catch (Exception e) {
                    System.out.println("Erro ao processar " + caminho + ": " + e.getMessage());
                }
            });

            tarefas.add(tarefa);
        }

        for (Future<?> tarefa : tarefas) {
            tarefa.get();
        }

        executor.shutdown();
        System.out.println("\nTodas as threads finalizadas.");
    }
}