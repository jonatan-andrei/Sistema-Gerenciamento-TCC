package service.propostaTCC;

import dao.propostatcc.PropostaTCCDAO;
import dao.propostatcc.PropostaTCCDAOImpl;
import dao.sugestaotcc.SugestaoTCCDAO;
import dao.sugestaotcc.SugestaoTCCDAOImpl;
import domain.Area;
import domain.Professor;
import domain.PropostaTCC;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import service.professor.ProfessorService;
import service.professor.ProfessorServiceImpl;

public class PropostaTCCServiceImpl implements PropostaTCCService {

    private static final ProfessorService professorService = new ProfessorServiceImpl();
    private static final PropostaTCCDAO propostaTCCDAO = new PropostaTCCDAOImpl();
    private static final SugestaoTCCDAO sugestaoTCCDAO = new SugestaoTCCDAOImpl();

    @Override
    public boolean cadastrar(String titulo, String descricao, Long idAluno, Long idProfessor, Long idArea) {
        // TODO
        // Validar se aluno já não enviou proposta (considerar apenas ativo)
        // Salvar +1 carga de trabalho professor
    }

    @Override
    public boolean cadastrarViaSugestao(String titulo, String descricao, Long idAluno, Long idProfessor, Long idArea, Long idSugestao) {
        sugestaoTCCDAO.escolherSugestao(idSugestao);
        // TODO
        // Validar se aluno já não enviou proposta (considerar apenas ativo)
        // Salvar +1 carga de trabalho professor
    }

    @Override
    public boolean salvarBanca(Long idPropostaTCC, List<Long> professores) {
        // Verificar se existe banca e remover + 1 da carga de trabalho dos professores
        // Salvar +1 carga de trabalho dos professores
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
    public boolean removerBanca(Long idPropostaTCC) {
        // Verificar se existe banca e remover + 1 da carga de trabalho dos professores
    }

}
