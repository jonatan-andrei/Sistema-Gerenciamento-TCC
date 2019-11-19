package domain;

import java.util.List;
import type.StatusPropostaTCC;

public class PropostaTCC {

    private Long idPropostaTCC;
    private String titulo;
    private String descricao;
    private String artigo;
    private Aluno autor;
    private Professor orientador;
    private SugestaoTCC sugestaoOrigem;
    private Area area;
    private List<Avaliacao> avaliacoes;
    private List<Professor> banca;
    private boolean ativo;
    private StatusPropostaTCC status;

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

    public PropostaTCC(Long idPropostaTCC, String titulo, String descricao, String artigo, Aluno autor, Professor orientador, List<Professor> banca) {
        this.idPropostaTCC = idPropostaTCC;
        this.titulo = titulo;
        this.descricao = descricao;
        this.artigo = artigo;
        this.autor = autor;
        this.orientador = orientador;
        this.banca = banca;
    }

    public PropostaTCC(Long idPropostaTCC, String titulo, String descricao, String artigo, Aluno autor, Professor orientador, Area area) {
        this.idPropostaTCC = idPropostaTCC;
        this.titulo = titulo;
        this.descricao = descricao;
        this.artigo = artigo;
        this.autor = autor;
        this.orientador = orientador;
        this.area = area;
    }

    public PropostaTCC(Long idPropostaTCC, String titulo, String descricao, String artigo, Aluno autor, Professor orientador, List<Professor> banca, Area area) {
        this.idPropostaTCC = idPropostaTCC;
        this.titulo = titulo;
        this.descricao = descricao;
        this.artigo = artigo;
        this.autor = autor;
        this.orientador = orientador;
        this.banca = banca;
        this.area = area;
    }

    public Long getIdPropostaTCC() {
        return idPropostaTCC;
    }

    public void setIdPropostaTCC(Long idPropostaTCC) {
        this.idPropostaTCC = idPropostaTCC;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getArtigo() {
        return artigo;
    }

    public void setArtigo(String artigo) {
        this.artigo = artigo;
    }

    public Aluno getAutor() {
        return autor;
    }

    public void setAutor(Aluno autor) {
        this.autor = autor;
    }

    public Professor getOrientador() {
        return orientador;
    }

    public void setOrientador(Professor orientador) {
        this.orientador = orientador;
    }

    public SugestaoTCC getSugestaoOrigem() {
        return sugestaoOrigem;
    }

    public void setSugestaoOrigem(SugestaoTCC sugestaoOrigem) {
        this.sugestaoOrigem = sugestaoOrigem;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public List<Professor> getBanca() {
        return banca;
    }

    public void setBanca(List<Professor> banca) {
        this.banca = banca;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public StatusPropostaTCC getStatus() {
        return status;
    }

    public void setStatus(StatusPropostaTCC status) {
        this.status = status;
    }
}
