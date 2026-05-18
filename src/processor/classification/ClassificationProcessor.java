package processor.classification;

import java.util.List;
import model.DataColumn;
import model.DataFile;
import model.DataRecord;
import pipeline.Processor;

// descobre o tipo de cada coluna analisando os valores
public class ClassificationProcessor implements Processor {

    @Override
    public DataFile process(DataFile dataFile) {
        List<DataColumn> columns = dataFile.getColumns();
        List<DataRecord> records = dataFile.getRecords();

        for (DataColumn column : columns) {
            column.setType(discoverType(column.getName(), records));
        }

        System.out.println("   --- Tipos descobertos por coluna ---");
        for (DataColumn column : columns) {
            System.out.println("   " + column);
        }

        return dataFile;
    }

    private String discoverType(String columnName, List<DataRecord> records) {
        int intCount = 0, decimalCount = 0, dateCount = 0, textCount = 0;

        for (DataRecord record : records) {
            String value = record.getValue(columnName);
            if (value == null || value.isEmpty()) continue;

            if (isDate(value))         dateCount++;
            else if (isInteger(value)) intCount++;
            else if (isDecimal(value)) decimalCount++;
            else                       textCount++;
        }

        if (dateCount > 0) return "DATE";
        if (intCount > 0 && decimalCount == 0 && textCount == 0) return "INTEGER";
        if (decimalCount > 0 && textCount == 0) return "DECIMAL";
        if (textCount > 0) return "TEXT";
        return "UNKNOWN";
    }

    private boolean isInteger(String value) {
        try { Integer.parseInt(value); return true; }
        catch (NumberFormatException e) { return false; }
    }

    private boolean isDecimal(String value) {
        try { Double.parseDouble(value); return true; }
        catch (NumberFormatException e) { return false; }
    }

    private boolean isDate(String value) {
        return value.startsWith("Mon") || value.startsWith("Tue") ||
               value.startsWith("Wed") || value.startsWith("Thu") ||
               value.startsWith("Fri") || value.startsWith("Sat") ||
               value.startsWith("Sun");
    }

    @Override
    public String getName() { return "ClassificationProcessor (Classificacao)"; }
}