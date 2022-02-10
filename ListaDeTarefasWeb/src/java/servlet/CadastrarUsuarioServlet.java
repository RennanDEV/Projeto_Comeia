package servlet;

import dao.UsuarioDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Usuario;

@WebServlet(name = "CadastrarUsuarioServlet", urlPatterns = {"/CadastrarUsuarioServlet"})
public class CadastrarUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            RequestDispatcher rd = request.getRequestDispatcher("cadastrar_usuario.jsp");
            
            rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Processar o cadastro do usuário
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario u = new Usuario();
        u.setEmail(email);
        u.setSenha(senha);

        boolean inserido = UsuarioDAO.inserirUsuario(u);

        if (inserido) {
            
            RequestDispatcher rd = request.getRequestDispatcher("cadastrar_sucesso.jsp");
            rd.forward(request, response);
            
        } else {
            
            RequestDispatcher rd = request.getRequestDispatcher("cadastrar_erro.jsp");
            rd.forward(request, response);
            
        }
    }
}

