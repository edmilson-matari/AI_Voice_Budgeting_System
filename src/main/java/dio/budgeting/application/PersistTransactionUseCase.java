package dio.budgeting.application;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dio.budgeting.application.input.PersistTransactionInput;
import dio.budgeting.application.output.TransactionOutput;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import dio.budgeting.infrastructure.persistence.entity.UserEntity;
import dio.budgeting.infrastructure.persistence.repository.UserRepository;

@Service
public class PersistTransactionUseCase {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public PersistTransactionUseCase(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }
    @Tool(name = "persist-transactions", description = "Persist a new transaction")
    public TransactionOutput execute(@ToolParam(description = "A saved transaction") PersistTransactionInput input) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username);
        var transaction = transactionRepository.save(new Transaction(input.description(), input.amount(), input.category()), user.getId());
        return TransactionOutput.from(transaction);
    }

}
