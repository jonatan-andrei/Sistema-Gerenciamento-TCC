package service.aluno;

import domain.Aluno;
import java.util.List;

public interface AlunoService {

    boolean cadastrar(String nome, String email, String matricula, String telefone);

    boolean editar(Long idAluno, String nome, String email, String matricula, String telefone);

    List<Aluno> listar();

    boolean deletar(Long idAluno);

}