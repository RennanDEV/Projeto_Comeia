package servlet;

import dao.TarefaDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Tarefa;
import model.Usuario;

@WebServlet(name = "ExcluirTarefaServlet", urlPatterns = {"/ExcluirTarefaServlet"})
public class ExcluirTarefaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        Usuario u = (Usuario) session.getAttribute("usuarioLogado");
        
        String idTarefa = request.getParameter("id");
        int id = Integer.parseInt(idTarefa);
        
        Tarefa t = new Tarefa();
        t.setId(id);
        t.setIdUsuario(u.getId());
        
        boolean removido = TarefaDAO.removerTarefa(t);
        
        if (removido) {
            
            request.setAttribute("msg", "Tarefa removida com sucesso");

        } else {
            
            request.setAttribute("msg", "Erro ao remover tarefa. Tente novamente");
            
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
        rd.forward(request, response); 

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
}

