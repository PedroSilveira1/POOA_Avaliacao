package pipeline;

import java.util.ArrayList;
import java.util.List;
import model.DataFile;

// monta e executa as etapas do pipeline em sequência
// o resultado de uma etapa vira entrada da próxima

public class DataPipeline {

    private List<Processor> processors = new ArrayList<>();

    public void addProcessor(Processor processor) {
        processors.add(processor);
    }

    public DataFile run(DataFile dataFile) {
        System.out.println("=== Iniciando Pipeline ===");
        System.out.println("Arquivo: " + dataFile.getFileName());
        System.out.println("Etapas: " + processors.size());
        System.out.println();

        for (Processor processor : processors) {
            System.out.println(">> Executando: " + processor.getName());
            dataFile = processor.process(dataFile);
            System.out.println();
        }

        System.out.println("=== Pipeline Concluído ===\n");
        return dataFile;
    }
}
