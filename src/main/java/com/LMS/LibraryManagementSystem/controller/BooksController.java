package com.LMS.LibraryManagementSystem.controller;

import com.LMS.LibraryManagementSystem.DTO.BookDTO;
import com.LMS.LibraryManagementSystem.DTO.Response.BookResponse;
import com.LMS.LibraryManagementSystem.model.Book;
import com.LMS.LibraryManagementSystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/books")
public class BooksController {

    @Autowired
    BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<String> addBooks(@RequestBody List<BookDTO> bookList, Model model){
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

    @GetMapping("/findBookByName")
    public String findBookByName(@RequestParam String bookName, Model model){
        BookResponse bookResponse = bookService.findBookByName(bookName);
        if (bookResponse != null){
            model.addAttribute("book",bookResponse);
            return "bookDetails";
        }else{
            model.addAttribute("error","Book not found");
            return "searchBookByName";
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

    @GetMapping("/findAllBooks")
    public String  findAllBooks(Model model){
        List<BookResponse> listOfAllBooks = bookService.findAllBooks();
        if (!listOfAllBooks.isEmpty()){
            model.addAttribute("books",listOfAllBooks);
            return "showAllBooks";
        }else {
            model.addAttribute("error","Books not found");
            return "home";
        }
    }

    @PostMapping("/issueBook")
    public String issueBook(@RequestParam String emailId, @RequestParam String book, Model model){
        String bookIssuedMessage = bookService.issueBookService(emailId, book);
        model.addAttribute("message",bookIssuedMessage);
        return "home";
    }


    @PostMapping("/returnBook")
    public String returnBook(@RequestParam String emailId,@RequestParam String book,Model model){
        String bookReturnedMessage = bookService.returnBook(emailId,book);
        model.addAttribute("message",bookReturnedMessage);
        return "home";
    }

}
