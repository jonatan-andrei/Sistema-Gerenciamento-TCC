<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TCC SSI - Editar aluno</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css"  href="css/estilo.css" />
    </head>
    <body>
        <div class ="formulario">
            <h1>Editar aluno</h1>  
            <form action="AlunoEditarServlet" method="post" class="col-8"> 
                <div class="form-group row">
                    <label for="idAluno">Id aluno</label> 
                    <input type="text" name="idAluno" id="idAluno" class="form-control" value=${aluno.id} readonly/>
                </div>

                <div class="form-group row">
                    <label for="nome">Nome:</label> 
                    <input type="text" name="nome" id="nome" class="form-control" placeholder="Nome" value='${aluno.nome}' required/>
                </div>

                <div class="form-group row">
                    <label for="email">E-mail: </label>
                    <input type="email" name="email" id="email" class="form-control" placeholder="E-mail" value='${aluno.email}' required/>
                </div>

                <div class="form-group row">
                    <label for="telefone">Telefone: </label>
                    <input type="text" name="telefone" id="telefone" class="form-control" placeholder="Telefone" value='${aluno.telefone}' required/>
                </div>

                <div class="form-group row">
                    <label for="matricula">Número de matrícula: </label>
                    <input type="number" name="matricula" id="matricula" class="form-control" placeholder="Número de matrícula" value='${aluno.matricula}' required/>
                </div>
                <button type="submit" class="btn btn-success btn-block">Salvar</button> 
                <a href="index.jsp" class="btn btn-secondary btn-block">Voltar a página inicial</a>
            </form>
        </div>
    </body>
</html>

