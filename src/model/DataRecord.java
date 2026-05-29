package model;

import java.util.LinkedHashMap;
import java.util.Map;

// uso LinkedHashMap pra manter a ordem das colunas
public class DataRecord {

    private int lineNumber;
    private Map<String, String> values;
    private boolean valid;
    private String invalidReason;

    public DataRecord(int lineNumber) {
        this.lineNumber = lineNumber;
        this.values = new LinkedHashMap<>();
        this.valid = true;
        this.invalidReason = "";
    }

    public void addValue(String columnName, String value) {
        values.put(columnName, value);
    }

    public String getValue(String columnName) {
        return values.get(columnName);
    }

    public Map<String, String> getValues() { return values; }
    public int getLineNumber() { return lineNumber; }
    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }
    public String getInvalidReason() { return invalidReason; }
    public void setInvalidReason(String reason) { this.invalidReason = reason; }

    @Override
    public String toString() {
        return "Linha " + lineNumber + ": " + values;
    }
}
