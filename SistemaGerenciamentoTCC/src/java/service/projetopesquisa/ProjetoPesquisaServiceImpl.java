package service.projetopesquisa;

import dao.projetopesquisa.ProjetoPesquisaDAO;
import dao.projetopesquisa.ProjetoPesquisaDAOImpl;
import domain.ProjetoPesquisa;

public class ProjetoPesquisaServiceImpl implements ProjetoPesquisaService {

    private static final ProjetoPesquisaDAO projetoPesquisaDAO = new ProjetoPesquisaDAOImpl();

    @Override
    public boolean salvar(String nome, String descricao) {
        return projetoPesquisaDAO.salvar(new ProjetoPesquisa(nome, descricao));
    }

}
