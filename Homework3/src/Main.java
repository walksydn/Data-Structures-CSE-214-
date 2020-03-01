public class Main {

    public static void main(String[] args) throws Exception {

        // setup a tree for testing
        RedBlackNode<Integer, String> root = new RedBlackNode<Integer, String>(5, "A");
        root.setBlack();
        root.setLeft(new RedBlackNode<Integer, String>(3, "B", "black"));
        root.setRight(new RedBlackNode<Integer, String>(6, "C", "black"));
        root.getLeft().setRight(new RedBlackNode<Integer, String>(4, "D"));

        System.out.println(root.printSubTree());
        // TreeNode [red=false, key=5, value=A]
        // ├──left| TreeNode [red=false, key=3, value=B]
        // │   └──right| TreeNode [red=true, key=4, value=D]
        // └──right| TreeNode [red=false, key=6, value=C]

        System.out.println(root.getLeft().getRight().getUncle());
        //TreeNode [red=false, key=6, value=C]

        System.out.println(root.getLeft().getRight().nextInOrder());
        //TreeNode [red=false, key=5, value=A]

        System.out.println(root.isRedBlackTree());
        //true

        System.out.println(root.isBST());
        //true

        // setup a tree for testing
        ArrayTree<Integer, String> tree = new ArrayTree<Integer, String>();
        tree.setLoc(0, new TreeElement<Integer, String>(3, "A"));
        tree.setLeft(0, new TreeElement<Integer, String>(5, "B"));
        tree.setRight(0, new TreeElement<Integer, String>(7, "C"));
        tree.setRight(tree.getLeftLoc(0), new TreeElement<Integer, String>(6, "D"));

        System.out.println(tree);
        //ArrayTree [TreeElement [key=3, treeLocation=0, value=A], TreeElement [key=5, treeLocation=1, value=B], TreeElement [key=7, treeLocation=2, value=C], null, TreeElement [key=6, treeLocation=4, value=D], null, null]

        System.out.println(tree.getUncle(4));
        //TreeElement [key=7, treeLocation=2, value=C]

        System.out.println(tree.isMinHeap(0));
        //true





    }
}