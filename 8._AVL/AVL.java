import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 */
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary. This is as simple as calling the balance() method on the
     * current node, before returning it (assuming that your balance method
     * is written correctly from part 1 of this assignment).
     *
     * @param data The data to add.
     * @throws java.lang.IllegalArgumentException If data is null.
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        this.root = recursiveAdd(this.root, data);
    }

    private AVLNode<T> recursiveAdd(AVLNode<T> node, T data) {
        if (node == null) {
            this.size++;
            return new AVLNode<T>(data);
        }
        if (node.getData().compareTo(data) < 0) {
            node.setRight(recursiveAdd(node.getRight(), data));
        }
        else if (node.getData().compareTo(data) > 0) {
            node.setLeft(recursiveAdd(node.getLeft(), data));
        }
        else {
            // Duplicate case
            ;
        }
        updateHeightAndBF(node);
        return balance(node);
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     *    simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     *    replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     *    replace the data, NOT predecessor. As a reminder, rotations can occur
     *    after removing the successor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary. This is as simple as calling the balance() method on the
     * current node, before returning it (assuming that your balance method
     * is written correctly from part 1 of this assignment).
     *
     * Do NOT return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data The data to remove.
     * @return The data that was removed.
     * @throws java.lang.IllegalArgumentException If the data is null.
     * @throws java.util.NoSuchElementException   If the data is not found.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        AVLNode<T> dummyNode = new AVLNode<>(null);
        this.root = recursiveRemove(this.root, dummyNode, data);
        return dummyNode.getData();
    }

    private AVLNode<T> recursiveRemove(AVLNode<T> node, AVLNode<T> dummyNode, T data) {
        if (node == null) {
            throw new NoSuchElementException();
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(recursiveRemove(node.getLeft(), dummyNode, data));
        }
        else if ((data.compareTo(node.getData()) > 0)) {
            node.setRight(recursiveRemove(node.getRight(), dummyNode, data));
        }
        else {
            // Data found
            this.size--;
            dummyNode.setData(node.getData());
            // No children case
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }
            // One child case
            if (node.getLeft() != null && node.getRight() == null) {
                return node.getLeft();
            }
            if (node.getLeft() == null && node.getRight() != null) {
                return node.getRight();
            }
            // Two children case
            AVLNode<T> dummyNode2 = new AVLNode<T>(null);
            node.setRight(removeSuccessor(node.getRight(), dummyNode2));
            node.setData(dummyNode2.getData());
        }
        updateHeightAndBF(node);
        return balance(node);
    }

    private AVLNode<T> removeSuccessor(AVLNode<T> node, AVLNode<T> dummyNode) {
        if (node.getLeft() == null) {
            dummyNode.setData(node.getData());
            return node.getRight();
        }
        node.setLeft(removeSuccessor(node.getLeft(), dummyNode));
        updateHeightAndBF(node);
        return balance(node); 
    }

    /**
     * Updates the height and balance factor of a node using its
     * setter methods.
     *
     * Recall that a null node has a height of -1. If a node is not
     * null, then the height of that node will be its height instance
     * data. The height of a node is the max of its left child's height
     * and right child's height, plus one.
     *
     * The balance factor of a node is the height of its left child
     * minus the height of its right child.
     *
     * This method should run in O(1).
     * You may assume that the passed in node is not null.
     *
     * This method should only be called in rotateLeft(), rotateRight(),
     * and balance().
     *
     * @param currentNode The node to update the height and balance factor of.
     */
    private void updateHeightAndBF(AVLNode<T> currentNode) {
        AVLNode<T> leftChild = currentNode.getLeft();
        AVLNode<T> rightChild = currentNode.getRight();
        int leftChildHeight;
        int rightChildHeight;
        if (leftChild == null) {
            leftChildHeight = -1;
        }
        else {
            leftChildHeight = leftChild.getHeight();
        }
        if (rightChild == null) {
            rightChildHeight = -1;
        }
        else {
            rightChildHeight = rightChild.getHeight();
        }
        currentNode.setHeight(Math.max(leftChildHeight, rightChildHeight) + 1);
        currentNode.setBalanceFactor(leftChildHeight - rightChildHeight);
    }

    /**
     * Method that rotates a current node to the left. After saving the
     * current's right node to a variable, the right node's left subtree will
     * become the current node's right subtree. The current node will become
     * the right node's left subtree.
     *
     * Don't forget to recalculate the height and balance factor of all
     * affected nodes, using updateHeightAndBF().
     *
     * This method should run in O(1).
     *
     * You may assume that the passed in node is not null and that the subtree
     * starting at that node is right heavy. Therefore, you do not need to
     * perform any preliminary checks, rather, you can immediately perform a
     * left rotation on the passed in node and return the new root of the subtree.
     *
     * This method should only be called in balance().
     *
     * @param currentNode The current node under inspection that will rotate.
     * @return The parent of the node passed in (after the rotation).
     */
    private AVLNode<T> rotateLeft(AVLNode<T> currentNode) {
        AVLNode<T> nodeB = currentNode.getRight();
        currentNode.setRight(nodeB.getLeft());
        nodeB.setLeft(currentNode);
        updateHeightAndBF(currentNode);
        updateHeightAndBF(nodeB);
        return nodeB;
    }

    /**
     * Method that rotates a current node to the right. After saving the
     * current's left node to a variable, the left node's right subtree will
     * become the current node's left subtree. The current node will become
     * the left node's right subtree.
     *
     * Don't forget to recalculate the height and balance factor of all
     * affected nodes, using updateHeightAndBF().
     *
     * This method should run in O(1).
     *
     * You may assume that the passed in node is not null and that the subtree
     * starting at that node is left heavy. Therefore, you do not need to perform
     * any preliminary checks, rather, you can immediately perform a right
     * rotation on the passed in node and return the new root of the subtree.
     *
     * This method should only be called in balance().
     *
     * @param currentNode The current node under inspection that will rotate.
     * @return The parent of the node passed in (after the rotation).
     */
    private AVLNode<T> rotateRight(AVLNode<T> currentNode) {
        AVLNode<T> nodeB = currentNode.getLeft();
        currentNode.setLeft(nodeB.getRight());
        nodeB.setRight(currentNode);
        updateHeightAndBF(currentNode);
        updateHeightAndBF(nodeB);
        return nodeB;
    }

    /**
     * Method that balances out the tree starting at the node passed in.
     * This method should be called in your add() and remove() methods to
     * facilitate rebalancing your tree after an operation.
     *
     * The height and balance factor of the current node is first recalculated.
     * Based on the balance factor, a no rotation, a single rotation, or a
     * double rotation takes place. The current node is returned.
     *
     * You may assume that the passed in node is not null. Therefore, you do
     * not need to perform any preliminary checks, rather, you can immediately
     * check to see if any rotations need to be performed.
     *
     * This method should run in O(1).
     *
     * @param currentNode The current node under inspection.
     * @return The AVLNode that the caller should return.
     */
    private AVLNode<T> balance(AVLNode<T> currentNode) {
        /* First, we update the height and balance factor of the current node. */
        updateHeightAndBF(currentNode);

        if (currentNode.getBalanceFactor() < -1) {
            if (currentNode.getRight().getBalanceFactor() > 0) {
                currentNode.setRight(rotateRight(currentNode.getRight()));
            }
            currentNode = rotateLeft(currentNode);
        } 
        else if (currentNode.getBalanceFactor() > 1) {
            if (currentNode.getLeft().getBalanceFactor() < 0) {
                currentNode.setLeft(rotateLeft(currentNode.getLeft()));
            }
            currentNode = rotateRight(currentNode);
        }
        return currentNode;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The root of the tree.
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the tree.
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
