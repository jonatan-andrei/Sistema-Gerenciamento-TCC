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
                                    Banca:
                                    <c:if test="${not empty proposta.banca}">
                                        <ul>
                                            <c:forEach var="profBanca" items="${proposta.banca}">
                                                <li>${profBanca.nome}</li>
                                            </c:forEach></ul>
                                            <c:if test="${empty proposta.avaliacoes}">
                                            <a href='PropostaTCCSalvarBancaServlet?id=${proposta.idPropostaTCC}'>Editar Banca</a><br>
                                            <a href='PropostaTCCRemoverBancaServlet?id=${proposta.idPropostaTCC}'>Remover Banca</a><br>
                                        </c:if>
                                        <c:if test="${not empty proposta.avaliacoes}">
                                            O TCC já foi avaliado. Não é permitido editar ou remover a banca nestes casos.<br>
                                        </c:if>
                                    </c:if>

                                    <c:if test="${empty proposta.banca}">
                                        <a href='PropostaTCCSalvarBancaServlet?id=${proposta.idPropostaTCC}'>Cadastrar Banca</a><br>
                                    </c:if>

                                    Avaliações:
                                    <c:if test="${empty proposta.avaliacoes}">
                                        <c:if test="${empty proposta.artigo}">
                                            O artigo final ainda não foi enviado. O TCC não pode ser avaliado ainda.
                                        </c:if>
                                        <c:if test="${empty proposta.banca && not empty proposta.artigo}">
                                            Cadastre primeiramente a banca para depois avaliar.
                                        </c:if>
                                        <c:if test="${not empty proposta.banca && not empty proposta.artigo}">
                                            <a href='AvaliacaoCadastrarServlet?id=${proposta.idPropostaTCC}'>Avaliar</a>
                                        </c:if>
                                        <br>
                                    </c:if>

                                    <c:if test="${not empty proposta.avaliacoes}">
                                        <ul>
                                            <c:forEach var="avaliacao" items="${proposta.avaliacoes}">
                                                <li>
                                                    Avaliador: ${avaliacao.avaliador.nome}<br>
                                                    Nota: ${avaliacao.notaFinal}<br>
                                                    Situação: 
                                                    <c:if test="${avaliacao.aprovado}">
                                                        Aprovado.
                                                    </c:if>
                                                    <c:if test="${not avaliacao.aprovado}">
                                                        Reprovado.
                                                    </c:if>
                                                    <br>
                                                    Parecer: ${avaliacao.parecer}<br>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                        <a href='AvaliacaoEditarServlet?id=${proposta.idPropostaTCC}'>Editar avaliações</a><br>
                                        <a href='AvaliacaoRemoverServlet?idAvaliacao1=${proposta.avaliacoes[0].idAvaliacao}&idAvaliacao2=${proposta.avaliacoes[1].idAvaliacao}'>Deletar avaliações</a><br>
                                    </c:if>
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
