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
        try {
            Connection conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO Projeto_Pesquisa ");
            sql.append(" (nome, descricao) ");
            sql.append(" VALUES ");
            sql.append(" (?, ?) ");
            PreparedStatement pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, projetoPesquisa.getNome());
            pstmt.setString(2, projetoPesquisa.getDescricao());
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
    public List<ProjetoPesquisa> buscar() {
        List<ProjetoPesquisa> projetos = new ArrayList();
        try {
            Connection conexao = criarConexao();
            String sql = "SELECT * FROM Projeto_Pesquisa";
            PreparedStatement pstmt = conexao.prepareCall(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ProjetoPesquisa projeto = new ProjetoPesquisa(rs.getLong("id_projeto_pesquisa"), rs.getString("nome"), rs.getString("descricao"));
                projetos.add(projeto);
            }
            fecharConexao(conexao, pstmt, rs);
        } catch (Exception e) {
            e.printStackTrace();
            // Retorna null em caso de erro
            return null;
        }
        return projetos;
    }

}
