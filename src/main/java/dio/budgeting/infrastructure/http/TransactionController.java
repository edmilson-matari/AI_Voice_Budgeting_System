package dio.budgeting.infrastructure.http;

import java.util.List;

import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dio.budgeting.application.ListTransactionsByCategoryUseCase;
import dio.budgeting.application.PersistTransactionUseCase;
import dio.budgeting.domain.Category;
import dio.budgeting.infrastructure.http.request.TransactionRequest;
import dio.budgeting.infrastructure.http.response.TransactionResponse;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final PersistTransactionUseCase pTransactionUseCase;
    private final TranscriptionModel transcriptionModel;
    private final ListTransactionsByCategoryUseCase listTransactionsByCategoryUseCase;

    public TransactionController(PersistTransactionUseCase pTransactionUseCase, ListTransactionsByCategoryUseCase listTransactionsByCategoryUseCase, TranscriptionModel transcriptionModel) {
        this.pTransactionUseCase = pTransactionUseCase;
        this.transcriptionModel = transcriptionModel;
        this.listTransactionsByCategoryUseCase = listTransactionsByCategoryUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransaction(@RequestBody TransactionRequest request) {
        pTransactionUseCase.execute(request.toInput());
    }
    
    @PostMapping(value = "/ai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String transcribe(@RequestParam("file") MultipartFile file) {
        var resource = file.getResource();
        var prompt = transcriptionModel.transcribe(resource);
        return prompt;
    }

    @GetMapping("/{category}")
    public List<TransactionResponse> readTransactions(@PathVariable Category category) {
        return listTransactionsByCategoryUseCase.execute(category).stream().map(TransactionResponse::from).toList();
    }
}
