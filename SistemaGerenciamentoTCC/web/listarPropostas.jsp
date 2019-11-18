<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TCC SSI - Listagem de propostas de TCC</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="css/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css"  href="css/estilo.css" />
    </head>
    <body>
        <div class="area-resposta">
            <c:if test="${empty propostas}">
                <div class="alert alert-danger">Sem propostas de TCC cadastradas</div>
            </c:if>
            <c:if test="${not empty propostas}">
                <h1>Propostas de TCC enviadas: </h1>
                <table class="table table-striped table-dark">
                    <tbody>
                        <c:forEach var="proposta" items="${propostas}">
                            <tr>
                                <td><span class="titulo-nome">Título: ${proposta.titulo}</span><br>
                                    Descrição: ${proposta.descricao}<br>
                                    Área: ${proposta.area.nome} <br>
                                    <c:if test="${empty proposta.artigo}">
                                        Artigo ainda não enviado. 
                                        <a href='PropostaTCCEnviarArtigoServlet?id=${proposta.idPropostaTCC}'><i class="fa fa-pencil fa-2x"></i>Enviar</a>
                                        </c:if>
                                    <c:if test="${not empty proposta.artigo}">
                                        Artigo: 
                                        ${proposta.artigo}
                                    </c:if>
                                    <br>
                                    Autor: ${proposta.autor.nome} <br>
                                    Orientador: ${proposta.orientador.nome} <br>
                                    Status:
                                    <c:choose>
                                        <c:when test = "${proposta.status == 'APROVADO'}">
                                            Aprovada.
                                        </c:when>
                                        <c:when test = "${proposta.status == 'REPROVADO'}">
                                            Reprovada.
                                        </c:when>
                                        <c:otherwise>
                                            Não avaliada.
                                        </c:otherwise>
                                    </c:choose>
                                    <br>
                                    Desativar Proposta: <a href='PropostaTCCDesativarServlet?id=${proposta.idPropostaTCC}'><i class="fa fa-times fa-2x"></i></a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <a href="index.jsp" class="btn btn-secondary btn-block">Voltar a página inicial</a>
        </div>
    </body>
</html>
