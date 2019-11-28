package dao.projetopesquisa;

import dao.conexao.ConexaoDAO;
import domain.ProjetoPesquisa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProjetoPesquisaDAOImpl extends ConexaoDAO implements ProjetoPesquisaDAO {

    @Override
    public boolean salvar(ProjetoPesquisa projetoPesquisa) {
        boolean sucesso;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO Projeto_Pesquisa ");
            sql.append(" (nome, descricao) ");
            sql.append(" VALUES ");
            sql.append(" (?, ?) ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, projetoPesquisa.getNome());
            pstmt.setString(2, projetoPesquisa.getDescricao());
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
    public List<ProjetoPesquisa> listar() {
        List<ProjetoPesquisa> projetos = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = criarConexao();
            String sql = "SELECT * FROM Projeto_Pesquisa";
            pstmt = conexao.prepareCall(sql);
            rs = pstmt.executeQuery();
            projetos = new ArrayList();
            while (rs.next()) {
                ProjetoPesquisa projeto = new ProjetoPesquisa(rs.getLong("id_projeto_pesquisa"), rs.getString("nome"), rs.getString("descricao"));
                projetos.add(projeto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(pstmt, rs);
        return projetos;
    }

    @Override
    public ProjetoPesquisa buscarPorId(Long id) {
        ProjetoPesquisa projeto = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT * FROM Projeto_Pesquisa ");
            sql.append(" WHERE Projeto_Pesquisa.id_projeto_pesquisa = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                projeto = new ProjetoPesquisa(rs.getLong("id_projeto_pesquisa"), rs.getString("nome"), rs.getString("descricao"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(pstmt, rs);
        return projeto;
    }

}
