package service.aluno;

import dao.aluno.AlunoDAO;
import domain.Aluno;
import dao.aluno.AlunoDAOImpl;
import java.util.List;

public class AlunoServiceImpl implements AlunoService {

    private static final AlunoDAO alunoDAO = new AlunoDAOImpl();

    @Override
    public boolean cadastrar(String nome, String email, String matricula, String telefone) {
        return alunoDAO.cadastrar(new Aluno(nome, email, matricula, telefone));
    }

    @Override
    public boolean editar(Long idAluno, String nome, String email, String matricula, String telefone) {
        return alunoDAO.editar(new Aluno(idAluno, nome, email, matricula, telefone));
    }

    @Override
    public List<Aluno> listar() {
        return alunoDAO.listar();
    }

    @Override
    public boolean desativar(Long idAluno) {
        return alunoDAO.desativar(idAluno);
    }

}
