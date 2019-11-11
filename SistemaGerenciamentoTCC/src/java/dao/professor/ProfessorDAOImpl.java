package dao.professor;

import dao.conexao.ConexaoDAO;
import domain.Professor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProfessorDAOImpl extends ConexaoDAO implements ProfessorDAO {

    @Override
    public Long cadastrar(Professor professor) {
        Long idProfessor = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // Cadastrar professor
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO Professor ");
            sql.append(" (nome, email) ");
            sql.append(" VALUES ");
            sql.append(" (?, ?) ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, professor.getNome());
            pstmt.setString(2, professor.getEmail());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            rs.next();
            idProfessor = rs.getLong(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        fecharConexao(conexao, pstmt, rs);
        // Retorna o id do professor cadastrado
        return idProfessor;
    }

    @Override
    public boolean editar(Professor professor) {
        boolean sucesso;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Professor SET ");
            sql.append(" nome = ?, ");
            sql.append(" email = ? ");
            sql.append(" WHERE id_professor = ? ");

            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, professor.getNome());
            pstmt.setString(2, professor.getEmail());
            pstmt.setLong(3, professor.getId());
            pstmt.executeUpdate();
            sucesso = true;
        } catch (Exception ex) {
            // Em caso de erro inesperado, retorna false
            ex.printStackTrace();
            sucesso = false;
        }
        fecharConexao(conexao, pstmt);
        // Em caso de sucesso, retorna true
        return sucesso;
    }

    @Override
    public Professor buscarPorId(Long idProfessor) {
        Professor professor = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conexao = criarConexao();
            String sql = "SELECT * FROM Professor WHERE id_professor = ?";
            pstmt = conexao.prepareCall(sql);
            pstmt.setLong(1, idProfessor);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                professor = new Professor(rs.getLong("id_professor"), rs.getString("nome"), rs.getString("email"), rs.getInt("carga_trabalho_semestre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(conexao, pstmt, rs);
        return professor;
    }

    @Override
    public List<Professor> listar() {
        List<Professor> professores = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = criarConexao();
            String sql = "SELECT * FROM Professor WHERE ativo = 'S'";
            pstmt = conexao.prepareCall(sql);
            rs = pstmt.executeQuery();
            professores = new ArrayList();
            while (rs.next()) {
                Professor professor = new Professor(rs.getLong("id_professor"), rs.getString("nome"), rs.getString("email"), rs.getInt("carga_trabalho_semestre"));
                professores.add(professor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(conexao, pstmt, rs);
        // Retorna a lista de professores cadastrados
        return professores;
    }

    @Override
    public boolean desativar(Long idProfessor) {
        boolean sucesso;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        // Exclui um professor
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Professor SET ");
            sql.append(" ativo = 'N' WHERE id_professor = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idProfessor);
            pstmt.executeUpdate();
            sucesso = true;
        } catch (Exception ex) {
            // Em caso de erro inesperado, retorna false
            ex.printStackTrace();
            sucesso = false;
        }
        fecharConexao(conexao, pstmt);
        // Em caso de sucesso, retorna true
        return sucesso;
    }

    @Override
    public boolean aumentarCargaDeTrabalho(List<Long> professores) {
        boolean sucesso;
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Professor SET ");
            sql.append(" carga_trabalho_semestre = carga_trabalho_semestre + 1 ");
            sql.append(" WHERE id_professor IN (?) ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, professores.stream().map(String::valueOf).collect(Collectors.joining(", ")));
            pstmt.executeUpdate();
            sucesso = true;
        } catch (Exception ex) {
            // Em caso de erro inesperado, retorna false
            ex.printStackTrace();
            sucesso = false;
        }
        fecharConexao(conexao, pstmt);
        // Em caso de sucesso, retorna true
        return sucesso;
    }

    @Override
    public boolean reduzirCargaDeTrabalho(List<Long> professores) {
        boolean sucesso;
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Professor SET ");
            sql.append(" carga_trabalho_semestre = carga_trabalho_semestre - 1 ");
            sql.append(" WHERE id_professor IN (?) ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, professores.stream().map(String::valueOf).collect(Collectors.joining(", ")));
            pstmt.executeUpdate();
            sucesso = true;
        } catch (Exception ex) {
            // Em caso de erro inesperado, retorna false
            ex.printStackTrace();
            sucesso = false;
        }
        fecharConexao(conexao, pstmt);
        // Em caso de sucesso, retorna true
        return sucesso;
    }

}
