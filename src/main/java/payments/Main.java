package payments;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

sealed interface Payment permits CardPayment, InvoicePayment, BankTransfer, Crypto {
    BigDecimal amount();
}
record Crypto(String wallet, BigDecimal amount) implements Payment {};
record CardPayment(String cardNumber, BigDecimal amount) implements Payment {
    CardPayment {
        if (amount == null || amount.signum() < 0)
            throw new IllegalArgumentException("Invalid amount: " + amount);
    }
}

record InvoicePayment(String reference, BigDecimal amount, LocalDate dueDate) implements Payment {
    InvoicePayment {
        if (amount == null || amount.signum() < 0)
            throw new IllegalArgumentException("Invalid amount: " + amount);
    }
}

record BankTransfer(String iban, BigDecimal amount) implements Payment {
    BankTransfer{
        if (amount == null || amount.signum() < 0)
            throw new IllegalArgumentException("Invalid amount: " + amount);
    }
}

public class Main {
    static BigDecimal fee(Payment p) {
        return switch (p) {
            case CardPayment card -> card.amount().multiply(new BigDecimal("0.02"));
            case InvoicePayment(String ref, BigDecimal amount, LocalDate due) when due.isBefore(LocalDate.now()) ->
                    amount.multiply(new BigDecimal("0.05"));
            case InvoicePayment invoice -> BigDecimal.ZERO;
            case BankTransfer bank -> new BigDecimal("1.00");
            case Crypto crypto -> new BigDecimal("2.50");
        };
    }

    public static void main(String[] args) {
        var payments = List.of(
                new CardPayment("4111111111111111", new BigDecimal("100")),
                new InvoicePayment("INV-42", new BigDecimal("500"), LocalDate.now().minusDays(3)),
                new BankTransfer("SE3550000000054910000003", new BigDecimal("250"))
        );

        for (Payment p : payments) {
            System.out.println(p.getClass().getSimpleName() + " fee: " + fee(p));
        }

        BigDecimal totalFees = payments.stream()
                .map(Main::fee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Total fees: " + totalFees);

        String receipt = """
                {
                    "first": "%s",
                    "last": "%s",
                    "count": %d
                }""".formatted(
                payments.getFirst().getClass().getSimpleName(),
                payments.getLast().getClass().getSimpleName(),
                payments.size()
        );

        System.out.println(receipt);

        Map<String, List<Payment>> byType = payments.stream()
                .collect(Collectors.groupingBy(p -> p.getClass().getSimpleName()));
        byType.forEach((type,list) -> System.out.println(type + ": " + list));
    }
}