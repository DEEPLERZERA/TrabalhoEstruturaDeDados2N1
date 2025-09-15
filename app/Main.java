package edu.mack.apl1.app;

import edu.mack.apl1.io.CsvMapper;
import edu.mack.apl1.io.CsvSaver;
import edu.mack.apl1.model.Servidor;
import edu.mack.apl1.repo.RepositorioServidores;
import edu.mack.apl1.service.Analytics;
import edu.mack.apl1.tree.BST;
import edu.mack.apl1.tree.ABB;
import edu.mack.apl1.util.Pair;

import java.io.IOException;
import java.util.*;

public class Main {
    static BST<Servidor> criarSuaABB() {
        return new ABB<>();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RepositorioServidores repo = new RepositorioServidores(criarSuaABB());

        while (true) {
            System.out.println("""
                === Apl1 – ABB + Exploração de Dados ===
                1) Ler CSV e montar árvore
                2) Análises (5 opções)
                3) Inserir funcionário
                4) Remover funcionário
                5) Salvar árvore em CSV
                6) Sair
                Escolha: """);
            String op = sc.nextLine().trim();
            switch (op) {
                case "1" -> {
                    System.out.print("Caminho do CSV: ");
                    String path = sc.nextLine();
                    try { CsvMapper.carregar(path, repo); }
                    catch (IOException e) { System.out.println("Erro ao ler: " + e.getMessage()); }
                }
                case "2" -> submenuAnalises(sc, repo);
                case "3" -> inserir(sc, repo);
                case "4" -> remover(sc, repo);
                case "5" -> {
                    System.out.print("Nome do arquivo CSV de saída: ");
                    String pathOut = sc.nextLine();
                    try { CsvSaver.salvar(pathOut, repo.getArvore()); System.out.println("Salvo em " + pathOut); }
                    catch (IOException e) { System.out.println("Erro ao salvar: " + e.getMessage()); }
                }
                case "6" -> { System.out.println("Até mais!"); return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    static void inserir(Scanner sc, RepositorioServidores repo) {
        System.out.print("Nome: "); String nome = sc.nextLine();
        System.out.print("Cargo Base: "); String cb = sc.nextLine();
        System.out.print("Cargo em Comissão: "); String cc = sc.nextLine();
        System.out.print("Remuneração Bruta (use ponto ou vírgula): ");
        double r = Double.parseDouble(sc.nextLine().trim().replace(".", "").replace(",", "."));
        System.out.print("Unidade: "); String un = sc.nextLine();
        System.out.print("Jornada: "); String jo = sc.nextLine();
        boolean ok = repo.inserir(new Servidor(nome, cb, cc, r, un, jo));
        System.out.println(ok ? "Inserido." : "Nome duplicado (ignorado).");
    }

    static void remover(Scanner sc, RepositorioServidores repo) {
        System.out.print("Nome para remover: ");
        System.out.println(repo.removerPorNome(sc.nextLine()) ? "Removido." : "Nome não encontrado.");
    }

    static void submenuAnalises(Scanner sc, RepositorioServidores repo) {
        System.out.println("""
            --- Análises ---
            A) Maior e menor salário (pré-ordem)
            B) Montante por cargo (em-ordem)
            C) Top-N salários (pós-ordem)
            D) Gasto total por unidade (em-ordem)
            E) Distribuição por jornada (pré-ordem)
            Escolha: """);
        String a = sc.nextLine().trim().toUpperCase(Locale.ROOT);
        switch (a) {
            case "A" -> {
                Pair<Servidor,Servidor> p = Analytics.extremosSalario(repo.getArvore());
                System.out.println("Menor: " + p.first());
                System.out.println("Maior: " + p.second());
            }
            case "B" -> {
                System.out.print("Informe parte do nome do cargo: ");
                String alvo = sc.nextLine();
                double tot = Analytics.totalPorCargo(repo.getArvore(), alvo);
                System.out.printf("Total gasto com \"%s\": R$ %.2f%n", alvo, tot);
            }
            case "C" -> {
                System.out.print("N (Top-N): ");
                int n = Integer.parseInt(sc.nextLine());
                Analytics.topN(repo.getArvore(), n).forEach(System.out::println);
            }
            case "D" -> {
                var mapa = Analytics.gastoPorUnidade(repo.getArvore());
                mapa.forEach((u, t) -> System.out.printf("%s -> R$ %.2f%n", u, t));
            }
            case "E" -> {
                var mapa = Analytics.distribuicaoPorJornada(repo.getArvore());
                mapa.forEach((j, c) -> System.out.printf("%s -> %d servidor(es)%n", j, c));
            }
            default -> System.out.println("Opção inválida.");
        }
    }
}
