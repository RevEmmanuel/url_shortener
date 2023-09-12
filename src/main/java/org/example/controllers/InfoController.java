package org.example.controllers;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@OpenAPIDefinition
@RequestMapping(value = "/")
public class InfoController {

    @Operation(summary = "Landing Page", description = "Information about the project")
    @GetMapping("")
    public String getLink() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>URL Shortener Home Page</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            margin: 0;
                            padding: 20px;
                        }
                        h1 {
                            color: #333;
                        }
                        h2 {
                            color: #555;
                        }
                        p {
                            color: #777;
                        }
                        ul {
                            list-style-type: disc;
                            padding-left: 20px;
                        }
                        li {
                            margin-bottom: 10px;
                        }
                        a {
                            color: #007bff;
                            text-decoration: none;
                        }
                        a:hover {
                            text-decoration: underline;
                        }
                        .developer {
                            margin-top: 40px;
                            border-top: 1px solid #ccc;
                            padding-top: 20px;
                        }
                    </style>
                </head>
                <body>
                    <h1>URL Shortener by Adeola Adekunle</h1>
                    <p>This ia a Java Spring boot server application to handle shortening of URLs using Rest APIs.</p>
                   \s
                    <h2>Features</h2>
                    <ul>
                        <li>Sign up</li>
                        <li>Login</li>
                        <li>Create and shorten a new link</li>
                        <li>Retrieve a list of your links</li>
                        <li>Retrieve a single link</li>
                        <li>Delete a link</li>
                    </ul>
                   \s
                   \s
                    <h2>Technologies Used</h2>
                    <ul>
                        <li>Java</li>
                        <li>Springboot</li>
                        <li>PostgreSQL</li>
                        <li>Swagger Docs</li>
                        <li>JWT</li>
                    </ul>
                   \s
                    <h2>Documentation</h2>
                    <p>The API documentation is available. You can access it using the following link:</p>
                    <p><a href="https://bit.ly/revemmanuel-urlshortener" target="_blank">Postman Documentation</a></p>
                    <p><a href="https://urlshortener-production-f380.up.railway.app/swagger-ui.html" target="_blank">Swagger Documentation</a></p>
                    <p><a href="https://urlshortener-production-f380.up.railway.app" target="_blank">Landing Page</a></p>
                   \s
                    <div class="developer">
                        <h2>Developer & Engineer</h2>
                        <p>Adeola Adekunle</p>
                        <ul>
                            <li><a href="https://github.com/RevEmmanuel" target="_blank">GitHub (RevEmmanuel)</a></li>
                            <li><a href="https://twitter.com/Adeola_Ade1" target="_blank">Twitter (@Adeola_Ade1)</a></li>
                            <li><a href="https://www.instagram.com/deolaaxo/" target="_blank">Instagram (@deolaaxo)</a></li>
                            <li><a href="https://www.linkedin.com/in/adeola-adekunle-emmanuel/" target="_blank">LinkedIn (Adeola Adekunle)</a></li>
                            <li>Email: <a href="mailto:adeolaae1@gmail.com">adeolaae1@gmail.com</a></li>
                        </ul>
                    </div>
                </body>
                </html>
                """;
    }

}
