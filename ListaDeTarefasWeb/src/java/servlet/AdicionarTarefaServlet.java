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

@WebServlet(name = "AdicionarTarefaServlet", urlPatterns = {"/AdicionarTarefaServlet"})
public class AdicionarTarefaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        Usuario u = (Usuario) session.getAttribute("usuarioLogado");
        
        String titulo = request.getParameter("titulo");
        
        Tarefa t = new Tarefa();
        t.setTitulo(titulo);
        t.setFinalizada(false);
        t.setIdUsuario(u.getId());
        
        boolean inserida = TarefaDAO.inserirTarefa(t);
        
        if (inserida) {
            
            request.setAttribute("msg", "Tarefa adicionada com sucesso");
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            rd.forward(request, response);
            
        } else {
            
            request.setAttribute("msg", "Erro ao adicionar tarefa. Tente novamente");
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            rd.forward(request, response);
        }

    }
}

