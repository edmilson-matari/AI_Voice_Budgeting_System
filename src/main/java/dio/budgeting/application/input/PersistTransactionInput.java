package dio.budgeting.application.input;

import dio.budgeting.domain.Category;

public record PersistTransactionInput(String description, double amount, Category category) {
    
}
