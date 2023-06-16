package org.example;


import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "URL Shortener",
                version = "v1",
                description = "This app provides REST APIs for URL shortener",
                contact = @Contact(
                        name = "Adeola ADEKUNLE",
                        email = "adeolaae1@gmail.com"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:7000",
                        description = "DEV Server"
                )
        },
        externalDocs = @ExternalDocumentation(
                url = "https://bit.ly/revemmanuel-urlshortener",
                description = "Postman Documentation")
)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        log.info("App started successfully!");
    }
}
