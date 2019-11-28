package dao.avaliacao;

import dao.conexao.ConexaoDAO;
import dao.professor.ProfessorDAO;
import dao.professor.ProfessorDAOImpl;
import domain.Avaliacao;
import domain.Professor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import type.CriterioAvaliacao;

public class AvaliacaoDAOImpl extends ConexaoDAO implements AvaliacaoDAO {

    private static final ProfessorDAO professorDAO = new ProfessorDAOImpl();

    @Override
    public Long cadastrar(Long idPropostaTCC, Long idProfessor, Avaliacao avaliacao) {
        Long idAvaliacao = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // Salva uma avaliação de TCC
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO Avaliacao ");
            sql.append(" (nota_final, aprovado, id_professor_avaliador, parecer, id_proposta_tcc) ");
            sql.append(" VALUES ");
            sql.append(" (?, ?, ?, ?, ?) ");
            pstmt = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pstmt.setDouble(1, avaliacao.getNotaFinal());
            pstmt.setString(2, avaliacao.isAprovado() ? "S" : "N");
            pstmt.setLong(3, idProfessor);
            pstmt.setString(4, avaliacao.getParecer());
            pstmt.setLong(5, idPropostaTCC);
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            rs.next();
            idAvaliacao = rs.getLong(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        fecharConexao(pstmt, rs);
        // Em caso de sucesso, retorna true
        return idAvaliacao;
    }

    @Override
    public void salvarCriteriosAvaliacao(Long idAvaliacao, Map<CriterioAvaliacao, String> criterios) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = criarConexao();
            conexao.setAutoCommit(false);
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO Avaliacao_Criterio ");
            sql.append(" (id_avaliacao, criterio, observacao) ");
            sql.append(" VALUES (?, ?, ?) ");
            pstmt = conexao.prepareCall(sql.toString());

            Iterator<Map.Entry<CriterioAvaliacao, String>> iterator = criterios.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<CriterioAvaliacao, String> criterioAvaliacao = iterator.next();
                pstmt.setLong(1, idAvaliacao);
                pstmt.setString(2, criterioAvaliacao.getKey().toString());
                pstmt.setString(3, criterioAvaliacao.getValue());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conexao.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        fecharConexao(pstmt);
    }

    @Override
    public boolean editar(Avaliacao avaliacao) {
        boolean sucesso;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Avaliacao SET ");
            sql.append(" nota_final = ?, ");
            sql.append(" aprovado = ?, ");
            sql.append(" parecer = ? ");
            sql.append(" WHERE id_avaliacao = ? ");

            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setDouble(1, avaliacao.getNotaFinal());
            pstmt.setString(2, avaliacao.isAprovado() ? "S" : "N");
            pstmt.setString(3, avaliacao.getParecer());
            pstmt.setLong(4, avaliacao.getIdAvaliacao());
            pstmt.executeUpdate();
            sucesso = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            sucesso = false;
        }
        fecharConexao(pstmt);
        // Em caso de sucesso, retorna true
        return sucesso;
    }

    @Override
    public Avaliacao buscarPorId(Long idAvaliacao) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Avaliacao avaliacao = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT * FROM Avaliacao ");
            sql.append(" INNER JOIN professor ");
            sql.append(" ON Avaliacao.id_professor_avaliador = professor.id_professor ");
            sql.append(" WHERE Avaliacao.id_avaliacao = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Professor professor = professorDAO.buscarPorId(rs.getLong("id_professor_avaliador"));
                avaliacao = new Avaliacao(rs.getLong("a.id_avaliacao"), rs.getDouble("a.nota_final"), rs.getString("a.parecer"), "S".equals(rs.getString("a.aprovado")), professor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(pstmt, rs);
        return avaliacao;
    }

    @Override
    public Map<CriterioAvaliacao, String> buscarCriteriosPorAvaliacao(Long idAvaliacao) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Map<CriterioAvaliacao, String> criterios = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT * FROM Avaliacao_Criterio ");
            sql.append(" WHERE id_avaliacao = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idAvaliacao);
            rs = pstmt.executeQuery();
            criterios = new HashMap<>();
            while (rs.next()) {
                criterios.put(CriterioAvaliacao.buscarPorString(rs.getString("criterio")), rs.getString("observacao"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(pstmt, rs);
        return criterios;
    }

    @Override
    public List<Avaliacao> buscarPorTCC(Long idPropostaTCC) {
        List<Avaliacao> avaliacoes = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT * FROM Avaliacao ");
            sql.append(" INNER JOIN professor ");
            sql.append(" ON Avaliacao.id_professor_avaliador = professor.id_professor ");
            sql.append(" WHERE id_proposta_tcc = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idPropostaTCC);
            rs = pstmt.executeQuery();
            avaliacoes = new ArrayList();
            while (rs.next()) {
                Professor professor = professorDAO.buscarPorId(rs.getLong("id_professor_avaliador"));
                Avaliacao avaliacao = new Avaliacao(rs.getLong("id_avaliacao"), rs.getDouble("nota_final"), rs.getString("parecer"), "S".equals(rs.getString("aprovado")), professor);
                avaliacoes.add(avaliacao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(pstmt, rs);
        return avaliacoes;
    }

    @Override
    public void editarCriteriosAvaliacao(Long idAvaliacao, Map<CriterioAvaliacao, String> criterios) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = criarConexao();
            conexao.setAutoCommit(false);
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Avaliacao_Criterio ");
            sql.append(" SET observacao = ? ");
            sql.append(" WHERE id_avaliacao = ? ");
            sql.append(" AND criterio = ? ");
            pstmt = conexao.prepareCall(sql.toString());

            Iterator<Map.Entry<CriterioAvaliacao, String>> iterator = criterios.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<CriterioAvaliacao, String> criterioAvaliacao = iterator.next();
                pstmt.setString(1, criterioAvaliacao.getValue());
                pstmt.setLong(2, idAvaliacao);
                pstmt.setString(3, criterioAvaliacao.getKey().toString());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conexao.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        fecharConexao(pstmt);
    }

    @Override
    public boolean deletar(Long idAvaliacao) {
        boolean sucesso;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM Avaliacao ");
            sql.append(" WHERE id_avaliacao = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idAvaliacao);
            pstmt.executeUpdate();
            sucesso = true;
        } catch (Exception ex) {
            // Em caso de erro inesperado, retorna false
            ex.printStackTrace();
            sucesso = false;
        }
        fecharConexao(pstmt);
        // Em caso de sucesso, retorna true
        return sucesso;
    }

    @Override
    public boolean deletarCriteriosAvaliacao(Long idAvaliacao) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM Avaliacao_Criterio ");
            sql.append(" WHERE id_avaliacao = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idAvaliacao);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            // Em caso de erro inesperado, retorna false
            ex.printStackTrace();
            return false;
        }
        fecharConexao(pstmt);
        return true;
    }
}
