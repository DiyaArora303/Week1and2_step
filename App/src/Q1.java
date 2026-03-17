import java.util.*;

// Q1 - Username Availability Checker using HashMap
class Q1 {

    // username -> userId
    private HashMap<String, Integer> users;

    // username -> attempt count
    private HashMap<String, Integer> attempts;

    public Q1() {
        users = new HashMap<>();
        attempts = new HashMap<>();

        // Preload some existing users
        users.put("john_doe", 1);
        users.put("admin", 2);
        users.put("user123", 3);
    }

    // Check availability in O(1)
    public boolean checkAvailability(String username) {
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);
        return !users.containsKey(username);
    }

    // Suggest alternatives
    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();

        if (!users.containsKey(username)) {
            suggestions.add(username);
            return suggestions;
        }

        // Simple strategies
        for (int i = 1; i <= 5; i++) {
            String suggestion = username + i;
            if (!users.containsKey(suggestion)) {
                suggestions.add(suggestion);
            }
        }

        // Replace '_' with '.'
        if (username.contains("_")) {
            String alt = username.replace("_", ".");
            if (!users.containsKey(alt)) {
                suggestions.add(alt);
            }
        }

        return suggestions;
    }

    // Get most attempted username
    public String getMostAttempted() {
        String maxUser = null;
        int maxCount = 0;

        for (String user : attempts.keySet()) {
            if (attempts.get(user) > maxCount) {
                maxCount = attempts.get(user);
                maxUser = user;
            }
        }

        return maxUser + " (" + maxCount + " attempts)";
    }

    // Register user
    public void registerUser(String username, int userId) {
        if (!users.containsKey(username)) {
            users.put(username, userId);
            System.out.println(username + " registered successfully!");
        } else {
            System.out.println("Username already taken!");
        }
    }

    // Demo
    public static void main(String[] args) {
        Q1 system = new Q1();

        System.out.println(system.checkAvailability("john_doe")); // false
        System.out.println(system.checkAvailability("jane_smith")); // true

        System.out.println(system.suggestAlternatives("john_doe"));

        system.checkAvailability("admin");
        system.checkAvailability("admin");
        system.checkAvailability("admin");

        System.out.println(system.getMostAttempted());

        system.registerUser("jane_smith", 10);
    }
}