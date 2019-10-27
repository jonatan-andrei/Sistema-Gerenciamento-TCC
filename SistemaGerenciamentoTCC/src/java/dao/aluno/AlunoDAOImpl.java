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
        // Salva um novo aluno
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO Aluno ");
            sql.append(" (nome, email, matricula, telefone) ");
            sql.append(" VALUES ");
            sql.append(" (?, ?, ?, ?) ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getEmail());
            pstmt.setString(3, aluno.getMatricula());
            pstmt.setString(4, aluno.getTelefone());
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
    public boolean editar(Aluno aluno) {
        // Edita um aluno cadastrado
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Aluno ");
            sql.append(" nome = ?, ");
            sql.append(" email = ?, ");
            sql.append(" matricula = ?, ");
            sql.append(" telefone = ? ");
            sql.append(" WHERE id_aluno = ? ");

            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getEmail());
            pstmt.setString(3, aluno.getMatricula());
            pstmt.setString(4, aluno.getTelefone());
            pstmt.setLong(5, aluno.getId());
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
    public List<Aluno> listar() {
        // Busca alunos cadastrados
        List alunos = new ArrayList();
        try {
            Connection conexao = criarConexao();
            String sql = "SELECT * FROM Aluno";
            PreparedStatement pstmt = conexao.prepareCall(sql);
            pstmt.executeQuery();

            ResultSet rs = pstmt.executeQuery(sql);
            while (rs.next()) {
                Aluno aluno = new Aluno(rs.getLong("id_aluno"), rs.getString("nome"), rs.getString("email"), rs.getString("matricula"), rs.getString("telefone"));
                alunos.add(aluno);
            }
            fecharConexao(conexao, pstmt);
        } catch (Exception e) {
            e.printStackTrace();
            // Retorna null em caso de erro
            return null;
        }
        // Retorna a lista de alunos encontrados
        return alunos;
    }

    @Override
    public boolean deletar(Long idAluno) {
        // Deleta um aluno cadastrado
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM Aluno ");
            sql.append(" WHERE id_aluno = ? ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idAluno);
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
