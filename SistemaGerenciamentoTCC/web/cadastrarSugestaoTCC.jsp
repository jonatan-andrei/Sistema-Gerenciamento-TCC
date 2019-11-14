<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TCC SSI - Cadastro de Sugestão de TCC</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css"  href="css/estilo.css" />
    </head>
    <body>
        <div class ="formulario">
            <h1>Cadastrar Sugestão de TCC</h1>  
            <form action="SugestaoTCCServlet" method="post" class="col-8"> 
                <div class="form-group row">
                    <label for="descricao">Descrição:</label> 
                    <input type="text" name="descricao" id="descricao" class="form-control" placeholder="Descrição" required/>
                </div>

                <div class="form-group">
                    <label for="idProfessor">Selecione o professor autor da sugestão:</label><br>
                    <select name="idProfessor" required>
                        <option value=""></option>
                        <c:forEach var="professor" items="${professores}">
                            <option value="${professor.id}">${professor.nome}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <c:if test="${not empty projetos}">
                        <label for="idProjeto">Relacionar com projeto de pesquisa do Campus?</label><br>
                        <select name="idProjeto"><option value=""></option>
                            <c:forEach var="projeto" items="${projetos}">
                                <option value="${projeto.idProjetoPesquisa}">${projeto.nome} - ${projeto.descricao}</option>
                            </c:forEach>
                        </select><br>
                    </c:if>
                </div>

                <button type="submit" class="btn btn-success btn-block">Salvar</button> 
                <a href="index.jsp" class="btn btn-secondary btn-block">Voltar a página inicial</a>
            </form>
        </div>
    </body>
</html>
