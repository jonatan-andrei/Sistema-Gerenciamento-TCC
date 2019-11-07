package servlet.aluno;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.aluno.AlunoService;
import service.aluno.AlunoServiceImpl;

public class AlunoDesativarServlet extends HttpServlet {

    private static final AlunoService alunoService = new AlunoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        boolean sucesso = alunoService.desativar(id);
        String mensagem;
        String areaResposta;
        if (sucesso) {
            areaResposta = "alert-success";
            mensagem = "Aluno desativado com sucesso.";
        } else {
            areaResposta = "alert-danger";
            mensagem = "Erro ao desativar aluno.";
        }

        request.setAttribute("mensagem", mensagem);
        request.setAttribute("areaResposta", areaResposta);
        request.getRequestDispatcher("respostaOperacao.jsp").forward(request, response);
    }

}
