package servlet.avaliacao;

import domain.Avaliacao;
import dto.AvaliacaoEditarDto;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
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
        Long idPropostaTCC = Long.parseLong(request.getParameter("id"));
        List<AvaliacaoEditarDto> avaliacoes = avaliacaoService.buscarPorTCC(idPropostaTCC).stream().map(av -> new AvaliacaoEditarDto(av)).collect(Collectors.toList());
        request.setAttribute("avaliacoes", avaliacoes);
        request.setAttribute("proposta", propostaTCCService.buscarPorId(idPropostaTCC));
        request.getRequestDispatcher("editarAvaliacao.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Avaliação do primeiro professor
        Long idAvaliacaoProf1 = Long.parseLong(request.getParameter("idAvaliacaoProf1"));
        Double notaFinalProf1 = Double.parseDouble(request.getParameter("notaFinalProf1"));
        String parecerProf1 = request.getParameter("parecerProf1");
        boolean aprovadoProf1 = Boolean.parseBoolean(request.getParameter("aprovadoProf1"));
        String usoDeLinguagemProf1 = request.getParameter("usoDeLinguagemProf1");
        String apresentacaoProf1 = request.getParameter("apresentacaoProf1");
        String estruturaDoTextoProf1 = request.getParameter("estruturaTextoProf1");
        String conteudoDoTextoProf1 = request.getParameter("conteudoTextoProf1");
        String relevanciaProfissionalProf1 = request.getParameter("relevanciaProfissionalProf1");

        // Avaliação do segundo professor
        Long idAvaliacaoProf2 = Long.parseLong(request.getParameter("idAvaliacaoProf2"));
        Double notaFinalProf2 = Double.parseDouble(request.getParameter("notaFinalProf2"));
        String parecerProf2 = request.getParameter("parecerProf2");
        boolean aprovadoProf2 = Boolean.parseBoolean(request.getParameter("aprovadoProf2"));
        String usoDeLinguagemProf2 = request.getParameter("usoDeLinguagemProf2");
        String apresentacaoProf2 = request.getParameter("apresentacaoProf2");
        String estruturaDoTextoProf2 = request.getParameter("estruturaTextoProf2");
        String conteudoDoTextoProf2 = request.getParameter("conteudoTextoProf2");
        String relevanciaProfissionalProf2 = request.getParameter("relevanciaProfissionalProf2");

        boolean sucesso = avaliacaoService.editarAvaliacao(idAvaliacaoProf1, notaFinalProf1, parecerProf1, aprovadoProf1, usoDeLinguagemProf1, apresentacaoProf1, estruturaDoTextoProf1, conteudoDoTextoProf1, relevanciaProfissionalProf1);
        sucesso = sucesso && avaliacaoService.editarAvaliacao(idAvaliacaoProf2, notaFinalProf2, parecerProf2, aprovadoProf2, usoDeLinguagemProf2, apresentacaoProf2, estruturaDoTextoProf2, conteudoDoTextoProf2, relevanciaProfissionalProf2);

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
