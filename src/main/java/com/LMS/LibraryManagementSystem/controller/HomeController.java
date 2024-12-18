package com.LMS.LibraryManagementSystem.controller;

import com.LMS.LibraryManagementSystem.model.Book;
import com.LMS.LibraryManagementSystem.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/home")
public class HomeController {

    @Autowired
    BookService bookService;

    @GetMapping("/returnHomePage")
    public String returnHomePage(HttpServletRequest request,Model model){
        String userName = (String) request.getAttribute("userName");
        model.addAttribute("userName",userName);
        return "home";
    }

    @GetMapping("/seachBookByNamePage")
    public String searchBookByNamePage(){
        return "searchBookByName";
    }

    @GetMapping("showAllBooksPage")
    public String showAllBooksPage(){
        return "showAllBooks";
    }

    @GetMapping("issueBooksPage")
    public String issueBooksPage(Model model){
        List<Book> bookList = bookService.getAllBooks();
        model.addAttribute("bookList",bookList);
        return "issueBooks";
    }

    @GetMapping("/returnBookPage")
    public String returnBooksPage(Model model){
        List<Book> bookList = bookService.getAllBooks();
        model.addAttribute("bookList",bookList);
        return "returnBook";
    }
}
