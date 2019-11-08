package servlet.professor;

import domain.Aluno;
import domain.Professor;
import java.io.IOException;
import java.util.List;
import static java.util.Objects.isNull;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.professor.ProfessorService;
import service.professor.ProfessorServiceImpl;

public class ProfessorListarServlet extends HttpServlet {
    
    private static final ProfessorService professorService = new ProfessorServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                List<Professor> professores = professorService.listar();

        if (isNull(professores)) {
            request.setAttribute("mensagem", "Erro ao buscar professores.");
            request.setAttribute("areaResposta", "alert-danger");
            request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
        } else {
            request.setAttribute("professores", professores);
            request.getRequestDispatcher("listarProfessores.jsp").forward(request, response);
        }
    }

}
