package com.LMS.LibraryManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "issued_books")
public class IssuedBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book bookId;
    @Column(name = "issued_date")
    private LocalDate issuedDate;
    @Column(name = "return_date")
    private LocalDate returnDate;
    private double fine;
    @Column(nullable = false)
    private String status;

}
