package service.projetopesquisa;

import domain.ProjetoPesquisa;
import java.util.List;

public interface ProjetoPesquisaService {

    boolean salvar(String nome, String descricao);

    List<ProjetoPesquisa> listar();
}
