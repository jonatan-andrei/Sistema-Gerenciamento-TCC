package service.avaliacao;

public interface AvaliacaoService {
    
    boolean salvarAvaliacao(double notaFinal, String parecer, boolean aprovado,  Long idProfessor, Long idPropostaTCC, String usoDeLinguagem, String apresentacao, String estruturaDoTexto, String conteudoDoTexto, String relevanciaProfissional);
    
    boolean editarAvaliacao(Long idAvaliacao, double notaFinal, String parecer, boolean aprovado, String usoDeLinguagem, String apresentacao, String estruturaDoTexto, String conteudoDoTexto, String relevanciaProfissional);
    
    boolean deletarAvaliacao(Long idAvaliacao);
    
}
