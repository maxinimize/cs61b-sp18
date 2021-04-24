package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(4);
        assertTrue(arb.isEmpty());
        arb.enqueue(33.1); // 33.1 null null  null
        assertFalse(arb.isEmpty());
        assertEquals(33.1, arb.peek());
        arb.enqueue(44.8); // 33.1 44.8 null  null
        arb.enqueue(62.3); // 33.1 44.8 62.3  null
        arb.enqueue(-3.4); // 33.1 44.8 62.3 -3.4
        assertTrue(arb.isFull());
        assertEquals(arb.dequeue(), 33.1);
        assertEquals(44.8, arb.peek());
        assertEquals(arb.dequeue(), 44.8);
        arb.enqueue(33.1); // 62.3 -3.4 33.1 null
        arb.enqueue(44.8); // 62.3 -3.4 33.1 44.8
        assertTrue(arb.isFull());
        assertEquals(62.3, arb.peek());
        assertEquals(arb.dequeue(), 62.3);
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
