package com.example.springrestcrudtest.services;

import com.example.springrestcrudtest.dto.BookDto;
import com.example.springrestcrudtest.entity.Author;
import com.example.springrestcrudtest.entity.Book;
import com.example.springrestcrudtest.repositories.AuthorRepository;
import com.example.springrestcrudtest.repositories.BooksRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Service
public class BooksCRUDService implements CRUDService<BookDto> {

    private final BooksRepository booksRepository;
    private final AuthorRepository authorRepository;

    public static BookDto mapToDto(Book books) {
        BookDto bookDto = new BookDto();
        bookDto.setId(books.getId());
        bookDto.setTitle(books.getTitle());
        bookDto.setAuthorId(books.getAuthor().getId());
        bookDto.setCreatedDate(books.getCreatedDate());
        bookDto.setUpdatedDate(books.getUpdatedDate());
        return bookDto;
    }

    public static Book mapToEntity(BookDto dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());

        return book;
    }

    @Override
    public BookDto getById(Long id) {
        log.info("Getting book by id: {}", id);
        Book book = booksRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id " + id));
        return mapToDto(book);
    }

    @Override
    public Collection<BookDto> getAll() {
        log.info("Getting all books");
        return booksRepository.findAll()
                .stream()
                .map(BooksCRUDService::mapToDto)
                .toList();
    }

    @Override
    public void create(BookDto item) {
        log.info("Create Book : {}", item);
        Book book = mapToEntity(item);
        Long authorId = item.getAuthorId();
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id " + item.getId()));
        book.setAuthor(author);
        booksRepository.save(book);
    }

    @Override
    public void update(BookDto item) {
        log.info("Updating book  {}", item);
        Book book = booksRepository.findById(item.getId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id " + item.getId()));
        book.setTitle(item.getTitle());
        Long authorId = item.getAuthorId();
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id " + item.getId()));
        book.setAuthor(author);
        booksRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting book with id {}", id);
        booksRepository.deleteById(id);
    }
}
