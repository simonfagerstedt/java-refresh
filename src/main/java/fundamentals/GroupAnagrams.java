package fundamentals;

import java.util.*;
import java.util.stream.Collectors;

public class GroupAnagrams {

    static List<List<String>> groupAnagrams(String[] words) {
        Map<String, List<String>> groups = new HashMap<>();
        for (String word : words) {
            groups.computeIfAbsent(key(word), k -> new ArrayList<>()).add(word);
        }
        return new ArrayList<>(groups.values());
    }

    static List<List<String>> groupAnagramsStream(String[] words) {
        return new ArrayList<>(
                Arrays.stream(words)
                        .collect(Collectors.groupingBy(GroupAnagrams::key))
                        .values());
    }

    static String key(String word) {
        char[] cs = word.toCharArray();
        Arrays.sort(cs);
        return new String(cs);
    }

    public static void main(String[] args) {

        List<String[]> tests = List.of(
                new String[]{"eat", "tea", "tan", "ate", "nat", "bat"},
                new String[]{},
                new String[]{"eat"},
                new String[]{"abcd","abdc","acbd","acdb","adbc","adcb","abab","abab","abba","bbaa","aabb","aaaa"}
        );

        for (String[] test : tests) {
            var withoutStreams = groupAnagrams(test);
            var withStreams = groupAnagramsStream(test);
            System.out.println("Without stream: " + withoutStreams);
            System.out.println("With stream: " + withStreams);
            if (Objects.equals(new HashSet<>(withoutStreams), new HashSet<>(withStreams))) {
                System.out.println("MATCH!");
            } else {
                System.out.println("The outputs don't match... :(");
            }
            System.out.println();
        }
    }
}