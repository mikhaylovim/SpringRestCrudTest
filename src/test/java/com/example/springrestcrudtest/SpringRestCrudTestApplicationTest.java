package com.example.springrestcrudtest;

import com.example.springrestcrudtest.dto.BookDto;
import com.example.springrestcrudtest.entity.Author;
import com.example.springrestcrudtest.entity.Book;
import com.example.springrestcrudtest.repositories.AuthorRepository;
import com.example.springrestcrudtest.repositories.BooksRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringRestCrudTestApplicationTest {

    private static final String BOOK_NAME = "Spring Rest Crud Test";

    @LocalServerPort
    private Integer port;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:14")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test")
                    .withInitScript("init_postgres.sql");

    @BeforeAll
    public static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    public static void afterAll() {
        postgreSQLContainer.stop();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }


    @BeforeEach
    public void fillingDatabase() {
        Book book = new Book();
        book.setTitle(BOOK_NAME);
        book.setAuthor(authorRepository.save(new Author()));
        booksRepository.save(book);
    }

    @AfterEach
    public void clearDatabase() {
        authorRepository.deleteAll();
    }

    @Test
    public void testGetBookById() {
        ResponseEntity<BookDto> response = restTemplate
                .getRestTemplate().getForEntity("http://localhost:" + port + "/books/1", BookDto.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
//        Assertions.assertEquals(Optional.of(1L), Objects.requireNonNull(response.getBody()).getId());
        Assertions.assertEquals(BOOK_NAME, Objects.requireNonNull(response.getBody()).getTitle());
    }
}