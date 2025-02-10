import java.sql.*;

public class Test {
    public static void main(String[] args) {
        try {
            // Carregar o driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Estabelecer a conexão com o banco de dados
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/LOJA?allowPublicKeyRetrieval=true&useSSL=false",
                    "divaldoh", "123456");

            // Criar o statement para executar a query
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUTO");

            // Iterar e exibir os resultados
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }

            // Fechar a conexão
            con.close();
        } catch(Exception e) {
            // Capturar e exibir exceções
            e.printStackTrace();
            System.out.println(e);
        }
    }
}
