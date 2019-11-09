package servlet.sugestaotcc;

import domain.Professor;
import domain.ProjetoPesquisa;
import java.io.IOException;
import java.util.List;
import static java.util.Objects.isNull;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.professor.ProfessorService;
import service.professor.ProfessorServiceImpl;
import service.projetopesquisa.ProjetoPesquisaService;
import service.projetopesquisa.ProjetoPesquisaServiceImpl;
import service.sugestaoTCC.SugestaoTCCService;
import service.sugestaoTCC.SugestaoTCCServiceImpl;

public class SugestaoTCCServlet extends HttpServlet {

    private static final ProjetoPesquisaService projetoPesquisaService = new ProjetoPesquisaServiceImpl();
    private static final SugestaoTCCService sugestaoTCCService = new SugestaoTCCServiceImpl();
    private static final ProfessorService professorService = new ProfessorServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<ProjetoPesquisa> projetos = projetoPesquisaService.listar();
        List<Professor> professores = professorService.listar();

        if (isNull(projetos) || isNull(professores)) {
            request.setAttribute("mensagem", "Erro na conexão com o banco de dados.");
            request.setAttribute("areaResposta", "alert-danger");
            request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
        } else {
            request.setAttribute("projetos", projetos);
            request.setAttribute("professores", professores);
            request.getRequestDispatcher("cadastrarSugestaoTCC.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String descricao = request.getParameter("descricao");
        String idProjeto = request.getParameter("idProjeto");
        String idProfessor = request.getParameter("idProfessor");

        boolean sucesso = sugestaoTCCService.cadastrar(descricao, Long.valueOf(idProfessor), isNull(idProjeto) ? null : Long.valueOf(idProjeto));

        String mensagem;
        String areaResposta;
        if (sucesso) {
            areaResposta = "alert-success";
            mensagem = "Sugestão cadastrada com sucesso.";
        } else {
            areaResposta = "alert-danger";
            mensagem = "Erro ao cadastrar sugestao.";
        }

        request.setAttribute("mensagem", mensagem);
        request.setAttribute("areaResposta", areaResposta);
        request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
    }

}
