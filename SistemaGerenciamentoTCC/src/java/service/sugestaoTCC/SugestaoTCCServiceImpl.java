package service.sugestaoTCC;

import dao.sugestaotcc.SugestaoTCCDAO;
import dao.sugestaotcc.SugestaoTCCDAOImpl;
import domain.SugestaoTCC;

public class SugestaoTCCServiceImpl implements SugestaoTCCService {

    private static final SugestaoTCCDAO sugestaoTCCDAO = new SugestaoTCCDAOImpl();

    @Override
    public boolean cadastrar(String descricao, Long idProfessor, Long idProjeto) {
        return sugestaoTCCDAO.cadastrar(descricao, idProfessor, idProjeto);
    }

    @Override
    public void escolherSugestao(Long idSugestaoTCC) {
        sugestaoTCCDAO.escolherSugestao(idSugestaoTCC);
    }

    @Override
    public SugestaoTCC buscarPorId(Long idSugestaoTCC) {
        return sugestaoTCCDAO.buscarPorId(idSugestaoTCC);
    }

}
