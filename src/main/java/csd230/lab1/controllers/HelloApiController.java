package csd230.lab1.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Demo API", description = "Simple endpoint to confirm Swagger/OpenAPI is working")
@RestController
@RequestMapping("/api")
public class HelloApiController {

    @Operation(summary = "Health check endpoint", description = "Returns a simple message to confirm the API is online.")
    @GetMapping("/hello")
    public String hello() {
        return "Hello from OpenAPI";
    }
}
