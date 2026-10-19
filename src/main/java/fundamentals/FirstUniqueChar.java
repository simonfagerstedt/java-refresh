package fundamentals;

import java.util.*;

public class FirstUniqueChar {

    static Optional<Character> firstUniqueChar(String s) {
        Map<Character, Integer> foundChars = new LinkedHashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            foundChars.merge(c, 1, Integer::sum);
        }
        return foundChars.entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst();
    }


    public static void main(String[] args) {
        System.out.println(firstUniqueChar("swiss")); //        "swiss"   -> w
        System.out.println(firstUniqueChar("aabbcc")); //        "aabbcc"  -> empty
        System.out.println(firstUniqueChar("aabbccd")); //        "aabbccd"  -> d
        System.out.println(firstUniqueChar("aaa")); //        "aaa"     -> empty
        System.out.println(firstUniqueChar("leetcode")); //        "leetcode" -> l
        System.out.println(firstUniqueChar("")); //        ""        -> empty
        System.out.println(firstUniqueChar("123abc123def456abcdef")); //        "123abc123def456abcdef"        -> 4

    }
}