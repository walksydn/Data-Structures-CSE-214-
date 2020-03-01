import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MinStack<T extends Comparable<T>> extends Stack<T> {

    LinkedList<T> minStack;

    /**
     * Constructor for MinStack.java
     *  Initializes the stack used to store the minimum values (minStack)
     */
    public MinStack() {
        minStack = new LinkedList<>();
    }

    /**
     * Adds an element to the main list
     * Adds the corresponding minimum (either the new element or
     *  the previous minimum) to the stack of minimums
     * @param element the element to be added
     */
    @Override
    public void push(T element) {
        list.add(element); //adds element to the main list
        if(minStack.isEmpty()) { //if the minimum stack is empty, add the element
            minStack.add(element);
        } else if (element.compareTo(minStack.peekLast()) > 0){ //compares new element and previous minimum
            // and adds the lesser to the minStack
            minStack.add(minStack.peekLast());
        } else {
            minStack.add(element);
        }
    }

    /**
     * Removes the element at the top of the stack
     * @return the value of the element at the top of the stack
     * @throws NoSuchElementException thrown when there are no elements in the stack to pop
     */
    @Override
    public T pop() throws NoSuchElementException{
        if(isEmpty()){
            throw new NoSuchElementException();
        } else {
            minStack.removeLast();
            return list.removeLast();
        }
    }

    /**
     * Peeks at the top of the minimum stack
     * @return the current minimum of the stack
     * @throws NoSuchElementException thrown if there are no elements in the stack
     */
    public T getMin() throws NoSuchElementException {
        if(minStack.isEmpty()){
            throw new NoSuchElementException();
        } else {
            return minStack.peekLast();
        }
    }
}