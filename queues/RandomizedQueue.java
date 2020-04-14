/* *****************************************************************************
 *  Name: Sherban Drulea
 *  Date: Arpil , 2020
 *  Description: Assignment 2
 *****************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    /**
     * init size.
     */
    private static final int INIT_SIZE = 2;

    /**
     * queue elements.
     */
    private Item[] queue;

    /**
     * number of elements on queue.
     */
    private int size = 0;


    // construct an empty randomized queue
    public RandomizedQueue() {
        // cast needed since no generic array creation in Java
        queue = (Item[]) new Object[INIT_SIZE];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (size == queue.length) {
            resize(2*queue.length);
        }

        queue[size++] = item;
    }

    /**
     * @param capacity
     */
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            temp[i] = queue[i];
        }

        queue = temp;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(size);
        Item item = queue[randomIndex];

        // move the last item to fill the gap
        if (randomIndex != size - 1) {
            queue[randomIndex] = queue[size - 1];
        }
        // set the last item to null
        queue[size - 1] = null;
        size--;

        if (size < queue.length / 4) {
            resize(queue.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(size);
        return queue[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        /**
         * index.
         */
        private int index = 0;
        /**
         * random index array.
         */
        private int[] random;

        /**
         * Create a random index array.
         */
        public ArrayIterator() {
            random = new int[size];
            for (int i = 0; i < random.length; i++) {
                random[i] = i;
            }
            StdRandom.shuffle(random);
        }

        /*
         * @see java.util.Iterator#hasNext()
         */
        public boolean hasNext() {
            return index < random.length;
        }

        /*
         * @see java.util.Iterator#remove()
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /*
         * @see java.util.Iterator#next()
         */
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int randomIndex = random[index];
            index++;
            return queue[randomIndex];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> d = new RandomizedQueue<Integer>();
        d.enqueue(1);
        d.enqueue(2);
        d.enqueue(3);
        StdOut.printf("original size = %d\n", d.size());
        StdOut.printf("sample1 = %d\n", d.sample());
        StdOut.printf("sample2 = %d\n", d.sample());
        StdOut.printf("sample3 = %d\n", d.sample());
        StdOut.printf("dequeue = %d\n", d.dequeue());
        StdOut.printf("size = %d\n", d.size());
        StdOut.printf("dequeue = %d\n", d.dequeue());
        StdOut.printf("size = %d\n", d.size());
        StdOut.printf("sample4 = %d\n", d.sample());
    }

}
