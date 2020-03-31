import java.util.NoSuchElementException;

/**
 * A queue data structure implemented using an array which resizes as the queue grows.
 */
public class CircularArrayQueue implements MyQueue {

    // Stores the position of the front of the queue
    private int head;
    // Stores the position of the end of the queue
    private int tail;
    // Holds the number of elements currently in the queue
    private int count;
    // The array which stores the actual values
    private Integer[] array;

    /**
     * Constructor which instantiates the pointers, count and the array to size 10.
     */
    CircularArrayQueue() {
        array = new Integer[10];
        head = tail = count = 0;
    }

    /**
     * @return The remaining space in the array by subtracting the number of elements from it's size.
     */
    int getCapacityLeft() {
        return array.length - count;
    }

    /**
     * Adds an element to the tail of the queue, resizing it if necessary.
     * @param element The integer to be added.
     */
    @Override
    public void enqueue(int element) {
        if (array.length == count) {
            resize();
        }
        array[tail] = element;
        // Use the remainder to allow circular indexing
        tail = (tail + 1) % array.length;
        count++;
    }

    /**
     * Remove the element from the head of the queue.
     * @return The element from the queue.
     * @throws NoSuchElementException If the queue is empty.
     */
    @Override
    public int dequeue() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int element = array[head];
        array[head] = null;
        // Use the remainder to allow circular indexing
        head = (head + 1) % array.length;
        count--;
        return element;
    }

    /**
     * Copy the contents to a new array of twice the size to store more elements.
     */
    private void resize() {
        Integer[] newArray = new Integer[array.length * 2];

        // Copy all of the elements from the old array across
        for (int i = 0; i < count; i++) {
            newArray[i] = array[head];
            head = (head + 1) % array.length;
        }

        head = 0;
        tail = count;
        // Change the array reference to the new larger array
        array = newArray;
    }

    /**
     * @return The number of items in the queue
     */
    @Override
    public int noItems() {
        return count;
    }

    /**
     * @return True if there are no elements in the queue, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return (count == 0);
    }
}
