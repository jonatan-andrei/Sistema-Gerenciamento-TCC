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
    public List<Area> listar() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Area> areas = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT * FROM area ");
            pstmt = conexao.prepareCall(sql.toString());
            rs = pstmt.executeQuery();
            areas = new ArrayList();
            while (rs.next()) {
                Area area = new Area(rs.getLong("id_area"), rs.getString("nome"));
                areas.add(area);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(pstmt, rs);
        // Retorna a lista de areas de interesse cadastradas
        return areas;
    }

    @Override
    public void salvarAreasDeInteresse(Long idProfessor, List<Long> areas) {
        // Salva as áreas de interesse do professor
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = criarConexao();
            conexao.setAutoCommit(false);
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO Professor_Area ");
            sql.append(" (id_area, id_professor) ");
            sql.append(" VALUES ");
            sql.append(" (?,?) ");
            pstmt = conexao.prepareCall(sql.toString());

            Iterator<Long> iterator = areas.iterator();
            while (iterator.hasNext()) {
                Long idArea = iterator.next();
                pstmt.setLong(1, idArea);
                pstmt.setLong(2, idProfessor);
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
    public void deletarAreasDeInteresse(Long idProfessor, List<Long> areas) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = criarConexao();
            conexao.setAutoCommit(false);
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM Professor_Area ");
            sql.append(" WHERE id_area = ? ");
            sql.append(" AND id_professor = ? ");
            pstmt = conexao.prepareCall(sql.toString());

            Iterator<Long> iterator = areas.iterator();
            while (iterator.hasNext()) {
                Long idArea = iterator.next();
                pstmt.setLong(1, idArea);
                pstmt.setLong(2, idProfessor);
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
    public void deletarTodasAreasDeInteresse(Long idProfessor) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        // Exclui todas as áreas de interesse de um professor
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM Professor_Area ");
            sql.append(" WHERE id_professor = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idProfessor);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        fecharConexao(pstmt);
    }

    @Override
    public List<Area> buscarAreasDeInteresse(Long idProfessor) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Area> areas = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT * FROM area ");
            sql.append(" INNER JOIN professor_area ");
            sql.append(" ON professor_area.id_area = area.id_area ");
            sql.append(" WHERE professor_area.id_professor = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idProfessor);
            rs = pstmt.executeQuery();
            areas = new ArrayList();
            while (rs.next()) {
                Area area = new Area(rs.getLong("id_area"), rs.getString("nome"));
                areas.add(area);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(pstmt, rs);
        // Retorna a lista de areas de interesse do professor
        return areas;
    }

    @Override
    public Area buscarPorId(Long idArea) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Area area = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT * FROM area ");
            sql.append(" WHERE id_area = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idArea);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                area = new Area(rs.getLong("id_area"), rs.getString("nome"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(pstmt, rs);
        return area;
    }

}
