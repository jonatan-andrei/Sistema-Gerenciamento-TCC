package servlet.propostatcc;

import domain.Professor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.propostaTCC.PropostaTCCService;
import service.propostaTCC.PropostaTCCServiceImpl;

public class PropostaTCCSalvarBancaServlet extends HttpServlet {

    private static final PropostaTCCService propostaTCCService = new PropostaTCCServiceImpl();

    final int NUMERO_PROFESSORES_BANCA = 2;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long idProposta = Long.valueOf(request.getParameter("id"));
        List<Professor> professoresOrdenados = propostaTCCService.indicarBanca(idProposta);

        if (isNull(professoresOrdenados)) {
            request.setAttribute("mensagem", "Erro na conexão com o banco de dados.");
            request.setAttribute("areaResposta", "alert-danger");
            request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
        } else if (professoresOrdenados.size() < NUMERO_PROFESSORES_BANCA) { // Valida se existem pelo menos dois professores para serem indicados
            request.setAttribute("mensagem", "Não existem professores suficientes para indicar a banca. Cadastre mais professores.");
            request.setAttribute("areaResposta", "alert-danger");
            request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
        } else {
            request.setAttribute("idProposta", idProposta);
            request.setAttribute("professores", professoresOrdenados);
            request.getRequestDispatcher("cadastrarBanca.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long idProposta = Long.valueOf(request.getParameter("idProposta"));
        String[] idsProfessores = request.getParameterValues("professores");
        List<Long> professores = new ArrayList<>();
        if (nonNull(idsProfessores)) {
            professores.addAll(Arrays.asList(idsProfessores).stream().map(id -> Long.parseLong(id)).collect(Collectors.toList()));
        }

        if (NUMERO_PROFESSORES_BANCA != professores.size()) {
            request.setAttribute("mensagem", "A banca não foi salva! Você deve selecionar exatamente dois professores para comporem a banca.");
            request.setAttribute("areaResposta", "alert-danger");
            request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
        } else {
            String mensagem = propostaTCCService.salvarBanca(idProposta, professores);

            String areaResposta;
            if (mensagem.equalsIgnoreCase("Banca salva com sucesso")) {
                areaResposta = "alert-success";
            } else {
                areaResposta = "alert-danger";
            }

            request.setAttribute("mensagem", mensagem);
            request.setAttribute("areaResposta", areaResposta);
            request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
        }
    }

}
