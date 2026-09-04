# AI Voice Budgeting System API

An AI-powered budgeting API built with Spring Boot, Spring AI, JPA, MySQL, and JWT security. The application lets users register and authenticate, create and list transactions, transcribe voice input, and generate spoken responses from text.

## Highlights

- Voice-first budgeting flow with transcription and text-to-speech support.
- AI-assisted transaction handling through Spring AI tools.
- JWT-based authentication for protected endpoints.
- MySQL persistence with a JPA-backed transaction repository.
- REST API designed for automation and frontend integration.

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Web
- Spring Security
- Spring Data JPA
- Spring AI
- JWT authentication
- MySQL
- Docker Compose

## Project Structure

- `src/main/java/dio/budgeting` contains the application entry point and controllers.
- `src/main/java/dio/budgeting/application` contains use cases.
- `src/main/java/dio/budgeting/domain` contains the core domain model.
- `src/main/java/dio/budgeting/infrastructure` contains HTTP, persistence, and security adapters.
- `src/main/resources/prompt/system-message.st` contains the system prompt used by the AI chat flow.

## Prerequisites

- Java 21
- Docker and Docker Compose
- A MySQL instance if you do not use the provided compose file
- An OpenAI API key

## Configuration

The application reads the OpenAI key from the environment variable `OPEN_AI_API_KEY`.

If you need local database settings, define them in your local `application.properties` override or export them as environment variables before running the app.

Recommended local settings:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/transaction
spring.datasource.username=app
spring.datasource.password=app
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

The default JWT settings in the repository are:

```properties
jwt.secret=my-super-secret-jwt-key-with-at-least-32-characters
jwt.expiration=3600000
```

## Run with Docker

Start the database container:

```bash
docker compose -f compose.yml up -d
```

Then run the application with your OpenAI key available in the environment.

## Run Locally

1. Start MySQL using Docker Compose or your own database.
2. Set `OPEN_AI_API_KEY`.
3. Configure datasource properties if they are not already provided in your local environment.
4. Run the application:

```bash
./gradlew bootRun
```

## Authentication

The API uses JWT security. Public endpoints are limited to the auth routes and the test route. Most application endpoints require an `Authorization: Bearer <token>` header.

### Register

`POST /api/auth/signup`

Request body:

```json
{
	"username": "alice",
	"password": "secret123"
}
```

### Login

`POST /api/auth/signin`

Request body:

```json
{
	"username": "alice",
	"password": "secret123"
}
```

Response: a JWT token string.

## API Endpoints

### Transactions

| Method | Path | Description |
| --- | --- | --- |
| `POST` | `/transaction` | Create a transaction. |
| `GET` | `/transaction/{category}` | List transactions by category. |
| `POST` | `/transaction/ai` | Send audio, transcribe it, let the AI process it, and receive generated audio back. |

Transaction request body:

```json
{
	"description": "Supermarket purchase",
	"category": "GROCERIES",
	"amount": 129.9
}
```

Supported categories:

- `GROCERIES`
- `PHARMA`
- `AUTO`

### Voice Helpers

| Method | Path | Description |
| --- | --- | --- |
| `POST` | `/api/transcribe` | Transcribe uploaded audio to text. |
| `POST` | `/api/synthesize` | Convert text into MP3 audio. |

Text-to-speech request body:

```json
{
	"text": "Your budget update is ready."
}
```

## Example Requests

Create a transaction:

```bash
curl -X POST http://localhost:8080/transaction \
	-H "Content-Type: application/json" \
	-H "Authorization: Bearer YOUR_TOKEN" \
	-d '{
		"description": "Lunch",
		"category": "GROCERIES",
		"amount": 24.5
	}'
```

List transactions by category:

```bash
curl http://localhost:8080/transaction/GROCERIES \
	-H "Authorization: Bearer YOUR_TOKEN"
```

Transcribe audio:

```bash
curl -X POST http://localhost:8080/api/transcribe \
	-H "Authorization: Bearer YOUR_TOKEN" \
	-F "file=@speech.mp3"
```

Synthesize speech:

```bash
curl -X POST http://localhost:8080/api/synthesize \
	-H "Authorization: Bearer YOUR_TOKEN" \
	-H "Content-Type: application/json" \
	-d '{"text":"Budget updated successfully."}' \
	--output audio.mp3
```

## AI Flow

The app uses Spring AI to connect transcription, chat, and text-to-speech capabilities.

1. Audio is uploaded to the API.
2. The transcription model converts speech to text.
3. The chat model processes the text using the system prompt in `src/main/resources/prompt/system-message.st`.
4. The response is converted back to MP3 audio.

## Notes

- The transaction database schema is managed by Hibernate with `ddl-auto=create` in the default configuration.
- The repository currently uses a committed JWT secret for development. Replace it with a secure value for any non-local environment.
- If you change the OpenAI model settings, update the corresponding Spring AI properties in your configuration.

## Contributing

1. Fork the repository.
2. Create a feature branch.
3. Make your changes.
4. Run the application and verify the endpoints.
5. Open a pull request with a clear description of the change.

## License

No license file is included in this repository yet. Add one before publishing or distributing the project.
