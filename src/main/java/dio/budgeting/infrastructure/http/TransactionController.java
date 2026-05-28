package dio.budgeting.infrastructure.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dio.budgeting.application.PersistTransactionUseCase;
import dio.budgeting.infrastructure.http.request.TransactionRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final PersistTransactionUseCase pTransactionUseCase;

    public TransactionController(PersistTransactionUseCase pTransactionUseCase) {
        this.pTransactionUseCase = pTransactionUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransaction(@RequestBody TransactionRequest request) {
        pTransactionUseCase.execute(request.toInput());
    }
}
