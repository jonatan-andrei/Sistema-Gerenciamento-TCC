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

public class AlunoListagemServlet extends HttpServlet {

    private static final AlunoService alunoService = new AlunoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Aluno> alunos = alunoService.listar();
        request.setAttribute("alunos", alunos);
        request.getRequestDispatcher("listagemaluno.jsp").forward(request, response);
    }

}
