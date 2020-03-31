import java.util.*;
import junit.framework.TestCase;
import java.lang.Integer;

public class CircularArrayRingTest extends TestCase  {

    public void testAdding() {
        Ring<Integer> ring = new CircularArrayRing<Integer>(10);
        for(int i=0; i<50; ++i) {
            ring.add(i);
            if (i<10)
                assertEquals(i+1, ring.size());
            else
                assertEquals(10, ring.size());
        }
    }

    public void testGet() {
        Ring<Integer> ring = new CircularArrayRing<Integer>(10);
        for(int i=0; i<1000; ++i) {
            ring.add(i);
            for(int j=0; j<ring.size(); ++j)
                assertEquals((i-j), (int) ring.get(j));
        }
        boolean exceptionThrown = false;
        try {
            ring.get(11);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        // call get with too large an index
        assertTrue("Should have thrown an exception", exceptionThrown);
        exceptionThrown = false;
        try {
            ring.get(-1);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        // call get with a negative index
        assertTrue("Should have thrown an exception", exceptionThrown);	}

    public void testOtherRings() {
        Ring<String> ring = new CircularArrayRing<String>(3);
        String[] fruit = {"apple", "banana", "pear", "orange"};
        int i = 0;
        for(String f: fruit) {
            ring.add(f);
            for(int j=0; j<ring.size(); ++j)
                assertEquals(fruit[i-j], ring.get(j));
            ++i;
        }
    }

    public void testDefaultRingSize() {
        // check sensible default size
        Ring<Integer> ring = new CircularArrayRing<Integer>();
        for(int i=0; i<1000; ++i) {
            ring.add(i);
        }
        assertTrue("Default size must be non-zero", ring.size()>0);
        // check default size is the default size
        Ring<Integer> ring1 = new CircularArrayRing<Integer>(55);
        for(int i=0; i<1000; ++i) {
            ring1.add(i);
        }
        assertEquals("Size is not default size", 55, ring1.size());
    }

    public void testIterator() {
        Ring<Integer> ring = new CircularArrayRing<Integer>(10);
        for(int i=0; i<1000; ++i) {
            ring.add(i);
        }
        Iterator<Integer> it = ring.iterator();
        int i = 999;
        while (it.hasNext()) {
            assertEquals(i, (int) it.next());
            --i;
        }
        // Try again just for luck
        it = ring.iterator();
        i = 999;
        while (it.hasNext()) {
            assertEquals(i, (int) it.next());
            --i;
        }
        // Be a bit more vicious
        it = ring.iterator();
        i = 999;
        while (it.hasNext()) {
            it.hasNext();  // Should do nothing
            assertEquals(i, (int) it.next());
            --i;
        }
        // Test end condition
        boolean exceptionThrown = false;
        try {
            it.next();
        } catch(NoSuchElementException e) {
            exceptionThrown = true;
        }
        // calling next() when there should be no next
        assertTrue("Should have thrown an exception", exceptionThrown);

        // Test enhanced for loop
        i = 999;
        for (Integer element: ring) {
            assertEquals(i, (int) element);
            --i;
        }
    }
}