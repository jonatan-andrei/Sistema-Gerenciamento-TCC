package servlet.projetopesquisa;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.projetopesquisa.ProjetoPesquisaService;
import service.projetopesquisa.ProjetoPesquisaServiceImpl;

public class ProjetoPesquisaServlet extends HttpServlet {

    private static final ProjetoPesquisaService projetoPesquisaService = new ProjetoPesquisaServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        boolean sucesso = projetoPesquisaService.salvar(nome, descricao);

        String mensagem;
        String areaResposta;
        if (sucesso) {
            areaResposta = "alert-success";
            mensagem = "Projeto de pesquisa salvo com sucesso.";
        } else {
            areaResposta = "alert-danger";
            mensagem = "Erro ao salvar projeto de pesquisa.";
        }

        request.setAttribute("mensagem", mensagem);
        request.setAttribute("areaResposta", areaResposta);
        request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
    }
}
