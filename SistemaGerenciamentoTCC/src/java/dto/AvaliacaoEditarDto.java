package dto;

import domain.Avaliacao;
import domain.Professor;
import java.util.HashMap;
import java.util.Map;

/*
    DTO utilizado para envio das informações da avaliação
 */
public class AvaliacaoEditarDto {

    private Long idAvaliacao;
    private Map<String, String> observacoesPorCriterio;
    private double notaFinal;
    private String parecer;
    private boolean aprovado;
    private Professor avaliador;

    public AvaliacaoEditarDto(Avaliacao avaliacao) {
        this.idAvaliacao = avaliacao.getIdAvaliacao();
        this.notaFinal = avaliacao.getNotaFinal();
        this.parecer = avaliacao.getParecer();
        this.aprovado = avaliacao.isAprovado();
        this.avaliador = avaliacao.getAvaliador();
        this.observacoesPorCriterio = new HashMap<>();
        avaliacao.getObservacoesPorCriterio().entrySet().forEach((entry) -> {
            this.observacoesPorCriterio.put(entry.getKey().name(), entry.getValue());
        });
    }

    public Long getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Long idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public Map<String, String> getObservacoesPorCriterio() {
        return observacoesPorCriterio;
    }

    public void setObservacoesPorCriterio(Map<String, String> observacoesPorCriterio) {
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
