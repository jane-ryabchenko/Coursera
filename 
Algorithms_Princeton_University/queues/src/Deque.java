import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
  private int size;
  private Node first;
  private Node last;

  private class DequeIterator implements Iterator<Item> {
    private Node current = first;

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Item item = current.item;
      current = current.next;
      return item;
    }
  }

  private class Node {
    Item item;
    Node next;
    Node prev;
  }

  public boolean isEmpty() {
    return first == null;
  }

  public int size() {
    return size;
  }

  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    Node oldFirst = first;
    first = new Node();
    first.item = item;
    first.next = oldFirst;
    if (size == 0) {
      last = first;
    } else {
      oldFirst.prev = first;
    }
    size++;
  }

  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    Node oldLast = last;
    last = new Node();
    last.item = item;
    last.prev = oldLast;
    if (size == 0) {
      first = last;
    } else {
      oldLast.next = last;
    }
    size++;
  }

  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    Item firstElement = first.item;
    first = first.next;
    size--;
    if (size == 0) {
      last = null;
    } else {
      first.prev = null;
    }
    return firstElement;
  }

  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    Item lastElement = last.item;
    last = last.prev;
    size--;
    if (size == 0) {
      first = last;
    } else {
      last.next = null;
    }
    return lastElement;
  }

  public Iterator<Item> iterator() {
    return new DequeIterator();
  }
}