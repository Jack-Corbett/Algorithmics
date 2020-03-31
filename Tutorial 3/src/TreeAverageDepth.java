import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class TreeAverageDepth {

    public static void main(String[] args) {
        new TreeAverageDepth();
    }

    private TreeAverageDepth() {
        Random random = new Random();
        try {
            // Use a print writer and string builder to output the results to a file
            PrintWriter pw = new PrintWriter(new File("averageDepth.csv"));
            StringBuilder sb = new StringBuilder();
            // Perform three trials and take the median
            for (int i = 0; i < 10000; i++) {
                BinarySearchTree bst = new BinarySearchTree();
                for (int j = 0; j < i; j++) {
                /* Adds a random number between 0-99, this is what leads to sporadic results as the depth depends
                   greatly on the distribution of these random values which change every time. */
                    bst.add(random.nextInt());
                }
                double averageDepth = bst.averageDepth(bst.getRoot(), 0);
                // Output result to the CSV file to make it easy to graph
                sb.append(i).append(",").append(averageDepth).append("\n");
                // Output result to the terminal for each number of inputs
                System.out.println("Number of inputs: " + i + " Average Depth: " + averageDepth);
            }
            // Write the data to the file
            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            System.err.println("Writing to CSV file failed");
            e.getMessage();
        }
    }
}