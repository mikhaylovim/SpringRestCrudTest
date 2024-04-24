package com.example.springrestcrudtest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class BookDto {
    private Long id;
    private String title;
    private Long authorId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorId='" + authorId + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
