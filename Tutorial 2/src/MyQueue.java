import java.util.*;

public interface MyQueue {
    void enqueue(int in);

    int dequeue() throws NoSuchElementException;  // throw exception if isEmpty() is true

    int noItems(); // returns the number of items in the array

    boolean isEmpty();  // true if queue is empty

}
