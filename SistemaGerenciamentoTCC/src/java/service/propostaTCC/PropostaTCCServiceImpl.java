package service.propostaTCC;

import dao.propostatcc.PropostaTCCDAO;
import dao.propostatcc.PropostaTCCDAOImpl;
import domain.Area;
import domain.Avaliacao;
import domain.Professor;
import domain.PropostaTCC;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static java.util.Objects.isNull;
import java.util.stream.Collectors;
import service.avaliacao.AvaliacaoService;
import service.avaliacao.AvaliacaoServiceImpl;
import service.professor.ProfessorService;
import service.professor.ProfessorServiceImpl;
import service.sugestaoTCC.SugestaoTCCService;
import service.sugestaoTCC.SugestaoTCCServiceImpl;
import static type.StatusPropostaTCC.APROVADO;
import static type.StatusPropostaTCC.NAO_AVALIADO;
import static type.StatusPropostaTCC.REPROVADO;

public class PropostaTCCServiceImpl implements PropostaTCCService {

    private static final ProfessorService professorService = new ProfessorServiceImpl();
    private static final SugestaoTCCService sugestaoTCCService = new SugestaoTCCServiceImpl();
    private static final AvaliacaoService avaliacaoService = new AvaliacaoServiceImpl();
    private static final PropostaTCCDAO propostaTCCDAO = new PropostaTCCDAOImpl();

    @Override
    public boolean cadastrarProposta(String titulo, String descricao, Long idAluno, Long idProfessor, Long idArea) {
        return cadastrar(titulo, descricao, idAluno, idProfessor, idArea, null);
    }

    @Override
    public boolean cadastrarViaSugestao(String titulo, String descricao, Long idAluno, Long idProfessor, Long idArea, Long idSugestao) {
        sugestaoTCCService.escolherSugestao(idSugestao);
        return cadastrar(titulo, descricao, idAluno, idProfessor, idArea, idSugestao);
    }

    @Override
    public boolean enviarArtigoFinal(Long idPropostaTCC, String artigo) {
        return propostaTCCDAO.enviarArtigoFinal(idPropostaTCC, artigo);
    }

    @Override
    public PropostaTCC buscarPorId(Long idPropostaTCC) {
        return propostaTCCDAO.buscarPorId(idPropostaTCC);
    }

    @Override
    public List<PropostaTCC> listar() {
        List<PropostaTCC> propostas = propostaTCCDAO.listar();
        propostas.forEach(p -> {
            List<Avaliacao> avaliacoes = avaliacaoService.buscarPorTCC(p.getIdPropostaTCC());
            p.setAvaliacoes(avaliacoes);
            if (avaliacoes.size() < 2) {
                p.setStatus(NAO_AVALIADO); // Se a proposta ainda não recebeu duas avaliações, não foi aprovado nem reprovado ainda
            } else if (avaliacoes.get(0).isAprovado() && avaliacoes.get(1).isAprovado()) {
                p.setStatus(APROVADO);
            } else {
                p.setStatus(REPROVADO);
            }
        });
        return propostas;
    }

    @Override
    public boolean desativarTCC(Long idPropostaTCC) {
        boolean sucesso = propostaTCCDAO.desativar(idPropostaTCC);
        if (sucesso) {
            removerBanca(idPropostaTCC, true);
        }
        return sucesso;
    }

    @Override
    public String salvarBanca(Long idPropostaTCC, List<Long> professores) {
        List<Avaliacao> avaliacoes = avaliacaoService.buscarPorTCC(idPropostaTCC);

        // Verifica se ocorreu erro na conexão com o banco
        if (isNull(avaliacoes)) {
            return "Erro na conexão com o banco de dados";
        }

        // Verifica se já não existem avaliações cadastradas para o TCC, o que impediria a substituição da banca
        if (!avaliacoes.isEmpty()) {
            return "Este TCC já foi avaliado, ele não pode ter sua banca substituída.";
        }

        // Verifica se já há uma banca cadastrada para o TCC e remove
        removerBanca(idPropostaTCC, false);

        // Salva a nova banca
        propostaTCCDAO.salvarBanca(idPropostaTCC, professores);

        // Aumenta a carga de trabalho dos professores
        professorService.aumentarCargaDeTrabalho(professores);

        return "Banca salva com sucesso";
    }

    @Override
    public List<Professor> indicarBanca(Long idPropostaTCC) {
        List<Professor> professoresOrdenadosParaIndicacao = new ArrayList<>();

        // Busca a proposta pelo id
        PropostaTCC proposta = propostaTCCDAO.buscarPorId(idPropostaTCC);

        if (isNull(proposta)) {
            return null;
        }

        //Busca professores que já compõem a banca, caso essa já seja definida
        List<Professor> professoresAtuais = propostaTCCDAO.verBanca(idPropostaTCC);

        // Busca lista de professores
        List<Professor> professores = professorService.listar();

        // Remove professor orientador da lista de professores
        professores.removeIf(p -> p.getId().equals(proposta.getOrientador().getId()));

        // Remove professores que já compõem a banca da lista de professores (eles serão adicionados no início da lista
        professores.removeIf(p -> professoresAtuais.stream().map(Professor::getId).collect(Collectors.toList()).contains(p.getId()));

        // Ordena professores pela menor carga de trabalho no semestre
        Collections.sort(professores, (Professor p1, Professor p2) -> p1.getCargaTrabalhoSemestre() > p2.getCargaTrabalhoSemestre() ? 1 : -1);

        // Cria nova lista apenas com professores que tem interesse na área do TCC
        List<Professor> professoresComInteresseNaArea = professores.stream()
                .filter(p -> p.getAreasDeInteresse().stream().map(Area::getIdArea)
                .collect(Collectors.toList()).contains(proposta.getArea().getIdArea()))
                .collect(Collectors.toList());

        // Cria lista com todos os demais professores
        List<Professor> professoresSemInteresseNaArea = professores.stream().
                filter(p -> !professoresComInteresseNaArea.contains(p))
                .collect(Collectors.toList());

        // Adiciona todos os professores na lista de retorno
        professoresOrdenadosParaIndicacao.addAll(professoresAtuais);
        professoresOrdenadosParaIndicacao.addAll(professoresComInteresseNaArea);
        professoresOrdenadosParaIndicacao.addAll(professoresSemInteresseNaArea);
        return professoresOrdenadosParaIndicacao;
    }

    @Override
    public List<Professor> verBanca(Long idPropostaTCC) {
        return propostaTCCDAO.verBanca(idPropostaTCC);
    }

    @Override
    public List<Professor> verBancaEOrientador(Long idPropostaTCC) {
        return propostaTCCDAO.verBancaEOrientador(idPropostaTCC);
    }

    @Override
    public boolean removerBanca(Long idPropostaTCC) {
        return removerBanca(idPropostaTCC, false);
    }

    private boolean removerBanca(Long idPropostaTCC, boolean incluiOrientador) {
        List<Professor> professores;
        if (incluiOrientador) {
            professores = verBancaEOrientador(idPropostaTCC);
        } else {
            professores = verBanca(idPropostaTCC);
        }
        boolean sucesso = propostaTCCDAO.deletarBanca(idPropostaTCC);
        if (sucesso && !professores.isEmpty()) {
            professorService.reduzirCargaDeTrabalho(professores.stream().map(Professor::getId).collect(Collectors.toList()));
        }
        return sucesso;
    }

    private boolean cadastrar(String titulo, String descricao, Long idAluno, Long idProfessor, Long idArea, Long idSugestao) {
        boolean sucesso = propostaTCCDAO.enviarTema(new PropostaTCC(titulo, descricao), idAluno, idProfessor, idSugestao, idArea);
        if (sucesso) {
            professorService.aumentarCargaDeTrabalho(Arrays.asList(idProfessor));
        }
        return sucesso;
    }
}
