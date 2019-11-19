<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TCC SSI - Cadastrar Banca</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="css/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css"  href="css/estilo.css" />
    </head>
    <body>
        <form action="PropostaTCCSalvarBancaServlet" method="post" class="col-8"> 
            <div class="area-resposta">
                <h1>Selecionar professores: </h1>
                <h4>Atenção: Selecione 2 professores. </h4>
                <input type="hidden" name="idProposta" id="idProposta" value='${idProposta}'/>
                <table class="table table-striped table-dark">
                    <tbody>
                        <c:forEach var="professor" items="${professores}">
                            <tr>
                                <td><span class="titulo-nome">Nome: ${professor.nome}</span><br>
                                    E-mail: ${professor.email}<br>
                                    Envolvido com ${professor.cargaTrabalhoSemestre} proposta(s) de TCC neste semestre.<br>
                                    <c:if test="${not empty professor.areasDeInteresse}">
                                        Áreas de interesse:
                                        <c:forEach var="area" items="${professor.areasDeInteresse}">
                                            ${area.nome};
                                        </c:forEach><br>
                                    </c:if>   
                                    <input type="checkbox" value="${professor.id}" name="professores"> Escolher
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <button type="submit" class="btn btn-success btn-block">Salvar Banca</button> 
                <a href="index.jsp" class="btn btn-secondary btn-block">Voltar a página inicial</a>
            </div>
        </form>
    </body>
</html>
