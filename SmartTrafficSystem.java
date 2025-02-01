import java.util.*;

class Road {
    String destination;
    int time; // Time in minutes

    public Road(String destination, int time) {
        this.destination = destination;
        this.time = time;
    }
}

class TrafficGraph {
    private final Map<String, List<Road>> cityMap;

    public TrafficGraph() {
        cityMap = new HashMap<>();
    }

    // Add roads with traffic weight (time in minutes)
    public void addRoad(String source, String destination, int time) {
        cityMap.putIfAbsent(source, new ArrayList<>());
        cityMap.putIfAbsent(destination, new ArrayList<>());
        cityMap.get(source).add(new Road(destination, time));
        cityMap.get(destination).add(new Road(source, time)); // Undirected road
    }

    // Dijkstra’s algorithm to find the fastest route
    public Map<String, Integer> findFastestRoute(String start) {
        Map<String, Integer> travelTimes = new HashMap<>();
        PriorityQueue<Road> pq = new PriorityQueue<>(Comparator.comparingInt(r -> r.time));

        for (String place : cityMap.keySet()) {
            travelTimes.put(place, Integer.MAX_VALUE);
        }
        travelTimes.put(start, 0);
        pq.add(new Road(start, 0));

        while (!pq.isEmpty()) {
            Road current = pq.poll();
            String location = current.destination;

            for (Road road : cityMap.get(location)) {
                int newTime = travelTimes.get(location) + road.time;
                if (newTime < travelTimes.get(road.destination)) {
                    travelTimes.put(road.destination, newTime);
                    pq.add(new Road(road.destination, newTime));
                }
            }
        }
        return travelTimes;
    }
}

public class SmartTrafficSystem {
    public static void main(String[] args) {
        TrafficGraph graph = new TrafficGraph();

        // Simulating a city with roads and traffic conditions (time in minutes)
        graph.addRoad("A", "B", 5);
        graph.addRoad("A", "C", 8);
        graph.addRoad("B", "D", 7);
        graph.addRoad("C", "D", 3);
        graph.addRoad("D", "E", 6);
        graph.addRoad("B", "E", 15);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter starting point: ");
        String start = scanner.next();
        System.out.print("Enter destination: ");
        String destination = scanner.next();

        Map<String, Integer> fastestRoutes = graph.findFastestRoute(start);

        System.out.println("\n🚗 Fastest Route from " + start + " to " + destination + ": " + fastestRoutes.get(destination) + " minutes");
    }
}
