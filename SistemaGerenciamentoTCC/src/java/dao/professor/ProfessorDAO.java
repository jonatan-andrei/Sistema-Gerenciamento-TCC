package dao.professor;

import domain.Professor;
import java.util.List;

public interface ProfessorDAO {

    void salvar(Professor professor);

    void editar(Long idProfessor, Professor professor);

    List<Professor> buscar();

    void deletar(Long idAluno);

}
