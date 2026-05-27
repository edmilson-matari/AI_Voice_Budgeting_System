package dio.budgeting;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class ChatModelController {

    private final OpenAiChatModel openAiChatModel;
    public ChatModelController(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }
    @GetMapping("/chat-model")
    public String chat(@RequestParam String prompt) {
        return this.openAiChatModel.call(prompt);
    }

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World";
    }
}
