package com.project.lms.controller;


import com.project.lms.constants.AppSuccessCode;
import com.project.lms.exception.SuccessException;
import com.project.lms.model.Book;
import com.project.lms.response.ApiResponse;
import com.project.lms.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;


    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public void getBooks() {
        List<Book> b = bookService.getAllBooks();
    }

    @PostMapping
    public void addBook(@RequestBody Book book) {
        Book b =  bookService.addBook(book);

    }

    @DeleteMapping("/{bookID}")
    public void deleteBook(@PathVariable long bookID) {
        bookService.deleteBook(bookID);
    }

    @PutMapping("/{bookID}/one")
    public void deleteOneBook(@PathVariable long bookID) {
        bookService.deleteOneBook(bookID);

    }

    @PutMapping
    public void updateBook(@RequestBody Book book) {
        Book b =  bookService.updateBook(book);

    }
}
