package servlet.avaliacao;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.avaliacao.AvaliacaoService;
import service.avaliacao.AvaliacaoServiceImpl;
import service.propostaTCC.PropostaTCCService;
import service.propostaTCC.PropostaTCCServiceImpl;

public class AvaliacaoEditarServlet extends HttpServlet {

    private static final PropostaTCCService propostaTCCService = new PropostaTCCServiceImpl();
    private static final AvaliacaoService avaliacaoService = new AvaliacaoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long idAvaliacao = Long.parseLong(request.getParameter("idAvaliacao"));
        request.setAttribute("avaliacao", avaliacaoService.buscarPorId(idAvaliacao));
        Long idPropostaTCC = Long.parseLong(request.getParameter("idPropostaTCC"));
        request.setAttribute("propostaTCC", propostaTCCService.buscarPorId(idPropostaTCC));
        request.getRequestDispatcher("editarAvaliacao.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long idAvaliacao = Long.parseLong(request.getParameter("idAvaliacao"));
        Double notaFinal = Double.parseDouble(request.getParameter("notaFinal"));
        String parecer = request.getParameter("parecer");
        boolean aprovado = Boolean.parseBoolean(request.getParameter("aprovado"));
        String usoDeLinguagem = request.getParameter("usoDeLinguagem");
        String apresentacao = request.getParameter("apresentacao");
        String estruturaDoTexto = request.getParameter("estruturaDoTexto");
        String conteudoDoTexto = request.getParameter("conteudoDoTexto");
        String relevanciaProfissional = request.getParameter("relevanciaProfissional");

        boolean sucesso = avaliacaoService.editarAvaliacao(idAvaliacao, notaFinal, parecer, aprovado, usoDeLinguagem, apresentacao, estruturaDoTexto, conteudoDoTexto, relevanciaProfissional);

        String mensagem;
        String areaResposta;

        if (sucesso) {
            areaResposta = "alert-success";
            mensagem = "Avaliação editada com sucesso.";
        } else {
            areaResposta = "alert-danger";
            mensagem = "Erro ao editar avaliação.";
        }

        request.setAttribute("mensagem", mensagem);
        request.setAttribute("areaResposta", areaResposta);
        request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
    }
}
