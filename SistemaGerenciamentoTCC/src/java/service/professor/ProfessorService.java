package service.professor;

import domain.Professor;
import java.util.List;

public interface ProfessorService {

    boolean cadastrar(String nome, String email, List<Long> areasInteresse);

    boolean editar(Long idProfessor, String nome, String email, List<Long> areasInteresse);

    Professor buscarPorId(Long idProfessor);

    List<Professor> listar();

    boolean desativar(Long idProfessor);

    boolean aumentarCargaDeTrabalho(List<Long> professores);

    boolean reduzirCargaDeTrabalho(List<Long> professores);

}
