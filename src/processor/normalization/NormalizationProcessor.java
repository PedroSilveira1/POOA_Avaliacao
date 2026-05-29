package processor.normalization;

import annotation.Processo;
import model.DataFile;
import model.DataRecord;
import pipeline.Processor;

@Processo(ordem = 2)
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

    private void normalize(DataRecord record, String column) {
        String value = record.getValue(column);
        if (value != null && !value.isEmpty()) {
            record.addValue(column, value.trim().toUpperCase());
        }
    }

    private void trimField(DataRecord record, String column) {
        String value = record.getValue(column);
        if (value != null) {
            record.addValue(column, value.trim());
        }
    }

    @Override
    public String getName() { return "NormalizationProcessor (Normalizacao)"; }
}