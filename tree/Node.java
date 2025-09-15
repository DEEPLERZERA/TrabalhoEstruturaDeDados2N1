package edu.mack.apl1.tree;

public class Node<E extends Comparable<E>> {
    private E value;
    private Node<E> filhoEsq;
    private Node<E> filhoDir;

    Node(E value){
        this.value = value;
    }

    public E getValue() { return value; }
    public void setValue(E value) { this.value = value; }

    public Node<E> getFilhoEsq() { return filhoEsq; }
    public Node<E> getFilhoDir() { return filhoDir; }

    public void setFilhoEsq(Node<E> n) { this.filhoEsq = n; }
    public void setFilhoDir(Node<E> n) { this.filhoDir = n; }
}
