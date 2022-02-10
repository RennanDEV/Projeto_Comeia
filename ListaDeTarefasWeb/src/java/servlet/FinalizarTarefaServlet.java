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

@WebServlet(name = "FinalizarTarefaServlet", urlPatterns = {"/FinalizarTarefaServlet"})
public class FinalizarTarefaServlet extends HttpServlet {

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
        
        boolean finalizada = TarefaDAO.finalizarTarefa(t);
        
        if (finalizada) {
            
            request.setAttribute("msg", "Tarefa finalizada com sucesso");
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            rd.forward(request, response);
            
        } else {
            
            request.setAttribute("msg", "Erro ao finalizar tarefa. Tente novamente");
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            rd.forward(request, response);
            
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        Usuario u = (Usuario) session.getAttribute("usuarioLogado");
        
        String[] idTarefas = request.getParameterValues("tarefas");
        
        boolean sucesso = true;
        
        for (String idTarefa : idTarefas) {
            
            int id = Integer.parseInt(idTarefa);
            
            Tarefa t = new Tarefa();
            t.setId(id);
            t.setIdUsuario(u.getId());
            
            boolean finalizada = TarefaDAO.finalizarTarefa(t);   
            
            if (!finalizada) {
                sucesso = false;
            }
        }
        
        if (sucesso) {
            
            request.setAttribute("msg", "Sucesso ao finalizar tarefas");
            
        } else {
            
            request.setAttribute("msg", "Erro ao finalizar uma ou mais tarefas");
            
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
        rd.forward(request, response);
        
    }
}

