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
}
