import java.util.*;

class Graph {
    private final Map<String, List<Node>> adjList;

    public Graph() {
        adjList = new HashMap<>();
    }

    public void addEdge(String source, String destination, int distance) {
        adjList.putIfAbsent(source, new ArrayList<>());
        adjList.putIfAbsent(destination, new ArrayList<>());
        adjList.get(source).add(new Node(destination, distance));
        adjList.get(destination).add(new Node(source, distance)); // Undirected Graph
    }

    public Map<String, Integer> dijkstra(String start) {
        Map<String, Integer> distances = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));

        for (String node : adjList.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }

        distances.put(start, 0);
        pq.add(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            String currLocation = current.name;

            for (Node neighbor : adjList.get(currLocation)) {
                int newDist = distances.get(currLocation) + neighbor.distance;
                if (newDist < distances.get(neighbor.name)) {
                    distances.put(neighbor.name, newDist);
                    pq.add(new Node(neighbor.name, newDist));
                }
            }
        }
        return distances;
    }
}

class Node {
    String name;
    int distance;

    Node(String name, int distance) {
        this.name = name;
        this.distance = distance;
    }
}

public class GPSNavigation {
    public static void main(String[] args) {
        Graph graph = new Graph();
        
        graph.addEdge("A", "B", 4);
        graph.addEdge("A", "C", 2);
        graph.addEdge("B", "C", 5);
        graph.addEdge("B", "D", 10);
        graph.addEdge("C", "D", 3);
        graph.addEdge("D", "E", 8);

        String start = "A";
        Map<String, Integer> shortestPaths = graph.dijkstra(start);

        System.out.println("Shortest distances from " + start + ":");
        for (Map.Entry<String, Integer> entry : shortestPaths.entrySet()) {
            System.out.println("To " + entry.getKey() + " → " + entry.getValue() + " km");
        }
    }
}
