package dao.projetopesquisa;

import domain.ProjetoPesquisa;
import java.util.List;

public interface ProjetoPesquisaDAO {
    
    boolean salvar(ProjetoPesquisa projetoPesquisa);
    
    List<ProjetoPesquisa> buscar();
    
}
