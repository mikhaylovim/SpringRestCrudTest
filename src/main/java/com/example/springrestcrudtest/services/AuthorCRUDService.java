package com.example.springrestcrudtest.services;

import com.example.springrestcrudtest.dto.AuthorDto;
import com.example.springrestcrudtest.entity.Author;
import com.example.springrestcrudtest.entity.Book;
import com.example.springrestcrudtest.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorCRUDService implements CRUDService<AuthorDto> {

    private final AuthorRepository repository;

    public static Author mapToEntity(AuthorDto authorDto) {
        Author author = new Author();
        author.setId(authorDto.getId());
        author.setFirstName(authorDto.getFirstName());
        author.setLastName(authorDto.getLastName());
        if (authorDto.getBooks() != null && !authorDto.getBooks().isEmpty()) {
            List<Book> books = authorDto.getBooks().stream().map(dto -> {
                Book book = new Book();
                book.setTitle(dto.getTitle());
                book.setAuthor(author);  // Устанавливаем связь между книгой и автором
                return book;
            }).collect(Collectors.toList());
            author.setBooks(books);  // Добавляем книги к автору
        }
//        author.setBooks(authorDto.getBooks()
//                .stream()
//                .map(BooksCRUDService::mapToEntity)
//                .toList());
        return author;
    }

    public static AuthorDto mapToDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setFirstName(author.getFirstName());
        authorDto.setLastName(author.getLastName());
        authorDto.setBooks(author.getBooks()
                .stream()
                .map(BooksCRUDService::mapToDto)
                .toList());
        return authorDto;
    }

    @Override
    public AuthorDto getById(Long id) {
        log.info("Getting author by id {}", id);
        return mapToDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id " + id)));
    }

    @Override
    public Collection<AuthorDto> getAll() {
        return repository.findAll().stream()
                .map(AuthorCRUDService::mapToDto).toList();
    }

    @Override
    public void create(AuthorDto item) {
        log.info("Creating author {}", item);
        Author author = mapToEntity(item);
        repository.save(author);
    }

    @Override
    public void update(AuthorDto item) {
        log.info("Updating author {}", item);
        Author author = repository.findById(item.getId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id " + item.getId()));
        author.setFirstName(item.getFirstName());
        author.setLastName(item.getLastName());
        repository.save(author);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting author {}", id);
        repository.deleteById(id);
    }
}
