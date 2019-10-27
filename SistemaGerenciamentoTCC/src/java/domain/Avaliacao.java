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

}
