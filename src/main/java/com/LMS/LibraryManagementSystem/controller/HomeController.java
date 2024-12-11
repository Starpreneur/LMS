package com.LMS.LibraryManagementSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/home")
public class HomeController {

    @GetMapping("/seachBookByNamePage")
    public String searchBookByNamePage(){
        return "searchBookByName";
    }

    @GetMapping("showAllBooksPage")
    public String showAllBooksPage(){
        return "showAllBooks";
    }

    @GetMapping("issueBooksPage")
    public String issueBooksPage(){
        return "issueBooks";
    }

    @GetMapping("/returnBookPage")
    public String returnBooksPage(){
        return "returnBook";
    }
}
