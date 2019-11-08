package servlet.professor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.professor.ProfessorService;
import service.professor.ProfessorServiceImpl;

public class ProfessorDesativarServlet extends HttpServlet {

    private static final ProfessorService professorService = new ProfessorServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        boolean sucesso = professorService.desativar(id);
        String mensagem;
        String areaResposta;
        if (sucesso) {
            areaResposta = "alert-success";
            mensagem = "Professor desativado com sucesso.";
        } else {
            areaResposta = "alert-danger";
            mensagem = "Erro ao desativar professor.";
        }

        request.setAttribute("mensagem", mensagem);
        request.setAttribute("areaResposta", areaResposta);
        request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
    }

}
