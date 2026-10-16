package payments;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

abstract class Payment {
    protected final BigDecimal amount;
    Payment(BigDecimal amount) { this.amount = amount; }
    BigDecimal getAmount() { return amount; }
}

class CardPayment extends Payment {
    private final String cardNumber;
    CardPayment(String cardNumber, BigDecimal amount) { super(amount); this.cardNumber = cardNumber; }
    String getCardNumber() { return cardNumber; }
}

class InvoicePayment extends Payment {
    private final String reference;
    private final LocalDate dueDate;
    InvoicePayment(String reference, BigDecimal amount, LocalDate dueDate) {
        super(amount); this.reference = reference; this.dueDate = dueDate;
    }
    String getReference() { return reference; }
    LocalDate getDueDate() { return dueDate; }
}

class BankTransfer extends Payment {
    private final String iban;
    BankTransfer(String iban, BigDecimal amount) { super(amount); this.iban = iban; }
    String getIban() { return iban; }
}

public class Main {
    static BigDecimal fee(Payment p) {
        if (p instanceof CardPayment) {
            return p.getAmount().multiply(new BigDecimal("0.02"));
        } else if (p instanceof InvoicePayment) {
            InvoicePayment inv = (InvoicePayment) p;
            if (inv.getDueDate().isBefore(LocalDate.now())) {
                return inv.getAmount().multiply(new BigDecimal("0.05"));
            }
            return BigDecimal.ZERO;
        } else if (p instanceof BankTransfer) {
            return new BigDecimal("1.00");
        }
        throw new IllegalArgumentException("Unknown payment: " + p);
    }

    public static void main(String[] args) {
        List<Payment> payments = new ArrayList<Payment>();
        payments.add(new CardPayment("4111111111111111", new BigDecimal("100")));
        payments.add(new InvoicePayment("INV-42", new BigDecimal("500"), LocalDate.now().minusDays(3)));
        payments.add(new BankTransfer("SE3550000000054910000003", new BigDecimal("250")));

        for (Payment p : payments) {
            System.out.println(p.getClass().getSimpleName() + " fee: " + fee(p));
        }

        Payment first = payments.get(0);
        Payment last = payments.get(payments.size() - 1);
        String receipt = "{\n" +
                "  \"first\": \"" + first.getClass().getSimpleName() + "\",\n" +
                "  \"last\": \"" + last.getClass().getSimpleName() + "\",\n" +
                "  \"count\": " + payments.size() + "\n" +
                "}";
        System.out.println(receipt);
    }
}