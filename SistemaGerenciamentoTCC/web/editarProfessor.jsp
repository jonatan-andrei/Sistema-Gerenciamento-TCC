<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TCC SSI - Editar professor</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css"  href="css/estilo.css" />
    </head>
    <body>
        <div class ="formulario">
            <h1>Editar professor</h1>  
            <form action="ProfessorEditarServlet" method="post" class="col-8"> 
                <div class="form-group row">
                    <label for="idProfessor">Id professor</label> 
                    <input type="text" name="idProfessor" id="idProfessor" class="form-control" value=${professor.id} readonly/>
                </div>

                <div class="form-group row">
                    <label for="nome">Nome:</label> 
                    <input type="text" name="nome" id="nome" class="form-control" placeholder="Nome" value='${professor.nome}' required/>
                </div>

                <div class="form-group row">
                    <label for="email">E-mail: </label>
                    <input type="email" name="email" id="email" class="form-control" placeholder="E-mail" value='${professor.email}' required/>
                </div>

                <c:if test="${not empty areas}">
                    <label for="areasInteresse">Áreas de interesse: </label>
                    <div class="checkbox">
                        <c:forEach var="area" items="${areas}">
                            <label><input type="checkbox" value="${area.idArea}"
                                          <c:if test="${area.selecionado}">
                                              checked
                                          </c:if> 
                                          name="areasInteresse"/> ${area.nome}</label><br>
                            </c:forEach>
                    </div>
                </c:if>

                <button type="submit" class="btn btn-success btn-block">Salvar</button> 
                <a href="index.jsp" class="btn btn-secondary btn-block">Voltar a página inicial</a>
            </form>
        </div>
    </body>
</html>
