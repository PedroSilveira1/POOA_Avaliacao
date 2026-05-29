package model;

import java.util.ArrayList;
import java.util.List;


public class DataFile {

    private String fileName;
    private List<DataColumn> columns;
    private List<DataRecord> records;

    public DataFile(String fileName) {
        this.fileName = fileName;
        this.columns = new ArrayList<>();
        this.records = new ArrayList<>();
    }

    public void addColumn(DataColumn column) { columns.add(column); }
    public void addRecord(DataRecord record) { records.add(record); }

    public String getFileName() { return fileName; }
    public List<DataColumn> getColumns() { return columns; }
    public List<DataRecord> getRecords() { return records; }
    public int getTotalColumns() { return columns.size(); }
    public int getTotalLines() { return records.size(); }
}
