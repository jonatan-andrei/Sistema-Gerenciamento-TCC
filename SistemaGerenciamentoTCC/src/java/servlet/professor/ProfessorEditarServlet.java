package servlet.professor;

import domain.Area;
import domain.Professor;
import dto.AreaProfessorDto;
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

public class ProfessorEditarServlet extends HttpServlet {

    private static final ProfessorService professorService = new ProfessorServiceImpl();
    private static final AreaService areaService = new AreaServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Professor professor = professorService.buscarPorId(id);

        if (isNull(professor)) {
            request.setAttribute("mensagem", "Erro ao buscar professor.");
            request.setAttribute("areaResposta", "alert-danger");
            request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
        } else {
            // Busca todas as áreas
            List<Area> todasAreas = areaService.listar();

            // Cria lista com áreas de interesse selecionadas
            List<AreaProfessorDto> areas = todasAreas.stream()
                    .map(area -> new AreaProfessorDto(area,
                    professor.getAreasDeInteresse().stream().map(Area::getIdArea)
                            .collect(Collectors.toList()).contains(area.getIdArea())))
                    .collect(Collectors.toList());

            // Envia as informações
            request.setAttribute("professor", professor);
            request.setAttribute("areas", areas);
            request.getRequestDispatcher("editarProfessor.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long idProfessor = Long.parseLong(request.getParameter("idProfessor"));
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String[] idsAreasInteresse = request.getParameterValues("areasInteresse");
        List<Long> areasDeInteresse = new ArrayList<>();
        if (nonNull(idsAreasInteresse)) {
            areasDeInteresse.addAll(Arrays.asList(idsAreasInteresse).stream().map(id -> Long.parseLong(id)).collect(Collectors.toList()));
        }
        boolean sucesso = professorService.editar(idProfessor, nome, email, areasDeInteresse);

        String mensagem;
        String areaResposta;
        if (sucesso) {
            areaResposta = "alert-success";
            mensagem = "Professor editado com sucesso.";
        } else {
            areaResposta = "alert-danger";
            mensagem = "Erro ao editar professor.";
        }

        request.setAttribute("mensagem", mensagem);
        request.setAttribute("areaResposta", areaResposta);
        request.getRequestDispatcher("common/respostaOperacao.jsp").forward(request, response);
    }
}
