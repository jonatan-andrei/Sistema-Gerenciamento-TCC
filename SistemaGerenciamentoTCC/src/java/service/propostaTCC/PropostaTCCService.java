package service.propostaTCC;

public interface PropostaTCCService {
    
    boolean cadastrar(String titulo, String descricao, Long idAluno, Long idProfessor, Long idArea);
    
    boolean cadastrarViaSugestao(String titulo, String descricao, Long idAluno, Long idProfessor, Long idArea, Long idSugestao);
    
}
