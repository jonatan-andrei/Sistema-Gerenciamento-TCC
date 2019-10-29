package domain;

import java.util.List;

public class PropostaTCC {

    private Long idPropostaTCC;
    private String titulo;
    private String descricao;
    private String artigo;
    private Aluno autor;
    private Professor orientador;
    private SugestaoTCC sugestaoOrigem;
    private Area area;
    private int ano;
    private int semestre;
    private List<Avaliacao> avaliacoes;
    private boolean aprovado;
    private List<Professor> banca;
    private boolean ativo;

    public PropostaTCC(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public PropostaTCC(Long idPropostaTCC, String titulo, String descricao, String artigo, Aluno autor, Professor orientador) {
        this.idPropostaTCC = idPropostaTCC;
        this.titulo = titulo;
        this.descricao = descricao;
        this.artigo = artigo;
        this.autor = autor;
        this.orientador = orientador;
    }
}
