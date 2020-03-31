public class GraphTest {

    public static void main(String[] args) {
        for (int i = 12; i <= 17; i++) {
            long time_prev = System.nanoTime();

            Graph graph = new Graph(i, 0.5);
            Colouring colouring = graph.bestColouring(3);
            double time = (System.nanoTime()-time_prev)/1000000000.0;
            System.out.println("It took: " + time + " seconds for size " + i);
            //graph.show(colouring);
        }
    }

}
