import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private static final int CHUNK_SIZE = 48;
  private Item[] chunks = (Item[]) new Object[0];
  private int size;

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    size++;
    if (getUsedLines() > chunks.length) {
      resize(chunks.length + 1);
    }

    int ind = StdRandom.uniform(size);
    set(get(ind), size - 1);
    set(item, ind);
  }

  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    size--;
    Item item = get(size);
    set(null, size);
    if (getUsedLines() < chunks.length) {
      resize(chunks.length - 1);
    }
    return item;
  }

  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    int ind = StdRandom.uniform(size);
    return get(ind);
  }

  public Iterator<Item> iterator() {
    RandomizedQueue<Item> queue = new RandomizedQueue<>();
    for (int ind = 0; ind < size; ind++) {
      queue.enqueue(get(ind));
    }
    return new Iterator<Item>() {
      public boolean hasNext() {
        return !queue.isEmpty();
      }

      public Item next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        return queue.dequeue();
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  private void resize(int capacity) {
    Item[] copy = (Item[]) new Object[capacity];
    if (capacity > chunks.length) {
      copy[capacity - 1] = (Item) new Object[CHUNK_SIZE];
    }
    for (int i = 0; i < chunks.length; i++) {
      if (i < capacity) {
        copy[i] = chunks[i];
        chunks[i] = null;
      }
    }
    chunks = copy;
  }

  private int getUsedLines() {
    int usedLines = size / CHUNK_SIZE;
    if (size % CHUNK_SIZE != 0) {
      usedLines++;
    }
    return usedLines;
  }

  private Item get(int index) {
    Item[] items = (Item[]) chunks[index / CHUNK_SIZE];
    return items[index % CHUNK_SIZE];
  }

  private void set(Item item, int index) {
    Item[] items = (Item[]) chunks[index / CHUNK_SIZE];
    items[index % CHUNK_SIZE] = item;
  }
}
