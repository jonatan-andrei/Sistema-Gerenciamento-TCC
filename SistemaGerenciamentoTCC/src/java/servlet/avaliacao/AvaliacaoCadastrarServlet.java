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

public class AvaliacaoCadastrarServlet extends HttpServlet {

    private static final PropostaTCCService propostaTCCService = new PropostaTCCServiceImpl();
    private static final AvaliacaoService avaliacaoService = new AvaliacaoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long idPropostaTCC = Long.parseLong(request.getParameter("idPropostaTCC"));
        request.setAttribute("propostaTCC", propostaTCCService.buscarPorId(idPropostaTCC));
        request.getRequestDispatcher("cadastrarAvaliacao.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long idPropostaTCC = Long.parseLong(request.getParameter("idPropostaTCC"));

        // Avaliação do primeiro professor
        Long idProfessor1 = Long.parseLong(request.getParameter("idProfessorProf1"));
        Double notaFinalProf1 = Double.parseDouble(request.getParameter("notaFinalProf1"));
        String parecerProf1 = request.getParameter("parecerProf1");
        boolean aprovadoProf1 = Boolean.parseBoolean(request.getParameter("aprovadoProf1"));
        String usoDeLinguagemProf1 = request.getParameter("usoDeLinguagemProf1");
        String apresentacaoProf1 = request.getParameter("apresentacaoProf1");
        String estruturaDoTextoProf1 = request.getParameter("estruturaDoTextoProf1");
        String conteudoDoTextoProf1 = request.getParameter("conteudoDoTextoProf1");
        String relevanciaProfissionalProf1 = request.getParameter("relevanciaProfissionalProf1");

        // Avaliação do segundo professor
        Long idProfessor2 = Long.parseLong(request.getParameter("idProfessorProf2"));
        Double notaFinalProf2 = Double.parseDouble(request.getParameter("notaFinalProf2"));
        String parecerProf2 = request.getParameter("parecerProf2");
        boolean aprovadoProf2 = Boolean.parseBoolean(request.getParameter("aprovadoProf2"));
        String usoDeLinguagemProf2 = request.getParameter("usoDeLinguagemProf2");
        String apresentacaoProf2 = request.getParameter("apresentacaoProf2");
        String estruturaDoTextoProf2 = request.getParameter("estruturaDoTextoProf2");
        String conteudoDoTextoProf2 = request.getParameter("conteudoDoTextoProf2");
        String relevanciaProfissionalProf2 = request.getParameter("relevanciaProfissionalProf2");

        boolean sucesso = avaliacaoService.salvarAvaliacao(notaFinalProf1, parecerProf1, aprovadoProf1, idProfessor1, idPropostaTCC, usoDeLinguagemProf1, apresentacaoProf1, estruturaDoTextoProf1, conteudoDoTextoProf1, relevanciaProfissionalProf1);
        sucesso = sucesso && avaliacaoService.salvarAvaliacao(notaFinalProf2, parecerProf2, aprovadoProf2, idProfessor2, idPropostaTCC, usoDeLinguagemProf2, apresentacaoProf2, estruturaDoTextoProf2, conteudoDoTextoProf2, relevanciaProfissionalProf2);
        String mensagem;
        String areaResposta;

        if (sucesso) {
            areaResposta = "alert-success";
            mensagem = "Avaliação cadastrada com sucesso.";
        } else {
            areaResposta = "alert-danger";
            mensagem = "Erro ao cadastrar avaliação.";
        }

        request.setAttribute("mensagem", mensagem);
        request.setAttribute("areaResposta", areaResposta);
        request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
    }

}
