package service.avaliacao;

import dao.avaliacao.AvaliacaoDAO;
import dao.avaliacao.AvaliacaoDAOImpl;
import domain.Avaliacao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Objects.nonNull;
import type.CriterioAvaliacao;

public class AvaliacaoServiceImpl implements AvaliacaoService {

    private static final AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAOImpl();

    @Override
    public boolean salvarAvaliacao(double notaFinal, String parecer, boolean aprovado, Long idProfessor, Long idPropostaTCC, String usoDeLinguagem, String apresentacao, String estruturaDoTexto, String conteudoDoTexto, String relevanciaProfissional) {
        Long idAvaliacao = avaliacaoDAO.cadastrar(idPropostaTCC, idProfessor, new Avaliacao(notaFinal, parecer, aprovado));
        if (nonNull(idAvaliacao)) {
            Map<CriterioAvaliacao, String> criterios = new HashMap();
            criterios.put(CriterioAvaliacao.USO_DE_LINGUAGEM, usoDeLinguagem);
            criterios.put(CriterioAvaliacao.APRESENTACAO, apresentacao);
            criterios.put(CriterioAvaliacao.ESTRUTURA_DO_TEXTO, estruturaDoTexto);
            criterios.put(CriterioAvaliacao.CONTEUDO_DO_TEXTO, conteudoDoTexto);
            criterios.put(CriterioAvaliacao.RELEVANCIA_AO_PERFIL_PROFISSIONAL, relevanciaProfissional);
            avaliacaoDAO.salvarCriteriosAvaliacao(idAvaliacao, criterios);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean editarAvaliacao(Long idAvaliacao, double notaFinal, String parecer, boolean aprovado, String usoDeLinguagem, String apresentacao, String estruturaDoTexto, String conteudoDoTexto, String relevanciaProfissional) {
        boolean sucesso = avaliacaoDAO.editar(new Avaliacao(idAvaliacao, notaFinal, parecer, aprovado));
        if (sucesso) {
            Map<CriterioAvaliacao, String> criterios = new HashMap();
            criterios.put(CriterioAvaliacao.USO_DE_LINGUAGEM, usoDeLinguagem);
            criterios.put(CriterioAvaliacao.APRESENTACAO, apresentacao);
            criterios.put(CriterioAvaliacao.ESTRUTURA_DO_TEXTO, estruturaDoTexto);
            criterios.put(CriterioAvaliacao.CONTEUDO_DO_TEXTO, conteudoDoTexto);
            criterios.put(CriterioAvaliacao.RELEVANCIA_AO_PERFIL_PROFISSIONAL, relevanciaProfissional);
            avaliacaoDAO.editarCriteriosAvaliacao(idAvaliacao, criterios);
        }
        return sucesso;
    }

    @Override
    public List<Avaliacao> buscarPorTCC(Long idPropostaTCC) {
        List<Avaliacao> avaliacoes = avaliacaoDAO.buscarPorTCC(idPropostaTCC);
        if (nonNull(avaliacoes)) {
            avaliacoes.forEach(a -> a.setObservacoesPorCriterio(avaliacaoDAO.buscarCriteriosPorAvaliacao(a.getIdAvaliacao())));
        }
        return avaliacoes;
    }

    @Override
    public Avaliacao buscarPorId(Long idAvaliacao) {
        Avaliacao avaliacao = avaliacaoDAO.buscarPorId(idAvaliacao);
        if (nonNull(avaliacao)){
            avaliacao.setObservacoesPorCriterio(avaliacaoDAO.buscarCriteriosPorAvaliacao(idAvaliacao));
        }
        return avaliacao;
    }

    @Override
    public boolean deletarAvaliacao(Long idAvaliacao) {
        boolean sucesso = avaliacaoDAO.deletarCriteriosAvaliacao(idAvaliacao);
        if (sucesso) {
            avaliacaoDAO.deletar(idAvaliacao);
        }
        return sucesso;
    }

}
