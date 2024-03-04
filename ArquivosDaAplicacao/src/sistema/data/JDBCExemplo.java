package sistema.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCExemplo {
    public static void main(String[] args) throws SQLException {
        Connection conexao = DriverManager.getConnection(
          "jdbc:mysql://localhost/academia", "root", "");
        System.out.println("Conectado!");
        conexao.close();
    }
}
