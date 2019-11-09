package servlet.propostatcc;

import domain.PropostaTCC;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.propostaTCC.PropostaTCCService;
import service.propostaTCC.PropostaTCCServiceImpl;

public class PropostaTCCEnviarArtigoServlet extends HttpServlet {

    private static final PropostaTCCService propostaTCCService = new PropostaTCCServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long idProposta = Long.parseLong(request.getParameter("id"));
        PropostaTCC proposta = propostaTCCService.buscarPorId(idProposta);
        request.setAttribute("proposta", proposta);
        request.getRequestDispatcher("editarPropostaTCC.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long idProposta = Long.parseLong(request.getParameter("id"));
        String artigo = request.getParameter("artigo");
        boolean sucesso = propostaTCCService.enviarArtigoFinal(idProposta, artigo);

        String mensagem;
        String areaResposta;
        if (sucesso) {
            areaResposta = "alert-success";
            mensagem = "Artigo enviado com sucesso.";
        } else {
            areaResposta = "alert-danger";
            mensagem = "Erro ao enviar artigo.";
        }

        request.setAttribute("mensagem", mensagem);
        request.setAttribute("areaResposta", areaResposta);
        request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
    }

}
