package csd230.lab1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BooksController {

    @GetMapping("/books")
    public String books(Model model) {
        // Temporary placeholder so the page loads.
        // In Lab 3 you'll load real books from DB.
        model.addAttribute("books", List.of());
        return "booklist";
    }
}
