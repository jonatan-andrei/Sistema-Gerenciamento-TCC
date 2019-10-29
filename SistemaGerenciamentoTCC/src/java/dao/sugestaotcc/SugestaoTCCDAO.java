package dao.sugestaotcc;

import domain.SugestaoTCC;
import java.util.List;

public interface SugestaoTCCDAO {

    boolean salvar(String descricao, Long idArea, Long idProfessor, Long idProjeto);
    
    List<SugestaoTCC> buscarSugestoesDeProfessor(Long idProfessor);

}
