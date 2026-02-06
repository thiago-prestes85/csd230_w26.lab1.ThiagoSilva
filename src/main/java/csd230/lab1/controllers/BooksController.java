package csd230.lab1.controllers;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.repositories.BookEntityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookEntityRepository bookRepository;

    public BooksController(BookEntityRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // LIST PAGE: /books
    @GetMapping
    public String books(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "booklist";
    }

    // SHOW FORM: /books/add
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new BookEntity());
        return "bookForm";
    }

    // SAVE: POST /books/add
    @PostMapping("/add")
    public String saveBook(@ModelAttribute("book") BookEntity book) {
        bookRepository.save(book);
        return "redirect:/books";
    }
}
