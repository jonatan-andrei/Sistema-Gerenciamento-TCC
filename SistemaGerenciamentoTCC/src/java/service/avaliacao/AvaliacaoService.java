package service.avaliacao;

import domain.Avaliacao;
import java.util.List;

public interface AvaliacaoService {

    boolean salvarAvaliacao(double notaFinal, String parecer, boolean aprovado, Long idProfessor, Long idPropostaTCC, String usoDeLinguagem, String apresentacao, String estruturaDoTexto, String conteudoDoTexto, String relevanciaProfissional);

    boolean editarAvaliacao(Long idAvaliacao, double notaFinal, String parecer, boolean aprovado, String usoDeLinguagem, String apresentacao, String estruturaDoTexto, String conteudoDoTexto, String relevanciaProfissional);

    Avaliacao buscarPorId(Long idAvaliacao);

    List<Avaliacao> buscarPorTCC(Long idPropostaTCC);

    boolean deletarAvaliacao(Long idAvaliacao);

}
