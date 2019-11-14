package servlet.propostatcc;

import domain.Aluno;
import java.io.IOException;
import java.util.List;
import static java.util.Objects.isNull;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.aluno.AlunoService;
import service.aluno.AlunoServiceImpl;
import service.area.AreaService;
import service.area.AreaServiceImpl;
import service.professor.ProfessorService;
import service.professor.ProfessorServiceImpl;
import service.propostaTCC.PropostaTCCService;
import service.propostaTCC.PropostaTCCServiceImpl;
import service.sugestaoTCC.SugestaoTCCService;
import service.sugestaoTCC.SugestaoTCCServiceImpl;

public class PropostaTCCCadastrarServlet extends HttpServlet {

    private static final PropostaTCCService propostaTCCService = new PropostaTCCServiceImpl();
    private static final SugestaoTCCService sugestaoTCCService = new SugestaoTCCServiceImpl();
    private static final AlunoService alunoService = new AlunoServiceImpl();
    private static final AreaService areaService = new AreaServiceImpl();
    private static final ProfessorService professorService = new ProfessorServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Aluno> alunos = alunoService.buscarQueNaoEnviaramProposta();
        if (isNull(alunos)) {
            request.setAttribute("mensagem", "Erro ao buscar alunos cadastrados.");
            request.setAttribute("areaResposta", "alert-danger");
            request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
        }

        if (alunos.isEmpty()) {
            request.setAttribute("mensagem", "Sem alunos cadastrados que ainda não enviaram propostas. Cadastre um aluno primeiro para depois cadastrar a proposta.");
            request.setAttribute("areaResposta", "alert-danger");
            request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
        }

        request.setAttribute("professores", professorService.listar());
        request.setAttribute("alunos", alunos);
        request.setAttribute("areas", areaService.listar());
        request.getRequestDispatcher("cadastrarProposta.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String titulo = request.getParameter("titulo");
        String descricao = request.getParameter("descricao");
        Long idAluno = Long.valueOf(request.getParameter("idAluno"));
        Long idProfessor = Long.valueOf(request.getParameter("idProfessor"));
        Long idArea = Long.valueOf(request.getParameter("idArea"));
        boolean sucesso = propostaTCCService.cadastrarProposta(titulo, descricao, idAluno, idProfessor, idArea);

        String mensagem;
        String areaResposta;
        if (sucesso) {
            areaResposta = "alert-success";
            mensagem = "Proposta de TCC cadastrada com sucesso.";
        } else {
            areaResposta = "alert-danger";
            mensagem = "Erro ao enviar proposta.";
        }

        request.setAttribute("mensagem", mensagem);
        request.setAttribute("areaResposta", areaResposta);
        request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
    }

}
