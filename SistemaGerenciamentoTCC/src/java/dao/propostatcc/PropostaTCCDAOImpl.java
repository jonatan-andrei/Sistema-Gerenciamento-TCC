package dao.propostatcc;

import dao.aluno.AlunoDAO;
import dao.aluno.AlunoDAOImpl;
import dao.conexao.ConexaoDAO;
import dao.professor.ProfessorDAO;
import dao.professor.ProfessorDAOImpl;
import domain.Aluno;
import domain.Area;
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

    private static final AlunoDAO alunoDAO = new AlunoDAOImpl();
    private static final ProfessorDAO professorDAO = new ProfessorDAOImpl();

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
        fecharConexao(pstmt);
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
        fecharConexao(pstmt);
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
            sql.append(" SELECT * ");
            sql.append(" FROM Proposta_TCC ");
            sql.append(" INNER JOIN aluno ");
            sql.append(" ON Proposta_TCC.id_aluno_autor = aluno.id_aluno ");
            sql.append(" INNER JOIN area ");
            sql.append(" ON area.id_area = proposta.id_area ");
            sql.append(" WHERE Proposta_TCC.ativo = 'S' AND aluno.ativo = 'S' ");
            pstmt = conexao.prepareCall(sql.toString());
            rs = pstmt.executeQuery();
            propostas = new ArrayList();
            while (rs.next()) {
                Aluno aluno = alunoDAO.buscarPorId(rs.getLong("id_aluno_autor"));
                Professor professor = professorDAO.buscarPorId(rs.getLong("id_professor_orientador"));
                Area area = new Area(rs.getLong("id_area"), rs.getString("nome"));
                List<Professor> banca = new ArrayList<>();
                if (rs.getLong("id_professor_avaliador_primeiro") != 0) {
                    banca.add(professorDAO.buscarPorId(rs.getLong("id_professor_avaliador_primeiro")));
                }
                if (rs.getLong("id_professor_avaliador_segundo") != 0) {
                    banca.add(professorDAO.buscarPorId(rs.getLong("id_professor_avaliador_segundo")));
                }
                PropostaTCC proposta = new PropostaTCC(rs.getLong("id_proposta_tcc"), rs.getString("titulo"), rs.getString("descricao"), rs.getString("artigo"), aluno, professor, banca, area);
                propostas.add(proposta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(pstmt, rs);
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
        fecharConexao(pstmt);
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
            sql.append(" SELECT * ");
            sql.append(" FROM Proposta_TCC ");
            sql.append(" WHERE Proposta_TCC.id_aluno = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idAluno);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Aluno aluno = alunoDAO.buscarPorId(rs.getLong("id_aluno_autor"));
                Professor professor = professorDAO.buscarPorId(rs.getLong("id_professor_orientador"));
                proposta = new PropostaTCC(rs.getLong("proposta.id_proposta_tcc"), rs.getString("proposta.titulo"), rs.getString("proposta.descricao"), rs.getString("proposta.artigo"), aluno, professor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(pstmt, rs);
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
            sql.append(" SELECT * ");
            sql.append(" FROM Proposta_TCC ");
            sql.append(" INNER JOIN area ");
            sql.append(" ON area.id_area = proposta.id_area ");
            sql.append(" WHERE proposta.id_proposta_tcc = ? ");
            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idPropostaTCC);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Aluno aluno = alunoDAO.buscarPorId(rs.getLong("id_aluno_autor"));
                Professor professor = professorDAO.buscarPorId(rs.getLong("id_professor_orientador"));
                Area area = new Area(rs.getLong("id_area"), rs.getString("nome"));
                List<Professor> banca = new ArrayList<>();
                if (rs.getLong("id_professor_avaliador_primeiro") != 0) {
                    banca.add(professorDAO.buscarPorId(rs.getLong("id_professor_avaliador_primeiro")));
                }
                if (rs.getLong("id_professor_avaliador_segundo") != 0) {
                    banca.add(professorDAO.buscarPorId(rs.getLong("id_professor_avaliador_segundo")));
                }
                proposta = new PropostaTCC(rs.getLong("id_proposta_tcc"), rs.getString("titulo"), rs.getString("descricao"), rs.getString("artigo"), aluno, professor, banca, area);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(pstmt, rs);
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
        fecharConexao(pstmt);
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
            sql.append(" SELECT * FROM Proposta_TCC ");
            sql.append(" WHERE Proposta_TCC.id_proposta_tcc = ? ");

            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idPropostaTCC);
            rs = pstmt.executeQuery();
            professores = new ArrayList<>();
            if (rs.next()) {
                Professor avaliador1 = professorDAO.buscarPorId(rs.getLong("id_professor_avaliador_primeiro"));
                Professor avaliador2 = professorDAO.buscarPorId(rs.getLong("id_professor_avaliador_segundo"));
                professores.addAll(Arrays.asList(avaliador1, avaliador2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(pstmt, rs);
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
            sql.append(" SELECT * FROM Proposta_TCC ");
            sql.append(" WHERE proposta.id_proposta_tcc = ? ");

            pstmt = conexao.prepareCall(sql.toString());
            pstmt.setLong(1, idPropostaTCC);
            rs = pstmt.executeQuery();
            professores = new ArrayList<>();
            if (rs.next()) {
                Professor orientador = professorDAO.buscarPorId(rs.getLong("id_professor_orientador"));
                if (rs.getLong("id_professor_avaliador_primeiro") != 0) {
                    professores.add(professorDAO.buscarPorId(rs.getLong("id_professor_avaliador_primeiro")));
                }
                if (rs.getLong("id_professor_avaliador_segundo") != 0) {
                    professores.add(professorDAO.buscarPorId(rs.getLong("id_professor_avaliador_segundo")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fecharConexao(pstmt, rs);
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
        fecharConexao(pstmt);
        // Em caso de sucesso, retorna true
        return sucesso;
    }

}
