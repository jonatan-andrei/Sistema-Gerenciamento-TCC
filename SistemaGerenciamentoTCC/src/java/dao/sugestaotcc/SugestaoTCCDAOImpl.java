package dao.sugestaotcc;

import dao.conexao.ConexaoDAO;
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
        fecharConexao(conexao, pstmt);
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
            sql.append(" SELECT sugestao.*, pp.* FROM Sugestao_TCC sugestao ");
            sql.append(" LEFT JOIN Projeto_Pesquisa pp on ");
            sql.append(" sugestao.id_projeto_pesquisa = pp.id_projeto_pesquisa ");
            sql.append(" WHERE sugestao.id_professor_criador = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idProfessor);
            rs = pstmt.executeQuery();
            sugestoes = new ArrayList();
            while (rs.next()) {
                ProjetoPesquisa projeto = null;
                if (rs.getLong("sugestao.id_projeto_pesquisa") != 0) {
                    projeto = new ProjetoPesquisa(rs.getLong("pp.id_projeto_pesquisa"), rs.getString("pp.nome"), rs.getString("pp.descricao"));
                }
                SugestaoTCC sugestao = new SugestaoTCC(rs.getLong("sugestao.id_sugestao_tcc"), rs.getString("sugestao.descricao"), rs.getString("sugestao.escolhida"), projeto);
                sugestoes.add(sugestao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(conexao, pstmt, rs);
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
        fecharConexao(conexao, pstmt);
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
            sql.append(" SELECT sugestao.*, pp.*, professor.* FROM Sugestao_TCC sugestao ");
            sql.append(" INNER JOIN professor p ON ");
            sql.append(" sugestao.id_professor_criador = professor.id_professor ");
            sql.append(" LEFT JOIN Projeto_Pesquisa pp on ");
            sql.append(" sugestao.id_projeto_pesquisa = pp.id_projeto_pesquisa ");
            sql.append(" WHERE sugestao.id_sugestao_tcc = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idSugestaoTCC);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ProjetoPesquisa projeto = null;
                if (rs.getLong("sugestao.id_projeto_pesquisa") != 0) {
                    projeto = new ProjetoPesquisa(rs.getLong("pp.id_projeto_pesquisa"), rs.getString("pp.nome"), rs.getString("pp.descricao"));
                }
                Professor professor = new Professor(rs.getLong("id_professor"), rs.getString("nome"), rs.getString("email"), rs.getInt("carga_trabalho_semestre"));
                sugestao = new SugestaoTCC(rs.getLong("sugestao.id_sugestao"), rs.getString("sugestao.descricao"), rs.getString("sugestao.escolhida"), projeto, professor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(conexao, pstmt, rs);
        return sugestao;
    }

}
