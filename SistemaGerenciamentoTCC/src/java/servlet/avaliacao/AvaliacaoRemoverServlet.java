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

        Long idAvaliacao1 = Long.parseLong(request.getParameter("idAvaliacao1"));
        Long idAvaliacao2 = Long.parseLong(request.getParameter("idAvaliacao2"));

        boolean sucesso = avaliacaoService.deletarAvaliacao(idAvaliacao1);
        sucesso = sucesso && avaliacaoService.deletarAvaliacao(idAvaliacao2);

        String mensagem;
        String areaResposta;
        if (sucesso) {
            areaResposta = "alert-success";
            mensagem = "Avaliações excluídas com sucesso.";
        } else {
            areaResposta = "alert-danger";
            mensagem = "Erro ao excluir avaliações.";
        }

        request.setAttribute("mensagem", mensagem);
        request.setAttribute("areaResposta", areaResposta);
        request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
    }

}
