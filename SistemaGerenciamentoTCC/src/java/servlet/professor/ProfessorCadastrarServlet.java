package servlet.professor;

import domain.Area;
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
import service.area.AreaService;
import service.area.AreaServiceImpl;
import service.professor.ProfessorService;
import service.professor.ProfessorServiceImpl;

public class ProfessorCadastrarServlet extends HttpServlet {

    private static final ProfessorService professorService = new ProfessorServiceImpl();
    private static final AreaService areaService = new AreaServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Area> areas = areaService.listar();

        if (isNull(areas)) {
            request.setAttribute("mensagem", "Erro na conexão com o banco de dados.");
            request.setAttribute("areaResposta", "alert-danger");
            request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
        } else {
            request.setAttribute("areas", areas);
            request.getRequestDispatcher("cadastrarProfessor.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String[] idsAreasInteresse = request.getParameterValues("areasInteresse");
        List<Long> areasDeInteresse = new ArrayList<>();
        if (nonNull(idsAreasInteresse)){
            areasDeInteresse.addAll(Arrays.asList(idsAreasInteresse).stream().map(id -> Long.parseLong(id)).collect(Collectors.toList()));
        } 
        boolean sucesso = professorService.cadastrar(nome, email, areasDeInteresse);

        String mensagem;
        String areaResposta;
        if (sucesso) {
            areaResposta = "alert-success";
            mensagem = "Professor cadastrado com sucesso.";
        } else {
            areaResposta = "alert-danger";
            mensagem = "Erro ao cadastrar professor.";
        }

        request.setAttribute("mensagem", mensagem);
        request.setAttribute("areaResposta", areaResposta);
        request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
    }

}
