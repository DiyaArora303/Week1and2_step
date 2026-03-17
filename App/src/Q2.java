import java.util.*;
import java.util.concurrent.*;

// Q2 - Flash Sale Inventory Manager with concurrency control
class Q2 {

    // productId -> stock
    private ConcurrentHashMap<String, Integer> stockMap;

    // productId -> waiting list (FIFO)
    private ConcurrentHashMap<String, Queue<Integer>> waitingList;

    public Q2() {
        stockMap = new ConcurrentHashMap<>();
        waitingList = new ConcurrentHashMap<>();

        // Initialize product with 100 units
        stockMap.put("IPHONE15_256GB", 100);
        waitingList.put("IPHONE15_256GB", new LinkedList<>());
    }

    // O(1) stock check
    public int checkStock(String productId) {
        return stockMap.getOrDefault(productId, 0);
    }

    // Thread-safe purchase
    public String purchaseItem(String productId, int userId) {

        synchronized (productId.intern()) { // lock per product

            int stock = stockMap.getOrDefault(productId, 0);

            if (stock > 0) {
                stockMap.put(productId, stock - 1);
                return "Success! Remaining stock: " + (stock - 1);
            } else {
                Queue<Integer> queue = waitingList.get(productId);
                queue.offer(userId);
                return "Out of stock! Added to waiting list. Position: " + queue.size();
            }
        }
    }

    // Process restock and serve waiting list
    public void restock(String productId, int quantity) {

        synchronized (productId.intern()) {

            stockMap.put(productId, stockMap.getOrDefault(productId, 0) + quantity);

            Queue<Integer> queue = waitingList.get(productId);

            while (!queue.isEmpty() && stockMap.get(productId) > 0) {
                int user = queue.poll();
                stockMap.put(productId, stockMap.get(productId) - 1);
                System.out.println("User " + user + " got item from waiting list!");
            }
        }
    }

    // Demo
    public static void main(String[] args) {
        Q2 system = new Q2();

        System.out.println(system.checkStock("IPHONE15_256GB"));

        // Simulate multiple users
        for (int i = 1; i <= 105; i++) {
            System.out.println(system.purchaseItem("IPHONE15_256GB", i));
        }

        System.out.println("Restocking...");
        system.restock("IPHONE15_256GB", 10);
    }
}