package dao.sugestaotcc;

import dao.conexao.ConexaoDAO;
import dao.projetopesquisa.ProjetoPesquisaDAO;
import dao.projetopesquisa.ProjetoPesquisaDAOImpl;
import domain.Professor;
import domain.ProjetoPesquisa;
import domain.SugestaoTCC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.nonNull;

public class SugestaoTCCDAOImpl extends ConexaoDAO implements SugestaoTCCDAO {

    private static final ProjetoPesquisaDAO projetoPesquisaDAO = new ProjetoPesquisaDAOImpl();

    @Override
    public boolean cadastrar(String descricao, Long idProfessor, Long idProjeto) {
        boolean sucesso;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO Sugestao_TCC ");
            sql.append(" (descricao, id_professor_criador, id_projeto_pesquisa) ");
            sql.append(" VALUES ");
            sql.append(" (?, ?, ?) ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, descricao);
            pstmt.setLong(2, idProfessor);
            if (nonNull(idProjeto)) {
                pstmt.setLong(3, idProjeto);
            } else {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            }
            pstmt.executeUpdate();
            sucesso = true;
        } catch (Exception ex) {
            // Em caso de erro inesperado, retorna false
            ex.printStackTrace();
            sucesso = false;
        }
        fecharConexao(pstmt);
        return sucesso;
    }

    @Override
    public List<SugestaoTCC> buscarSugestoesDeProfessor(Long idProfessor) {
        List<SugestaoTCC> sugestoes = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT Sugestao_TCC.* FROM Sugestao_TCC ");
            sql.append(" WHERE id_professor_criador = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idProfessor);
            rs = pstmt.executeQuery();
            sugestoes = new ArrayList();
            while (rs.next()) {
                ProjetoPesquisa projeto = null;
                if (rs.getLong("id_projeto_pesquisa") != 0) {
                    projeto = projetoPesquisaDAO.buscarPorId(rs.getLong("id_projeto_pesquisa"));
                }
                SugestaoTCC sugestao = new SugestaoTCC(rs.getLong("id_sugestao_tcc"), rs.getString("descricao"), rs.getString("escolhida"), projeto);
                sugestoes.add(sugestao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(pstmt, rs);
        // Retorna a lista de sugestões do professor
        return sugestoes;
    }

    @Override
    public void escolherSugestao(Long idSugestaoTCC) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Sugestao_TCC SET ");
            sql.append(" escolhida = 'S' WHERE id_sugestao_tcc = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idSugestaoTCC);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        fecharConexao(pstmt);
    }

    @Override
    public SugestaoTCC buscarPorId(Long idSugestaoTCC) {
        SugestaoTCC sugestao = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT * FROM Sugestao_TCC ");
            sql.append(" INNER JOIN Professor ON ");
            sql.append(" Sugestao_TCC.id_professor_criador = Professor.id_professor ");
            sql.append(" WHERE Sugestao_TCC.id_sugestao_tcc = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idSugestaoTCC);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ProjetoPesquisa projeto = null;
                if (rs.getLong("id_projeto_pesquisa") != 0) {
                    projeto = projetoPesquisaDAO.buscarPorId(rs.getLong("id_projeto_pesquisa"));
                }
                Professor professor = new Professor(rs.getLong("id_professor"), rs.getString("nome"), rs.getString("email"), rs.getInt("carga_trabalho_semestre"));
                sugestao = new SugestaoTCC(rs.getLong("id_sugestao_tcc"), rs.getString("descricao"), rs.getString("escolhida"), projeto, professor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(pstmt, rs);
        return sugestao;
    }

}
