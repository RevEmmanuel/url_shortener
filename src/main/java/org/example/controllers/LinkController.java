package org.example.controllers;

import java.util.List;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.example.data.dtos.requests.CreateLinkRequest;
import org.example.data.dtos.requests.UpdateLinkRequest;
import org.example.data.dtos.responses.LinkDTO;
import org.example.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@OpenAPIDefinition
@RequestMapping(value = "/api/v1/link")
public class LinkController {

    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @Operation(summary = "Create new link", description = "Endpoint to create a new shortened link")
    @PostMapping("")
    public ResponseEntity<LinkDTO> createNewLink(@RequestBody @Valid CreateLinkRequest request) {
        return new ResponseEntity<>(linkService.createLink(request), HttpStatus.OK);
    }

    @Operation(summary = "Get all links", description = "Endpoint to find all links shortened by a user")
    @GetMapping("/all")
    public ResponseEntity<List<LinkDTO>> getAllLinks() {
        return new ResponseEntity<>(linkService.getAllLinks(), HttpStatus.OK);
    }

    @Operation(summary = "Get link", description = "Find a link by it's ID")
    @GetMapping("/find/{id}")
    public ResponseEntity<LinkDTO> getLink(
            @Parameter(name = "id", description = "The id of the link")
            @PathVariable Long id) {
        return new ResponseEntity<>(linkService.getLink(id), HttpStatus.OK);
    }

    @Operation(summary = "Find link by title", description = "Find a link by it's title")
    @GetMapping("find/{title}")
    public ResponseEntity<LinkDTO> findLinkByTitle(
            @Parameter(name = "title", description = "The title of the link")
            @PathVariable String title) {
        return new ResponseEntity<>(linkService.findLinkByTitle(title), HttpStatus.OK);
    }

    @Operation(summary = "Update link", description = "Update the details for a link")
    @PatchMapping("")
    public ResponseEntity<LinkDTO> updateLink(@RequestBody @Valid UpdateLinkRequest updateLinkRequest) {
        return new ResponseEntity<>(linkService.updateLink(updateLinkRequest), HttpStatus.OK);
    }

    @Operation(summary = "Delete link", description = "Delete a link")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLink(
            @Parameter(name = "id", description = "The id of the link to be deleted")
            @PathVariable Long id) {
        return new ResponseEntity<>(linkService.deleteLink(id), HttpStatus.OK);
    }

    @Operation(summary = "Get original url", description = "Get the original url of a link for redirecting")
    @GetMapping("/get-original-url/{linkName}")
    public ResponseEntity<String> getOriginalUrl(
            @Parameter(name = "linkName")
            @PathVariable String linkName) {
        return new ResponseEntity<>(linkService.getOriginalUrl(linkName), HttpStatus.OK);
    }

}
