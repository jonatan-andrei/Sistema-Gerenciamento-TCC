package service.sugestaoTCC;

import domain.SugestaoTCC;

public interface SugestaoTCCService {

    boolean cadastrar(String descricao, Long idProfessor, Long idProjeto);

    void escolherSugestao(Long idSugestaoTCC);

    SugestaoTCC buscarPorId(Long idSugestaoTCC);

}
