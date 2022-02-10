package dao;

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Usuario;


public class UsuarioDAO {

    // CONSTANTES
    private static final String DB_URL = "jdbc:mysql://localhost:3306";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static final String SELECT_SQL = "SELECT * FROM lista_tarefas.usuario";
    private static final String SELECT_BY_EMAIL_SQL = "SELECT * FROM lista_tarefas.usuario WHERE email = ?";
    private static final String INSERT_SQL = "INSERT INTO lista_tarefas.usuario (email, senha) VALUES (?, ?)";
    private static final String UPDATE_SQL = "UPDATE lista_tarefas.usuario SET email = ?, senha = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM lista_tarefas.usuario WHERE id = ?";

    // CRUD - Create Read Update Delete
    // READ
    public static ArrayList<Usuario> buscarUsuarios() {

        ArrayList<Usuario> usuarios = new ArrayList();

        // Java DataBase Connection (JDBC)
        try {

            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            PreparedStatement stmt = c.prepareStatement(SELECT_SQL);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String email = rs.getString("email");
                String senha = rs.getString("senha");

                Usuario u = new Usuario();
                u.setId(id);
                u.setEmail(email);
                u.setSenha(senha);

                usuarios.add(u);

            }

            stmt.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;

    }
    
    public static Usuario buscarUsuarioPorEmail(String email) {
        
        Usuario u = null;
        
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
            
            Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            PreparedStatement stmt = c.prepareStatement(SELECT_BY_EMAIL_SQL);
            
            stmt.setString(1, email);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                
                int id = rs.getInt("id");
                email = rs.getString("email");
                String senha = rs.getString("senha");
                
                u = new Usuario();
                u.setId(id);
                u.setEmail(email);
                u.setSenha(senha);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return u;
    }

    // CREATE
    public static boolean inserirUsuario(Usuario u) {

        boolean sucesso = false;

        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            PreparedStatement stmt = c.prepareStatement(INSERT_SQL);

            stmt.setString(1, u.getEmail());
            stmt.setString(2, u.getSenha());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                sucesso = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sucesso;

    }
 
    // UPDATE
    public static boolean atualizarUsuario(Usuario u) {

        boolean sucesso = false;

        try {

            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            PreparedStatement stmt = c.prepareStatement(UPDATE_SQL);

            stmt.setString(1, u.getEmail());
            stmt.setString(2, u.getSenha());
            stmt.setInt(3, u.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                sucesso = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sucesso;
    }

    // DELETE
    public static boolean deletarUsuario(Usuario u) {

        boolean sucesso = false;

        try {

            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            PreparedStatement stmt = c.prepareStatement(DELETE_SQL);

            stmt.setInt(1, u.getId());

            int rowsAffect = stmt.executeUpdate();

            if (rowsAffect > 0) {
                sucesso = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sucesso;
    }

}
