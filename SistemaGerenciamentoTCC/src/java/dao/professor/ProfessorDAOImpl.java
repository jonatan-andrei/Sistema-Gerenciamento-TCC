package dao.professor;

import dao.conexao.ConexaoDAO;
import domain.Professor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAOImpl extends ConexaoDAO implements ProfessorDAO {

    @Override
    public Long cadastrar(Professor professor) {
        // Cadastrar professor
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO Professor ");
            sql.append(" (nome, email) ");
            sql.append(" VALUES ");
            sql.append(" (?, ?) ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, professor.getNome());
            pstmt.setString(2, professor.getEmail());
            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            Long idProfessor = generatedKeys.getLong(1);
            fecharConexao(conexao, pstmt);
            // Em caso de sucesso, retorna o id do professor cadastrado
            return idProfessor;
        } catch (Exception ex) {
            // Em caso de erro inesperado, retorna false
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean editar(Professor professor) {
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Professor SET ");
            sql.append(" nome = ?, ");
            sql.append(" email = ? ");
            sql.append(" WHERE id_professor = ? ");

            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, professor.getNome());
            pstmt.setString(2, professor.getEmail());
            pstmt.setLong(5, professor.getId());
            pstmt.executeUpdate();
            fecharConexao(conexao, pstmt);
        } catch (Exception ex) {
            // Em caso de erro inesperado, retorna false
            ex.printStackTrace();
            return false;
        }
        // Em caso de sucesso, retorna true
        return true;
    }

    @Override
    public List<Professor> buscar() {
        List<Professor> professores = new ArrayList();
        try {
            Connection conexao = criarConexao();
            String sql = "SELECT * FROM Professor";
            PreparedStatement pstmt = conexao.prepareCall(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Professor professor = new Professor(rs.getLong("id_professor"), rs.getString("nome"), rs.getString("email"), rs.getInt("carga_trabalho_semestre"));
                professores.add(professor);
            }
            fecharConexao(conexao, pstmt, rs);
        } catch (Exception e) {
            e.printStackTrace();
            // Retorna null em caso de erro
            return null;
        }
        // Retorna a lista de professores cadastrados
        return professores;
    }

    @Override
    public boolean desativar(Long idProfessor) {
        // Exclui um professor
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Professor SET ");
            sql.append(" ativo = 'N' WHERE id_professor = ? ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idProfessor);
            pstmt.executeUpdate();
            fecharConexao(conexao, pstmt);
        } catch (Exception ex) {
            // Em caso de erro inesperado, retorna false
            ex.printStackTrace();
            return false;
        }
        // Em caso de sucesso, retorna true
        return true;
    }

}
