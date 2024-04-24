package com.example.springrestcrudtest.controllers;

import com.example.springrestcrudtest.dto.BookDto;
import com.example.springrestcrudtest.services.BooksCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    private final BooksCRUDService booksCRUDService;

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable("id") Long id) {
        return booksCRUDService.getById(id);
    }

    @GetMapping
    public Collection<BookDto> getAllBooks() {
        return booksCRUDService.getAll();
    }

    @PostMapping
    public void createBook(@RequestBody BookDto bookDto) {
        booksCRUDService.create(bookDto);
    }

    @PatchMapping("/{id}")
    public void updateBookById(@PathVariable("id") Long id, @RequestBody BookDto bookDto) {
        bookDto.setId(id);
        booksCRUDService.update(bookDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable("id") Long id) {
        booksCRUDService.delete(id);
    }
}
