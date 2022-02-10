<%@page import="dao.TarefaDAO"%>
<%@page import="model.Tarefa"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home | Lista de Tarefas</title>
    </head>
    <body>
        <a>
            <%
                Usuario u = (Usuario) session.getAttribute("usuarioLogado");
                
                if (u != null) {
                    out.print(u.getEmail());
                }
               
            %>
        </a>
        <a href="LogoutServlet">Sair</a>

        <hr>
        <form method="POST" action="AdicionarTarefaServlet">
            <p>
                <label>Título: </label>
                <input type="text" name="titulo">
            </p>
            <p>
                <input type="submit" value="Adicionar Tarefa" />
            </p>
            <p>
                <%
                    String msg = (String) request.getAttribute("msg");
                    if (msg != null) {
                        out.println(msg);
                    }
                %>
            </p>
        </form>
        <hr>

        <h1>Lista de Tarefas</h1>
        
        <form method="POST" action="FinalizarTarefaServlet">
            <%
                ArrayList<Tarefa> tarefas = TarefaDAO.buscarTarefasDoUsuario(u);

                if (tarefas.isEmpty()) {
                    out.println("Não há tarefas");
                }

                for ( Tarefa t : tarefas) {
                    out.println("<p>"
                            + "<input type=\"checkbox\" name=\"tarefas\" value=\""+ t.getId() +"\" />"
                            + t.getTitulo() + " - " + t.isFinalizada() 
                            + " <a href=\"ExcluirTarefaServlet?id=" + t.getId() +"\">Excluir</a>"
                            + "</p>");
                }

            %>
            <p>
                <input type="submit" value="Finalizar" />
            </p>
        </form>
    </body>
</html>
