package com.LMS.LibraryManagementSystem.service;

import com.LMS.LibraryManagementSystem.DTO.BookDTO;
import com.LMS.LibraryManagementSystem.model.Book;
import com.LMS.LibraryManagementSystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {


    @Autowired
    BookRepository bookRepository;

    public List<Book> addBooks(List<BookDTO> bookList){
       List<Book> bookList1 = bookList.stream()
                .map(bookDTO -> Book.builder()
                         .isbn(bookDTO.getIsbn())
                         .language(bookDTO.getLanguage())
                         .author(bookDTO.getAuthor())
                         .publisher(bookDTO.getPublisher())
                         .status(bookDTO.getStatus())
                         .numOfPages(bookDTO.getNumOfPages())
                         .build()).toList();

      List<Book> booksSaved = bookRepository.saveAll(bookList1);
      return booksSaved;
    }
}
