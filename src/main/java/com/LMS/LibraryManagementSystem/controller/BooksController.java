package com.LMS.LibraryManagementSystem.controller;

import com.LMS.LibraryManagementSystem.DTO.BookDTO;
import com.LMS.LibraryManagementSystem.model.Book;
import com.LMS.LibraryManagementSystem.repository.BookRepository;
import com.LMS.LibraryManagementSystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BooksController {

    @Autowired
    BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<String> addBooks(@RequestBody List<BookDTO> bookList){
        if (!bookList.isEmpty()){
           List<Book> books = bookService.addBooks(bookList);
           if (!books.isEmpty())
               return new ResponseEntity<>("List of Books Saved", HttpStatus.OK);
           else
               return new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            return new ResponseEntity<>("Please Send a list of Books",HttpStatus.OK);
        }
    }
}
