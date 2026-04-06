package processors;

import java.io.BufferedReader;
import java.io.FileReader;
import model.DataColumn;
import model.DataFile;
import model.DataRecord;
import pipeline.Processor;

// le o arquivo csv e monta o DataFile
// primeira linha = colunas, restante = registros
public class CsvLoaderProcessor implements Processor {

    private String filePath;

    public CsvLoaderProcessor(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public DataFile process(DataFile dataFile) {
        System.out.println("   Lendo arquivo: " + filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            // le o cabeçalho
            String headerLine = reader.readLine();
            if (headerLine == null) {
                System.out.println("   ERRO: arquivo vazio.");
                return dataFile;
            }

            String[] columnNames = headerLine.split(",");
            for (String name : columnNames) {
                dataFile.addColumn(new DataColumn(name.trim()));
            }

            // le as linhas de dados
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] cells = line.split(",", -1);

                DataRecord record = new DataRecord(lineNumber);

                for (int i = 0; i < columnNames.length; i++) {
                    String columnName = columnNames[i].trim();
                    String value = (i < cells.length) ? cells[i].trim() : "";
                    record.addValue(columnName, value);
                }

                dataFile.addRecord(record);
            }

            System.out.println("   Colunas carregadas: " + dataFile.getTotalColumns());
            System.out.println("   Linhas carregadas:  " + dataFile.getTotalLines());

        } catch (Exception e) {
            System.out.println("   ERRO ao ler o arquivo: " + e.getMessage());
        }

        return dataFile;
    }

    @Override
    public String getName() {
        return "CsvLoaderProcessor (Carga)";
    }
}
