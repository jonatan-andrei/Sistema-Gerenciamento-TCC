package service.aluno;

import domain.Aluno;
import java.util.List;

public interface AlunoService {

    boolean cadastrar(String nome, String email, String matricula, String telefone);

    boolean editar(Long idAluno, String nome, String email, String matricula, String telefone);

    Aluno buscarPorId(Long idAluno);

    List<Aluno> listar();

    boolean desativar(Long idAluno);

}
