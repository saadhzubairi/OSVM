import java.util.Iterator;
import java.util.NoSuchElementException;

public class queue<Item> implements Iterable<Item> {
    private int size = 0; // total number of elements in queue
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
    }

    public queue() {
        // queue is public because we will use it in PCB and process
        first = null;
        last = null;

    }

    public boolean isEmpty() {
        return first == null;
    }

    public int length() {
        return size;
    }

    public Item topElement() {
        if (isEmpty()) {
            return null;
        }
        return first.item;
    }

    public void enqueue(Item item) {
        Node add = new Node();
        add.item = item;
        if (isEmpty()) {
            first = add;
            last = add;
        } else {
            last.next = add;
            last = add;
        }
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new RuntimeException();
        }
        // take top element and store it to return
        Item item = first.item;
        first = first.next;
        size--;

        return item;

    }


    @Override
    public Iterator<Item> iterator() {
        // TODO Auto-generated method stub
        return new stackIterator();
    }

    //it is stack becasuew we want it to be FIFO
    private class stackIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
            // != because if it was = then it would return the opp
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

    public String toString() {
        String s = " ";
        Node temp = first;
        for (int i = 0; i < size; i++) {
            s = s + temp.item;
            first = first.next;
        }
        return s.toString();
    }
}