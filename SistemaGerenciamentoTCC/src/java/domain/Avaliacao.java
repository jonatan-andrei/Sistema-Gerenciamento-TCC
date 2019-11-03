package domain;

import type.CriterioAvaliacao;
import java.util.Map;

public class Avaliacao {

    private Long idAvaliacao;
    private Map<CriterioAvaliacao, String> observacoesPorCriterio;
    private double notaFinal;
    private String parecer;
    private boolean aprovado;
    private Professor avaliador;

    public Avaliacao(double notaFinal, String parecer, boolean aprovado) {
        this.notaFinal = notaFinal;
        this.parecer = parecer;
        this.aprovado = aprovado;
    }

    public Avaliacao(Long idAvaliacao, double notaFinal, String parecer, boolean aprovado) {
        this.idAvaliacao = idAvaliacao;
        this.notaFinal = notaFinal;
        this.parecer = parecer;
        this.aprovado = aprovado;
    }

    public Avaliacao(Long idAvaliacao, double notaFinal, String parecer, boolean aprovado, Professor avaliador) {
        this.idAvaliacao = idAvaliacao;
        this.notaFinal = notaFinal;
        this.parecer = parecer;
        this.aprovado = aprovado;
        this.avaliador = avaliador;
    }

    public Long getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Long idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public Map<CriterioAvaliacao, String> getObservacoesPorCriterio() {
        return observacoesPorCriterio;
    }

    public void setObservacoesPorCriterio(Map<CriterioAvaliacao, String> observacoesPorCriterio) {
        this.observacoesPorCriterio = observacoesPorCriterio;
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String getParecer() {
        return parecer;
    }

    public void setParecer(String parecer) {
        this.parecer = parecer;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public Professor getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(Professor avaliador) {
        this.avaliador = avaliador;
    }

}
