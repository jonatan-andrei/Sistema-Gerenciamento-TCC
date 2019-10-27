package service.aluno;

import dao.aluno.AlunoDAO;
import domain.Aluno;
import dao.aluno.AlunoDAOImpl;
import java.util.List;

public class AlunoServiceImpl implements AlunoService {

    @Override
    public boolean cadastrar(String nome, String email, String matricula, String telefone) {
        AlunoDAO alunoDAO = new AlunoDAOImpl();
        return alunoDAO.cadastrar(new Aluno(nome, email, matricula, telefone));
    }

    @Override
    public boolean editar(Long idAluno, String nome, String email, String matricula, String telefone) {
        AlunoDAO alunoDAO = new AlunoDAOImpl();
        return alunoDAO.editar(new Aluno(idAluno, nome, email, matricula, telefone));
    }

    @Override
    public List<Aluno> listar() {
        AlunoDAO alunoDAO = new AlunoDAOImpl();
        List<Aluno> alunos = alunoDAO.listar();
        return alunos;
    }

    @Override
    public boolean deletar(Long idAluno) {
        AlunoDAO alunoDAO = new AlunoDAOImpl();
        return alunoDAO.deletar(idAluno);
    }

}
