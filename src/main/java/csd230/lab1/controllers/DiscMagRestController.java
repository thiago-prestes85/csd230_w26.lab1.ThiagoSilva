package csd230.lab1.controllers;

import csd230.lab1.entities.DiscMagEntity;
import csd230.lab1.exceptions.ResourceNotFoundException;
import csd230.lab1.repositories.DiscMagEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rest/discmags")
@Tag(name = "DiscMag REST API", description = "JSON API for managing disc magazines")
public class DiscMagRestController {

    private final DiscMagEntityRepository repo;

    public DiscMagRestController(DiscMagEntityRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    @Operation(summary = "Get all disc magazines as JSON")
    @ApiResponse(responseCode = "200", description = "OK")
    public List<DiscMagEntity> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a single disc magazine by ID")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    public DiscMagEntity getOne(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find disc magazine with ID: " + id));
    }

    @PostMapping
    @Operation(summary = "Create a new disc magazine")
    @ApiResponse(responseCode = "200", description = "Created")
    public DiscMagEntity create(@RequestBody DiscMagEntity discMag) {
        return repo.save(discMag);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update or replace a disc magazine")
    @ApiResponse(responseCode = "200", description = "Updated")
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    public DiscMagEntity update(@RequestBody DiscMagEntity newDiscMag, @PathVariable Long id) {
        return repo.findById(id)
                .map(d -> {
                    d.setTitle(newDiscMag.getTitle());
                    d.setPrice(newDiscMag.getPrice());
                    d.setCopies(newDiscMag.getCopies());
                    d.setOrderQty(newDiscMag.getOrderQty());
                    d.setDiscType(newDiscMag.getDiscType());
                    return repo.save(d);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Could not find disc magazine with ID: " + id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a disc magazine")
    @ApiResponse(responseCode = "200", description = "Deleted")
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    public String delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Could not find disc magazine with ID: " + id);
        }
        repo.deleteById(id);
        return "Deleted disc magazine with ID: " + id;
    }
}
