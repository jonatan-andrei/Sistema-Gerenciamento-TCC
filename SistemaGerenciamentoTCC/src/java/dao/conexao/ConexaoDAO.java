package dao.conexao;

import java.sql.*;
import static java.util.Objects.isNull;

public abstract class ConexaoDAO {

    private Connection con = null;

    // Inicia conexão com o banco
    protected Connection criarConexao() {
        try {
            // Caso não tenha aberto nenhuma conexão com o Derby, cria uma nova conexão e cria as tabelas
            if (isNull(con)) {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                con = DriverManager.getConnection("jdbc:derby:mydb;create=true");
                criaTabelas();
            }
            return con;
        } catch (Exception ex) {
            throw new RuntimeException("Erro na conexão com o Derby: ", ex);
        }
    }

    // Método para fechar a conexão com o Derby
    protected void fecharConexao(PreparedStatement pstmt) {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao fechar conexão com o Derby: ", ex);
        }

    }

    // Método para fechar a conexão com o Derby
    protected void fecharConexao(PreparedStatement pstmt, ResultSet rs) {

        fecharConexao(pstmt);

        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao fechar conexão com o Derby: ", ex);
        }

    }

    private void criaTabelas() throws SQLException {
        Statement st = con.createStatement();
        criarTabelaAluno(st);
        criarTabelaProfessor(st);
        criarTabelaArea(st);
        criarTabelaProfessorArea(st);
        criarTabelaProjetoPesquisa(st);
        criarTabelaSugestaoTCC(st);
        criarTabelaPropostaTCC(st);
        criarTabelaAvaliacao(st);
        criarTabelaAvaliacaoCriterio(st);
        inserirDadosArea();
        st.close();
    }

    private void criarTabelaAluno(Statement st) throws SQLException {
        try {
            st.execute("DROP TABLE ALUNO");
        } catch (Exception e) {
        }
        String createTable = "CREATE TABLE Aluno ( "
                + "  id_aluno integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "  nome varchar(100) NOT NULL, "
                + "  email varchar(100) NOT NULL, "
                + "  matricula varchar(15) NOT NULL, "
                + "  telefone varchar(15), "
                + "  ativo varchar(1) DEFAULT 'S' "
                + ") ";
        st.execute(createTable);
    }

    private void criarTabelaProfessor(Statement st) throws SQLException {
        try {
            st.execute("DROP TABLE Professor");
        } catch (Exception e) {
        }
        String createTable = "CREATE TABLE Professor ("
                + "  id_professor integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                + "  nome varchar(100) NOT NULL,"
                + "  email varchar(100) NOT NULL,"
                + "  carga_trabalho_semestre integer DEFAULT 0,"
                + "  ativo varchar(1) DEFAULT 'S'"
                + ")";
        st.execute(createTable);
    }

    private void criarTabelaArea(Statement st) throws SQLException {
        try {
            st.execute("DROP TABLE Area");
        } catch (Exception e) {
        }
        String createTable = "CREATE TABLE Area ("
                + "  id_area integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                + "  nome varchar(100) NOT NULL"
                + ")";
        st.execute(createTable);
    }

    private void criarTabelaProfessorArea(Statement st) throws SQLException {
        try {
            st.execute("DROP TABLE Professor_Area");
        } catch (Exception e) {
        }
        String createTable = "CREATE TABLE Professor_Area ("
                + "  id integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                + "  id_area integer NOT NULL,"
                + "  id_professor integer NOT NULL"
                + ")";
        st.execute(createTable);
    }

    private void criarTabelaProjetoPesquisa(Statement st) throws SQLException {
        try {
            st.execute("DROP TABLE Projeto_Pesquisa");
        } catch (Exception e) {
        }
        String createTable = "CREATE TABLE Projeto_Pesquisa ("
                + "  id_projeto_pesquisa integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                + "  nome varchar(100) NOT NULL,"
                + "  descricao varchar(200) NOT NULL"
                + ")";
        st.execute(createTable);
    }

    private void criarTabelaSugestaoTCC(Statement st) throws SQLException {
        try {
            st.execute("DROP TABLE Sugestao_TCC");
        } catch (Exception e) {
        }
        String createTable = "CREATE TABLE Sugestao_TCC ("
                + "  id_sugestao_tcc integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                + "  descricao varchar(100) NOT NULL,"
                + "  id_professor_criador integer NOT NULL,"
                + "  id_projeto_pesquisa integer,"
                + "  escolhida varchar(1) default 'N'"
                + ")";
        st.execute(createTable);
    }

    private void criarTabelaPropostaTCC(Statement st) throws SQLException {
        try {
            st.execute("DROP TABLE Proposta_TCC");
        } catch (Exception e) {
        }
        String createTable = "CREATE TABLE Proposta_TCC ("
                + "  id_proposta_tcc integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                + "  titulo varchar(100) NOT NULL,"
                + "  descricao varchar(100) NOT NULL,"
                + "  artigo varchar(100),"
                + "  id_aluno_autor integer NOT NULL,"
                + "  id_professor_orientador integer NOT NULL,"
                + "  id_professor_avaliador_primeiro integer,"
                + "  id_professor_avaliador_segundo integer,"
                + "  id_sugestao_origem integer,"
                + "  id_area integer NOT NULL,"
                + "  ativo varchar(1) DEFAULT 'S'"
                + ")";
        st.execute(createTable);
    }

    private void criarTabelaAvaliacao(Statement st) throws SQLException {
        try {
            st.execute("DROP TABLE Avaliacao");
        } catch (Exception e) {
        }
        String createTable = "CREATE TABLE Avaliacao ("
                + "  id_avaliacao integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                + "  nota_final float(4) NOT NULL,"
                + "  aprovado varchar(1) NOT NULL,"
                + "  id_professor_avaliador integer NOT NULL,"
                + "  parecer varchar(100) NOT NULL,"
                + "  id_proposta_tcc integer NOT NULL"
                + ")";
        st.execute(createTable);
    }

    private void criarTabelaAvaliacaoCriterio(Statement st) throws SQLException {
        try {
            st.execute("DROP TABLE Avaliacao_Criterio");
        } catch (Exception e) {
        }
        String createTable = "CREATE TABLE Avaliacao_Criterio ("
                + "  id_avaliacao_criterio integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                + "  id_avaliacao integer NOT NULL,"
                + "  criterio varchar(100) NOT NULL,"
                + "  observacao varchar(100) NOT NULL"
                + ")";
        st.execute(createTable);
    }

    private void inserirDadosArea() throws SQLException {
        Statement stmt = con.createStatement();
        con.setAutoCommit(false);
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Desenvolvimento Java')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Desenvolvimento Javascript')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Desenvolvimento PHP')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Desenvolvimento Python')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Desenvolvimento Android')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Desenvolvimento IOS')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Desenvolvimento Desktop')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('IHC')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Estrutura de Dados')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Web Design')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Engenharia de Software')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Banco de Dados')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Redes de Computadores')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Teste de Software')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Segurança e Auditoria')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Gestão de TI')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Inteligência Artificial')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Ciência de Dados')");
        stmt.addBatch("INSERT INTO Area(nome) VALUES ('Internet das coisas')");
        stmt.executeBatch();
        con.commit();
        stmt.close();
    }

}
