import java.util.*;
import junit.framework.TestCase;

public class CircularArrayQueueTest extends TestCase
{

    private void checkSize( int length, MyQueue queue )
    {
        assertEquals( "Queue has wrong number of elements", length,
                queue.noItems() );
        if( length == 0 )
            assertTrue( "Queue should be empty", queue.isEmpty() );
        else
            assertTrue( "Queue should not be empty", !queue.isEmpty() );
    }

    public void testSimple()
    {
        MyQueue queue = new CircularArrayQueue();
        checkSize( 0, queue );
        queue.enqueue( 3 );
        checkSize( 1, queue );
        try
        {
            assertEquals( "Dequeue returns wrong element", 3, queue.dequeue() );
        } catch( NoSuchElementException e )
        {
            throw e;
        }
        checkSize( 0, queue );
    }

    public void testMultiInput()
    {
        MyQueue queue = new CircularArrayQueue();
        for( int i = 0; i < 1000; ++i )
        {
            int r = ( int )Math.round( Math.random() );
            checkSize( 0, queue );
            queue.enqueue( r );
            checkSize( 1, queue );
            assertEquals( "Dequeue returns wrong element", r, queue.dequeue() );
        }

    }

    public void testManyEnqueueDequeue()
    {
        CircularArrayQueue queue = new CircularArrayQueue();
        int cnt = 0;
        for( int i = 0; i < 100000; ++i )
        {
            if( Math.random() > 0.5 )
            {
                queue.enqueue( i );
                cnt++;
            } else
            {
                if( !queue.isEmpty() )
                {
                    queue.dequeue();
                    cnt--;
                }
            }
            assertEquals( "Correct number of items", cnt, queue.noItems() );
        }
    }

    public void testLargeQueue()
    {
        MyQueue queue = new CircularArrayQueue();
        int[] r = new int[ 1000 ];
        for( int i = 0; i < r.length; ++i )
        {
            r[ i ] = ( int )Math.round( Math.random() );
            checkSize( i, queue );
            queue.enqueue( r[ i ] );
        }
        for( int i = 0; i < r.length; ++i )
        {
            assertEquals( "Dequeue returns wrong element", r[ i ],
                    queue.dequeue() );
            checkSize( r.length - i - 1, queue );
        }
        for( int i = 0; i < r.length; ++i )
        {
            r[ i ] = ( int )Math.round( Math.random() );
            checkSize( i, queue );
            queue.enqueue( r[ i ] );
        }
        for( int i = 0; i < r.length; ++i )
        {
            assertEquals( "Dequeue returns wrong element", r[ i ],
                    queue.dequeue() );
            checkSize( r.length - i - 1, queue );
        }
    }

    public void testThrows()
    {
        MyQueue queue = new CircularArrayQueue();
        int[] r = new int[ 1000 ];
        for( int i = 0; i < r.length; ++i )
        {
            r[ i ] = ( int )Math.round( Math.random() );
            checkSize( i, queue );
            queue.enqueue( r[ i ] );
        }
        for( int i = 0; i < r.length; ++i )
        {
            assertEquals( "Dequeue returns wrong element", r[ i ],
                    queue.dequeue() );
            checkSize( r.length - i - 1, queue );
        }
        boolean throwsCorrectly = false;
        try
        {
            queue.dequeue();
        } catch( NoSuchElementException e )
        {
            throwsCorrectly = true;
        }
        assertTrue( "Throws when dequeuing empty queue", throwsCorrectly );
    }

    public void testResize()
    {
        CircularArrayQueue queue = new CircularArrayQueue();
        assertTrue( "Initial capacity too large", queue.getCapacityLeft() <= 1024 );
        for( int i = 0; i < 1000; ++i )
        {
            queue.enqueue( i );
        }
        int currentCapacity = queue.getCapacityLeft();
        while( currentCapacity > 0 )
        {
            queue.enqueue( 9 );
            currentCapacity--;
            assertEquals( "Array size should not change", currentCapacity, queue.getCapacityLeft() );
        }
        assertTrue( "Should have reached capacity", queue.getCapacityLeft() == 0 );
        queue.enqueue( 42 );
        assertTrue( "Should have resized array",
                currentCapacity < queue.getCapacityLeft() );
        currentCapacity = queue.getCapacityLeft();
        for( int i = 0; i < 100; ++i )
        {
            queue.enqueue( i );
            currentCapacity--;
            assertEquals( "Resizing too often (inefficient)", currentCapacity, queue.getCapacityLeft() );
        }
    }
}