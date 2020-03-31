import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static java.lang.StrictMath.floorMod;

/**
 * A data structure that only remembers the last n elements (default is 10) which is circular to make optimal use of
 * memory.
 * @param <E> sets the type
 */
public class CircularArrayRing<E> extends AbstractCollection<E> implements Ring<E> {

    // Stores a pointer to the next position to fill
    private int tail;
    // Keeps track of the number of elements stored
    private int count;
    // Holds the actual objects
    private E[] array;

    /**
     * Default constructor which initialises the tail, count and array to the default size of 10.
     */
    CircularArrayRing() {
        tail = count = 0;
        array = (E[]) new Object[10];
    }

    /**
     * Constructor that initialises the tail and count, setting the length of the array to the passed value.
     * @param size The desired size of the ring.
     */
    CircularArrayRing(int size) {
        tail = count = 0;
        array = (E[]) new Object[size];
    }

    /**
     * Adds an elements to the circular array ring, updating the tail and count.
     * @param e Element to add to the data structure
     * @return Always returns true for this data structure as you can always add an element
     * even if it is full as it will overwrite another.
     */
    @Override
    public boolean add(E e) {
        array[tail] = e;

        // Use mod to ensure the tail stays within the array bounds (ie set to 0 when it reaches the array size)
        tail = floorMod((tail + 1), array.length);

        // Only increment count if the array hasn't yet been filled
        if (count < array.length) {
            count++;
        }

        return true;
    }

    /**
     * Get an element from the circular array ring.
     * @param index The position of the element to access.
     * @return The fetched object.
     * @throws IndexOutOfBoundsException If there is no element in that position or the index
     * given was outside the array bounds.
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        try {
            /* If there are elements in the array AND the index given is less than the number of elements
            AND the index is 0 or greater then fetch the element.
             */
            if (count > 0 && index < count && index >= 0) {
                // Use mod for the circular behaviour, keeping elements in the correct order
                return array[floorMod((tail - (index + 1)), array.length)];
            } else {
                throw new IndexOutOfBoundsException();
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * An iterator to loop through each element in the circular array ring, backwards from the
     * most recently added.
     * @return The iterator object.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private int itCount;

            // Constructor initialises the iterator count to 0
            {
                itCount = 0;
            }

            /**
             * Checks if there is an object in the next position by comparing the iterator count with the length
             * of the array and the element count.
             * @return True if there is an object to iterate to.
             * False if there is no object OR we have iterated through the entire array.
             */
            @Override
            public boolean hasNext() {
                return size() == array.length && itCount < array.length - 1 || itCount < size();
            }

            /**
             * Fetches the next object from the data structure.
             * @return The fetched object.
             */
            @Override
            public E next() {
                // Check there is an object in the next position and fetch it
                if(hasNext()) {
                    return get(itCount++);
                } else {
                    throw new NoSuchElementException();
                }
            }

            /**
             * Not implemented for this data structure.
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * @return The number of elements in the array, once it is full this remains constant.
     */
    @Override
    public int size() {
        return count;
    }
}
