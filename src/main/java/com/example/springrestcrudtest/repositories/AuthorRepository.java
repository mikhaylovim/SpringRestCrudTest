package com.example.springrestcrudtest.repositories;

import com.example.springrestcrudtest.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
