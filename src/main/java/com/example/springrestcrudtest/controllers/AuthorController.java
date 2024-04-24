package com.example.springrestcrudtest.controllers;

import com.example.springrestcrudtest.dto.AuthorDto;
import com.example.springrestcrudtest.services.AuthorCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorCRUDService authorCRUDService;

    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable("id") Long id) {
        return authorCRUDService.getById(id);
    }

    @GetMapping
    public Collection<AuthorDto> getAllAuthors() {
        return authorCRUDService.getAll();
    }

    @PostMapping
    public void create(@RequestBody AuthorDto authorDto) {
        authorCRUDService.create(authorDto);
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto){
        authorDto.setId(id);
        authorCRUDService.update(authorDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        authorCRUDService.delete(id);
    }
}
