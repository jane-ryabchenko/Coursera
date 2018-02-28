import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RandomizedQueueTest {
  private RandomizedQueue<Integer> queue;

  @Before
  public void beforeTest() {
    queue = new RandomizedQueue<>();
  }

  @Test
  public void testIsEmpty() {
    assertTrue(queue.isEmpty());
  }

  @Test
  public void testSize() {
    queue.enqueue(1);
    queue.enqueue(2);
    queue.enqueue(3);
    queue.enqueue(4);
    assertEquals(4, queue.size());
  }

  @Test
  public void testRandomDequeue() {
    queue.enqueue(1);
    queue.enqueue(2);
    queue.enqueue(3);
    queue.enqueue(4);

    Iterator<Integer> iterator1 = queue.iterator();
    Iterator<Integer> iterator2 = queue.iterator();
    boolean same = true;
    while (iterator1.hasNext() && iterator2.hasNext()) {
      if (!iterator1.next().equals(iterator2.next())) {
        same = false;
      }
    }
    assertFalse(iterator1.hasNext());
    assertFalse(iterator2.hasNext());
    assertFalse(same);
    assertEquals(4, queue.size());
  }

  @Test
  public void testRemoveRandom() {
    queue.enqueue(5);
    assertEquals(5, (int) queue.dequeue());
    queue.enqueue(6);
    assertEquals(6, (int) queue.dequeue());
  }

  @Test
  public void testRemoveRandomBulk() {
    for (int i = 0; i < 548; i++) {
      queue.enqueue(i);
    }
    while (!queue.isEmpty()) {
      queue.dequeue();
    }
    queue.enqueue(6);
    assertEquals(6, (int) queue.dequeue());
  }

  @Test
  public void testIterator(){
    queue.enqueue(1);
    queue.enqueue(2);
    queue.enqueue(3);
    queue.enqueue(4);
    Iterator<Integer> iterator = queue.iterator();
    assertTrue(iterator.hasNext());
  }
}
