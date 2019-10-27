package dao.propostatcc;

import domain.Professor;
import domain.PropostaTCC;
import java.util.List;

public interface PropostaTCCDAO {

    void enviarTema(PropostaTCC propostaTCC);

    void enviarArtigoFinal(Long idPropostaTCC, String artigo);

    List<PropostaTCC> listar();

    void deletar(Long idPropostaTCC);

    void cadastrarBanca(Long idPropostaTCC, List<Professor> professores);
    
    void editarBanca(Long idPropostaTCC, List<Professor> professores);
    
    List<Professor> verBanca(Long idPropostaTCC);
    
    void deletarBanca(Long idPropostaTCC);
    
}
