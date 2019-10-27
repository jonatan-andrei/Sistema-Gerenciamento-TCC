package dao.avaliacao;

import domain.Avaliacao;
import java.util.List;

public interface AvaliacaoDAO {

    void cadastrar(Long idPropostaTCC, Avaliacao avaliacao);

    void editar(Long idAvaliacao, Avaliacao avaliacao);

    List<Avaliacao> verAvaliacoes(Long idPropostaTCC);

    void deletar(Long idAvaliacao);

}
