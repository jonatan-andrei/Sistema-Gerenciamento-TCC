package service.propostaTCC;

import domain.Professor;
import java.util.List;

public interface PropostaTCCService {

    String cadastrarProposta(String titulo, String descricao, Long idAluno, Long idProfessor, Long idArea);

    String cadastrarViaSugestao(String titulo, String descricao, Long idAluno, Long idProfessor, Long idArea, Long idSugestao);

    String desativarTCC(Long idPropostaTCC);
    
    String salvarBanca(Long idPropostaTCC, List<Long> professores);

    List<Professor> indicarBanca(Long idPropostaTCC);

    List<Professor> verBanca(Long idPrpostaTCC);

    List<Professor> verBancaEOrientador(Long idPropostaTCC);

    String removerBanca(Long idPropostaTCC);
}
