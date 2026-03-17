import java.util.*;

// Q5 - Real-Time Analytics Dashboard
class Q5 {

    private Map<String, Integer> pageViews = new HashMap<>();
    private Map<String, Set<String>> uniqueVisitors = new HashMap<>();
    private Map<String, Integer> sourceCount = new HashMap<>();

    public void processEvent(String url, String userId, String source) {

        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        uniqueVisitors.putIfAbsent(url, new HashSet<String>());
        uniqueVisitors.get(url).add(userId);

        sourceCount.put(source, sourceCount.getOrDefault(source, 0) + 1);
    }

    public void getDashboard() {

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<Map.Entry<String, Integer>>(
                        new Comparator<Map.Entry<String, Integer>>() {
                            public int compare(Map.Entry<String, Integer> a,
                                               Map.Entry<String, Integer> b) {
                                return b.getValue() - a.getValue();
                            }
                        });

        pq.addAll(pageViews.entrySet());

        System.out.println("Top Pages:");
        int count = 0;

        while (!pq.isEmpty() && count < 10) {
            Map.Entry<String, Integer> entry = pq.poll();

            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println(url + " - " + views + " views (" + unique + " unique)");
            count++;
        }

        System.out.println("\nTraffic Sources:");

        int total = 0;
        for (int val : sourceCount.values()) {
            total += val;
        }

        for (String src : sourceCount.keySet()) {
            double percent = (sourceCount.get(src) * 100.0) / total;
            System.out.println(src + ": " + percent + "%");
        }
    }

    public static void main(String[] args) {
        Q5 system = new Q5();

        system.processEvent("/news", "u1", "google");
        system.processEvent("/news", "u2", "facebook");
        system.processEvent("/sports", "u1", "direct");
        system.processEvent("/news", "u1", "google");

        system.getDashboard();
    }
}