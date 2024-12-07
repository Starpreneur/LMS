package com.LMS.LibraryManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private String isbn;
    private String author;
    private String publisher;
    private String language;
    private int numOfPages;
    private int categoryId;
    private String status;

}
