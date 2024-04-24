package com.example.springrestcrudtest.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private List<BookDto> books;

    @Override
    public String toString() {
        return "AuthorDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", books=" + books +
                '}';
    }
}
