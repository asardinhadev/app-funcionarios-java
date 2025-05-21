import java.io.*;
import java.util.Scanner;

public class AppFuncionarios {

    public static void main(String[] args) {
        Scanner ent = new Scanner(System.in);
        boolean sair = false;

        while (!sair) {
            System.out.println("\nMenu:");
            System.out.println("1. Adicionar funcionário");
            System.out.println("2. Mostrar todos os funcionários");
            System.out.println("3. Pesquisar funcionário");
            System.out.println("4. Excluir funcionário");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = ent.nextInt();
            ent.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o ID: ");
                    int id = ent.nextInt();
                    ent.nextLine(); // Consumir a nova linha
                    System.out.print("Digite o nome: ");
                    String nome = ent.nextLine();
                    System.out.print("Digite o salário: ");
                    double salario = ent.nextDouble();
                    escreverFuncionario(id, nome, salario);
                    System.out.println("Funcionário adicionado.");
                    break;
                case 2:
                    lerFuncionarios();
                    break;
                case 3:
                    System.out.print("Digite o ID a pesquisar: ");
                    int idPesquisa = ent.nextInt();
                    pesquisarFuncionario(idPesquisa);
                    break;
                case 4:
                    System.out.print("Digite o ID a excluir: ");
                    int idExcluir = ent.nextInt();
                    excluirFuncionario(idExcluir);
                    break;
                case 5:
                    sair = true;
                    System.out.println("Programa terminado.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        ent.close();
    }

    public static void escreverFuncionario(int id, String nome, double salario) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("funcionarios.txt", true))) {
            writer.write(id + ";" + nome + ";" + salario);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao escrever no ficheiro: " + e.getMessage());
        }
    }

    public static void lerFuncionarios() {
        try (BufferedReader reader = new BufferedReader(new FileReader("funcionarios.txt"))) {
            String linha;
            System.out.println("\nFuncionários:");
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                System.out.println("ID: " + dados[0] + ", Nome: " + dados[1] + ", Salário: " + dados[2]);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro: " + e.getMessage());
        }
    }

    public static void pesquisarFuncionario(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader("funcionarios.txt"))) {
            String linha;
            boolean encontrado = false;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (Integer.parseInt(dados[0]) == id) {
                    System.out.println("Funcionário encontrado: ID: " + dados[0] + ", Nome: " + dados[1] + ", Salário: " + dados[2]);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("Funcionário não encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao pesquisar no ficheiro: " + e.getMessage());
        }
    }

    public static void excluirFuncionario(int id) {
        File inputFile = new File("funcionarios.txt");
        File tempFile = new File("funcionarios_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String linha;
            boolean encontrado = false;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (Integer.parseInt(dados[0]) != id) {
                    writer.write(linha);
                    writer.newLine();
                } else {
                    encontrado = true;
                }
            }

            if (encontrado) {
                if (inputFile.delete()) {
                    tempFile.renameTo(inputFile);
                    System.out.println("Funcionário excluído.");
                } else {
                    System.out.println("Erro ao substituir o ficheiro original.");
                }
            } else {
                System.out.println("Funcionário não encontrado.");
                tempFile.delete(); // Apagar ficheiro temporário se não foi usado
            }

        } catch (IOException e) {
            System.out.println("Erro ao excluir funcionário: " + e.getMessage());
        }
    }
}
