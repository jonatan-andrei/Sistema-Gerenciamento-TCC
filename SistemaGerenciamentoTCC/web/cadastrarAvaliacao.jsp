<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TCC SSI - Avaliação de TCC</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css"  href="css/estilo.css" />
    </head>
    <body>
        <div class ="formulario">
            <h1>Avaliação do TCC: ${proposta.descricao}</h1>  
            <form action="AvaliacaoCadastrarServlet" method="post" class="col-8"> 
                <input type="hidden" id="idPropostaTCC" value='${proposta.idPropostaTCC}' name="idPropostaTCC">
                <h2>Avaliação do(a) professor(a) orientador(a) ${proposta.orientador.nome}</h2>
                <input type="hidden" id="idProfessorOrientador" value='${proposta.orientador.id}' name="idProfessorOrientador">
                <div class="form-group row">
                    <label for="parecerProfOrientador">Parecer: </label> 
                    <input type="text" name="parecerProfOrientador" id="parecerProfOrientador" class="form-control" placeholder="Parecer: " required/>
                </div>
                <div class="form-group row">
                    <label for="notaFinalProfOrientador">Nota final: </label> 
                    <input type="number" step="0.1" min=0 max='10' name="notaFinalProfOrientador" id="notaFinalProfOrientador" class="form-control" placeholder="Nota final: " required/>
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="radio" name="aprovadoProfOrientador" id="aprovadoProfOrientador" value="true" checked>
                    <label class="form-check-label" for="aprovadoProfOrientador">
                        Aprovado
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="aprovadoProfOrientador" id="reprovadoProfOrientador" value="false">
                    <label class="form-check-label" for="reprovadoProfOrientador">
                        Reprovado
                    </label>
                </div>

                <div class="form-group row">
                    <label for="usoDeLinguagemProfOrientador">Uso de Linguagem: </label> 
                    <input type="text" name="usoDeLinguagemProfOrientador" id="usoDeLinguagemProfOrientador" class="form-control" placeholder="Uso de Linguagem: " required/>
                </div>

                <div class="form-group row">
                    <label for="apresentacaoProfOrientador">Apresentação: </label> 
                    <input type="text" name="apresentacaoProfOrientador" id="apresentacaoProfOrientador" class="form-control" placeholder="Apresentação: " required/>
                </div>

                <div class="form-group row">
                    <label for="estruturaTextoProfOrientador">Estrutura do Texto: </label> 
                    <input type="text" name="estruturaTextoProfOrientador" id="estruturaTextoProfOrientador" class="form-control" placeholder="Estrutura do Texto: " required/>
                </div>

                <div class="form-group row">
                    <label for="conteudoTextoProfOrientador">Conteúdo do Texto: </label> 
                    <input type="text" name="conteudoTextoProfOrientador" id="conteudoTextoProfOrientador" class="form-control" placeholder="Conteúdo do Texto: " required/>
                </div>

                <div class="form-group row">
                    <label for="relevanciaProfissionalProfOrientador">Relevância ao Perfil Profissional: </label> 
                    <input type="text" name="relevanciaProfissionalProfOrientador" id="relevanciaProfissionalProfOrientador" class="form-control" placeholder="Relevância ao perfil profissional: " required/>
                </div>

                <h2>Avaliação do(a) professor(a) ${proposta.banca[0].nome}</h2>
                <input type="hidden" id="idProfessor1" value='${proposta.banca[0].id}' name="idProfessor1">
                <div class="form-group row">
                    <label for="parecerProf1">Parecer: </label> 
                    <input type="text" name="parecerProf1" id="parecerProf1" class="form-control" placeholder="Parecer: " required/>
                </div>
                <div class="form-group row">
                    <label for="notaFinalProf1">Nota final: </label> 
                    <input type="number" step="0.1" min=0 max='10' name="notaFinalProf1" id="notaFinalProf1" class="form-control" placeholder="Nota final: " required/>
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="radio" name="aprovadoProf1" id="aprovadoProf1" value="true" checked>
                    <label class="form-check-label" for="aprovadoProf1">
                        Aprovado
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="aprovadoProf1" id="reprovadoProf1" value="false">
                    <label class="form-check-label" for="reprovadoProf1">
                        Reprovado
                    </label>
                </div>

                <div class="form-group row">
                    <label for="usoDeLinguagemProf1">Uso de Linguagem: </label> 
                    <input type="text" name="usoDeLinguagemProf1" id="usoDeLinguagemProf1" class="form-control" placeholder="Uso de Linguagem: " required/>
                </div>

                <div class="form-group row">
                    <label for="apresentacaoProf1">Apresentação: </label> 
                    <input type="text" name="apresentacaoProf1" id="apresentacaoProf1" class="form-control" placeholder="Apresentação: " required/>
                </div>

                <div class="form-group row">
                    <label for="estruturaTextoProf1">Estrutura do Texto: </label> 
                    <input type="text" name="estruturaTextoProf1" id="estruturaTextoProf1" class="form-control" placeholder="Estrutura do Texto: " required/>
                </div>

                <div class="form-group row">
                    <label for="conteudoTextoProf1">Conteúdo do Texto: </label> 
                    <input type="text" name="conteudoTextoProf1" id="conteudoTextoProf1" class="form-control" placeholder="Conteúdo do Texto: " required/>
                </div>

                <div class="form-group row">
                    <label for="relevanciaProfissionalProf1">Relevância ao Perfil Profissional: </label> 
                    <input type="text" name="relevanciaProfissionalProf1" id="relevanciaProfissionalProf1" class="form-control" placeholder="Relevância ao perfil profissional: " required/>
                </div>

                <h2>Avaliação do(a) professor(a) ${proposta.banca[1].nome}</h2>
                <input type="hidden" id="idProfessor2" value='${proposta.banca[1].id}' name='idProfessor2'>
                <div class="form-group row">
                    <label for="parecerProf2">Parecer: </label> 
                    <input type="text" name="parecerProf2" id="parecerProf2" class="form-control" placeholder="Parecer: " required/>
                </div>
                <div class="form-group row">
                    <label for="notaFinalProf2">Nota final: </label> 
                    <input type="number" step="0.1" min=0 max='10' name="notaFinalProf2" id="notaFinalProf2" class="form-control" placeholder="Nota final: " required/>
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="radio" name="aprovadoProf2" id="aprovadoProf2" value="true" checked>
                    <label class="form-check-label" for="aprovadoProf2">
                        Aprovado
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="aprovadoProf2" id="reprovadoProf2" value="false">
                    <label class="form-check-label" for="reprovadoProf2">
                        Reprovado
                    </label>
                </div>

                <div class="form-group row">
                    <label for="usoDeLinguagemProf2">Uso de Linguagem: </label> 
                    <input type="text" name="usoDeLinguagemProf2" id="usoDeLinguagemProf2" class="form-control" placeholder="Uso de Linguagem: " required/>
                </div>

                <div class="form-group row">
                    <label for="apresentacaoProf2">Apresentação: </label> 
                    <input type="text" name="apresentacaoProf2" id="apresentacaoProf2" class="form-control" placeholder="Apresentação: " required/>
                </div>

                <div class="form-group row">
                    <label for="estruturaTextoProf2">Estrutura do Texto: </label> 
                    <input type="text" name="estruturaTextoProf2" id="estruturaTextoProf2" class="form-control" placeholder="Estrutura do Texto: " required/>
                </div>

                <div class="form-group row">
                    <label for="conteudoTextoProf2">Conteúdo do Texto: </label> 
                    <input type="text" name="conteudoTextoProf2" id="conteudoTextoProf2" class="form-control" placeholder="Conteúdo do Texto: " required/>
                </div>

                <div class="form-group row">
                    <label for="relevanciaProfissionalProf2">Relevância ao Perfil Profissional: </label> 
                    <input type="text" name="relevanciaProfissionalProf2" id="relevanciaProfissionalProf2" class="form-control" placeholder="Relevância ao perfil profissional: " required/>
                </div>

                <button type="submit" class="btn btn-success btn-block">Salvar</button> 
                <a href="index.jsp" class="btn btn-secondary btn-block">Voltar a página inicial</a>
            </form>
        </div>
    </body>
</html>

