package dio.budgeting.domain;

import java.util.List;

import dio.budgeting.infrastructure.persistence.entity.UserEntity;

public interface TransactionRepository {
    Transaction save(Transaction transaction, UserEntity userId);
    List<Transaction> findAllByCategory(Category category, Long userId);
    List<Transaction> findAllByUserId(Long userId);
}
