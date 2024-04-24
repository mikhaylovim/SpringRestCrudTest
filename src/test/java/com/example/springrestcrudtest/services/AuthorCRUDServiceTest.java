package com.example.springrestcrudtest.services;

import com.example.springrestcrudtest.dto.AuthorDto;
import com.example.springrestcrudtest.entity.Author;
import com.example.springrestcrudtest.repositories.AuthorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorCRUDServiceTest {
    private final AuthorRepository authorRepository = mock(AuthorRepository.class);
    private final AuthorCRUDService authorCRUDService = new AuthorCRUDService(authorRepository);

    @Test
    @DisplayName("Test getById")
    public void testGetById() {
        long authorId = 1;
        Author author = new Author();
        author.setId(authorId);
        author.setBooks(List.of());

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        AuthorDto authorDto = authorCRUDService.getById(authorId);

        assertEquals(authorId, authorDto.getId());
        verify(authorRepository, times(1)).findById(authorId);
    }

    @Test
    @DisplayName("Test getAll")
    public void testGetAll() {
        List<Author> authors = new ArrayList<>();
        Author author1 = new Author();
        author1.setId(1L);
        author1.setBooks(List.of());
        authors.add(author1);

        when(authorRepository.findAll()).thenReturn(authors);
        Collection<AuthorDto> authorsDto = authorCRUDService.getAll();

        assertEquals(authors.size(), authorsDto.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test create")
    public void testCreate() {
        AuthorDto authorDto = new AuthorDto();
        authorCRUDService.create(authorDto);

        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    @DisplayName("Test update")
    public void testUpdate() {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1L);
        authorDto.setFirstName("John");
        authorDto.setLastName("Doe");
        Author author = new Author();
        author.setFirstName("OldFirstName");
        author.setLastName("OldLastName");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        authorCRUDService.update(authorDto);

        verify(authorRepository, times(1)).save(any(Author.class));
        verify(authorRepository).save(argThat(updatedAuthor ->
                updatedAuthor.getFirstName().equals("John") &&
                        updatedAuthor.getLastName().equals("Doe")
        ));
    }

    @Test
    @DisplayName("Test delete")
    public void testDeleteById() {
        long authorId = 1;
        authorCRUDService.delete(authorId);
        verify(authorRepository, times(1)).deleteById(authorId);
    }
}