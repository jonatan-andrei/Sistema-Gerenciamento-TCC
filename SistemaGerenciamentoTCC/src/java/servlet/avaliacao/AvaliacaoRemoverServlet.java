package servlet.avaliacao;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.avaliacao.AvaliacaoService;
import service.avaliacao.AvaliacaoServiceImpl;

public class AvaliacaoRemoverServlet extends HttpServlet {

    private static final AvaliacaoService avaliacaoService = new AvaliacaoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        boolean sucesso = avaliacaoService.deletarAvaliacao(id);
        String mensagem;
        String areaResposta;
        if (sucesso) {
            areaResposta = "alert-success";
            mensagem = "Avaliação excluída com sucesso.";
        } else {
            areaResposta = "alert-danger";
            mensagem = "Erro ao excluir avaliação.";
        }

        request.setAttribute("mensagem", mensagem);
        request.setAttribute("areaResposta", areaResposta);
        request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
    }

}
