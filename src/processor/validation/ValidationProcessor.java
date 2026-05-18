package processor.validation;

import java.util.List;
import model.DataColumn;
import model.DataFile;
import model.DataRecord;
import pipeline.Processor;

// valida cada registro com base nos tipos descobertos na classificacao
public class ValidationProcessor implements Processor {

    @Override
    public DataFile process(DataFile dataFile) {
        List<DataColumn> columns = dataFile.getColumns();
        int validCount = 0, invalidCount = 0;

        for (DataRecord record : dataFile.getRecords()) {
            boolean recordValid = true;
            StringBuilder reasons = new StringBuilder();

            for (DataColumn column : columns) {
                String value = record.getValue(column.getName());
                if (!isValid(value, column.getType())) {
                    recordValid = false;
                    reasons.append(column.getName()).append(" invalido (")
                           .append(column.getType()).append("); ");
                }
            }

            record.setValid(recordValid);
            if (!recordValid) {
                record.setInvalidReason(reasons.toString());
                invalidCount++;
            } else {
                validCount++;
            }
        }

        System.out.println("   Registros validos:   " + validCount);
        System.out.println("   Registros invalidos: " + invalidCount);
        return dataFile;
    }

    private boolean isValid(String value, String type) {
        if (value == null || value.isEmpty()) return false;
        switch (type) {
            case "INTEGER":
                try { Integer.parseInt(value); return true; }
                catch (NumberFormatException e) { return false; }
            case "DECIMAL":
                try { Double.parseDouble(value); return true; }
                catch (NumberFormatException e) { return false; }
            case "TEXT": return !value.isBlank();
            case "DATE": return !value.isEmpty();
            default: return true;
        }
    }

    @Override
    public String getName() { return "ValidationProcessor (Validacao)"; }
}