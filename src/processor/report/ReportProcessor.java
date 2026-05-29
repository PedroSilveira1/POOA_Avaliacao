package processor.report;

import annotation.Processo;
import model.DataColumn;
import model.DataFile;
import model.DataRecord;
import pipeline.Processor;

@Processo(ordem = 5)
public class ReportProcessor implements Processor {

    @Override
    public DataFile process(DataFile dataFile) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║           RELATORIO FINAL                ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println();

        System.out.println("Arquivo:          " + dataFile.getFileName());
        System.out.println("Total de colunas: " + dataFile.getTotalColumns());
        System.out.println("Total de linhas:  " + dataFile.getTotalLines());
        System.out.println();

        System.out.println("--- Descricao das Colunas ---");
        for (DataColumn column : dataFile.getColumns()) {
            int filled = countFilled(column.getName(), dataFile);
            System.out.printf("   %-15s | Tipo: %-8s | Preenchidos: %d/%d%n",
                column.getName(), column.getType(), filled, dataFile.getTotalLines());
        }
        System.out.println();

        System.out.println("--- Registros Invalidos ---");
        int invalidCount = 0, shown = 0;
        for (DataRecord record : dataFile.getRecords()) {
            if (!record.isValid()) {
                invalidCount++;
                if (shown < 10) {
                    System.out.println("   Linha " + record.getLineNumber() + ": " + record.getInvalidReason());
                    shown++;
                }
            }
        }
        if (invalidCount > 10)
            System.out.println("   ... e mais " + (invalidCount - 10) + " registros invalidos.");
        if (invalidCount == 0)
            System.out.println("   Nenhum registro invalido encontrado.");

        System.out.println();
        System.out.println("══════════════════════════════════════════");

        return dataFile;
    }

    private int countFilled(String columnName, DataFile dataFile) {
        int count = 0;
        for (DataRecord record : dataFile.getRecords()) {
            String value = record.getValue(columnName);
            if (value != null && !value.isEmpty()) count++;
        }
        return count;
    }

    @Override
    public String getName() { return "ReportProcessor (Relatorio)"; }
}