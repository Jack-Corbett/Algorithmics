import org.junit.*;
import static org.junit.Assert.*;
import java.util.Random;
import java.util.Iterator;

/**
 * A class used to unit test the binary tree implementation.
 */
public class BinarySearchTreeTest {

    /**
     * Add a single value to the tree and check it is submitted by calling the iterator. It also checks that
     * after adding the element the tree is not empty.
     */
    @Test
    public void add() {
        // Add a single object to the root
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        binarySearchTree.add(1);
        Iterator it = binarySearchTree.iterator();
        assertEquals("Unexpected value returned from tree",1, it.next());
        assertTrue("Tree should not be empty", !binarySearchTree.isEmpty());
    }

    /**
     * Add a single value to the tree, remove it and check that the tree is empty.
     */
    @Test
    public void remove() {
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        binarySearchTree.add(1);
        binarySearchTree.remove(1);
        assertTrue("Tree should be empty", binarySearchTree.isEmpty());
    }

    /**
     * Add values 0 to 7 to the tree and then check the edge cases: 0 and 7 are in the tree but 8 is not.
     */
    @Test
    public void contains() {
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        for (int i = 0; i < 8; i++) {
            binarySearchTree.add(i);
        }
        assertTrue("0 should be in the Tree", binarySearchTree.contains(0));
        assertTrue("7 should be in the Tree", binarySearchTree.contains(7));
        assertTrue("8 should not be in the Tree", !binarySearchTree.contains(8));
    }

    /**
     * Check the iterator returns the correct ordering of nodes by testing both linear and random input.
     */
    @Test
    public void iterator() {
        // Test for linear input
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        for (int i = 0; i < 8; i++) {
            binarySearchTree.add(i);
        }
        Iterator it = binarySearchTree.iterator();
        int count = 0;
        while(it.hasNext()) {
            assertEquals(count, it.next());
            count++;
        }

        // Test for random input with greater depth
        BinarySearchTree randomBinarySearchTree = new BinarySearchTree();
        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            // Adds a random number between 0-99
            randomBinarySearchTree.add(rand.nextInt(100));
        }
        Iterator randomIt = randomBinarySearchTree.iterator();
        int node, previousNode = 0;
        while (randomIt.hasNext()) {
            node = (int)randomIt.next();
            assertTrue("Iterator returned incorrect order", node >= previousNode);
            previousNode = node;
        }
    }
}
