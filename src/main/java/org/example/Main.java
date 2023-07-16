package org.example;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.example.data.models.Link;
import org.example.data.repositories.LinkRepository;
import org.example.security.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@Slf4j
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
                ),
                @Server(
                        url = "https://urlshortener-production-3315.up.railway.app",
                        description = "PROD server"
                )
        },
        externalDocs = @ExternalDocumentation(
                url = "https://bit.ly/revemmanuel-urlshortener",
                description = "Postman Documentation"
        )
)
@EnableAsync
public class Main {

    @Autowired
    private LinkRepository linkRepository;


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        log.info("::::::Server Running::::::");
    }

    @Scheduled(fixedRate =  60 * 60 * 1000)
    private void getAllLinkNames() {
        AppUtils.setLinkNames(linkRepository.findAll().stream().map(Link::getLinkName).toList());
    }

    @Scheduled(initialDelay = 0, fixedDelay = Long.MAX_VALUE)
    public void runOnStartUp() {
        getAllLinkNames();
    }

}
