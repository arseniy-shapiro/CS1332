import java.util.NoSuchElementException;

/**
 * Your implementation of a Singly-Linked List.
 */
public class SinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private SinglyLinkedListNode<T> head;
    private SinglyLinkedListNode<T> tail;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the element to the front of the list.
     *
     * Method should run in O(1) time.
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        SinglyLinkedListNode<T> newNode;

        if (this.head == null && this.tail == null) {
            newNode = new SinglyLinkedListNode<T>(data);
            this.head = newNode;
            this.tail = newNode;
        }
        else {
            SinglyLinkedListNode<T> oldHead = this.head;
            newNode = new SinglyLinkedListNode<T>(data, oldHead);
            this.head = newNode;
        }
        this.size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Method should run in O(1) time.
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        SinglyLinkedListNode<T> newNode;

        if (this.head == null && this.tail == null) {
            newNode = new SinglyLinkedListNode<T>(data);
            this.head = newNode;
            this.tail = newNode;
        }
        else {
            newNode = new SinglyLinkedListNode<T>(data);
            this.tail.setNext(newNode);
            this.tail = newNode;
        }
        this.size++;
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Method should run in O(1) time.
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (this.head == null && this.tail == null) {
           throw new NoSuchElementException();
        }
        SinglyLinkedListNode<T> nodeToRemove = this.head;

        if (this.head == this.tail) {
            this.head = null;
            this.tail = null;
            this.size = 0;
        }
        else {
            this.head = this.head.getNext();
            this.size--;
        }
        return nodeToRemove.getData();
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Method should run in O(n) time.
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (this.head == null && this.tail == null) {
            throw new NoSuchElementException();
        }
        SinglyLinkedListNode<T> nodeToRemove = this.tail;

        if (this.head == this.tail) {
            this.head = null;
            this.tail = null;
            this.size = 0;
        }
        else {
            SinglyLinkedListNode<T> currentNode = this.head;
            if (this.size == 2) {
                currentNode.getNext().setNext(null);
                this.tail = currentNode;
            }
            else {
                currentNode = this.head;
                while (currentNode.getNext().getNext() != null) {
                    currentNode = currentNode.getNext();
                }
                currentNode.setNext(null);
                this.tail = currentNode;
            }
            this.size--;
        }
        return nodeToRemove.getData();
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public SinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public SinglyLinkedListNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
