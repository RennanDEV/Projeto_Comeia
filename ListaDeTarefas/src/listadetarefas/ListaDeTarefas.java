package listadetarefas;

import dao.TarefaDAO;
import dao.UsuarioDAO;
import model.Tarefa;
import model.Usuario;
import java.util.ArrayList;
import java.util.Scanner;

public class ListaDeTarefas {

    public static Usuario usuarioLogado = null;
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        ArrayList<Usuario> usuarios = new ArrayList();

        boolean rodando = true;
        while (rodando) {

            // Menu + input do usuário
            System.out.println("==== PÁGINA INICIAL ====");
            System.out.println("[1] Fazer cadastro");
            System.out.println("[2] Fazer login");
            System.out.println("[3] Sair");
            System.out.print("Digite a opção: ");
            String opcao = scanner.nextLine();

            // Processar o input do usuário
            switch (opcao) {
                case "1": {
                    System.out.println("==== CADASTRO ====");
                    System.out.print("Digite o email: ");
                    String email = scanner.nextLine();
                    System.out.print("Digite a senha: ");
                    String senha = scanner.nextLine();

                    Usuario u = new Usuario();
                    u.setEmail(email);
                    u.setSenha(senha);

                    ArrayList<Tarefa> tarefas = new ArrayList();
                    u.setTarefas(tarefas);

                    boolean cadastrado = UsuarioDAO.inserirUsuario(u);

                    if (cadastrado) {
                        System.out.println("------> Usuário cadastrado com sucesso!");
                    } else {
                        System.out.println("------> Erro ao cadastrar usuário. Tente novamente.");
                    }

                    break;
                }
                case "2": {
                    System.out.println("==== LOGIN ====");
                    System.out.print("Digite o email: ");
                    String email = scanner.nextLine();
                    System.out.print("Digite a senha: ");
                    String senha = scanner.nextLine();

                    Usuario u = UsuarioDAO.buscarUsuarioPorEmail(email);

                    boolean loginSucesso = false;

                    if (u != null && u.getSenha().equals(senha)) {
                        loginSucesso = true;
                    }

                    if (loginSucesso) {
                        System.out.println("------> Login feito com sucesso!");
                        usuarioLogado = u;
                        homePage();
                    } else {
                        System.out.println("------> Email/Senha incorretos.");
                    }

                    break;
                }
                case "3": {
                    rodando = false;
                    System.out.println("Encerrando programa...");
                    break;
                }
                default: {
                    break;
                }
            }

        }

        System.out.println("Fim do programa.");

    }

    public static void homePage() {

        boolean rodando = true;
        while (rodando) {

            System.out.println("==== HOME PAGE ====");
            System.out.println("[1] Mostrar tarefas");
            System.out.println("[2] Mostrar tarefas finalizadas");
            System.out.println("[3] Mostrar tarefas não finalizadas");
            System.out.println("[4] Adicionar tarefa");
            System.out.println("[5] Finalizar tarefa");
            System.out.println("[6] Remover tarefa");
            System.out.println("[7] Logout");
            System.out.print("Digite a opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1": {
                    System.out.println("==== TAREFAS ====");
                    ArrayList<Tarefa> lista = TarefaDAO.buscarTarefasDoUsuario(usuarioLogado);

                    if (lista.isEmpty()) {
                        System.out.println("[Lista de tarefas vazia.]");
                    }

                    for (int i = 0; i < lista.size(); i++) {

                        Tarefa t = lista.get(i);
                        System.out.println("Tarefa " + i);
                        System.out.println("\tTítulo: " + t.getTitulo());
                        System.out.println("\tFinalizada: " + t.isFinalizada());

                    }
                    break;
                }
                case "2": {
                    System.out.println("==== TAREFAS FINALIZADAS ====");

                    ArrayList<Tarefa> lista = TarefaDAO.buscarTarefasDoUsuario(usuarioLogado);
                    ArrayList<Tarefa> finalizadas = new ArrayList();

                    for (Tarefa t : lista) {
                        if (t.isFinalizada()) {
                            finalizadas.add(t);
                        }
                    }

                    if (finalizadas.isEmpty()) {
                        System.out.println("[Não há tarefas finalizadas para mostrar]");
                    }

                    for (int i = 0; i < finalizadas.size(); i++) {
                        Tarefa t = finalizadas.get(i);
                        System.out.println("Tarefa " + i);
                        System.out.println("\tTítulo: " + t.getTitulo());
                        System.out.println("\tFinalizada: " + t.isFinalizada());
                    }

                    break;
                }
                case "3": {
                    System.out.println("==== TAREFAS NÃO FINALIZADAS ====");

                    ArrayList<Tarefa> lista = TarefaDAO.buscarTarefasDoUsuario(usuarioLogado);
                    ArrayList<Tarefa> naoFinalizadas = new ArrayList();

                    for (Tarefa t : lista) {
                        if (!t.isFinalizada()) {
                            naoFinalizadas.add(t);
                        }
                    }

                    if (naoFinalizadas.isEmpty()) {
                        System.out.println("[Não há tarefas em aberto]");
                    }

                    for (int i = 0; i < naoFinalizadas.size(); i++) {
                        Tarefa t = naoFinalizadas.get(i);

                        System.out.println("Tarefa " + i);
                        System.out.println("\tTítulo: " + t.getTitulo());
                        System.out.println("\tFinalizada: " + t.isFinalizada());
                    }

                    break;
                }
                case "4": {
                    System.out.println("==== NOVA TAREFA ====");
                    System.out.print("Digite o título: ");
                    String titulo = scanner.nextLine();

                    Tarefa t = new Tarefa();
                    t.setTitulo(titulo);
                    t.setFinalizada(false);
                    t.setIdUsuario(usuarioLogado.getId());

                    boolean inserida = TarefaDAO.inserirTarefa(t);
                    
                    if (inserida){
                        System.out.println("------> Tarefa adicionada com sucesso!");
                    } else {
                        System.out.println("------> Erro ao adicionar tarefa. Tente novamente.");
                    }
                    
                    break;
                }

                case "5": {
                    System.out.println("==== FINALIZAR TAREFA ====");
                    ArrayList<Tarefa> lista = TarefaDAO.buscarTarefasDoUsuario(usuarioLogado);
                    ArrayList<Tarefa> naoFinalizadas = new ArrayList();

                    for (Tarefa t : lista) {
                        if (!t.isFinalizada()) {
                            naoFinalizadas.add(t);
                        }
                    }

                    if (naoFinalizadas.isEmpty()) {
                        System.out.println("[Não há tarefas para finalizar]");
                    } else {

                        for (Tarefa t : naoFinalizadas) {
                            System.out.println("[" + t.getId() + "]" + t.getTitulo());
                        }

                        System.out.print("Digite a tarefa que deseja finalizar: ");
                        int id = scanner.nextInt();
                        scanner.nextLine(); // limpar buffer

                        Tarefa t = new Tarefa();
                        t.setId(id);
                        t.setIdUsuario(usuarioLogado.getId());
                        
                        boolean finalizada = TarefaDAO.finalizarTarefa(t);
                        
                        if (finalizada){
                            System.out.println("------> Tarefa finalizada com sucesso!");
                        } else {
                            System.out.println("------> Erro ao atualizar tarefa. Tente novamente,");
                        }
                        
                    }

                    break;
                }
                case "6": {
                    System.out.println("==== REMOVER TAREFA ====");
                    ArrayList<Tarefa> lista = TarefaDAO.buscarTarefasDoUsuario(usuarioLogado);

                    for (Tarefa t : lista) {
                        System.out.println("[" + t.getId() + "] " + t.getTitulo());
                    }

                    System.out.print("Digite a tarefa que deseja remover: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // limpar buffer

                    Tarefa t = new Tarefa();  
                    t.setId(id);
                    t.setIdUsuario(usuarioLogado.getId());
                    
                    boolean removida = TarefaDAO.removerTarefa(t);
                    if (removida) {
                        System.out.println("------> Tarefa removida com sucesso!");
                    } else {
                        System.out.println("------> Erro ao remover tarefa. Tente novamente.");
                    }

                    break;
                }
                case "7": {
                    System.out.println("Fazendo logout...");
                    rodando = false;
                    usuarioLogado = null;
                    break;
                }
            }

        }

    }

}
