package com.LMS.LibraryManagementSystem.DTO.Response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {
    private String isbn;
    private String author;
    private String publisher;
    private String language;
    private int numOfPages;
    private String name;
    private String Category;
    private String status;
}
