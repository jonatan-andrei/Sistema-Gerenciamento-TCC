package dao.sugestaotcc;

import domain.SugestaoTCC;
import java.util.List;

public interface SugestaoTCCDAO {

    boolean cadastrar(String descricao, Long idProfessor, Long idProjeto);

    List<SugestaoTCC> buscarSugestoesDeProfessor(Long idProfessor);

    void escolherSugestao(Long idSugestaoTCC);
    
    SugestaoTCC buscarPorId(Long idSugestaoTCC);

}
