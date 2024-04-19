import java.util.List;
import java.util.LinkedList;

/**
 * Your implementation of the pre-order, in-order, and post-order
 * traversals of a tree.
 */
public class Traversals<T extends Comparable<? super T>> {

    /**
     * DO NOT ADD ANY GLOBAL VARIABLES!
     */

    /**
     * Given the root of a binary search tree, generate a
     * pre-order traversal of the tree. The original tree
     * should not be modified in any way.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the pre-order traversal of the tree.
     */
    public List<T> preorder(TreeNode<T> root) {
        LinkedList<T> traversal = new LinkedList<>();
        return preorderRecursion(traversal, root);
    }

    private LinkedList<T> preorderRecursion(LinkedList<T> traversal, TreeNode<T> node) {
        if (node == null) {
            return new LinkedList<>();
        }
        traversal.add(node.getData());
        preorderRecursion(traversal, node.getLeft());
        preorderRecursion(traversal, node.getRight());
        return traversal;
    }

    /**
     * Given the root of a binary search tree, generate an
     * in-order traversal of the tree. The original tree
     * should not be modified in any way.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the in-order traversal of the tree.
     */
    public List<T> inorder(TreeNode<T> root) {
        LinkedList<T> traversal = new LinkedList<>();
        return inorderRecursion(traversal, root);
    }

    private LinkedList<T> inorderRecursion(LinkedList<T> traversal, TreeNode<T> node) {
        if (node == null) {
            return new LinkedList<>();
        }
        inorderRecursion(traversal, node.getLeft());
        traversal.add(node.getData());
        inorderRecursion(traversal, node.getRight());
        return traversal;
    }

    /**
     * Given the root of a binary search tree, generate a
     * post-order traversal of the tree. The original tree
     * should not be modified in any way.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the post-order traversal of the tree.
     */
    public List<T> postorder(TreeNode<T> root) {
        LinkedList<T> traversal = new LinkedList<>();
        return postorderRecursion(traversal, root);
    }

    private LinkedList<T> postorderRecursion(LinkedList<T> traversal, TreeNode<T> node) {
        if (node == null) {
            return new LinkedList<>();
        }
        postorderRecursion(traversal, node.getLeft());
        postorderRecursion(traversal, node.getRight());
        traversal.add(node.getData());
        return traversal;
    }
}
