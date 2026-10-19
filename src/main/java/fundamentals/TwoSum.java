package fundamentals;

import java.util.*;

public class TwoSum {

    record Pair(int i, int j) { }

    static Optional<Pair> twoSum(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer idx = seen.get(target - nums[i]);
            if (idx != null) {
                return Optional.of(new Pair(idx, i));
            }
            seen.put(nums[i], i);
        }
        return Optional.empty();                                // the empty box
    }

    public static void main(String[] args) {
        System.out.println(twoSum(new int[]{2, 7, 11, 15}, 9));   // Optional[Pair[i=0, j=1]]
        System.out.println(twoSum(new int[]{3, 2, 4}, 6));        // Optional[Pair[i=1, j=2]]
        System.out.println(twoSum(new int[]{3, 3}, 6));           // Optional[Pair[i=0, j=1]]
        System.out.println(twoSum(new int[]{1, 2, 3}, 100));      // Optional.empty
        System.out.println(twoSum(new int[]{1, 2, 3, 4, 5, 7, 7, 7, 8, 22, 18, 93, 5, 6, 99}, 100));      // Optional[Pair[i=7, j=11]]
    }
}
