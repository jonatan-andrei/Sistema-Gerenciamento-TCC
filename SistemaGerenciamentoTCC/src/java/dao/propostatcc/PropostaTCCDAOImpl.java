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
import static java.util.Objects.nonNull;

public class PropostaTCCDAOImpl extends ConexaoDAO implements PropostaTCCDAO {

    @Override
    public boolean enviarTema(PropostaTCC propostaTCC, Long idAluno, Long idProfessor, Long idSugestao, Long idArea) {
        boolean sucesso;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO Proposta_TCC ");
            sql.append(" (titulo, descricao, id_aluno_autor, id_professor_orientador, id_sugestao_origem, id_area) ");
            sql.append(" VALUES ");
            sql.append(" (?, ?, ?, ?, ?, ?) ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, propostaTCC.getTitulo());
            pstmt.setString(2, propostaTCC.getDescricao());
            pstmt.setLong(3, idAluno);
            pstmt.setLong(4, idProfessor);
            if (nonNull(idSugestao)) {
                pstmt.setLong(5, idSugestao);
            } else {
                pstmt.setNull(5, java.sql.Types.INTEGER);
            }
            pstmt.setLong(6, idArea);
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
    public boolean enviarArtigoFinal(Long idPropostaTCC, String artigo) {
        boolean sucesso;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Proposta_TCC ");
            sql.append(" SET artigo = ? ");
            sql.append(" WHERE id_proposta_tcc = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setString(1, artigo);
            pstmt.setLong(2, idPropostaTCC);
            pstmt.executeUpdate();
            sucesso = true;
        } catch (Exception ex) {
            // Em caso de erro inesperado, retorna false
            ex.printStackTrace();
            sucesso = false;
        }
        fecharConexao(conexao, pstmt);
        // Em caso de sucesso, retorna true
        return sucesso;
    }

    @Override
    public List<PropostaTCC> listar() {
        List<PropostaTCC> propostas = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT proposta.*, aluno.*, orientador.*, avaliador1.*, avaliador2.* ");
            sql.append(" FROM Proposta_TCC proposta ");
            sql.append(" INNER JOIN aluno ");
            sql.append(" ON sugestao.id_aluno_autor = aluno.id_aluno ");
            sql.append(" INNER JOIN Professor orientador ");
            sql.append(" ON proposta.id_professor_orientador = orientador.id_professor ");
            sql.append(" LEFT JOIN professor avaliador1 ");
            sql.append(" ON proposta.id_professor_avaliador_primeiro = avaliador1.id_professor ");
            sql.append(" LEFT JOIN professor avaliador2 ");
            sql.append(" ON proposta.id_professor_avaliador_segundo = avaliador2.id_professor ");
            sql.append(" WHERE proposta.ativo = 'S' AND aluno.ativo = 'S' ");
            pstmt = conexao.prepareCall(sql.toString());
            rs = pstmt.executeQuery();
            propostas = new ArrayList();
            while (rs.next()) {
                Aluno aluno = new Aluno(rs.getLong("aluno.id_aluno"), rs.getString("aluno.nome"), rs.getString("aluno.email"), rs.getString("aluno.matricula"), rs.getString("aluno.telefone"));
                Professor professor = new Professor(rs.getLong("orientador.id_professor"), rs.getString("orientador.nome"), rs.getString("orientador.email"), rs.getInt("orientador.carga_trabalho_semestre"));
                List<Professor> banca = new ArrayList<>();
                if (rs.getLong("proposta.id_professor_avaliador_primeiro") != 0) {
                    banca.add(new Professor(rs.getLong("avaliador1.id_professor"), rs.getString("avaliador1.nome"), rs.getString("avaliador1.email"), rs.getInt("avaliador1.carga_trabalho_semestre")));
                }
                if (rs.getLong("proposta.id_professor_avaliador_segundo") != 0) {
                    banca.add(new Professor(rs.getLong("avaliador2.id_professor"), rs.getString("avaliador2.nome"), rs.getString("avaliador2.email"), rs.getInt("avaliador2.carga_trabalho_semestre")));
                }
                PropostaTCC proposta = new PropostaTCC(rs.getLong("proposta.id_proposta_tcc"), rs.getString("proposta.titulo"), rs.getString("proposta.descricao"), rs.getString("proposta.artigo"), aluno, professor, banca);
                propostas.add(proposta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(conexao, pstmt, rs);
        return propostas;
    }

    @Override
    public boolean desativar(Long idPropostaTCC) {
        boolean sucesso;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Proposta_TCC ");
            sql.append(" SET ativo = 'N' ");
            sql.append(" WHERE id_proposta_tcc = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idPropostaTCC);
            pstmt.executeUpdate();
            sucesso = true;
        } catch (Exception ex) {
            // Em caso de erro inesperado, retorna false
            ex.printStackTrace();
            sucesso = false;
        }
        fecharConexao(conexao, pstmt);
        // Em caso de sucesso, retorna true
        return sucesso;
    }

    @Override
    public PropostaTCC buscarPorAluno(Long idAluno) {
        PropostaTCC proposta = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT proposta.*, aluno.*, professor.* ");
            sql.append(" FROM Proposta_TCC proposta ");
            sql.append(" INNER JOIN aluno ");
            sql.append(" ON sugestao.id_aluno_autor = aluno.id_aluno ");
            sql.append(" INNER JOIN professor ");
            sql.append(" ON proposta.id_professor_orientador = professor.id_professor ");
            sql.append(" WHERE proposta.id_aluno = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idAluno);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Aluno aluno = new Aluno(rs.getLong("aluno.id_aluno"), rs.getString("aluno.nome"), rs.getString("aluno.email"), rs.getString("aluno.matricula"), rs.getString("aluno.telefone"));
                Professor professor = new Professor(rs.getLong("id_professor"), rs.getString("nome"), rs.getString("email"), rs.getInt("carga_trabalho_semestre"));
                proposta = new PropostaTCC(rs.getLong("proposta.id_proposta_tcc"), rs.getString("proposta.titulo"), rs.getString("proposta.descricao"), rs.getString("proposta.artigo"), aluno, professor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(conexao, pstmt, rs);
        return proposta;
    }

    @Override
    public PropostaTCC buscarPorId(Long idPropostaTCC) {
        PropostaTCC proposta = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT proposta.*, aluno.*, professor.* ");
            sql.append(" FROM Proposta_TCC proposta ");
            sql.append(" INNER JOIN aluno ");
            sql.append(" ON sugestao.id_aluno_autor = aluno.id_aluno ");
            sql.append(" INNER JOIN professor ");
            sql.append(" ON proposta.id_professor_orientador = professor.id_professor ");
            sql.append(" WHERE proposta.id_proposta_tcc = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idPropostaTCC);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Aluno aluno = new Aluno(rs.getLong("aluno.id_aluno"), rs.getString("aluno.nome"), rs.getString("aluno.email"), rs.getString("aluno.matricula"), rs.getString("aluno.telefone"));
                Professor professor = new Professor(rs.getLong("id_professor"), rs.getString("nome"), rs.getString("email"), rs.getInt("carga_trabalho_semestre"));
                proposta = new PropostaTCC(rs.getLong("proposta.id_proposta_tcc"), rs.getString("proposta.titulo"), rs.getString("proposta.descricao"), rs.getString("proposta.artigo"), aluno, professor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(conexao, pstmt, rs);
        return proposta;
    }

    @Override
    public boolean salvarBanca(Long idPropostaTCC, List<Long> professores) {
        boolean sucesso;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Proposta_TCC ");
            sql.append(" SET id_professor_avaliador_primeiro = ?, ");
            sql.append(" id_professor_avaliador_segundo = ? ");
            sql.append(" WHERE id_proposta_tcc = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, professores.get(0));
            pstmt.setLong(2, professores.get(1));
            pstmt.setLong(3, idPropostaTCC);
            pstmt.executeUpdate();
            sucesso = true;
        } catch (Exception ex) {
            // Em caso de erro inesperado, retorna false
            ex.printStackTrace();
            sucesso = false;
        }
        fecharConexao(conexao, pstmt);
        // Em caso de sucesso, retorna true
        return sucesso;
    }

    @Override
    public List<Professor> verBanca(Long idPropostaTCC) {
        List<Professor> professores = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT avaliador1.*, avaliador2.* FROM Proposta_TCC proposta ");
            sql.append(" INNER JOIN Professor avaliador1 on proposta.id_professor_avaliador_primeiro = avaliador1.id_professor ");
            sql.append(" INNER JOIN Professor avaliador2 on proposta.id_professor_avaliador_segundo = avaliador2.id_professor ");
            sql.append(" WHERE proposta.id_proposta_tcc = ? ");

            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idPropostaTCC);
            rs = pstmt.executeQuery();
            professores = new ArrayList<>();
            if (rs.next()) {
                Professor avaliador1 = new Professor(rs.getLong("avaliador1.id_professor"), rs.getString("avaliador1.nome"), rs.getString("avaliador1.email"), rs.getInt("avaliador1.carga_trabalho_semestre"));
                Professor avaliador2 = new Professor(rs.getLong("avaliador2.id_professor"), rs.getString("avaliador2.nome"), rs.getString("avaliador2.email"), rs.getInt("avaliador2.carga_trabalho_semestre"));
                professores.addAll(Arrays.asList(avaliador1, avaliador2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(conexao, pstmt, rs);
        return professores;
    }

    @Override
    public List<Professor> verBancaEOrientador(Long idPropostaTCC) {
        List<Professor> professores = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT orientador.*, avaliador1.*, avaliador2.* FROM Proposta_TCC proposta ");
            sql.append(" INNER JOIN Professor orientador on proposta.id_professor_orientador = orientador.id_professor ");
            sql.append(" INNER JOIN Professor avaliador1 on proposta.id_professor_avaliador_primeiro = avaliador1.id_professor ");
            sql.append(" INNER JOIN Professor avaliador2 on proposta.id_professor_avaliador_segundo = avaliador2.id_professor ");
            sql.append(" WHERE proposta.id_proposta_tcc = ? ");

            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idPropostaTCC);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Professor orientador = new Professor(rs.getLong("orientador.id_professor"), rs.getString("orientador.nome"), rs.getString("orientador.email"), rs.getInt("orientador.carga_trabalho_semestre"));
                Professor avaliador1 = new Professor(rs.getLong("avaliador1.id_professor"), rs.getString("avaliador1.nome"), rs.getString("avaliador1.email"), rs.getInt("avaliador1.carga_trabalho_semestre"));
                Professor avaliador2 = new Professor(rs.getLong("avaliador2.id_professor"), rs.getString("avaliador2.nome"), rs.getString("avaliador2.email"), rs.getInt("avaliador2.carga_trabalho_semestre"));
                professores = Arrays.asList(orientador, avaliador1, avaliador2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(conexao, pstmt, rs);
        return professores;
    }

    @Override
    public boolean deletarBanca(Long idPropostaTCC) {
        boolean sucesso;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = criarConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE Proposta_TCC ");
            sql.append(" SET id_professor_avaliador_primeiro = null, ");
            sql.append(" id_professor_avaliador_segundo = null ");
            sql.append(" WHERE id_proposta_tcc = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idPropostaTCC);
            pstmt.executeUpdate();
            sucesso = true;
        } catch (Exception ex) {
            // Em caso de erro inesperado, retorna false
            ex.printStackTrace();
            sucesso = false;
        }
        fecharConexao(conexao, pstmt);
        // Em caso de sucesso, retorna true
        return sucesso;
    }

}
