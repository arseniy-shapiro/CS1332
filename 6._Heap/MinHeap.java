import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

     /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * This is the constructor that constructs a new MinHeap.
     *
     * Recall that Java does not allow for regular generic array creation,
     * so instead we cast a Comparable[] to a T[] to get the generic typing.
     */
    @SuppressWarnings("unchecked")
    public MinHeap() {
        //DO NOT MODIFY THIS METHOD!
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * Method should run in amortized O(log n) time.
     *
     * @param data The data to add.
     * @throws java.lang.IllegalArgumentException If the data is null.
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (this.size == this.backingArray.length - 1) {
            this.resizeHeap();
        }
        this.size++;
        this.backingArray[this.size] = data;
        performUpHeap(this.size);
    }

    private void performUpHeap(int index) {
        if (index <= 1) {
            return;
        }
        int parentIndex = index / 2;
        if (this.backingArray[index].compareTo(this.backingArray[parentIndex]) < 0) {
            swapValues(index, parentIndex);
        }
        performUpHeap(parentIndex);
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * Method should run in O(log n) time.
     *
     * @return The data that was removed.
     * @throws java.util.NoSuchElementException If the heap is empty.
     */
    public T remove() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        T data = this.backingArray[1];
        int index = this.size;
        this.backingArray[1] = this.backingArray[index];
        this.backingArray[index] = null;
        index = 1;
        this.size--;
        performDownHeap(index);
        return data;
    }

    private void performDownHeap(int index) {
        if (index > this.size / 2) {
            return;
        }
        int leftChildIndex = index * 2;
        int rightChildIndex = index * 2 + 1;
        T leftChildData = this.backingArray[leftChildIndex];
        T rightChildData = this.backingArray[rightChildIndex];
        boolean hasTwoChildren = leftChildData != null && rightChildData != null;

        if (hasTwoChildren) {
            if (leftChildData.compareTo(rightChildData) < 0) {
                if (this.backingArray[index].compareTo(leftChildData) > 0) {
                    swapValues(index, leftChildIndex);
                }
                performDownHeap(leftChildIndex);
            }
            else {
                if (this.backingArray[index].compareTo(rightChildData) > 0) {
                    swapValues(index, rightChildIndex);
                }
                performDownHeap(rightChildIndex);
            }   
        }
        else {
            if (this.backingArray[index].compareTo(leftChildData) > 0) {
                swapValues(index, leftChildIndex);
            }
            performDownHeap(leftChildIndex);
        }
    }

    @SuppressWarnings("unchecked")
    private void resizeHeap() {
        T[] newHeap = (T[]) new Comparable[backingArray.length * 2];
        for (int i = 0, n = this.backingArray.length; i < n; i++) {
            newHeap[i] = this.backingArray[i];
        }
        this.backingArray = newHeap;
    }

    private void swapValues(int firstIndex, int secondIndex) {
        T tmpVar = this.backingArray[firstIndex];
        this.backingArray[firstIndex] = this.backingArray[secondIndex];
        this.backingArray[secondIndex] = tmpVar;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
