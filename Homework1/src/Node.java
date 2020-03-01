import java.util.prefs.NodeChangeListener;

public class Node<T> {

    private T data;
    private Node prev, next;

    /**
     * The constructor for the general type Node class sets the initial data for the created node and the link to null
     * @param initialData: the data to be contained within the node upon its creation
     */
    public Node(T initialData){
        data = initialData;
        prev = null;
        next = null;
    }

    /**
     * Gives the user the data stored within the node
     * @return: the data within the node
     */
    public T getData(){
        return data;
    }

    /**
     * Gives the user the next link within the node
     * @return: the next link within the node
     */
    public Node getNext(){
        return next;
    }

    /**
     * Gives the user the previous link of the node
     * @return: the previous link within the node
     */
    public Node getPrev(){
        return prev;
    }


    /**
     * Changes the data stored within the node
     * @param newData: the new data to be stored
     */
    public void setData(T newData){
        data = newData;
    }

    /**
     * Changes the next link of the node
     * @param newNext: the new link for the next node
     */
    public void setNext(Node newNext){
        next = newNext;
    }

    /**
     * Changes the previous link of the node
     * @param newPrev: the new link for the previous node
     */
    public void setPrev(Node newPrev){
        prev = newPrev;
    }


}