package dao.professor;

import domain.Professor;
import java.util.List;

public interface ProfessorDAO {

    Long cadastrar(Professor professor);

    boolean editar(Professor professor);
    
    Professor buscarPorId(Long idProfessor);

    List<Professor> listar();

    boolean desativar(Long idProfessor);
    
    boolean aumentarCargaDeTrabalho(List<Long> professores);
    
    boolean reduzirCargaDeTrabalho(List<Long> professores);

}
