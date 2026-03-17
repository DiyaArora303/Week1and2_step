import java.util.*;

// Q4 - Plagiarism Detection using n-grams and HashMap
class Q4 {

    private Map<String, Set<String>> ngramIndex = new HashMap<>();
    private int N = 5;

    // Add document to index
    public void indexDocument(String docId, String content) {
        List<String> ngrams = generateNGrams(content);

        for (String gram : ngrams) {
            ngramIndex.putIfAbsent(gram, new HashSet<>());
            ngramIndex.get(gram).add(docId);
        }
    }

    // Analyze document
    public void analyzeDocument(String docId, String content) {
        List<String> ngrams = generateNGrams(content);

        Map<String, Integer> matchCount = new HashMap<>();

        for (String gram : ngrams) {
            if (ngramIndex.containsKey(gram)) {
                for (String otherDoc : ngramIndex.get(gram)) {
                    matchCount.put(otherDoc, matchCount.getOrDefault(otherDoc, 0) + 1);
                }
            }
        }

        for (String doc : matchCount.keySet()) {
            int matches = matchCount.get(doc);
            double similarity = (matches * 100.0) / ngrams.size();

            System.out.println("Match with " + doc + ": " + similarity + "%");
        }
    }

    // Generate n-grams
    private List<String> generateNGrams(String text) {
        List<String> grams = new ArrayList<>();
        String[] words = text.split("\\s+");

        for (int i = 0; i <= words.length - N; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < N; j++) {
                sb.append(words[i + j]).append(" ");
            }
            grams.add(sb.toString().trim());
        }

        return grams;
    }

    public static void main(String[] args) {
        Q4 system = new Q4();

        system.indexDocument("doc1", "this is a sample document for plagiarism detection system");
        system.indexDocument("doc2", "this is another sample document used for testing plagiarism");

        system.analyzeDocument("doc3", "this is a sample document for testing plagiarism detection");
    }
}