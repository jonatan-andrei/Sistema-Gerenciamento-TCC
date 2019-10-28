package service.professor;

import dao.area.AreaDAO;
import dao.area.AreaDAOImpl;
import dao.professor.ProfessorDAO;
import dao.professor.ProfessorDAOImpl;
import domain.Area;
import domain.Professor;
import java.util.List;
import static java.util.Objects.nonNull;
import java.util.stream.Collectors;

public class ProfessorServiceImpl implements ProfessorService {

    private static final ProfessorDAO professorDAO = new ProfessorDAOImpl();
    private static final AreaDAO areaDAO = new AreaDAOImpl();

    @Override
    public boolean cadastrar(String nome, String email, List<Long> areasInteresse) {
        Long idProfessor = professorDAO.cadastrar(new Professor(nome, email));
        if (nonNull(idProfessor)) {
            areaDAO.salvarAreasDeInteresse(idProfessor, areasInteresse);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean editar(Long idProfessor, String nome, String email, List<Long> areasInteresse) {
        boolean sucesso = professorDAO.editar(new Professor(idProfessor, nome, email));
        if (sucesso) {
            List<Area> areas = areaDAO.buscarAreasDeInteresse(idProfessor);

            // Separa ids das áreas relacionadas ao professor em uma lista
            List<Long> idsAreasAtuais = areas.stream().map(Area::getIdArea).collect(Collectors.toList());

            // Filtra nas áreas de interesse do professor, qual ainda não está salva no banco
            List<Long> idsParaInserir = areasInteresse.stream().filter(area -> !idsAreasAtuais.contains(area)).collect(Collectors.toList());

            // Filtra nas áreas buscadas do banco, quais foram removidas dos interesses do professor
            List<Long> idsParaRemover = idsAreasAtuais.stream().filter(area -> !areasInteresse.contains(area)).collect(Collectors.toList());

            if (!idsParaInserir.isEmpty()) {
                // Caso tenha alguma área nova de interesse do professor, salva no banco
                areaDAO.salvarAreasDeInteresse(idProfessor, idsParaInserir);
            }

            if (!idsParaRemover.isEmpty()) {
                // Caso alguma área de interesse tenha sido removida para o professor, remove do banco
                areaDAO.deletarAreasDeInteresse(idProfessor, idsParaRemover);
            }
        }

        return sucesso;

    }

    @Override
    public List<Professor> listar() {
        List<Professor> professores = professorDAO.buscar();
        if (nonNull(professores)) {
            professores.forEach(professor -> {
                professor.setAreasDeInteresse(areaDAO.buscarAreasDeInteresse(professor.getId()));
            });
        } else {
            return null;
        }
        return professores;
    }

    @Override
    public boolean desativar(Long idProfessor) {
        areaDAO.deletarTodasAreasDeInteresse(idProfessor);
        return professorDAO.desativar(idProfessor);
    }

}
