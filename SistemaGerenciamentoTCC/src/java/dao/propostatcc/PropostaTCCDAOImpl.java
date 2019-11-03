package dao.propostatcc;

import dao.conexao.ConexaoDAO;
import domain.Aluno;
import domain.Professor;
import domain.PropostaTCC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PropostaTCCDAOImpl extends ConexaoDAO implements PropostaTCCDAO {
    
    // TODO alterar para fechar conexão mesmo em caso de erro

    @Override
    public boolean enviarTema(PropostaTCC propostaTCC, Long idAluno, Long idProfessor, Long idSugestao, Long idArea) {
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO Proposta_TCC ");
            sql.append(" (titulo, descricao, id_aluno_autor, id_professor_orientador, id_sugestao_origem, id_area) ");
            sql.append(" VALUES ");
            sql.append(" (?, ?, ?, ?, ?, ?) ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, propostaTCC.getTitulo());
            pstmt.setString(2, propostaTCC.getDescricao());
            pstmt.setLong(3, idAluno);
            pstmt.setLong(4, idProfessor);
            pstmt.setLong(5, idSugestao);
            pstmt.setLong(6, idArea);
            pstmt.executeUpdate();
            fecharConexao(conexao, pstmt);
            return true;
        } catch (Exception ex) {
            // Em caso de erro inesperado, retorna false
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean enviarArtigoFinal(Long idPropostaTCC, String artigo) {
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Proposta_TCC ");
            sql.append(" SET artigo = ? ");
            sql.append(" WHERE id_proposta_tcc = ? ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, artigo);
            pstmt.setLong(2, idPropostaTCC);
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
                Professor professor = new Professor(rs.getLong("professor.id_professor"), rs.getString("professor.nome"), rs.getString("professor.email"), rs.getInt("professor.carga_trabalho_semestre"));
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
    public PropostaTCC buscarPorId(Long idPropostaTCC) {
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
            sql.append(" WHERE proposta.id_proposta_tcc = ? ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idPropostaTCC);
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
    public boolean salvarBanca(Long idPropostaTCC, List<Long> professores) {
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Proposta_TCC ");
            sql.append(" SET id_professor_avaliador_primeiro = ?, ");
            sql.append(" id_professor_avaliador_segundo = ? ");
            sql.append(" WHERE id_proposta_tcc = ? ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, professores.get(0));
            pstmt.setLong(2, professores.get(1));
            pstmt.setLong(3, idPropostaTCC);
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
    public List<Professor> verBanca(Long idPropostaTCC) {
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT orientador.*, avaliador1.*, avaliador2.* FROM Proposta_TCC proposta ");
            sql.append(" INNER JOIN Professor orientador on proposta.id_professor_orientador = orientador.id_professor ");
            sql.append(" INNER JOIN Professor avaliador1 on proposta.id_professor_avaliador_primeiro = avaliador1.id_professor ");
            sql.append(" INNER JOIN Professor avaliador2 on proposta.id_professor_avaliador_segundo = avaliador2.id_professor ");
            sql.append(" WHERE proposta.id_proposta_tcc = ? ");

            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idPropostaTCC);
            ResultSet rs = pstmt.executeQuery();
            List<Professor> professores = null;
            if (rs.next()) {
                Professor orientador = new Professor(rs.getLong("orientador.id_professor"), rs.getString("orientador.nome"), rs.getString("orientador.email"), rs.getInt("orientador.carga_trabalho_semestre"));
                Professor avaliador1 = new Professor(rs.getLong("avaliador1.id_professor"), rs.getString("avaliador1.nome"), rs.getString("avaliador1.email"), rs.getInt("avaliador1.carga_trabalho_semestre"));
                Professor avaliador2 = new Professor(rs.getLong("avaliador2.id_professor"), rs.getString("avaliador2.nome"), rs.getString("avaliador2.email"), rs.getInt("avaliador2.carga_trabalho_semestre"));
                professores = Arrays.asList(orientador, avaliador1, avaliador2);
            }
            fecharConexao(conexao, pstmt, rs);
            return professores;
        } catch (Exception e) {
            e.printStackTrace();
            // Retorna null em caso de erro
            return null;
        }
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
