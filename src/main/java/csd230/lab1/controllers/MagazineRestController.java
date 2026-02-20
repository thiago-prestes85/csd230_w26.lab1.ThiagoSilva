package csd230.lab1.controllers;

import csd230.lab1.entities.MagazineEntity;
import csd230.lab1.exceptions.ResourceNotFoundException;
import csd230.lab1.repositories.MagazineEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rest/magazines")
@Tag(name = "Magazine REST API", description = "JSON API for managing magazines")
public class MagazineRestController {

    private final MagazineEntityRepository repo;

    public MagazineRestController(MagazineEntityRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    @Operation(summary = "Get all magazines as JSON")
    @ApiResponse(responseCode = "200", description = "OK")
    public List<MagazineEntity> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a single magazine by ID")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    public MagazineEntity getOne(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find magazine with ID: " + id));
    }

    @PostMapping
    @Operation(summary = "Create a new magazine")
    @ApiResponse(responseCode = "200", description = "Created")
    public MagazineEntity create(@RequestBody MagazineEntity magazine) {
        return repo.save(magazine);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update or replace a magazine")
    @ApiResponse(responseCode = "200", description = "Updated")
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    public MagazineEntity update(@RequestBody MagazineEntity newMagazine, @PathVariable Long id) {
        return repo.findById(id)
                .map(m -> {
                    m.setTitle(newMagazine.getTitle());
                    m.setPrice(newMagazine.getPrice());
                    m.setCopies(newMagazine.getCopies());
                    m.setOrderQty(newMagazine.getOrderQty());
                    return repo.save(m);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Could not find magazine with ID: " + id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a magazine")
    @ApiResponse(responseCode = "200", description = "Deleted")
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    public String delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Could not find magazine with ID: " + id);
        }
        repo.deleteById(id);
        return "Deleted magazine with ID: " + id;
    }
}
