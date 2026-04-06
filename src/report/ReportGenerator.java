package report;

import model.DataColumn;
import model.DataFile;
import model.DataRecord;

// gera o relatório final depois que o pipeline termina
public class ReportGenerator {

    public void generate(DataFile dataFile) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║           RELATÓRIO FINAL                ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println();

        // informações gerais do arquivo
        System.out.println("Arquivo:          " + dataFile.getFileName());
        System.out.println("Total de colunas: " + dataFile.getTotalColumns());
        System.out.println("Total de linhas:  " + dataFile.getTotalLines());
        System.out.println();

        // descrição de cada coluna: nome, tipo e quantos valores preenchidos
        System.out.println("--- Descrição das Colunas ---");
        for (DataColumn column : dataFile.getColumns()) {
            int filled = countFilled(column.getName(), dataFile.getRecords());
            System.out.printf("   %-15s | Tipo: %-8s | Preenchidos: %d/%d%n",
                column.getName(), column.getType(), filled, dataFile.getTotalLines());
        }
        System.out.println();

        // registros inválidos — mostra só os 10 primeiros pra não poluir o console
        System.out.println("--- Registros Inválidos ---");
        int invalidCount = 0;
        int shown = 0;
        for (DataRecord record : dataFile.getRecords()) {
            if (!record.isValid()) {
                invalidCount++;
                if (shown < 10) {
                    System.out.println("   Linha " + record.getLineNumber() + ": " + record.getInvalidReason());
                    shown++;
                }
            }
        }
        if (invalidCount > 10) {
            System.out.println("   ... e mais " + (invalidCount - 10) + " registros inválidos.");
        }
        if (invalidCount == 0) {
            System.out.println("   Nenhum registro inválido encontrado.");
        }

        System.out.println();
        System.out.println("══════════════════════════════════════════");
    }

    // conta quantas celulas dessa coluna estão preenchidas
    private int countFilled(String columnName, java.util.List<DataRecord> records) {
        int count = 0;
        for (DataRecord record : records) {
            String value = record.getValue(columnName);
            if (value != null && !value.isEmpty()) count++;
        }
        return count;
    }
}
