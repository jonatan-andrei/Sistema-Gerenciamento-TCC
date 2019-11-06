<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TCC SSI - Listagem de aunos</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css"  href="css/estilo.css" />
    </head>
    <body>
        <div class="area-resposta">
            <c:if test="${empty alunos}">
                <div class="alert alert-danger">Sem alunos cadastrados</div>
            </c:if>
            <c:if test="${not empty alunos}">
                <h1>Alunos: </h1>
                <table class="table table-striped table-dark">
                    <thead>
                        <tr>
                            <th scope="col">Nome</th>
                            <th scope="col">Email</th>
                            <th scope="col">Matrícula</th>
                            <th scope="col">Telefone</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="aluno" items="${alunos}">
                        <tr>
                            <td>${aluno.nome}</td>
                            <td>${aluno.email}</td>
                            <td>${aluno.matricula}</td>
                            <td>${aluno.telefone}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <a href="index.jsp" class="btn btn-secondary btn-block">Voltar a página inicial</a>
        </div>
    </body>
</html>

