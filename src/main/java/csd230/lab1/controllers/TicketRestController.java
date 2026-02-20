package csd230.lab1.controllers;

import csd230.lab1.entities.TicketEntity;
import csd230.lab1.exceptions.ResourceNotFoundException;
import csd230.lab1.repositories.TicketEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rest/tickets")
@Tag(name = "Ticket REST API", description = "JSON API for managing tickets")
public class TicketRestController {

    private final TicketEntityRepository repo;

    public TicketRestController(TicketEntityRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    @Operation(summary = "Get all tickets as JSON")
    @ApiResponse(responseCode = "200", description = "OK")
    public List<TicketEntity> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a single ticket by ID")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    public TicketEntity getOne(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find ticket with ID: " + id));
    }

    @PostMapping
    @Operation(summary = "Create a new ticket")
    @ApiResponse(responseCode = "200", description = "Created")
    public TicketEntity create(@RequestBody TicketEntity ticket) {
        return repo.save(ticket);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update or replace a ticket")
    @ApiResponse(responseCode = "200", description = "Updated")
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    public TicketEntity update(@RequestBody TicketEntity newTicket, @PathVariable Long id) {
        return repo.findById(id)
                .map(t -> {
                    t.setEventName(newTicket.getEventName());
                    t.setPrice(newTicket.getPrice());
                    t.setQuantity(newTicket.getQuantity());
                    return repo.save(t);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Could not find ticket with ID: " + id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a ticket")
    @ApiResponse(responseCode = "200", description = "Deleted")
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    public String delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Could not find ticket with ID: " + id);
        }
        repo.deleteById(id);
        return "Deleted ticket with ID: " + id;
    }
}
