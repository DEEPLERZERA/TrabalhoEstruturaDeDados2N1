package edu.mack.apl1.tree;

import java.util.Objects;
import java.util.function.Consumer;

public class ABB<E extends Comparable<E>> implements BST<E> {
    private Node<E> raiz;
    private int size;

    public ABB() { this.raiz = null; this.size = 0; }

    public Node<E> getRaiz(){ return raiz; }

    @Override
    public boolean insert(E value) {
        Objects.requireNonNull(value, "value");
        if (search(value) != null) return false; // evita duplicata
        raiz = inserirRec(raiz, value);
        size++;
        return true;
    }

    private Node<E> inserirRec(Node<E> node, E value){
        if (node == null) return new Node<>(value);
        int cmp = value.compareTo(node.getValue());
        if (cmp < 0) node.setFilhoEsq(inserirRec(node.getFilhoEsq(), value));
        else if (cmp > 0) node.setFilhoDir(inserirRec(node.getFilhoDir(), value));
        // se cmp == 0, já tratamos acima (não insere); aqui apenas retorna node
        return node;
    }

    @Override
    public boolean remove(E value) {
        int[] removed = {0};
        raiz = removeRec(raiz, value, removed);
        if (removed[0] == 1) { size--; return true; }
        return false;
    }

    private Node<E> removeRec(Node<E> node, E value, int[] removed){
        if (node == null) return null;
        int cmp = value.compareTo(node.getValue());
        if (cmp < 0) {
            node.setFilhoEsq(removeRec(node.getFilhoEsq(), value, removed));
        } else if (cmp > 0) {
            node.setFilhoDir(removeRec(node.getFilhoDir(), value, removed));
        } else {
            removed[0] = 1;
            // casos: 0/1/2 filhos
            if (node.getFilhoEsq() == null) return node.getFilhoDir();
            if (node.getFilhoDir() == null) return node.getFilhoEsq();
            // dois filhos: usa predecessor (máximo da subárvore esquerda)
            Node<E> maxLeft = node.getFilhoEsq();
            Node<E> parent = null;
            while (maxLeft.getFilhoDir() != null) {
                parent = maxLeft;
                maxLeft = maxLeft.getFilhoDir();
            }
            node.setValue(maxLeft.getValue());
            if (parent == null) {
                // predecessor era o filho esquerdo imediato
                node.setFilhoEsq(maxLeft.getFilhoEsq());
            } else {
                parent.setFilhoDir(maxLeft.getFilhoEsq());
            }
        }
        return node;
    }

    @Override
    public E search(E probe) {
        Node<E> cur = raiz;
        while (cur != null) {
            int cmp = probe.compareTo(cur.getValue());
            if (cmp == 0) return cur.getValue();
            cur = (cmp < 0) ? cur.getFilhoEsq() : cur.getFilhoDir();
        }
        return null;
    }

    @Override
    public void inOrder(Consumer<E> action) { inOrderRec(raiz, action); }
    private void inOrderRec(Node<E> n, Consumer<E> a){
        if (n == null) return;
        inOrderRec(n.getFilhoEsq(), a);
        a.accept(n.getValue());
        inOrderRec(n.getFilhoDir(), a);
    }

    @Override
    public void preOrder(Consumer<E> action) { preOrderRec(raiz, action); }
    private void preOrderRec(Node<E> n, Consumer<E> a){
        if (n == null) return;
        a.accept(n.getValue());
        preOrderRec(n.getFilhoEsq(), a);
        preOrderRec(n.getFilhoDir(), a);
    }

    @Override
    public void postOrder(Consumer<E> action) { postOrderRec(raiz, action); }
    private void postOrderRec(Node<E> n, Consumer<E> a){
        if (n == null) return;
        postOrderRec(n.getFilhoEsq(), a);
        postOrderRec(n.getFilhoDir(), a);
        a.accept(n.getValue());
    }

    @Override public int size(){ return size; }
    @Override public boolean isEmpty(){ return size == 0; }
}
