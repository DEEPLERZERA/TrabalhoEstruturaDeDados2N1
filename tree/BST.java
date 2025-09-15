package tree;

import java.util.function.Consumer;

public interface BST<T extends Comparable<T>> {
    boolean insert(T value);
    boolean remove(T value);
    T search(T probe);
    void inOrder(Consumer<T> action);
    void preOrder(Consumer<T> action);
    void postOrder(Consumer<T> action);
    int size();
    boolean isEmpty();
}
