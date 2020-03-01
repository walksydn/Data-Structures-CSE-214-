import java.util.NoSuchElementException;
import java.util.concurrent.TransferQueue;

public class DoublyLinkedList<T extends Comparable<T>> implements LinkedList<T> {

    // proper formatting is important for automated testing of your code
    // Format example:
    // head=y tail=a\ny->z->a->null\na->z->y->null
    // When printed:
    // head=y tail=a
    // y->z->a->null
    // a->z->y->null


    private Node<T> head, tail, cursor = null;

    /**
     * Creates a string representation of the doubly linked list
     *
     * @return: string representation of doubly linked list
     */
    public String toString() {
        String s = "";

        s += "head=" + (head == null ? "null" : head.getData()) + " ";
        s += "tail=" + (tail == null ? "null" : tail.getData());
        s += "\n";
        Node<T> nodePtr = head;
        while (nodePtr != null) {
            s += nodePtr.getData() + "->";
            nodePtr = nodePtr.getNext();
        }
        s += "null";
        s += "\n";
        nodePtr = tail;
        while (nodePtr != null) {
            s += nodePtr.getData() + "->";
            nodePtr = nodePtr.getPrev();
        }
        s += "null";
        return s;
    }

    /**
     * Returns the head of the doubly linked list
     *
     * @return: the node at the beginning of the list
     */
    @Override
    public Node<T> getHead() {
        return head;
    }

    /**
     * Returns the tail of the doubly linked list
     *
     * @return: the node at the end of the list
     */
    @Override
    public Node<T> getTail() {
        return tail;
    }

    /**
     * Changes the head of the doubly linked list - replacing the current head
     *
     * @param newHead the new node to replace the current head
     */
    @Override
    public void setHead(Node<T> newHead) {
        if (newHead != null) { //prevents NullPointerException
            if (head != null) {
                removeHead(); //removes current head
            }
            addHead(newHead.getData()); //adds newHead in old head's place
        } else { //eliminates head when set to null
            head = null;
        }
    }

    /**
     * Changes the tail of the doubly linked list - replacing the current tail
     *
     * @param newTail the new node to replace the current tail
     */
    @Override
    public void setTail(Node<T> newTail) {
        if (newTail != null) { //prevents NullPointerException
            if (tail != null) {
                removeTail(); //removes current tail
            }
            addTail(newTail.getData()); //adds newTail in old tail's place
        } else { //eliminates tail when set to null
            tail = null;
        }
    }


    /**
     * Puts a new head onto the doubly linked list through making a temp variable to store the previous head
     *
     * @param element: the data within the new head
     */
    @Override
    public void addHead(T element) {
        Node<T> newHead = new Node<>(element);
        if (head != null) { //prevents NullPointerException by checking if there are other elements in the list
            head.setPrev(newHead);
        }
        newHead.setNext(head);
        head = newHead;
        if (tail == null) { //ensures tail is set to head if there are no other elements in the list
            tail = head;
        }
    }

    /**
     * Puts a new tail onto the doubly linked list through making a temp variable to store the previous tail
     *
     * @param element: the data within the new tail
     */
    @Override
    public void addTail(T element) {
        Node<T> newTail = new Node<>(element);
        newTail.setPrev(tail);
        if (tail != null) { //prevents NullPointerException by checking if there are more elements in the list
            tail.setNext(newTail);
        }
        tail = newTail;
        if (head == null) { //ensure the head is set to the added element if there are no other elements in the list
            head = tail;
        }
    }

    /**
     * Takes the head of the list off the linked list
     *
     * @return the data within the removed head
     * @throws NoSuchElementException thrown when there is no head to remove
     */
    @Override
    public T removeHead() throws NoSuchElementException {
        if (head != null) {
            T removed = head.getData();
            if (head.getNext() != null) {  //prevents NullPointerException if there is only one element in the list
                head.getNext().setPrev(null);
            } else { //sets the previous to null if there is only one element in the list
                head.setNext(null);
            }
            head = head.getNext();
            if (head == null) {//sets tail to null if the list is now empty
                tail = null;
            }
            cursor = head;
            return removed;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Takes the tail of the list off the linked list
     *
     * @return the data within the removed tail
     * @throws NoSuchElementException thrown when there is no tail to remove
     */
    @Override
    public T removeTail() throws NoSuchElementException {
        if (tail != null) {
            T removed = tail.getData();
            if (tail.getPrev() != null) { //prevents NullPointerException if there's only one element in the list
                tail.getPrev().setNext(null);
            } else { //sets the previous to null if removing last element in the list
                head.setPrev(null);
            }
            tail = tail.getPrev();
            if (tail == null) { //sets head to null if there are no elements in the list
                head = null;
            }
            cursor = head;
            return removed;
        }
        throw new NoSuchElementException();
    }


    /**
     * Finds the node at the specified index
     * Note: indexes are zero based
     *
     * @param index: the index of the node desired
     * @return the node at the desired index
     * @throws NoSuchElementException thrown when there is no node at the desired index
     */
    @Override
    public Node<T> get(int index) throws NoSuchElementException {
        if (index < 0) { //index cannot be less than 0
            throw new NoSuchElementException();
        }
        Node<T> result, traverse; //result = returned Node, traverse = node used to traverse the list
        traverse = head;
        for (int i = 0; i < index; i++) {
            if (traverse.getNext() != null) { //prevents NullPointerExceptions
                traverse = traverse.getNext();
            } else { //throws exception if there is no element within the specified index
                throw new NoSuchElementException();
            }
        }
        result = traverse;
        return result;
    }

    /**
     * Adds a new node to the doubly linked list at the desired index
     * Note: indexes are zero based
     *
     * @param index   the desired index
     * @param element the element to be stored in the added node
     * @throws NoSuchElementException thrown when there is no such index to add a node to
     */
    @Override
    public void add(int index, T element) throws NoSuchElementException {
        if (index < 0) { //index cannot be less than 0
            throw new NoSuchElementException();
        }
        Node<T> traverse; //node used to traverse the linked list
        if (index == 0) { //adds element as the head if desired index is 0
            addHead(element);
        } else {
            Node<T> added = new Node(element); //the node being added to the list
            Node<T> before, after; //the nodes before and after the node being added
            traverse = head;
            for (int i = 0; i < index - 1; i++) { //traverses to the node before the desired addition
                if (traverse != null) {
                    traverse = traverse.getNext();
                } else { //throws exception if the index is out of ranged of the linked list
                    throw new NoSuchElementException();
                }
            }
            before = traverse;
            after = traverse.getNext();
            if (after == null) { //checks if the new element is the new tail
                addTail(element);
            } else { //sets up all four linkages for the doubly linked list
                before.setNext(added);
                after.setPrev(added);
                added.setPrev(before);
                added.setNext(after);
            }
        }
    }

    /**
     * Removes the node at the specified index
     * Note: indexes are zero based
     *
     * @param index the index of the node to be removed
     * @return the data stored within the removed node
     * @throws NoSuchElementException thrown when there is no node at the specified index
     */
    @Override
    public T remove(int index) throws NoSuchElementException {
        if (index < 0) { //index cannot be less than 0
            throw new NoSuchElementException();
        }
        Node<T> before, after, removed; //before = node before one removed, after = node after one removed, removed = node removed
        Node<T> traverse; //the node used to travese the linked list
        if (index == 0) { //removing index 0 is the same as removing the head
            removed = head;
            removeHead();
        } else {
            traverse = head;
            for (int i = 0; i < index; i++) {
                if (traverse.getNext() != null) { //prevents null pointer exception
                    traverse = traverse.getNext();
                } else { //throws exception when there is no index matching the one requested
                    throw new NoSuchElementException();
                }
            }
            before = traverse.getPrev();
            removed = traverse;
            after = traverse.getNext();
            if (after == null) { //checks if the removed node is the tail
                removeTail();
            } else { //sets necessary linkages to prevent a break in the linked list
                before.setNext(after);
                after.setPrev(before);
            }
        }
        return removed.getData();
    }

    /**
     * Clones the linked list into a new linked list
     *
     * @return the new, cloned linked list
     */
    @Override
    public LinkedList<T> clone() {
        DoublyLinkedList<T> clone = new DoublyLinkedList<>(); //the cloned list
        Node<T> traverse = head; //node used to traverse the list
        for (int i = 0; traverse != null; i++) { //adds the nodes in order from start to finish to the cloned list
            clone.addTail(traverse.getData());
            traverse = traverse.getNext();
        }
        return clone;
    }

    /**
     * Adds the entered linked list to the end of the current linked list
     *
     * @param list the list to be added to the end
     */
    @Override
    public void concatenate(LinkedList<T> list) {
        if (list.getHead() != null) {
            tail.setNext(list.getHead()); //sets the tail of the old list to point to the head of the new list
            list.getHead().setPrev(tail); //sets the head of the new list to point to the tail of the old list
            tail = list.getTail(); //sets the tail of the old list to the tail of the new list
            list.setHead(null); //takes the new head node off of the merged list
            list.setTail(null); //takes the new tail node off of the merged list
        }
    }

    /**
     * Removes all values greater than the target
     *
     * @param target the cutoff value for the list
     */
    @Override
    public void filter(T target) {
        Node<T> traverse = head; //node used to traverse the linked list
        Node<T> before = null, after = null;
        int i = 0;
        boolean iRemoved = false; //has the current index been removed?
        while (traverse != null) { //loop stops after all elements have analyzed
            iRemoved = false;
            if (traverse.getData().compareTo(target) > 0) { //if the value at index i is greater than the target, it is removed
                if (traverse.getNext() == null) { //checks if tail is being removed
                    removeTail();
                    iRemoved = true;
                } else {
                    if (i == 0) { //checks if it must be placed as the new head
                        removeHead();
                    } else { //removes the node traverse is pointing to
                        before = traverse.getPrev();
                        after = traverse.getNext();
                        before.setNext(after);
                        after.setPrev(before);
                    }
                    iRemoved = true;
                }
            }
            traverse = traverse.getNext();
            if (!iRemoved) { //increment i when an element is not removed (otherwise the next element moves into the index just removed)
                i++;
            }
        }
    }


    /**
     * Adds a node with the desired element to the list in order
     * Note: the list must be sorted in ascending order before this method is run
     *
     * @param element the element to be added
     */
    @Override
    public void sortedAdd(T element) {
        Node<T> traverse = head; //node used to traverse the linked list
        Node<T> before = null, after = null, adding = null;
        boolean addedElement = false; //records if the element has been added yet
        if (traverse == null) { //if element is the first in the list, adds it as the head
            addHead(element);
            addedElement = true;
        } else {
            int i = 0;
            while (traverse != null) { //goes through entire list until the element is added
                if (traverse.getData().compareTo(element) >= 0) { //if the data is >= to the element being added, add it in index i
                    if (i == 0) {
                        addHead(element);
                    } else {
                        adding = new Node<T>(element);
                        before = traverse.getPrev();
                        after = traverse;
                        if (after == null) { //checks if the new element is the new tail
                            addTail(element);
                        } else { //sets up all four linkages for the doubly linked list
                            before.setNext(adding);
                            after.setPrev(adding);
                            adding.setPrev(before);
                            adding.setNext(after);
                        }
                    }
                    addedElement = true;
                    break;
                }
                traverse = traverse.getNext();
                i++;
            }
        }
        if (!addedElement) {
            addTail(element);
        }
    }


    /**
     * Sorts the linked list into ascending order
     *
     * @return the sorted list
     */
    @Override
    public LinkedList<T> sort() {
        DoublyLinkedList<T> sorted = new DoublyLinkedList<>(); //sorted list to be returned
        Node<T> traverse = head; //node used to traverse the list
        for (int i = 0; traverse != null; i++) { //goes through entire list, adding in a sorted manner to the result
            sorted.sortedAdd(traverse.getData());
            traverse = traverse.getNext();
        }
        return sorted;
    }

    /**
     * Removes all duplicated values from the list
     * Note: the list must be sorted into ascending order before this method is run
     */
    @Override
    public void removeDuplicates() {
        Node<T> traverse = head; //node used to traverse the list
        Node<T> before = null, after = null;
        int i = 0;
        boolean elementRemoved = false; //whether or not the element at index i has been removed
        while (traverse.getNext() != null) { //stops at the end of the list
            if (traverse.getData().compareTo((T) traverse.getNext().getData()) == 0) { //if the current element is equal to the next element, remove it
                if (i == 0) { //checks if it must be placed as the new head
                    removeHead();
                } else { //removes the node traverse is pointing to
                    before = traverse.getPrev();
                    after = traverse.getNext();
                    before.setNext(after);
                    after.setPrev(before);
                }
                elementRemoved = true;
            }
            traverse = traverse.getNext();
            if (!elementRemoved) { //only increment when element is not removed because list changes indexes to replace one removed
                i++;
            }
            elementRemoved = false;
        }
    }

    /**
     * Merges two linked lists one list of ascending order
     * Note: both linked lists must be in ascending order before this method is run
     *
     * @param list the list to be merged with the one saved
     * @return the merged list
     */
    @Override
    public LinkedList<T> merge(LinkedList<T> list) {
        DoublyLinkedList<T> mergedList = new DoublyLinkedList<>(); //resultant of the merge
        Node<T> traverseA, traverseB; //node used to traverse each list (A and B)
        traverseA = head;
        traverseB = list.getHead();
        while (traverseA != null || traverseB != null) { //if either list still has elements
            if (traverseA == null) { //if only list B has elements
                while (traverseB != null) { //add all of list B to the end of the merged list
                    mergedList.addTail(traverseB.getData());
                    traverseB = traverseB.getNext();
                }
            } else if (traverseB == null) { //if only list A has elements
                while (traverseA != null) { //add all of list A to the end of the merged list
                    mergedList.addTail(traverseA.getData());
                    traverseA = traverseA.getNext();
                }
            } else { //if lists A and B still have elements
                if (traverseA.getData().compareTo(traverseB.getData()) <= 0) {  //if A is smaller or equal to B, add traverseA to the end of the list
                    mergedList.addTail(traverseA.getData());
                    traverseA = traverseA.getNext();
                } else { //if B is smaller than A, add traverseB to the end of the list
                    mergedList.addTail(traverseB.getData());
                    traverseB = traverseB.getNext();
                }
            }
        }
        return mergedList;
    }
}