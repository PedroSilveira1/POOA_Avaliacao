package processor.database;

import annotation.Processo;
import model.DataFile;
import model.DataRecord;
import pipeline.Processor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

// salva os registros validos no banco de dados SQLite
@Processo(ordem = 6)
public class DatabaseWriter implements Processor {

    private String dbPath;

    public DatabaseWriter(String dbPath) {
        this.dbPath = dbPath;
    }

    @Override
    public DataFile process(DataFile dataFile) {
        String url = "jdbc:sqlite:" + dbPath;
        int salvos = 0;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            criarTabela(conn);

            String sql = "INSERT INTO car_sales (year, make, model, condition, sellingprice, saledate) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (DataRecord record : dataFile.getRecords()) {
                    if (!record.isValid()) continue;

                    stmt.setString(1, record.getValue("year"));
                    stmt.setString(2, record.getValue("make"));
                    stmt.setString(3, record.getValue("model"));
                    stmt.setString(4, record.getValue("condition"));
                    stmt.setString(5, record.getValue("sellingprice"));
                    stmt.setString(6, record.getValue("saledate"));
                    stmt.addBatch();
                    salvos++;
                }
                stmt.executeBatch();
            }

            conn.close();
            System.out.println("   Registros salvos no banco: " + salvos);
            System.out.println("   Arquivo: " + dbPath);

        } catch (Exception e) {
            System.out.println("   ERRO ao salvar no banco: " + e.getMessage());
        }

        return dataFile;
    }

    private void criarTabela(Connection conn) throws Exception {
        String sql = "CREATE TABLE IF NOT EXISTS car_sales (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "year TEXT, make TEXT, model TEXT," +
                     "condition TEXT, sellingprice TEXT, saledate TEXT)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Override
    public String getName() { return "DatabaseWriter (Banco de Dados)"; }
}