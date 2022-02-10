<%@page contentType="text/html" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro | Lista de Tarefas</title>
    </head>
    <body>
        <h1>Fazer Cadastro</h1>
        <form method="POST" action="CadastrarUsuarioServlet">
            <p>
                <label>E-mail:</label>
                <input type="email" name="email" />
            </p>
            <p>
                <label>Senha:</label>
                <input type="password" name="senha" />
            </p>
            <input type="submit" value="Cadastrar" />
        </form>
    </body>
</html>
