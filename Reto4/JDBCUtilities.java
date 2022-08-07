package Reto4;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class JDBCUtilities {
    private static final String UBICACION_BD = "ProyectosConstruccion.db";

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:" + UBICACION_BD;
        return DriverManager.getConnection(url);
    }
}
