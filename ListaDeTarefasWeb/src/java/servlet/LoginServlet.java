package servlet;

import dao.UsuarioDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Usuario;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Página para fazer o Login
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Processar o login
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario u = UsuarioDAO.buscarUsuarioPorEmail(email);

        boolean loginSucesso = false;

        if (u != null && u.getSenha().equals(senha)) {
            loginSucesso = true;
        }

        if (loginSucesso) {
            
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", u);
            
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            rd.forward(request, response);
            
        } else {

            // Informar o usuário que deu erro
            request.setAttribute("erro", "E-mail/senha incorretos");
            
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);

        }

    }
}
