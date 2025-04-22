import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {

    public Connection connectDB() {
        try {
           String url = "jdbc:mysql://localhost:3306/uc11?useSSL=false";
            String user = "root"; 
            String password = "Apolo2010"; 

            
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;

        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }
}
