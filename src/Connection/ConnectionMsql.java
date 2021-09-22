package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMsql {

    private Connection connection;
    private String user = "root";
    private String password = "";
    private String server = "Localhost";
    private String port = "3306";
    private String DbName = "db_crud_java_fx";

    private String url = "jdbc:mysql://" + server + ":" + port + "/" + DbName + "?serverTimezone=UTC";
    private String driver = "com.mysql.cj.jdbc.Driver";
    
    public ConnectionMsql() {

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Conexion Realizada  correctamente");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Ocurrio un error en la conexion");
            System.err.println("Mensaje del erro:" + e.getMessage());
            System.err.println("Detalle del error:");
            e.printStackTrace();
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
}
