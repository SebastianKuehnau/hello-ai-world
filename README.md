# Hello AI World

A Vaadin + Spring Boot starter that demonstrates an AI-powered chat experience using Spring AI. It provides a simple
chat UI and streams responses from a configured LLM provider with conversation memory.

## Tech Stack

- Java 21
- Spring Boot
- Vaadin Flow
- Spring AI (ChatClient with conversation memory)
- Spring Data JPA
- Maven (Wrapper included)

## Prerequisites

- Java Development Kit (JDK) 21
- Internet access (required for downloading frontend tooling and calling the AI provider)
- Maven is not required globally (the Maven Wrapper `./mvnw` is included)

Note: Vaadin manages required frontend tooling automatically during development builds.

## Configure your AI provider

This project uses Spring AI. You must provide credentials and a model for your preferred provider.

Add the relevant properties (for example in `src/main/resources/application.properties` or via environment variables).

- Example: OpenAI

    ```
    spring.ai.openai.api-key=${OPENAI_API_KEY} 
    spring.ai.openai.chat.options.model=gpt-4o-mini
    ```

## Running the application

Open the project in an IDE. You can download the [IntelliJ community edition](https://www.jetbrains.com/idea/download)
if you do not have a suitable IDE already.
Once opened in the IDE, locate the `Application` class and run the main method using "Debug".

For more information on installing in various IDEs,
see [how to import Vaadin projects to different IDEs](https://vaadin.com/docs/latest/getting-started/import).

If you install the Vaadin plugin for IntelliJ, you should instead launch the `Application` class using "Debug using
HotswapAgent" to see updates in the Java code immediately reflected in the browser.

## Deploying to Production

The project is a standard Maven project. To create a production build, call

```
./mvnw clean package -Pproduction
```

If you have Maven globally installed, you can replace `./mvnw` with `mvn`.

This will build a JAR file with all the dependencies and front-end resources,ready to be run. The file can be found in
the `target` folder after the build completes.
You then launch the application using

```
java -jar target/hello-ai-world-1.0-SNAPSHOT.jar
```

## What you get

- A chat page as the default route that:
    - Displays a scrolling message list.
    - Streams tokens from the AI provider into the UI in real time.
    - Uses conversation memory so the model can consider prior messages.

## Project structure

- `src/main/java`
    - `.../Application.java` – Spring Boot entry point and application setup.
    - `.../views/...` – Vaadin views. The main chat UI lives here and is mapped to the root route.
    - `.../services/...` – Application services (e.g., the AI chat service that wraps Spring AI ChatClient and memory).
    - `.../data/...` – Entities and repositories (JPA). The app can initialize demo data only when the database is
      empty.
- `src/main/resources`
    - `application.properties` – Configuration (including Spring AI properties).
    - Optional SQL init scripts (executed only when the DB is empty).
- `src/main/frontend`
    - `themes/` – Custom theme for Vaadin.
    - `views/` – Client-side assets (if any).

## Troubleshooting

- AI responses don’t appear:
    - Ensure your API key and model/deployment are correctly configured.
    - Check application logs for provider errors.
- Frontend build is slow on first run:
    - Vaadin downloads and prepares tooling the first time; subsequent runs are faster.
- Port conflicts:
    - Change the port via `server.port` in `application.properties` or `--server.port=9090`.

## Useful links

- Vaadin Docs: https://vaadin.com/docs
- Spring Boot Docs: https://docs.spring.io/spring-boot/docs/current/reference/html/
- Spring AI Docs: https://docs.spring.io/spring-ai/reference/
