package com.example.springrestcrudtest.repositories;

import com.example.springrestcrudtest.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
}
