package fundamentals;

import java.util.*;

public class Bugs {
    static class Customer {
        private final String email;
        private String name;
        Customer(String email, String name) { this.email = Objects.requireNonNull(email, "email must not be null"); this.name = name; }
        void setName(String name) { this.name = name; }
        @Override public boolean equals(Object o) {
            return o instanceof Customer c && email.equals(c.email);
        }
        @Override public int hashCode() { return email.hashCode(); }
    }

    record Point(int x, int y) { }

    public static void main(String[] args) {
        // Bug 1: equals without hashCode
        Set<Customer> customers = new HashSet<>();
        customers.add(new Customer("anna@example.com", "Anna"));
        customers.add(new Customer("anna@example.com", "Anna A."));
        System.out.println("Bug 1 - customers in set (expected 1): " + customers.size());

        // Bug 2: mutable key
        Map<Point, String> labels = new HashMap<>();
        Point p = new Point(1, 2);
        labels.put(p, "start");
        System.out.println("Bug 2 - label for p (expected start): " + labels.get(p));

        // Bug 3: modifying a list while iterating
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        numbers.removeIf(n -> n % 2 == 0);
        System.out.println("Bug 3 - odd numbers: " + numbers);

        // Bug 4: comparing Integers with ==
        Integer a = 127, b = 127, c = 128, d = 128;
        System.out.println("Bug 4 - Objects.equals(127, 127): " + (Objects.equals(a, b)) + ", Objects.equals(128, 128): " + (Objects.equals(c, d)));
    }
}