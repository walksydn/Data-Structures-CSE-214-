public interface LinkedList<T extends Comparable<T>> {

    Node<T> getHead();

    Node<T> getTail();

    void setHead(Node<T> head);

    void setTail(Node<T> tail);

    void addHead(T element);

    void addTail(T element);

    T removeHead() throws java.util.NoSuchElementException;

    T removeTail() throws java.util.NoSuchElementException;

    Node<T> get(int index) throws java.util.NoSuchElementException;

    void add(int index, T element) throws java.util.NoSuchElementException;

    T remove(int index) throws java.util.NoSuchElementException;

    LinkedList<T> clone();

    void concatenate(LinkedList<T> list);

    void filter(T target);

    void sortedAdd(T element);

    LinkedList<T> sort();

    void removeDuplicates();

    LinkedList<T> merge(LinkedList<T> list);
}