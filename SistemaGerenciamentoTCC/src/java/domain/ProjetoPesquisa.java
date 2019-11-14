package domain;

public class ProjetoPesquisa {

    private Long idProjetoPesquisa;
    private String nome;
    private String descricao;

    public ProjetoPesquisa(Long idProjetoPesquisa, String nome, String descricao) {
        this.idProjetoPesquisa = idProjetoPesquisa;
        this.nome = nome;
        this.descricao = descricao;
    }

    public ProjetoPesquisa(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getIdProjetoPesquisa() {
        return idProjetoPesquisa;
    }

    public void setIdProjetoPesquisa(Long idProjetoPesquisa) {
        this.idProjetoPesquisa = idProjetoPesquisa;
    }

}
