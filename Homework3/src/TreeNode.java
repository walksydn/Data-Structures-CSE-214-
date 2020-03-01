import java.util.ArrayList;

public class TreeNode<K extends Comparable<K>, V> {
    
    // pointers shared with subclasses
    protected TreeNode<K, V> left;
    protected TreeNode<K, V> right;
    protected TreeNode<K, V> parent;
    private K key;
    private V value;

    public TreeNode(K key, V value) {
        this.key = key;
        this.value = value;
        left = null;
        right = null;
        parent = null;
    }

    public TreeNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<K, V> node) {
        node.setParent(this);
        this.left = node;
    }

    public TreeNode<K, V> getRight() {
        return right;
    }

    public void setRight(TreeNode<K, V> node) {
        node.setParent(this);
        this.right = node;
    }

    public TreeNode<K, V> getParent() {
        return parent;
    }

    public void setParent(TreeNode<K, V> parent) {
        this.parent = parent;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public String printSubTree() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(this.toString());
        buffer.append('\n');

        if (left != null) {
            if (right != null) {
                left.print(buffer, childrenPrefix + "├──left| ", childrenPrefix + "│   ");
            } else {
                left.print(buffer, childrenPrefix + "└──left| ", childrenPrefix + "    ");
            }
        }
        if (right != null) {
            right.print(buffer, childrenPrefix + "└──right| ", childrenPrefix + "    ");
        }
    }

    @Override
    public String toString() {
        return "TreeNode [key=" + key + ", value=" + value + "]";
    }

    /**
     * This method returns the uncle (the parent's sibling) of this node
     *  First: find the grandparent of this node
     *  Second: determine which child of the grandparent is the parent of this node
     *  Third: return the sibling of the parent of this node
     * @return the uncle of this node
     */
    public TreeNode<K, V> getUncle() {
        //uncleLeft and uncleRight are used to store the two possible nodes that could be
        //uncles of the node at loc
        TreeNode uncleLeft = null, uncleRight = null;
        try { //attempts to find the grandparent and both of its children
            uncleLeft = parent.getParent().getLeft();
            uncleRight = parent.getParent().getRight();
        } catch (NullPointerException e){ //if there is no grandparent there cannot be an uncle
            return null;
        }
        //if uncleLeft is the parent of the node at loc return uncleRight, and vice versa
        if(uncleLeft.getKey().compareTo(parent.getKey()) == 0){
            return uncleRight;
        } else {
            return uncleLeft;
        }
    }

    /**
     * Finds the node that follows this node in a in-order traversal of the tree:
     *  If it has a right child, return the leftmost child of the right subtree
     *  If it does not have a right child:
     *      Go up the subtree by parents until the node is a left child of its parent
     *      If going up the tree reaches the root, then it is the last node in the traversal,
     *          so null is returned
     * @return the next node in an in-order traversal of the tree (null if it is called on the rightmost node)
     */
    public TreeNode<K, V> nextInOrder() {
        //solution starts out as the node nextInOrder is called on
        TreeNode solution = this;
        //if it has a right child
        if (right != null) {
            solution = right;
            //find the leftmost child of the right subtree
            while (solution.getLeft() != null){
                solution = solution.getLeft();
            }
            return solution;
        } else {
            //if the tree is only one node, return null
            if(solution.getParent() == null){
                return null;
            }
            //go up the tree until the node is a left child
            while(solution.getKey().compareTo(solution.getParent().getRight().getKey()) == 0){
                solution = solution.getParent();
                //if solution reaches the root, return null
                if(solution.getParent() == null){
                    return null;
                }
            }
            return solution.getParent();
        }
    }

    /**
     * Checks if the subtree of this node is a binary search tree (BST):
     *  the left children must all be less than their parents
     *  the right children must all be greater than their parents
     * @return true if the subtree is a binary search tree
     */
    public boolean isBST() {
        //creates ArrayList to store the inOrder traversal of the array
        ArrayList<TreeNode> inOrder = new ArrayList<>();
        //returns the value of the helper method that checks the properties of a BST
        return isBST(inOrder, this);
    }

    private boolean isBST(ArrayList<TreeNode> list, TreeNode n){
        //if the end of the branch has not been reached
        if (n != null){
            //this recursion stores the values of the subtree in an ArrayList in an in-order traversal
            //check the left branch
            isBST(list, n.getLeft());
            //once the leftmost value is reached, add it to the list
            list.add(n);
            //check the right branch
            isBST(list, n.getRight());
        }
        //checks to make sure that the in-order traversal results in a sorted ArrayList,
        //  indicating the subtree is a BST
        for (int i = 0; i < list.size()-1; i++){
            //returns false if any values are out of order
            if(list.get(i).getKey().compareTo(list.get(i+1).getKey()) > 0){
                return false;
            }
        }
        return true;
    }
}