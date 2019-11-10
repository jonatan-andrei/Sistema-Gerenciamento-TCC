package servlet.propostatcc;

import domain.PropostaTCC;
import java.io.IOException;
import java.util.List;
import static java.util.Objects.isNull;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.propostaTCC.PropostaTCCService;
import service.propostaTCC.PropostaTCCServiceImpl;

public class PropostaTCCListarServlet extends HttpServlet {

    private static final PropostaTCCService propostaTCCService = new PropostaTCCServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<PropostaTCC> propostas = propostaTCCService.listar();

        if (isNull(propostas)) {
            request.setAttribute("mensagem", "Erro ao buscar propostas de TCC.");
            request.setAttribute("areaResposta", "alert-danger");
            request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
        } else {
            request.setAttribute("propostas", propostas);
            request.getRequestDispatcher("listarPropostasTCC.jsp").forward(request, response);
        }
    }

}
