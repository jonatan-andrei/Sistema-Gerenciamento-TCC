<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TCC SSI - Cadastro Projeto de Pesquisa</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css"  href="css/estilo.css" />
    </head>
    <body>
        <div class ="formulario">
            <h1>Cadastrar novo projeto de pesquisa</h1>  
            <form action="ProjetoPesquisaServlet" method="post" class="col-8"> 
                <div class="form-group row">
                    <label for="nome">Nome: </label> 
                    <input type="text" name="nome" id="nome" class="form-control" placeholder="Nome" required/>
                </div>

                <div class="form-group row">
                    <label for="descricao">Descrição: </label>
                    <input type="text" name="descricao" id="descricao" class="form-control" placeholder="Descrição" required/>
                </div>

                <button type="submit" class="btn btn-success btn-block">Salvar</button> 
                <a href="index.jsp" class="btn btn-secondary btn-block">Voltar a página inicial</a>
            </form>
        </div>
    </body>
</html>