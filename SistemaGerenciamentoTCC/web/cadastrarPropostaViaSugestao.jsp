<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TCC SSI - Cadastro Proposta de TCC</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css"  href="css/estilo.css" />
    </head>
    <body>
        <div class ="formulario">
            <h1>Cadastrar Proposta de TCC</h1>  
            <form action="PropostaTCCCadastrarViaSugestaoServlet" method="post" class="col-8"> 

                <div class="form-group row">
                    <label for="titulo">Título</label> 
                    <input type="text" name="titulo" id="titulo" class="form-control" placeholder="Descrição" required/>
                </div>

                <div class="form-group row">
                    <label for="descricao">Descrição:</label> 
                    <input type="text" name="descricao" id="descricao" class="form-control" placeholder="Descrição" required/>
                </div>

                <div class="form-group">
                    <label for="idArea">Selecione a área principal do TCC:</label><br>
                    <select name="idArea" required>
                        <option value=""></option>
                        <c:forEach var="area" items="${areas}">
                            <option value="${area.idArea}">${area.nome}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="idAluno">Selecione o aluno:</label><br>
                    <select name="idAluno" required>
                        <option value=""></option>
                        <c:forEach var="aluno" items="${alunos}">
                            <option value="${aluno.id}">${aluno.nome}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group row">
                    <label for="professor">Orientador:</label> 
                    <input type="hidden" name="idProfessor" id="idProfessor" class="form-control" value="${sugestao.professor.id}"/>
                    <input type="text" name="nomeProfessor" class="form-control" value="${sugestao.professor.nome}" disabled/>
                </div>

                <div class="form-group row">
                    <label for="professor">Sugestão de projeto:</label> 
                    <input type="hidden" name="idSugestao" id="idSugestao" class="form-control" value="${sugestao.idSugestaoTCC}"/>
                    <input type="text" name="sugestao" class="form-control" value="${sugestao.descricao}" disabled/>
                </div>

                <button type="submit" class="btn btn-success btn-block">Salvar</button> 
                <a href="index.jsp" class="btn btn-secondary btn-block">Voltar a página inicial</a>
            </form>
        </div>
    </body>
</html>
