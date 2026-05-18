package pipeline;

import java.util.ArrayList;
import java.util.List;
import model.DataFile;

// monta e executa as etapas do pipeline
// usa o padrao Strategy pra decidir a ordem dos processadores
public class DataPipeline {

    private List<Processor> processors = new ArrayList<>();
    private PipelineStrategy strategy;

    public DataPipeline(PipelineStrategy strategy) {
        this.strategy = strategy;
    }

    public void addProcessor(Processor processor) {
        processors.add(processor);
    }

    public DataFile run(DataFile dataFile) {
        List<Processor> ordered = strategy.ordenar(processors);

        System.out.println("=== Iniciando Pipeline ===");
        System.out.println("Arquivo: " + dataFile.getFileName());
        System.out.println("Etapas: " + ordered.size());
        System.out.println();

        for (Processor processor : ordered) {
            System.out.println(">> Executando: " + processor.getName());
            dataFile = processor.process(dataFile);
            System.out.println();
        }

        System.out.println("=== Pipeline Concluido ===\n");
        return dataFile;
    }
}