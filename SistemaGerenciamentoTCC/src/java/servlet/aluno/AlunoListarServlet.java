package servlet.aluno;

import domain.Aluno;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.aluno.AlunoService;
import service.aluno.AlunoServiceImpl;
import static java.util.Objects.isNull;

public class AlunoListarServlet extends HttpServlet {

    private static final AlunoService alunoService = new AlunoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Aluno> alunos = alunoService.listar();

        if (isNull(alunos)) {
            request.setAttribute("mensagem", "Erro ao buscar alunos.");
            request.setAttribute("areaResposta", "alert-danger");
            request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
        } else {
            request.setAttribute("alunos", alunos);
            request.getRequestDispatcher("listarAlunos.jsp").forward(request, response);
        }
    }

}
