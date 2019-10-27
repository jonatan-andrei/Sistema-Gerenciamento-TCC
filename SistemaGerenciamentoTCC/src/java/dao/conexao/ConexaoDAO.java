package dao.conexao;

import java.sql.*;

public abstract class ConexaoDAO {

    // Configurações da conexão com o banco
    private static final String SERVER = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "tcc";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://" + SERVER + ":" + PORT + "/" + DATABASE;

    // Inicia conexão com o banco
    public Connection criarConexao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception ex) {
            throw new RuntimeException("Erro na conexão com o MySQL: ", ex);
        }
    }

    // Método para fechar a conexão com o MySQL
    public void fecharConexao(Connection con) {

        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao fechar conexão com o MySQL: ", ex);
        }

    }

    // Método para fechar a conexão com o MySQL
    public void fecharConexao(Connection con, PreparedStatement pstmt) {

        fecharConexao(con);

        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao fechar conexão com o MySQL: ", ex);
        }

    }

    // Método para fechar a conexão com o MySQL
    public void fecharConexao(Connection con, PreparedStatement pstmt, ResultSet rs) {

        fecharConexao(con, pstmt);

        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao fechar conexão com o MySQL: ", ex);
        }

    }

}
