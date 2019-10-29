package dao.sugestaotcc;

import dao.conexao.ConexaoDAO;
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
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO Sugestao_TCC ");
            sql.append(" (descricao, id_professor_criador, id_projeto_pesquisa) ");
            sql.append(" VALUES ");
            sql.append(" (?, ?, ?) ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, descricao);
            pstmt.setLong(2, idProfessor);
            pstmt.setLong(3, idProjeto);
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
    public List<SugestaoTCC> buscarSugestoesDeProfessor(Long idProfessor) {
        List<SugestaoTCC> sugestoes = new ArrayList();
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT sugestao.*, pp.* FROM Sugestao_TCC sugestao ");
            sql.append(" LEFT JOIN Projeto_Pesquisa pp on ");
            sql.append(" sugestao.id_projeto_pesquisa = pp.id_projeto_pesquisa ");
            sql.append(" WHERE sugestao.id_professor_criador = ? ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idProfessor);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ProjetoPesquisa projeto = null;
                if (nonNull(rs.getLong("sugestao.id_projeto_pesquisa"))) {
                    projeto = new ProjetoPesquisa(rs.getLong("pp.id_projeto_pesquisa"), rs.getString("pp.nome"), rs.getString("pp.descricao"));
                }
                SugestaoTCC sugestao = new SugestaoTCC(rs.getLong("sugestao.id_sugestao"), rs.getString("sugestao.descricao"), rs.getString("sugestao.escolhida"), projeto);
                sugestoes.add(sugestao);
            }
            fecharConexao(conexao, pstmt, rs);
        } catch (Exception e) {
            e.printStackTrace();
            // Retorna null em caso de erro
            return null;
        }
        // Retorna a lista de sugestões do professor
        return sugestoes;
    }

    @Override
    public void escolherSugestao(Long idSugestaoTCC) {
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Sugestao_TCC SET ");
            sql.append(" escolhida = 'S' WHERE id_sugestao_tcc = ? ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idSugestaoTCC);
            pstmt.executeUpdate();
            fecharConexao(conexao, pstmt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
