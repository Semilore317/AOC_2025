package Day_8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Playground {

    // --- 1. Point Class ---
    static class Point {
        int id;
        int x, y, z;

        public Point(int id, int x, int y, int z) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        //

// [Image of 3D Distance Formula]

        public double distanceTo(Point other) {
            return Math.sqrt(Math.pow(this.x - other.x, 2) +
                    Math.pow(this.y - other.y, 2) +
                    Math.pow(this.z - other.z, 2));
        }
    }

    // --- 2. Connection Class ---
    static class Connection implements Comparable<Connection> {
        Point p1, p2;
        double distance;

        public Connection(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
            this.distance = p1.distanceTo(p2);
        }

        @Override
        public int compareTo(Connection other) {
            return Double.compare(this.distance, other.distance);
        }
    }

    // --- 3. Circuit Manager (Union-Find) ---
    static class CircuitManager {
        int[] parent;
        int[] size;

        public CircuitManager(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int i) {
            if (parent[i] == i) return i;
            return parent[i] = find(parent[i]);
        }

        public void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI != rootJ) {
                // Merge smaller into larger
                if (size[rootI] < size[rootJ]) {
                    int temp = rootI; rootI = rootJ; rootJ = temp;
                }
                parent[rootJ] = rootI;
                size[rootI] += size[rootJ];
            }
        }

        public List<Integer> getCircuitSizes() {
            List<Integer> sizes = new ArrayList<>();
            for (int i = 0; i < parent.length; i++) {
                if (parent[i] == i) {
                    sizes.add(size[i]);
                }
            }
            return sizes;
        }
    }

    // --- 4. Main Execution ---
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/Day_8/input.txt");
        Scanner scanner = new Scanner(file);
        List<Point> points = new ArrayList<>();
        int idCounter = 0;

        // Parse Input
        while(scanner.hasNextLine()){
            String line = scanner.nextLine().trim();
            if(line.isEmpty()) continue;
            String[] parts = line.split("\\s*,\\s*");
            points.add(new Point(
                    idCounter++,
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            ));
        }
        scanner.close();

        // Generate All Edges
        List<Connection> allConnections = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                allConnections.add(new Connection(points.get(i), points.get(j)));
            }
        }

        // Sort by shortest distance
        Collections.sort(allConnections);

        // Process EXACTLY the top 1000 pairs
        CircuitManager dsu = new CircuitManager(points.size());

        int limit = 1000; // <--- THIS IS THE CRITICAL NUMBER FROM THE PROMPT
        int attempts = Math.min(limit, allConnections.size());

        for (int i = 0; i < attempts; i++) {
            Connection c = allConnections.get(i);
            dsu.union(c.p1.id, c.p2.id);
        }

        // Calculate Result
        List<Integer> sizes = dsu.getCircuitSizes();
        sizes.sort(Collections.reverseOrder());

        if (sizes.size() >= 3) {
            long product = (long) sizes.get(0) * sizes.get(1) * sizes.get(2);
            System.out.println("Top 3 Sizes: " + sizes.get(0) + ", " + sizes.get(1) + ", " + sizes.get(2));
            System.out.println("Result: " + product);
        } else {
            System.out.println("Not enough circuits remaining (Count: " + sizes.size() + ")");
            if(sizes.size() > 0) System.out.println("Largest: " + sizes.get(0));
        }
    }
}