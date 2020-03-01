
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Queue<T> {
    protected LinkedList<T> list;
    protected int length;

    public Queue() {
        list = new LinkedList<T>();
        length = 0;
    }

    public void enqueue(T element) {
        // add to end of the linked list and increment the length
        list.addLast(element);
        length++;
    }

    public T dequeue() throws NoSuchElementException {
        // remove from front of linked list
        T ret = list.removeFirst();
        // decrement length after so that this doesn't happen if removeFirst throws an
        // exception
        length--;
        // return the value removed
        return ret;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public T peek() throws NoSuchElementException {
        return list.getFirst();
    }

    public int length() {
        return length;
    }
}