package view;

import controller.BancoInicializador;
import controller.Cadastro;
import controller.PessoaDAO;
import model.Pessoa;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        Cadastro cadastro = new Cadastro();
        PessoaDAO pessoaDAO = new PessoaDAO();

        BancoInicializador.criarTabela();

        //Menu interativo
        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("[1] Cadastrar nova pessoa");
            System.out.println("[2] Listar todas");
            System.out.println("[3] Buscar pessoa por nome");
            System.out.println("[4] Editar pessoa");
            System.out.println("[5] Remover pessoa");
            System.out.println("[6] Exportar");
            System.out.println("[7] Importar");
            System.out.println("[0] Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = entrada.nextInt();
            entrada.nextLine();
            switch (opcao) {
                case 1:
                    cadastrarPessoa(entrada, pessoaDAO);
                    break;
                case 2:
                    listarPessoa(pessoaDAO);
                    break;
                case 3:
                    buscarPessoa(entrada, pessoaDAO);
                    break;
                case 4:


                    editarPessoa(entrada, pessoaDAO);
                    break;
                case 5:
                    removerPessoa(entrada, cadastro);
                    break;
                case 6:
                    exportarPessoa(entrada, cadastro);
                    break;
                case 7:
                    importarPessoa(entrada, cadastro);
                    break;
                case 0:
                    System.out.println("Encerrando o programa...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }

    }

    private static void cadastrarPessoa(Scanner entrada, PessoaDAO pessoaDAO) {
        System.out.println("--- Cadastrar novo pessoa ---");
        System.out.print("Nome: ");
        String nome = entrada.nextLine();
        System.out.print("Idade: ");
        int idade = entrada.nextInt();
        entrada.nextLine();
        System.out.print("Email: ");
        String email = entrada.nextLine();
        System.out.print("CPF: ");
        String cpf = entrada.nextLine();

        Pessoa p = new Pessoa(nome, idade, email,cpf);
        boolean sucesso = pessoaDAO.salvar(p);
        System.out.println(sucesso ? "Pessoa cadastrada com sucesso" : "Erro ao cadastrar pessoa");
    }

    private static void listarPessoa(PessoaDAO pessoaDAO) {
        System.out.println("--- Listar todas as pessoas ---");
        for (Pessoa p2 : pessoaDAO.listarTodos()) {
            System.out.println(p2.getNome() + ", tem " + p2.getIdade() + " anos, E-mail: " + p2.getEmail() + ", CPF: " + p2.getCpf());
        }
    }

    private static void buscarPessoa(Scanner entrada,PessoaDAO pessoaDAO) {
        System.out.println("--- Buscar pessoa por nome ---");
        System.out.print("Nome da pessoa: ");
        String cpfBusca = entrada.nextLine();
        Pessoa encontrada = pessoaDAO.buscarPorCpf(cpfBusca);
        if (encontrada != null) {
            System.out.println("Encontrada: " + encontrada.getNome() + ", " + encontrada.getIdade() + ", " + encontrada.getEmail()  + ", " + encontrada.getCpf());
        } else {
            System.out.println("Pessoa não encontrada.");
        }
    }

    private static void editarPessoa(Scanner entrada, PessoaDAO pessoaDAO) {
        System.out.println("--- Buscar nome de pessoa para editar ---");
        System.out.print("Nome da pessoa: ");
        String cpfBusca = entrada.nextLine();
        Pessoa encontrada = pessoaDAO.buscarPorCpf(cpfBusca);
        if (encontrada != null) {
            System.out.println("Encontrada: " + encontrada.getNome() + ", " + encontrada.getIdade() + ", " + encontrada.getEmail() + ", " + encontrada.getCpf());
            System.out.println("--- Insira os novos dados ---");
            System.out.print("Nome da pessoa: ");
            String nomeEdita = entrada.nextLine();
            System.out.print("Idade: ");
            int idadeEdita = entrada.nextInt();
            entrada.nextLine();
            System.out.print("Email: ");
            String emailEdita = entrada.nextLine();

            Pessoa novosDados = new Pessoa(nomeEdita, idadeEdita, emailEdita, cpfBusca);
            boolean editado = pessoaDAO.editar(cpfBusca, novosDados);
            System.out.println(editado ? "Dados atualizados!" : "Erro ao editar pessoa");
        } else {
            System.out.println("Pessoa não encontrada.");
        }
    }

    private static void removerPessoa(Scanner entrada, Cadastro cadastro) {
        System.out.println("--- Remover pessoa por nome ---");
        System.out.print("Nome da pessoa: ");
        String nomeRemover = entrada.nextLine();
        boolean removido = cadastro.removerPessoa(nomeRemover);
        System.out.println(removido ? "Pessoa removida!" : "Erro ao remover pessoa");
    }

    private static void exportarPessoa(Scanner entrada, Cadastro cadastro) {
        System.out.println("--- Exportar todas as pessoas ---");
        System.out.print("Nome do arquivo: ");
        String nomeArquivo = entrada.nextLine();
        boolean exportado = cadastro.exportarPessoas(nomeArquivo);
        System.out.println(exportado ? "Exportado com sucesso!" : "Erro ao exportar pessoas");
    }

    private static void importarPessoa(Scanner entrada, Cadastro cadastro) {
        System.out.println("--- Importar todas as pessoas ---");
        System.out.print("Nome do arquivo: ");
        String nomeArquivoImportar = entrada.nextLine();
        boolean importado = cadastro.importarPessoas(nomeArquivoImportar);
        System.out.println(importado ? "Arquivo importado com sucesso!" : "Erro ao importar pessoas");
    }
    //teste
}
