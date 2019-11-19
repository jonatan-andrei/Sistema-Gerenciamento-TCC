<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TCC SSI - Envio de artigo de Proposta de TCC</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css"  href="css/estilo.css" />
    </head>
    <body>
        <div class ="formulario">
            <h1>Enviar artigo de Proposta de TCC</h1>  
            <form action="PropostaTCCEnviarArtigoServlet" method="post" class="col-8"> 

                <div class="form-group row">
                    <label for="id">Id Proposta: </label> 
                    <input type="text" name="id" id="id" class="form-control" value=${proposta.idPropostaTCC} readonly/>
                </div>

                <div class="form-group row">
                    <label for="titulo">Título</label> 
                    <input type="text" name="titulo" id="titulo" class="form-control" value='${proposta.titulo}' readonly/>
                </div>

                <div class="form-group row">
                    <label for="descricao">Descrição:</label> 
                    <input type="text" name="descricao" id="descricao" class="form-control" value='${proposta.descricao}' readonly/>
                </div>

                <div class="form-group row">
                    <label for="area">Área: </label> 
                    <input type="text" name="area" id="area" class="form-control" value='${proposta.area.nome}' readonly/>
                </div>

                <div class="form-group row">
                    <label for="autor">Autor: </label> 
                    <input type="text" name="autor" id="area" class="form-control" value='${proposta.autor.nome}' readonly/>
                </div>

                <div class="form-group row">
                    <label for="area">Orientador: </label> 
                    <input type="text" name="orientador" id="orientador" class="form-control" value='${proposta.orientador.nome}' readonly/>
                </div>

                <div class="form-group row">
                    <label for="artigo">Artigo: </label>
                    <textarea class="form-control" name="artigo" id="artigo" rows="6" placeholder="Artigo" required></textarea>
                </div>

                <button type="submit" class="btn btn-success btn-block">Salvar</button> 
                <a href="index.jsp" class="btn btn-secondary btn-block">Voltar a página inicial</a>
            </form>
        </div>
    </body>
</html>
