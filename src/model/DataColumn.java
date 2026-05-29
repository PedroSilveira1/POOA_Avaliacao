package model;

public class DataColumn {

    private String name;
    private String type;

    public DataColumn(String name) {
        this.name = name;
        this.type = "UNKNOWN";
    }

    public String getName() { return name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return name + " [" + type + "]";
    }
}
