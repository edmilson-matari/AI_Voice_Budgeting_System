package dio.budgeting.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private TransactionId id;
    private String description;
    private double amount;
    private Category category;

    public Transaction(String description, double amount, Category category) {
        this.id = new TransactionId();
        this.amount = amount;
        this.category = category;
        this.description = description;
    }
}
