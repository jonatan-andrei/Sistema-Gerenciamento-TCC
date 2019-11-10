package dao.area;

import domain.Area;
import java.util.List;

public interface AreaDAO {
    
    List<Area> listar();

    void salvarAreasDeInteresse(Long idProfessor, List<Long> areas);

    void deletarAreasDeInteresse(Long idProfessor, List<Long> areas);

    void deletarTodasAreasDeInteresse(Long idProfessor);

    List<Area> buscarAreasDeInteresse(Long idProfessor);

}
