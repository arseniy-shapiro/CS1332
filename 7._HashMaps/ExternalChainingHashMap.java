import java.util.NoSuchElementException;

/**
 * Your implementation of a ExternalChainingHashMap.
 */
public class ExternalChainingHashMap<K, V> {

    /*
     * The initial capacity of the ExternalChainingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * The max load factor of the ExternalChainingHashMap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private ExternalChainingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new ExternalChainingHashMap with an initial capacity of INITIAL_CAPACITY.
     */
    @SuppressWarnings("unchecked")
    public ExternalChainingHashMap() {
        //DO NOT MODIFY THIS METHOD!
        table = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[INITIAL_CAPACITY];
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use external chaining as your resolution
     * strategy. Add new entries to the front of an existing chain, but don't
     * forget to check the entire chain for duplicate keys first.
     *
     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in. When replacing the old value, replace it at that position
     * in the chain, not by creating a new entry and adding it to the front.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the table would violate the max load factor if the data was
     * added. Resize if the load factor (LF) is greater than max LF (it is
     * okay if the load factor is equal to max LF). For example, let's say
     * the table is of length 5 and the current size is 3 (LF = 0.6). For
     * this example, assume that no elements are removed in between steps.
     * If another entry is attempted to be added, before doing anything else,
     * you should check whether (3 + 1) / 5 = 0.8 is larger than the max LF.
     * It is, so you would trigger a resize before you even attempt to add
     * the data or figure out if it's a duplicate. Be careful to consider the
     * differences between integer and double division when calculating load
     * factor.
     *
     * When regrowing, resize the length of the backing table to
     * (2 * old length) + 1. You should use the resizeBackingTable method to do so.
     *
     * @param key   The key to add.
     * @param value The value to add.
     * @return null if the key was not already in the map. If it was in the
     *         map, return the old value associated with it.
     * @throws java.lang.IllegalArgumentException If key or value is null.
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        if ((this.size + 1) / (double) this.table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(this.table.length * 2 + 1);
        }
        int index = getHashedIndex(key);
        if (this.table[index] == null) {
            table[index] = new ExternalChainingMapEntry<K, V>(key, value);
        }
        else {
            if (table[index].getKey().equals(key)) {
                table[index].setValue(value);
            }
            else {
                ExternalChainingMapEntry<K, V> hashEntry = table[index];
                ExternalChainingMapEntry<K, V> newEntry = new ExternalChainingMapEntry<>(key, value);
                newEntry.setNext(hashEntry);
                table[index] = newEntry;
                this.size++;
            }
        }
        return null;
    }

    /**
     * Removes the entry with a matching key from the map.
     *
     * @param key The key to remove.
     * @return The value associated with the key.
     * @throws java.lang.IllegalArgumentException If key is null.
     * @throws java.util.NoSuchElementException   If the key is not in the map.
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int index = getHashedIndex(key);
        if (this.table[index] == null) {
            throw new NoSuchElementException();
        }
        ExternalChainingMapEntry<K, V> entry = null;
        ExternalChainingMapEntry<K, V> nextEntry = this.table[index];

        while (nextEntry.getNext() != null && !nextEntry.getKey().equals(key)) {
            entry = nextEntry;
            nextEntry = nextEntry.getNext();
        }
        if (nextEntry.getKey().equals(key)) {
            V returnValue = nextEntry.getValue();

            if (entry == null) {
                this.table[index] = nextEntry.getNext();
            }
            else {
                entry.setNext(nextEntry.getNext());
            }
            this.size--;
            return returnValue;
        }
        throw new NoSuchElementException();
    }

    private int getHashedIndex(K key) {
        return Math.abs(key.hashCode() % this.table.length);
    }

    /**
     * Helper method stub for resizing the backing table to length.
     *
     * This method should be called in put() if the backing table needs to
     * be resized.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you won't need to explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new table.
     *
     * @param Length The new length of the backing table.
     */
    @SuppressWarnings("unchecked")
    private void resizeBackingTable(int length) {
        ExternalChainingMapEntry<K, V>[] newTable = new ExternalChainingMapEntry[length];
        int index;
        for (ExternalChainingMapEntry<K, V> entry: this.table) {
            if (entry != null) {
                index = Math.abs(entry.getKey().hashCode() % newTable.length);
                newTable[index] = entry;
            }
        }
        this.table = newTable;
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The table of the map.
     */
    public ExternalChainingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the map.
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
