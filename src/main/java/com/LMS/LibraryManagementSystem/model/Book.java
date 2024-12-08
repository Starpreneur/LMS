package com.LMS.LibraryManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Book")
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String isbn;
    private String author;
    private String publisher;
    private String language;
    private int numOfPages;
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    private String status;
    @Column(nullable = false)
    private int quantity;

    @OneToMany
    @JoinColumn(name = "issued_id")
    private List<IssuedBooks> issuedBooks;

}
