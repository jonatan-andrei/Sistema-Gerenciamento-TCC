package service.propostaTCC;

import dao.propostatcc.PropostaTCCDAO;
import dao.propostatcc.PropostaTCCDAOImpl;
import domain.Area;
import domain.Professor;
import domain.PropostaTCC;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static java.util.Objects.nonNull;
import java.util.stream.Collectors;
import service.professor.ProfessorService;
import service.professor.ProfessorServiceImpl;
import service.sugestaoTCC.SugestaoTCCService;
import service.sugestaoTCC.SugestaoTCCServiceImpl;

public class PropostaTCCServiceImpl implements PropostaTCCService {
    
    private static final ProfessorService professorService = new ProfessorServiceImpl();
    private static final SugestaoTCCService sugestaoTCCService = new SugestaoTCCServiceImpl();
    private static final PropostaTCCDAO propostaTCCDAO = new PropostaTCCDAOImpl();
    
    @Override
    public String cadastrarProposta(String titulo, String descricao, Long idAluno, Long idProfessor, Long idArea) {
        return cadastrar(titulo, descricao, idAluno, idProfessor, idArea, null);
    }
    
    @Override
    public String cadastrarViaSugestao(String titulo, String descricao, Long idAluno, Long idProfessor, Long idArea, Long idSugestao) {
        sugestaoTCCService.escolherSugestao(idSugestao);
        return cadastrar(titulo, descricao, idAluno, idProfessor, idArea, idSugestao);
    }
    
    private String cadastrar(String titulo, String descricao, Long idAluno, Long idProfessor, Long idArea, Long idSugestao) {
        PropostaTCC propostaSalva = propostaTCCDAO.buscarPorAluno(idAluno);
        if (nonNull(propostaSalva)) {
            return "O aluno já cadastrou uma proposta de TCC. Desative-a para cadastrar outra proposta.";
        }
        boolean sucesso = propostaTCCDAO.enviarTema(new PropostaTCC(titulo, descricao), idAluno, idProfessor, idSugestao, idArea);
        if (sucesso) {
            professorService.aumentarCargaDeTrabalho(Arrays.asList(idProfessor));
        }
        return sucesso ? "Proposta de TCC salva com sucesso" : "Erro na conexão com o banco de dados.";
    }
    
    @Override
    public String desativarTCC(Long idPropostaTCC) {
        boolean sucesso = propostaTCCDAO.desativar(idPropostaTCC);
        if (sucesso) {
            removerBanca(idPropostaTCC);
        }
        return sucesso ? "Proposta de TCC desativada com sucesso" : "Erro na conexão com o banco de dados.";
    }
    
    @Override
    public String salvarBanca(Long idPropostaTCC, List<Long> professores) {
        // Validar se existe avaliação cadastrada para o professor
        // Verificar se existe banca e remover + 1 da carga de trabalho dos professores
        // Salvar +1 carga de trabalho dos professores
        return "TODO"; // TODO
    }
    
    @Override
    public List<Professor> indicarBanca(Long idPropostaTCC) {
        List<Professor> professoresOrdenadosParaIndicacao = new ArrayList<>();

        // Busca a proposta pelo id
        PropostaTCC proposta = propostaTCCDAO.buscarPorId(idPropostaTCC);

        // Busca lista de professores
        List<Professor> professores = professorService.listar();

        // Remove professor orientador da lista de professores
        professores.removeIf(p -> p.getId().equals(proposta.getOrientador().getId()));

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
    public String removerBanca(Long idPropostaTCC) {
        List<Professor> professores = verBancaEOrientador(idPropostaTCC);
        boolean sucesso = propostaTCCDAO.deletarBanca(idPropostaTCC);
        if (sucesso && !professores.isEmpty()) {
            professorService.reduzirCargaDeTrabalho(professores.stream().map(Professor::getId).collect(Collectors.toList()));
        }
        return sucesso ? "Banca removida com sucesso!" : "Erro na conexão com o banco de dados.";
    }
}
