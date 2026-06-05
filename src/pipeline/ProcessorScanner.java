package pipeline;

import annotation.Processo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import processor.classification.ClassificationProcessor;
import processor.database.DatabaseWriter;
import processor.loader.CsvLoaderProcessor;
import processor.normalization.NormalizationProcessor;
import processor.report.ReportProcessor;
import processor.validation.ValidationProcessor;

// usa reflection pra descobrir os processadores automaticamente
// ordena pelo campo ordem da annotation @Processo
public class ProcessorScanner {

    private String csvPath;
    private String dbPath;

    public ProcessorScanner(String csvPath, String dbPath) {
        this.csvPath = csvPath;
        this.dbPath = dbPath;
    }

    public List<Processor> descobrir() throws Exception {
        List<Class<?>> candidatos = new ArrayList<>();
        candidatos.add(CsvLoaderProcessor.class);
        candidatos.add(NormalizationProcessor.class);
        candidatos.add(ClassificationProcessor.class);
        candidatos.add(ValidationProcessor.class);
        candidatos.add(ReportProcessor.class);
        candidatos.add(DatabaseWriter.class);

        List<Processor> encontrados = new ArrayList<>();

        for (Class<?> classe : candidatos) {
            if (classe.isAnnotationPresent(Processo.class)) {
                Processo anotacao = classe.getAnnotation(Processo.class);

                Processor instancia;
                if (classe.equals(CsvLoaderProcessor.class)) {
                    instancia = (Processor) classe
                        .getConstructor(String.class)
                        .newInstance(csvPath);
                } else if (classe.equals(DatabaseWriter.class)) {
                    instancia = (Processor) classe
                        .getConstructor(String.class)
                        .newInstance(dbPath);
                } else {
                    instancia = (Processor) classe
                        .getConstructor()
                        .newInstance();
                }

                encontrados.add(instancia);
                System.out.println("   Processador encontrado: " + classe.getSimpleName() + " (ordem " + anotacao.ordem() + ")");
            }
        }

        encontrados.sort(Comparator.comparingInt(p ->
            p.getClass().getAnnotation(Processo.class).ordem()
        ));

        return encontrados;
    }
}