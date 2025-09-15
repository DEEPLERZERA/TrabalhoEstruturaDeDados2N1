package repo;

import model.Servidor;
import tree.BST;

public class RepositorioServidores {
    private final BST<Servidor> arvore;

    public RepositorioServidores(BST<Servidor> arvore) { this.arvore = arvore; }

    public boolean inserir(Servidor s) { return arvore.insert(s); }
    public boolean removerPorNome(String nome) {
        return arvore.remove(new Servidor(nome, "", "", 0, "", ""));
    }
    public Servidor buscarPorNome(String nome) {
        return arvore.search(new Servidor(nome, "", "", 0, "", ""));
    }
    public BST<Servidor> getArvore() { return arvore; }
}
