package service.sugestaoTCC;

public interface SugestaoTCCService {

    boolean cadastrar(String descricao, Long idProfessor, Long idProjeto);

    void escolherSugestao(Long idSugestaoTCC);

}
