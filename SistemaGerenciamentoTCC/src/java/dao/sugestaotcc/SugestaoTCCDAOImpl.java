package dao.sugestaotcc;

import dao.conexao.ConexaoDAO;
import domain.SugestaoTCC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SugestaoTCCDAOImpl extends ConexaoDAO implements SugestaoTCCDAO {

    @Override
    public boolean salvar(String descricao, Long idArea, Long idProfessor, Long idProjeto) {
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO Sugestao_TCC ");
            sql.append(" (descricao, id_area, id_professor_criador, id_projeto_pesquisa) ");
            sql.append(" VALUES ");
            sql.append(" (?, ?, ?, ?) ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, descricao);
            pstmt.setLong(2, idArea);
            pstmt.setLong(3, idProfessor);
            pstmt.setLong(4, idProjeto);
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
            sql.append(" SELECT * FROM Sugestao_TCC sugestao ");
            sql.append(" WHERE sugestao.id_professor_criador = ? ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                SugestaoTCC sugestao = new SugestaoTCC(rs.getLong("id_sugestao"), rs.getString("descricao"), rs.getString("escolhida"));
                sugestoes.add(sugestao);
            }
            fecharConexao(conexao, pstmt, rs);
        } catch (Exception e) {
            e.printStackTrace();
            // Retorna null em caso de erro
            return null;
        }
        // Retorna a lista de areas de interesse do professor
        return sugestoes;
    }

}
