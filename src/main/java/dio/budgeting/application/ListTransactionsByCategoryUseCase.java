package dio.budgeting.application;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dio.budgeting.application.output.TransactionOutput;
import dio.budgeting.domain.Category;
import dio.budgeting.domain.TransactionRepository;
import dio.budgeting.infrastructure.persistence.entity.UserEntity;
import dio.budgeting.infrastructure.persistence.repository.UserRepository;

@Service
public class ListTransactionsByCategoryUseCase {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public ListTransactionsByCategoryUseCase(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Tool(name = "find-transactions", description = "List transactions by category")
    public List<TransactionOutput> execute(Category category) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username);
        return transactionRepository.findAllByCategory(category, user.getId()).stream().map(TransactionOutput::from).toList();
    }

    @Tool(name = "get-all-transactions")
    public List<TransactionOutput> execute() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username);
        return transactionRepository.findAllByUserId(user.getId()).stream().map(TransactionOutput::from).toList();
    }
}
