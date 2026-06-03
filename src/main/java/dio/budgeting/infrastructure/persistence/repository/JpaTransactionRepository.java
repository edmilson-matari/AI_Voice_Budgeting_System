package dio.budgeting.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import dio.budgeting.domain.Category;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import dio.budgeting.infrastructure.persistence.entity.TransactionEntity;
import dio.budgeting.infrastructure.persistence.entity.UserEntity;

@Repository
public class JpaTransactionRepository implements TransactionRepository {

    private final TransactionEntityRepository transactionEntityRepository;

    public JpaTransactionRepository(TransactionEntityRepository transactionEntityRepository) {
        this.transactionEntityRepository = transactionEntityRepository;
    }

    @Override
    public Transaction save(Transaction transaction, UserEntity userId) {
        var entity = TransactionEntity.from(transaction, userId);
        return transactionEntityRepository.save(entity).toDomain();
    }

    @Override
    public List<Transaction> findAllByCategory(Category category, Long userId) {
        return transactionEntityRepository.findAllByCategoryAndUserId(category, userId)
                .stream()
                .map(TransactionEntity::toDomain)
                .toList();
    }

    @Override
    public List<Transaction> findAllByUserId(Long userId) {
        return transactionEntityRepository.findAllByUserId(userId)
                .stream()
                .map(TransactionEntity::toDomain)
                .toList();
    }

}
