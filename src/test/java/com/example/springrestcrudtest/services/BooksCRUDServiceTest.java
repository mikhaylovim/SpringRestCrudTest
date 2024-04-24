package com.example.springrestcrudtest.services;

import com.example.springrestcrudtest.dto.BookDto;
import com.example.springrestcrudtest.entity.Author;
import com.example.springrestcrudtest.entity.Book;
import com.example.springrestcrudtest.repositories.AuthorRepository;
import com.example.springrestcrudtest.repositories.BooksRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BooksCRUDServiceTest {

    @Mock
    private BooksRepository booksRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BooksCRUDService booksCRUDService;

    public Book getBook() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Sample Book");
        Author author = new Author();
        author.setId(1L);
        author.setFirstName("John");
        author.setLastName("Doe");
        book.setAuthor(author);
        return book;
    }


    @Test
    @DisplayName("Test getById success")
    public void testGetByIdSuccess() {
        Book book = getBook();
        when(booksRepository.findById(book.getId())).thenReturn(Optional.of(book));
        BookDto bookDto = booksCRUDService.getById(book.getId());

        assertEquals(book.getId(), bookDto.getId());
        verify(booksRepository, times(1)).findById(book.getId());
    }

    @Test
    @DisplayName("Test getById throws when book not found")
    public void testGetByIdThrows() {
        Book book = getBook();
        when(booksRepository.findById(book.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> booksCRUDService.getById(book.getId()));
    }

    @Test
    @DisplayName("Test getAll books")
    public void testGetAll() {

        List<Book> books = List.of(getBook(), getBook());
        when(booksRepository.findAll()).thenReturn(books);
        Collection<BookDto> results = booksCRUDService.getAll();

        assertNotNull(results);
        assertEquals(2, results.size());
        verify(booksRepository).findAll();
    }

    @Test
    @DisplayName("Test create book")
    public void testCreate() {
        BookDto bookDto = new BookDto();
        bookDto.setAuthorId(1L);

        when(authorRepository.findById(bookDto.getAuthorId())).thenReturn(Optional.of(new Author()));
        booksCRUDService.create(bookDto);

        verify(booksRepository).save(any(Book.class));
    }

    @Test
    @DisplayName("Test create throws when author not found")
    public void testCreateThrows() {
        BookDto bookDto = new BookDto();
        bookDto.setAuthorId(1L);

        when(authorRepository.findById(bookDto.getAuthorId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> booksCRUDService.create(bookDto));
    }

    @Test
    @DisplayName("Test update book")
    public void testUpdate() {
        Long bookId = 1L;
        BookDto bookDto = new BookDto();
        bookDto.setId(bookId);
        bookDto.setTitle("Updated Title");
        bookDto.setAuthorId(2L);

        Book book = new Book();
        book.setId(bookId);
        Author author = new Author();
        author.setId(2L);

        when(booksRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(bookDto.getAuthorId())).thenReturn(Optional.of(author));

        booksCRUDService.update(bookDto);

        verify(booksRepository).save(book);
    }

    @Test
    @DisplayName("Test update throws when book not found")
    public void testUpdateThrowsBookNotFound() {
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        when(booksRepository.findById(bookDto.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> booksCRUDService.update(bookDto));
    }

    @Test
    @DisplayName("Test update throws when author not found")
    public void testUpdateThrowsAuthorNotFound() {
        Long bookId = 1L;
        BookDto bookDto = new BookDto();
        bookDto.setId(bookId);
        bookDto.setAuthorId(2L);

        when(booksRepository.findById(bookId)).thenReturn(Optional.of(new Book()));
        when(authorRepository.findById(bookDto.getAuthorId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> booksCRUDService.update(bookDto));
    }


    @Test
    @DisplayName("Test delete book")
    public void testDelete() {
        Long bookId = 1L;

        booksCRUDService.delete(bookId);

        verify(booksRepository).deleteById(bookId);
    }



}