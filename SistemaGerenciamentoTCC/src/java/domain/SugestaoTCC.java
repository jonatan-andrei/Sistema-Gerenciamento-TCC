package domain;

public class SugestaoTCC {

    private Long idSugestaoTCC;
    private String descricao;
    private ProjetoPesquisa projeto;
    private boolean escolhida;

    public SugestaoTCC(Long idSugestaoTCC, String descricao, String escolhida, ProjetoPesquisa projeto) {
        this.idSugestaoTCC = idSugestaoTCC;
        this.descricao = descricao;
        this.escolhida = "S".equals(escolhida);
        this.projeto = projeto;
    }

    public Long getIdSugestaoTCC() {
        return idSugestaoTCC;
    }

    public void setIdSugestaoTCC(Long idSugestaoTCC) {
        this.idSugestaoTCC = idSugestaoTCC;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ProjetoPesquisa getProjeto() {
        return projeto;
    }

    public void setProjeto(ProjetoPesquisa projeto) {
        this.projeto = projeto;
    }

    public boolean isEscolhida() {
        return escolhida;
    }

    public void setEscolhida(boolean escolhida) {
        this.escolhida = escolhida;
    }

}
