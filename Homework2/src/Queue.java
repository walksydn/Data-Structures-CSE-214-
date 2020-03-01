import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Queue<T> {

    protected LinkedList<T> list;

    /**
     * Constructor for the Queue class
     * Initializes the LinkedList used for the queue
     */
    public Queue() {
        list = new LinkedList<>();
    }

    /**
     * Adds an element to the top of the queue
     * @param element the element to be added
     */
    public void enqueue(T element) {
        list.add(element);
    }

    /**
     * Removes the element at the bottom of the queue
     * @return the element being removed
     * @throws NoSuchElementException thrown when the queue is empty and there is no element to remove
     */
    public T dequeue() throws NoSuchElementException {
        if(isEmpty()){
            throw new NoSuchElementException();
        } else {
            return list.removeFirst();
        }
    }

    /**
     * Checks if the queue is empty
     * @return true if the queue is empty
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Looks at the element at the bottom of the queue without removing it
     * @return the element at the top of the queue
     * @throws NoSuchElementException thrown when the queue is empty and there is no element to peek at
     */
    public T peek() {
        if(isEmpty()){
            throw new NoSuchElementException();
        } else {
            return list.getFirst();
        }
    }

    /**
     * Gets the length of the queue
     * @return the number of elements in the queue
     */
    public int length() {
        return list.size();
    }

}