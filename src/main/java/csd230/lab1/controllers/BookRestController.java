package csd230.lab1.controllers;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.repositories.BookEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Book REST API", description = "JSON API for managing books")
@RestController
@RequestMapping("/api/rest/books")
@CrossOrigin(origins = "*")
public class BookRestController {

    private final BookEntityRepository bookRepository;

    public BookRestController(BookEntityRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Operation(summary = "Get all books as JSON")
    @GetMapping
    public List<BookEntity> all() {
        return bookRepository.findAll();
    }

    @Operation(summary = "Get a single book by ID")
    @GetMapping("/{id}")
    public BookEntity getBook(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Operation(summary = "Create a new book")
    @PostMapping
    public BookEntity newBook(@RequestBody BookEntity newBook) {
        return bookRepository.save(newBook);
    }

    @Operation(summary = "Update a book")
    @PutMapping("/{id}")
    public BookEntity updateBook(@RequestBody BookEntity updatedBook, @PathVariable Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setAuthor(updatedBook.getAuthor());
                    book.setTitle(updatedBook.getTitle());
                    book.setPubPrice(updatedBook.getPubPrice());
                    book.setCopies(updatedBook.getCopies());
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Operation(summary = "Delete a book")
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }
}