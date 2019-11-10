package servlet.propostatcc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.propostaTCC.PropostaTCCService;
import service.propostaTCC.PropostaTCCServiceImpl;

public class PropostaTCCRemoverBancaServlet extends HttpServlet {

    private static final PropostaTCCService propostaTCCService = new PropostaTCCServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        boolean sucesso = propostaTCCService.removerBanca(id);
        String mensagem;
        String areaResposta;
        if (sucesso) {
            areaResposta = "alert-success";
            mensagem = "Banca removida com sucesso.";
        } else {
            areaResposta = "alert-danger";
            mensagem = "Erro ao excluir a banca.";
        }

        request.setAttribute("mensagem", mensagem);
        request.setAttribute("areaResposta", areaResposta);
        request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
    }

}
