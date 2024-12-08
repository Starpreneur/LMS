package com.LMS.LibraryManagementSystem.service;

import com.LMS.LibraryManagementSystem.DTO.BookDTO;
import com.LMS.LibraryManagementSystem.DTO.Response.BookResponse;
import com.LMS.LibraryManagementSystem.model.Book;
import com.LMS.LibraryManagementSystem.model.Category;
import com.LMS.LibraryManagementSystem.repository.BookRepository;
import com.LMS.LibraryManagementSystem.repository.CategoriesRepository;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {


    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoriesRepository categoriesRepository;

    public List<Book> addBooks(List<BookDTO> bookList){
        List<Book> booksSaved = new ArrayList<>();
        List<Book> bookList1 = bookList.stream()
                .map(bookDTO -> Book.builder()
                         .isbn(UUID.randomUUID().toString())
                         .language(bookDTO.getLanguage())
                         .author(bookDTO.getAuthor())
                         .name(bookDTO.getName())
                         .publisher(bookDTO.getPublisher())
                         .status(bookDTO.getStatus())
                         .numOfPages(bookDTO.getNumOfPages())
                         .category(findCategory(bookDTO.getCategory()))
                         .build()).toList();
        booksSaved = bookRepository.saveAll(bookList1);
        return booksSaved;
    }

    public Category findCategory(String categoryName){
        Optional<Category> optionalCategory = categoriesRepository.findByName(categoryName);
        return optionalCategory.orElseGet(() -> categoriesRepository.findByName("Miscellaneous").get());
    }

    public BookResponse findBookByName(String name){
        BookResponse response = new BookResponse();
        try {
            Optional<Book> optionalBook = bookRepository.findByName(name);
            if (optionalBook.isPresent()) {
                response = BookResponse.builder()
                        .isbn(optionalBook.get().getIsbn())
                        .author(optionalBook.get().getAuthor())
                        .name(optionalBook.get().getName())
                        .status(optionalBook.get().getStatus())
                        .language(optionalBook.get().getLanguage())
                        .Category(optionalBook.get().getCategory().getName())
                        .numOfPages(optionalBook.get().getNumOfPages())
                        .build();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return response;
    }

    public List<BookResponse> findBooksByAuthor(String author){
        List<BookResponse> booksByAuthor = new ArrayList<>();
        try{
            Optional<List<Book>> optionalBooks = bookRepository.findByAuthor(author);
            if (optionalBooks.isPresent()){
                List<Book> books = optionalBooks.get();
                books.forEach(book -> {
                            BookResponse bookResponse = new BookResponse();
                            mapToBookResponse(bookResponse,book);
                            booksByAuthor.add(bookResponse);
                        });
            }
        }catch (Exception e){
            System.out.println("Exception occured : "+e.getMessage());
        }
        return booksByAuthor;
    }

    public void mapToBookResponse(BookResponse bookResponse,Book book){
        bookResponse.setAuthor(book.getAuthor());
        bookResponse.setIsbn(book.getIsbn());
        bookResponse.setName(book.getName());
        bookResponse.setLanguage(book.getLanguage());
        bookResponse.setCategory(book.getCategory().getName());
        bookResponse.setPublisher(book.getPublisher());
        bookResponse.setStatus(book.getStatus());
    }
}
