package servlet.aluno;

import domain.Aluno;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.aluno.AlunoService;
import service.aluno.AlunoServiceImpl;

@WebServlet(name = "AlunoEditarServlet", urlPatterns = {"/AlunoEditarServlet"})
public class AlunoEditarServlet extends HttpServlet {

    private static final AlunoService alunoService = new AlunoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Aluno aluno = alunoService.buscarPorId(id);

        request.setAttribute("aluno", aluno);
        request.getRequestDispatcher("editarAluno.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long idAluno = Long.parseLong(request.getParameter("idAluno"));
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String matricula = request.getParameter("matricula");
        String telefone = request.getParameter("telefone");

        boolean sucesso = alunoService.editar(idAluno, nome, email, matricula, telefone);

        String mensagem;
        String areaResposta;
        if (sucesso) {
            areaResposta = "alert-success";
            mensagem = "Aluno editado com sucesso.";
        } else {
            areaResposta = "alert-danger";
            mensagem = "Erro ao editar aluno.";
        }

        request.setAttribute("mensagem", mensagem);
        request.setAttribute("areaResposta", areaResposta);
        request.getRequestDispatcher("respostaOperacao.jsp").forward(request, response);
    }
}
