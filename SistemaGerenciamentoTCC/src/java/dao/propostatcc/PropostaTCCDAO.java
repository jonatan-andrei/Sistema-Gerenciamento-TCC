package dao.propostatcc;

import domain.Professor;
import domain.PropostaTCC;
import java.util.List;

public interface PropostaTCCDAO {

    boolean enviarTema(PropostaTCC propostaTCC, Long idAluno, Long idProfessor, Long idSugestao, Long idArea);

    boolean enviarArtigoFinal(Long idPropostaTCC, String artigo);

    List<PropostaTCC> listar();

    boolean desativar(Long idPropostaTCC);

    PropostaTCC buscarPorAluno(Long idAluno);
    
    PropostaTCC buscarPorId(Long idPropostaTCC);

    boolean salvarBanca(Long idPropostaTCC, List<Long> professores);

    List<Professor> verBanca(Long idPropostaTCC);

    boolean deletarBanca(Long idPropostaTCC);

}
