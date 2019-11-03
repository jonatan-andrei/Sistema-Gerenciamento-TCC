package service.sugestaoTCC;

import dao.sugestaotcc.SugestaoTCCDAO;
import dao.sugestaotcc.SugestaoTCCDAOImpl;

public class SugestaoTCCServiceImpl implements SugestaoTCCService {
    
    private static final SugestaoTCCDAO sugestaoTCCDAO = new SugestaoTCCDAOImpl();
    
    @Override
    public void escolherSugestao(Long idSugestaoTCC) {
        sugestaoTCCDAO.escolherSugestao(idSugestaoTCC);
    }
    
}
