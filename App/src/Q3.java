import java.util.*;

// Q3 - DNS Cache with TTL and LRU eviction
class Q3 {

    // Entry class
    static class DNSEntry {
        String ip;
        long expiryTime;

        DNSEntry(String ip, long ttlSeconds) {
            this.ip = ip;
            this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    // LRU Cache using LinkedHashMap
    private LinkedHashMap<String, DNSEntry> cache;

    private int capacity;

    // Stats
    private int hits = 0;
    private int misses = 0;

    public Q3(int capacity) {
        this.capacity = capacity;

        this.cache = new LinkedHashMap<String, DNSEntry>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                return size() > Q3.this.capacity;
            }
        };
    }

    // Resolve domain
    public String resolve(String domain) {

        DNSEntry entry = cache.get(domain);

        if (entry != null) {
            if (!entry.isExpired()) {
                hits++;
                return "Cache HIT → " + entry.ip;
            } else {
                cache.remove(domain); // expired
            }
        }

        // Cache MISS
        misses++;
        String ip = queryUpstreamDNS(domain);

        // Assume TTL = 5 seconds for demo
        cache.put(domain, new DNSEntry(ip, 5));

        return "Cache MISS → " + ip;
    }

    // Simulate DNS lookup
    private String queryUpstreamDNS(String domain) {
        // Fake IP generation
        return "192.168.1." + new Random().nextInt(255);
    }

    // Cache stats
    public void getCacheStats() {
        int total = hits + misses;
        double hitRate = total == 0 ? 0 : (hits * 100.0 / total);

        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
        System.out.println("Hit Rate: " + hitRate + "%");
    }

    // Demo
    public static void main(String[] args) throws InterruptedException {

        Q3 dns = new Q3(3);

        System.out.println(dns.resolve("google.com")); // MISS
        System.out.println(dns.resolve("google.com")); // HIT

        Thread.sleep(6000); // wait for expiry

        System.out.println(dns.resolve("google.com")); // EXPIRED → MISS

        dns.resolve("yahoo.com");
        dns.resolve("bing.com");
        dns.resolve("duckduckgo.com"); // triggers LRU eviction

        dns.getCacheStats();
    }
}