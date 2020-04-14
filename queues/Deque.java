/* *****************************************************************************
 *  Name: Sherban Drulea
 *  Date: April 13, 2020
 *  Description: Assignment 2
 **************************************************************************** */
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldfirst = first;

        first = new Node();
        first.item = item;
        first.previous = null;
        first.next = oldfirst;

        if (isEmpty()) {
            last = first;
        } else {
            oldfirst.previous = first;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("addFirst argument cannot be null");
        }

        Node oldlast = last;

        last = new Node();
        last.item = item;
        last.previous = oldlast;
        last.next = null;

        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        Item item = first.item;

        first = first.next;
        if (first != null) { // to avoid loitering
            first.previous = null;
        }
        size--;
        if (isEmpty()) {
            last = null;
        }

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        Item item = last.item;

        last = last.previous;
        if (last != null) { // to avoid loitering
            last.next = null;
        }
        size--;
        if (isEmpty()) {
            first = null;
        }

        return item;
    }


    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();
        d.addFirst(1);
        d.addLast(2);
        d.addLast(3);
        StdOut.printf("original size = %d\n", d.size());
        StdOut.printf("removing first = %d\n", d.removeFirst());
        StdOut.printf("size = %d\n", d.size());
        StdOut.printf("removing last = %d\n", d.removeLast());
        StdOut.printf("size = %d\n", d.size());
    }

}
