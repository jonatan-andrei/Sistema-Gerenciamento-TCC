package service.propostaTCC;

import domain.Professor;
import domain.PropostaTCC;
import java.util.List;

public interface PropostaTCCService {

    boolean cadastrarProposta(String titulo, String descricao, Long idAluno, Long idProfessor, Long idArea);

    boolean cadastrarViaSugestao(String titulo, String descricao, Long idAluno, Long idProfessor, Long idArea, Long idSugestao);

    boolean enviarArtigoFinal(Long idPropostaTCC, String artigo);

    List<PropostaTCC> listar();

    boolean desativarTCC(Long idPropostaTCC);

    PropostaTCC buscarPorId(Long idPropostaTCC);

    String salvarBanca(Long idPropostaTCC, List<Long> professores);

    List<Professor> indicarBanca(Long idPropostaTCC);

    List<Professor> verBanca(Long idPrpostaTCC);

    List<Professor> verBancaEOrientador(Long idPropostaTCC);

    boolean removerBanca(Long idPropostaTCC);
}
