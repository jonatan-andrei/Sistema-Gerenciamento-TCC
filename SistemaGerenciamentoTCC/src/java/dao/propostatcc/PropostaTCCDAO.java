package dao.propostatcc;

import domain.Professor;
import domain.PropostaTCC;
import java.util.List;

public interface PropostaTCCDAO {

    boolean enviarTema(PropostaTCC propostaTCC);

    boolean enviarArtigoFinal(Long idPropostaTCC, String artigo);

    List<PropostaTCC> listar();

    boolean desativar(Long idPropostaTCC);
    
    PropostaTCC buscarPorAluno(Long idAluno);

    boolean cadastrarBanca(Long idPropostaTCC, List<Professor> professores);
    
    boolean editarBanca(Long idPropostaTCC, List<Professor> professores);
    
    List<Professor> verBanca(Long idPropostaTCC);
    
    boolean deletarBanca(Long idPropostaTCC);
    
}
