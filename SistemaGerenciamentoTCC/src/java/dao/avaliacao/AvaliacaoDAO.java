package dao.avaliacao;

import domain.Avaliacao;
import java.util.List;
import java.util.Map;
import type.CriterioAvaliacao;

public interface AvaliacaoDAO {

    Long cadastrar(Long idPropostaTCC, Long idProfessor, Avaliacao avaliacao);
    
    void salvarCriteriosAvaliacao(Long idAvaliacao, Map<CriterioAvaliacao, String> criterios);

    boolean editar(Avaliacao avaliacao);
    
    List<Avaliacao> buscarPorTCC (Long idPropostaTCC);
    
    void editarCriteriosAvaliacao(Long idAvaliacao, Map<CriterioAvaliacao, String> criterios);

    boolean deletar(Long idAvaliacao);
    
    void deletarCriteriosAvaliacao(Long idAvaliacao);

}
