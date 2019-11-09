package dao.aluno;

import domain.Aluno;
import java.util.List;

public interface AlunoDAO {

    boolean cadastrar(Aluno aluno);

    boolean editar(Aluno aluno);
    
    Aluno buscarPorId(Long idAluno);
    
    List<Aluno> buscarQueNaoEnviaramProposta();

    List<Aluno> listar();

    boolean desativar(Long idAluno);

}
