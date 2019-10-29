package dao.propostatcc;

import dao.conexao.ConexaoDAO;
import domain.Aluno;
import domain.Professor;
import domain.PropostaTCC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PropostaTCCDAOImpl extends ConexaoDAO implements PropostaTCCDAO {

    @Override
    public boolean enviarTema(PropostaTCC propostaTCC) {
    }

    @Override
    public boolean enviarArtigoFinal(Long idPropostaTCC, String artigo) {

    }

    @Override
    public List<PropostaTCC> listar() {
        List<PropostaTCC> propostas = new ArrayList();
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT proposta.*, aluno.*, professor.* ");
            sql.append(" FROM Proposta_TCC proposta ");
            sql.append(" INNER JOIN aluno ");
            sql.append(" ON sugestao.id_aluno_autor = aluno.id_aluno ");
            sql.append(" INNER JOIN professor ");
            sql.append(" ON proposta.id_professor_orientador = professor.id_professor ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Aluno aluno = new Aluno(rs.getLong("aluno.id_aluno"), rs.getString("aluno.nome"), rs.getString("aluno.email"), rs.getString("aluno.matricula"), rs.getString("aluno.telefone"));
                Professor professor = new Professor(rs.getLong("id_professor"), rs.getString("nome"), rs.getString("email"), rs.getInt("carga_trabalho_semestre"));
                PropostaTCC proposta = new PropostaTCC(rs.getLong("proposta.id_proposta_tcc"), rs.getString("proposta.titulo"), rs.getString("proposta.descricao"), rs.getString("proposta.artigo"), aluno, professor);
                propostas.add(proposta);
            }
            fecharConexao(conexao, pstmt, rs);
        } catch (Exception e) {
            e.printStackTrace();
            // Retorna null em caso de erro
            return null;
        }
        return propostas;
    }

    @Override
    public boolean desativar(Long idPropostaTCC) {
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Proposta_TCC ");
            sql.append(" SET ativo = 'N' ");
            sql.append(" WHERE id_proposta_tcc = ? ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idPropostaTCC);
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
    public PropostaTCC buscarPorAluno(Long idAluno) {
        PropostaTCC proposta = null;
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT proposta.*, aluno.*, professor.* ");
            sql.append(" FROM Proposta_TCC proposta ");
            sql.append(" INNER JOIN aluno ");
            sql.append(" ON sugestao.id_aluno_autor = aluno.id_aluno ");
            sql.append(" INNER JOIN professor ");
            sql.append(" ON proposta.id_professor_orientador = professor.id_professor ");
            sql.append(" WHERE proposta.id_aluno = ? ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idAluno);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Aluno aluno = new Aluno(rs.getLong("aluno.id_aluno"), rs.getString("aluno.nome"), rs.getString("aluno.email"), rs.getString("aluno.matricula"), rs.getString("aluno.telefone"));
                Professor professor = new Professor(rs.getLong("id_professor"), rs.getString("nome"), rs.getString("email"), rs.getInt("carga_trabalho_semestre"));
                proposta = new PropostaTCC(rs.getLong("proposta.id_proposta_tcc"), rs.getString("proposta.titulo"), rs.getString("proposta.descricao"), rs.getString("proposta.artigo"), aluno, professor);
            }
            fecharConexao(conexao, pstmt, rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proposta;
    }

    @Override
    public boolean cadastrarBanca(Long idPropostaTCC, List<Professor> professores) {

    }

    @Override
    public boolean editarBanca(Long idPropostaTCC, List<Professor> professores) {

    }

    @Override
    public List<Professor> verBanca(Long idPropostaTCC) {

    }

    @Override
    public boolean deletarBanca(Long idPropostaTCC) {
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Proposta_TCC ");
            sql.append(" SET id_professor_avaliador_primeiro = null, ");
            sql.append(" id_professor_avaliador_segundo = null ");
            sql.append(" WHERE id_proposta_tcc = ? ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idPropostaTCC);
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
