package dao.aluno;

import domain.Aluno;
import java.util.List;

public interface AlunoDAO {

    boolean cadastrar(Aluno aluno);

    boolean editar(Aluno aluno);

    List<Aluno> listar();

    boolean deletar(Long idAluno);

}
