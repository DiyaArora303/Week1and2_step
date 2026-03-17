import java.util.*;

// Q9 - Two Sum Variants for Transactions
class Q9 {

    static class Transaction {
        int id;
        int amount;

        Transaction(int id, int amount) {
            this.id = id;
            this.amount = amount;
        }
    }

    public List<int[]> twoSum(List<Transaction> list, int target) {
        Map<Integer, Transaction> map = new HashMap<>();
        List<int[]> result = new ArrayList<>();

        for (Transaction t : list) {
            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                result.add(new int[]{map.get(complement).id, t.id});
            }

            map.put(t.amount, t);
        }
        return result;
    }

    public static void main(String[] args) {
        Q9 q = new Q9();

        List<Transaction> list = Arrays.asList(
                new Transaction(1, 500),
                new Transaction(2, 300),
                new Transaction(3, 200)
        );

        System.out.println(q.twoSum(list, 500));
    }
}