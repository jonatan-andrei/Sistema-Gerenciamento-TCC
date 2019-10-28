package dao.area;

import dao.conexao.ConexaoDAO;
import domain.Area;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AreaDAOImpl extends ConexaoDAO implements AreaDAO {

    @Override
    public void salvarAreasDeInteresse(Long idProfessor, List<Long> areas) {
        // Salva as áreas de interesse do professor
        try {
            Connection conexao = criarConexao();
            conexao.setAutoCommit(false);
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO Professor_Area ");
            sql.append(" (id_area, id_professor) ");
            sql.append(" VALUES ");
            sql.append(" (?,?) ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());

            Iterator<Long> iterator = areas.iterator();
            while (iterator.hasNext()) {
                Long idArea = iterator.next();
                pstmt.setLong(1, idArea);
                pstmt.setLong(2, idProfessor);
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conexao.commit();
            fecharConexao(conexao, pstmt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deletarAreasDeInteresse(Long idProfessor, List<Long> areas) {
        try {
            Connection conexao = criarConexao();
            conexao.setAutoCommit(false);
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM Professor_Area ");
            sql.append(" WHERE id_area = ? ");
            sql.append(" AND id_professor = ? ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());

            Iterator<Long> iterator = areas.iterator();
            while (iterator.hasNext()) {
                Long idArea = iterator.next();
                pstmt.setLong(1, idArea);
                pstmt.setLong(2, idProfessor);
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conexao.commit();
            fecharConexao(conexao, pstmt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deletarTodasAreasDeInteresse(Long idProfessor) {
        // Exclui todas as áreas de interesse de um professor
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM Professor_Area ");
            sql.append(" WHERE id_professor = ? ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idProfessor);
            pstmt.executeUpdate();
            fecharConexao(conexao, pstmt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Area> buscarAreasDeInteresse(Long idProfessor) {
        List<Area> areas = new ArrayList();
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT area.* FROM area ");
            sql.append(" INNER JOIN professor_area pa ");
            sql.append(" ON pa.id_area = area.id_area ");
            sql.append(" WHERE pa.id_professor = ? ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Area area = new Area(rs.getLong("id_area"), rs.getString("nome"), rs.getString("descricao"));
                areas.add(area);
            }
            fecharConexao(conexao, pstmt, rs);
        } catch (Exception e) {
            e.printStackTrace();
            // Retorna null em caso de erro
            return null;
        }
        // Retorna a lista de areas de interesse do professor
        return areas;
    }

}
