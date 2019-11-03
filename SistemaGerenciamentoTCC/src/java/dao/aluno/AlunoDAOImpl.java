package dao.aluno;

import dao.conexao.ConexaoDAO;
import domain.Aluno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAOImpl extends ConexaoDAO implements AlunoDAO {

    @Override
    public boolean cadastrar(Aluno aluno) {
        boolean sucesso;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        // Salva um novo aluno
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO Aluno ");
            sql.append(" (nome, email, matricula, telefone) ");
            sql.append(" VALUES ");
            sql.append(" (?, ?, ?, ?) ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getEmail());
            pstmt.setString(3, aluno.getMatricula());
            pstmt.setString(4, aluno.getTelefone());
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
    public boolean editar(Aluno aluno) {
        boolean sucesso;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        // Edita um aluno cadastrado
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Aluno SET ");
            sql.append(" nome = ?, ");
            sql.append(" email = ?, ");
            sql.append(" matricula = ?, ");
            sql.append(" telefone = ? ");
            sql.append(" WHERE id_aluno = ? ");

            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getEmail());
            pstmt.setString(3, aluno.getMatricula());
            pstmt.setString(4, aluno.getTelefone());
            pstmt.setLong(5, aluno.getId());
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
    public List<Aluno> listar() {
        // Busca alunos cadastrados
        List<Aluno> alunos = null;

        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = criarConexao();
            String sql = "SELECT * FROM Aluno WHERE ativo = 'S'";
            pstmt = conexao.prepareCall(sql);
            rs = pstmt.executeQuery();
            alunos = new ArrayList();
            while (rs.next()) {
                Aluno aluno = new Aluno(rs.getLong("id_aluno"), rs.getString("nome"), rs.getString("email"), rs.getString("matricula"), rs.getString("telefone"));
                alunos.add(aluno);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(conexao, pstmt, rs);
        // Retorna a lista de alunos encontrados
        return alunos;
    }

    @Override
    public boolean desativar(Long idAluno) {
        boolean sucesso;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        // Desativa um aluno cadastrado
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Aluno ");
            sql.append(" SET ativo = 'N' ");
            sql.append(" WHERE id_aluno = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idAluno);
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
