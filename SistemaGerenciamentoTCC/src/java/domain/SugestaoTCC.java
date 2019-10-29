package domain;

public class SugestaoTCC {

    private Long idSugestaoTCC;
    private String descricao;
    private ProjetoPesquisa projeto;
    private boolean escolhida;
    
    public SugestaoTCC(Long idSugestaoTCC, String descricao, String escolhida){
        this.idSugestaoTCC = idSugestaoTCC;
        this.descricao = descricao;
        this.escolhida = "S".equals(escolhida);
    }

}
