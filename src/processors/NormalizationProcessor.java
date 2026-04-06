package processors;

import model.DataFile;
import model.DataRecord;
import pipeline.Processor;

// padroniza os valores de cada registro
// texto vai pra maiúsculo, espaços são removidos
public class NormalizationProcessor implements Processor {

    @Override
    public DataFile process(DataFile dataFile) {
        int count = 0;

        for (DataRecord record : dataFile.getRecords()) {
            normalize(record, "make");
            normalize(record, "model");
            trimField(record, "year");
            trimField(record, "condition");
            trimField(record, "sellingprice");
            trimField(record, "saledate");
            count++;
        }

        System.out.println("   " + count + " registro(s) normalizados.");
        return dataFile;
    }

    // converte texto pra maiúsculo e remove espaços
    private void normalize(DataRecord record, String column) {
        String value = record.getValue(column);
        if (value != null && !value.isEmpty()) {
            record.addValue(column, value.trim().toUpperCase());
        }
    }

    // só remove espaços
    private void trimField(DataRecord record, String column) {
        String value = record.getValue(column);
        if (value != null) {
            record.addValue(column, value.trim());
        }
    }

    @Override
    public String getName() {
        return "NormalizationProcessor (Normalização)";
    }
}
