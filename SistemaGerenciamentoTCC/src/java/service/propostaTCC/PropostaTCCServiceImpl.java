package service.propostaTCC;

import dao.sugestaotcc.SugestaoTCCDAO;
import dao.sugestaotcc.SugestaoTCCDAOImpl;

public class PropostaTCCServiceImpl implements PropostaTCCService {
    
    private static final SugestaoTCCDAO sugestaoTCCDAO = new SugestaoTCCDAOImpl();
    
    @Override
    public boolean cadastrar(String titulo, String descricao, Long idAluno, Long idProfessor, Long idArea){
        // TODO
        // Validar se aluno já não enviou proposta (considerar apenas ativo)
    }
    
    @Override
    public boolean cadastrarViaSugestao(String titulo, String descricao, Long idAluno, Long idProfessor, Long idArea, Long idSugestao){
        sugestaoTCCDAO.escolherSugestao(idSugestao);
        // TODO
        // Validar se aluno já não enviou proposta (considerar apenas ativo)
    }
    
}
