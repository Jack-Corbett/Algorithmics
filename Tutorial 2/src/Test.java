import java.util.Iterator;

public class Test {

    public static void main(String[] args) {
        Ring<Integer> ring = new CircularArrayRing<>();

        for(int i=0; i<1; ++i) {
            ring.add(i);
        }

        Iterator<Integer> it = ring.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
