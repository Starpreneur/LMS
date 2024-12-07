package com.LMS.LibraryManagementSystem.controller;

import com.LMS.LibraryManagementSystem.DTO.BookDTO;
import com.LMS.LibraryManagementSystem.DTO.Response.BookResponse;
import com.LMS.LibraryManagementSystem.model.Book;
import com.LMS.LibraryManagementSystem.repository.BookRepository;
import com.LMS.LibraryManagementSystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/findBookByName/{name}")
    public ResponseEntity<BookResponse> findBookByName(@PathVariable String name){
        BookResponse bookResponse = bookService.findBookByName(name);
        if (bookResponse != null){
            return new ResponseEntity<>(bookResponse,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(bookResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findBooksByAuthor/{author}")
    public ResponseEntity<List<BookResponse>> findBooksByAuthor(@PathVariable String author){
        List<BookResponse> booksByAuthor = bookService.findBooksByAuthor(author);
        if (!booksByAuthor.isEmpty()){
            return new ResponseEntity<>(booksByAuthor,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(booksByAuthor,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
