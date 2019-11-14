<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TCC SSI - Listagem de professores</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="css/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css"  href="css/estilo.css" />
    </head>
    <body>
        <div class="area-resposta">
            <c:if test="${empty professores}">
                <div class="alert alert-danger">Sem professores cadastrados</div>
            </c:if>
            <c:if test="${not empty professores}">
                <h1>Professores: </h1>
                <table class="table table-striped table-dark">
                    <tbody>
                        <c:forEach var="professor" items="${professores}">
                            <tr>
                                <td><span class="professor-nome">Nome: ${professor.nome}</span><br>
                                    E-mail: ${professor.email}<br>
                                    Envolvido com ${professor.cargaTrabalhoSemestre} proposta(s) de TCC neste semestre.<br>
                                    <c:if test="${not empty professor.areasDeInteresse}">
                                        Áreas de interesse:
                                        <c:forEach var="area" items="${professor.areasDeInteresse}">
                                            ${area.nome};
                                        </c:forEach><br>
                                    </c:if>
                                    <c:if test="${not empty professor.sugestoes}">
                                        Sugestões de projeto:<ul>
                                            <c:forEach var="sugestao" items="${professor.sugestoes}">
                                                <li>${sugestao.descricao} 
                                                    <c:if test="${sugestao.escolhida}">
                                                        (escolhida)
                                                    </c:if>  
                                                    <c:if test="${not sugestao.escolhida}">
                                                        <a href='PropostaTCCCadastrarViaSugestaoServlet?idSugestao=${sugestao.idSugestaoTCC}'>(escolher)</a>
                                                    </c:if>  
                                                </li>
                                                <c:if test="${not empty sugestao.projeto}">
                                                    Relacionada ao projeto: ${sugestao.projeto.nome} - ${sugestao.projeto.descricao}
                                                </c:if>  
                                            </c:forEach></ul>
                                        </c:if>      
                                    Editar Professor: <a href='ProfessorEditarServlet?id=${professor.id}'><i class="fa fa-pencil fa-2x"></i></a>
                                    Desativar Professor: <a href='ProfessorDesativarServlet?id=${professor.id}'><i class="fa fa-times fa-2x"></i></a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <a href="index.jsp" class="btn btn-secondary btn-block">Voltar a página inicial</a>
        </div>
    </body>
</html>

