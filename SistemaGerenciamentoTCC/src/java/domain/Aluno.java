package domain;

public class Aluno extends Pessoa {

    private String matricula;
    private String telefone;

    public Aluno() {
    }

    public Aluno(String nome, String email, String matricula, String telefone) {
        super(nome, email);
        this.matricula = matricula;
        this.telefone = telefone;
    }
    
    public Aluno(Long idAluno, String nome, String email, String matricula, String telefone) {
        super(idAluno, nome, email);
        this.matricula = matricula;
        this.telefone = telefone;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
