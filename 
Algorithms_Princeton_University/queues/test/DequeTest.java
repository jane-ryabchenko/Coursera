import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DequeTest {
  private Deque<Integer> deque;

  @Before
  public void beforeTest() {
    deque = new Deque<>();
  }

  @Test
  public void testisEmpty() {
    assertTrue(deque.isEmpty());
  }

  @Test
  public void testSize() {
    deque.addFirst(1);
    deque.addFirst(2);
    deque.addFirst(3);
    deque.addFirst(4);
    assertEquals(4, deque.size());
  }

  @Test
  public void testRemoveFirst() {
    deque.addFirst(5);
    assertEquals(5, (int)deque.removeFirst());
  }

  @Test
  public void testRemoveLast() {
    deque.addLast(5);
    assertEquals(5, (int)deque.removeLast());
    deque.addFirst(1);
    deque.addFirst(2);
    deque.addFirst(3);
    deque.addFirst(4);
    assertEquals(1, (int)deque.removeLast());
  }

  @Test
  public void testIterator(){
    deque.addFirst(1);
    deque.addFirst(2);
    deque.addFirst(3);
    deque.addFirst(4);
    Iterator<Integer> iterator = deque.iterator();
    assertTrue(iterator.hasNext());
    assertEquals(4, (int)iterator.next());
    assertEquals(3, (int)iterator.next());
    assertEquals(2, (int)iterator.next());
    assertEquals(1, (int)iterator.next());
    assertFalse(iterator.hasNext());
  }
}
