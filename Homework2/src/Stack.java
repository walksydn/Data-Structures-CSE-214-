import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Stack<T> {
    protected LinkedList<T> list;

    /**
     * Constructor for the Stack class
     * Initializes the LinkedList used for the stack
     */
    public Stack() {
        list = new LinkedList<>();
    }

    /**
     * Adds an element to the top of the stack
     * @param element the element to be added
     */
    public void push(T element) {
        list.add(element);
    }

    /**
     * Removes the element at the top of the stack
     * @return the element being popped
     * @throws NoSuchElementException thrown when the stack is empty and there is no element to pop
     */
    public T pop() throws NoSuchElementException{
        if(isEmpty()){
            throw new NoSuchElementException();
        } else {
            return list.removeLast();
        }
    }

    /**
     * Looks at the element at the top of the stack without removing it
     * @return the element at the top of the stack
     * @throws NoSuchElementException thrown when the stack is empty and there is no element to peek at
     */
    public T peek() throws NoSuchElementException {
        if(isEmpty()){
            throw new NoSuchElementException();
        } else {
            return list.getLast();
        }
    }

    /**
     * Checks if the stack is empty
     * @return true if the stack is empty
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Gets the length of the stack
     * @return the number of elements in the stack
     */
    public int length() {
        return list.size();
    }

}