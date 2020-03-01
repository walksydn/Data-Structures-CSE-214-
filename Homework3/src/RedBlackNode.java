import java.security.InvalidParameterException;
import java.util.ArrayList;

public class RedBlackNode<K extends Comparable<K>, V> extends TreeNode<K, V> {
    // red node if this is true, black if false
    private boolean red;

    // default to red
    public RedBlackNode(K key, V value) {
        super(key, value);
        red = true;
    }

    // can take a string to set color
    // this makes things a little easier when testing
    public RedBlackNode(K key, V value, String color) {
        super(key, value);
        if (color.equalsIgnoreCase("black"))
            setBlack();
        else if (color.equalsIgnoreCase("red"))
            setRed();
        else
            throw new InvalidParameterException("color must be red or black");
    }

    public boolean isRed() {
        return red;
    }

    public boolean isBlack() {
        return !red;
    }

    public void setRed() {
        this.red = true;
    }

    public void setBlack() {
        this.red = false;
    }

    public RedBlackNode<K, V> getLeft() {
        return (RedBlackNode<K, V>) left;
    }

    public RedBlackNode<K, V> getRight() {
        return (RedBlackNode<K, V>) right;
    }

    public RedBlackNode<K, V> getParent() {
        return (RedBlackNode<K, V>) parent;
    }

    @Override
    public String toString() {
        return "TreeNode [red=" + red + ", " + "key=" + getKey() + ", value=" + getValue() + "]";
    }

    /**
     * Checks whether the subtree of this node meets the last three conditions of a red-black tree:
     *  The root is black
     *  No red node has a red parent
     *  There is the same number of black nodes from the root to any null child (children of leaves)
     * @return true if the subtree of this node is a red-black tree, false otherwise
     */
    public Boolean isRedBlackTree() {
        //checks if the root is red and returns false if it is
        if(red){
            return false;
        } else {
            //ArrayList for the recursive helper method to use
            ArrayList<Integer> numBlacks = new ArrayList<>();
            //the final result of the recursive helper method
            ArrayList<Integer> results = isRedBlackTree(this, numBlacks, 0);
            //if the only two nodes are red, return false (because a red node cannot have a red parent)
            if(results.size() == 1 && results.get(0) == -1){
                return false;
            }
            //cycles through the array with the number of black nodes on each path to a null (child of a leaf)
            // returns false if there is a -1 (a red child with a red parent), or if the number of blacks
            //  is not equal, returns true otherwise
            for (int i = 0; i < results.size()-1; i++){
                if(results.get(i) == -1 || results.get(i) != results.get(i+1)){
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Checks the last two properties of a red-black tree using recursion:
     *  No red node has a red parent
     *  There is the same number of black nodes from the root to any null child (children of leaves)
     * @param n the node being checked for red-black tree properties
     * @param numBlacks ArrayList where the number of black nodes along each path to a null are stored
     * @param numB the number of blacks in the path being check so far
     * @return numBlacks - the ArrayList storing the number of black nodes along each path to the root of the subtree
     */
    private ArrayList<Integer> isRedBlackTree(RedBlackNode n, ArrayList numBlacks, int numB) {
        //checks if n has a parent to prevent null pointer exceptions
        if(n.getParent() != null){
            //checks if n and its parent are red, adds a -1 and stops recursion along this path if so
            if(n.isRed() && n.getParent().isRed()){
                numBlacks.add(-1);
                return numBlacks;
            }
        }
        //if n does not have a left child
        if(n.getLeft() == null){
            //add the number of blacks on this path to numBlacks
            if(n.isBlack()){
                numBlacks.add(numB+1);
            } else {
                numBlacks.add(numB);
            }
        } else { //if n has a left child
            //checks the subtree of the left child to see if it is a red-black tree
            if(n.isBlack()){
                isRedBlackTree(n.getLeft(), numBlacks, numB+1);
            } else {
                isRedBlackTree(n.getLeft(), numBlacks, numB);
            }
        }
        //if n does not have a right child
        if(n.getRight() == null){
            //add the number of blacks on this path to numBlacks
            if(n.isBlack()){
                numBlacks.add(numB+1);
            } else {
                numBlacks.add(numB);
            }
        } else { //if n has a right child
            //checks the subtree of the right child to see if it is a red-black tree
            if(n.isBlack()){
                isRedBlackTree(n.getRight(), numBlacks, numB+1);
            } else {
                isRedBlackTree(n.getRight(), numBlacks, numB);
            }
        }
        //returns the ArrayL storing the number of black nodes along each path to the root of the subtree
        return numBlacks;
    }
}