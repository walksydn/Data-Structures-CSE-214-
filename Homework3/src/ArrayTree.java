import com.sun.source.tree.Tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

class ArrayTree<K extends Comparable<K>, V> {
    TreeElement<K, V>[] tree;

    // Sets up an initial array for the tree with 1 slot for the root
    @SuppressWarnings("unchecked")
    public ArrayTree() {
        tree = (TreeElement<K, V>[]) new TreeElement[1];
    }

    public TreeElement<K, V> getLeft(int loc) {
        return locToElement(getLeftLoc(loc));
    }

    public TreeElement<K, V> getRight(int loc) {
        return locToElement(getRightLoc(loc));
    }

    public TreeElement<K, V> getParent(int loc) {
        return locToElement(getParentLoc(loc));
    }

    public int getLeftLoc(int loc) {
        return 2 * loc + 1;
    }

    public int getRightLoc(int loc) {
        return 2 * loc + 2;
    }

    public int getParentLoc(int loc) {
        return (loc - 1) / 2;
    }

    // returns the element stored at a location
    public TreeElement<K, V> locToElement(int loc) {
        if (loc >= tree.length || loc < 0)
            return null;
        else
            return tree[loc];
    }

    // only method that actually adds things to the array
    public void setLoc(int loc, TreeElement<K, V> e) {
        if (loc < 0)
            throw new IndexOutOfBoundsException("Tree locations must be 0 or more");
        // if we're adding something beyond the length of the tree array, then we must
        // grow the array
        if (loc >= tree.length)
            resize();
        tree[loc] = e;
        e.setTreeLocation(loc);
    }

    // takes a location in the tree and a new Element
    // makes that element the left child of the node at loc
    public void setLeft(int loc, TreeElement<K, V> e) {
        setLoc(getLeftLoc(loc), e);
    }

    // takes a location in the tree and a new Element
    // makes that element the right child of the node at loc
    public void setRight(int loc, TreeElement<K, V> e) {
        setLoc(getRightLoc(loc), e);
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        // double the size of the array
        // adds just enough spaces for one more full level of a complete tree
        int new_length = tree.length * 2 + 1;
        // create new array
        TreeElement<K, V>[] temp_tree = (TreeElement<K, V>[]) new TreeElement[new_length];
        // copy contents of old array to new array
        for (int i = 0; i < tree.length; i++) {
            temp_tree[i] = tree[i];
        }
        // set replace old array with the new one
        tree = temp_tree;
    }

    @Override
    public String toString() {
        // returns string representation of tree array which holds nodes in complete
        // order
        // I.e. left to right, top to bottom
        return "ArrayTree " + Arrays.toString(tree);
    }

    /**
     * This method returns the uncle (the parent's sibling) of the node at loc
     *  First: find the grandparent of the node at loc
     *  Second: determine which child of the grandparent is the parent of the node at loc
     *  Third: return the sibling of the parent of the node at loc
     * @param loc location of the node to find the uncle of
     * @return the uncle of the node at loc
     */
    public TreeElement<K, V> getUncle(int loc) {
        //uncleLeft and uncleRight are used to store the two possible nodes that could be
            //uncles of the node at loc
        TreeElement uncleLeft = null, uncleRight = null;
        //makes sure that the grandparent is actually two nodes up the tree (verifies there can be an Uncle)
        if(getParentLoc(getParentLoc(loc)) < getParentLoc(loc)){
            //sets uncleLeft to the left child of the grandparent of the node at loc
            uncleLeft = tree[getLeftLoc(getParentLoc(getParentLoc(loc)))];
            //sets uncleRight to the right child of the grandparent of the node at loc
            uncleRight = tree[getRightLoc(getParentLoc(getParentLoc(loc)))];
        } else { //if there is no grandparent there cannot be an Uncle, so null is returned
            return null;
        }
        //if uncleLeft is the parent of the node at loc return uncleRight, and vice versa
        if(uncleLeft.getKey().compareTo(tree[getParentLoc(loc)].getKey()) == 0){
            return uncleRight;
        } else {
            return uncleLeft;
        }
    }

    /**
     * Checks that the children of all nodes below the node at loc have a key that is less than it
     *  (checks if the subtree of the node at loc is a minHeap)
     * @param loc the location of the root of the subtree to be examined
     * @return true if the subtree of the node at loc is a minHeap, false otherwise
     */
    public boolean isMinHeap(int loc) {
        boolean result = true;
        //makes sure the left and right children of the node at loc are within the limits
            //of the tree array to prevent null pointer exceptions
        if(getRightLoc(loc) < tree.length && getLeftLoc(loc) < tree.length) {
            //if the node at loc has left and right children
            if (tree[getRightLoc(loc)] != null && tree[getLeftLoc(loc)] != null) {
                //if the value of the children are greater than the node at loc, it is not a minHeap
                if (tree[getRightLoc(loc)].getKey().compareTo(tree[loc].getKey()) < 0 || tree[getLeftLoc(loc)].getKey().compareTo(tree[loc].getKey()) < 0) {
                    result = false;
                } else {
                    //if the left child is a minHeap, check the right child
                    if(isMinHeap(getLeftLoc(loc))){
                        //if the right child is not a minHeap, stop this recursive branch
                        if(!isMinHeap(getRightLoc(loc))){
                            return false;
                        }
                    } else { //if the left child is not a minHeap make the result false
                        result = false;
                    }
                }
            } else if (tree[getLeftLoc(loc)] != null) { //node at loc only has a left child
                //if the left child is greater than the node at loc, it is not a minHeap
                if (tree[getLeftLoc(loc)].getKey().compareTo(tree[loc].getKey()) < 0) {
                    result = false;
                } else {
                    // checks the left child to see if it is a minHeap
                    if(!isMinHeap(getLeftLoc(loc))){
                        result = false;
                    }
                }
            } else if (tree[getRightLoc(loc)] != null) { //node at loc only has a right child
                //if the right child is greater than the node at loc, it is not a minHeap
                if (tree[getRightLoc(loc)].getKey().compareTo(tree[loc].getKey()) < 0) {
                    result = false;
                } else {
                    // checks the right child to see if it is a minHeap
                    if(!isMinHeap(getRightLoc(loc))){
                        result = false;
                    }
                }
            }
        }
        return result;
    }
}