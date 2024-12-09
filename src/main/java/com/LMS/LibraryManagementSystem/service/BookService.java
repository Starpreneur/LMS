package com.LMS.LibraryManagementSystem.service;

import com.LMS.LibraryManagementSystem.DTO.BookDTO;
import com.LMS.LibraryManagementSystem.DTO.Response.BookResponse;
import com.LMS.LibraryManagementSystem.model.Book;
import com.LMS.LibraryManagementSystem.model.Category;
import com.LMS.LibraryManagementSystem.model.IssuedBooks;
import com.LMS.LibraryManagementSystem.model.Users;
import com.LMS.LibraryManagementSystem.repository.BookRepository;
import com.LMS.LibraryManagementSystem.repository.CategoriesRepository;
import com.LMS.LibraryManagementSystem.repository.IssuedBooksRepository;
import com.LMS.LibraryManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    public final double DAILY_FINE_RATE = 20.00;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private IssuedBooksRepository issuedBooksRepository;

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

    public List<BookResponse> findAllBooks(){
        List<BookResponse> listOfAllBooks = new ArrayList<>();
        try {
            List<Book> books = bookRepository.findAll();
            if (!books.isEmpty()) {
                books.forEach(book -> {
                    BookResponse bookResponse = new BookResponse();
                    mapToBookResponse(bookResponse,book);
                    listOfAllBooks.add(bookResponse);
                });
            }
        }catch (Exception e){
            System.out.println("Exception Occurred : "+e.getMessage());
        }
        return listOfAllBooks;
    }

    public String issueBookService(long userId,String book){
        //Validate the User
        Users user = userRepository.findById(userId).orElseThrow(RuntimeException::new);

        //Check if User has already maximum books assigned
        String listOfIssuedBooks = user.getIssuedBooks();
        List<Long> issuedBooks = convertStringToList(listOfIssuedBooks);
        if (issuedBooks.size() > 5)
            return "User has already 5 books issued";

        //fetch list of issued books to the user
        List<IssuedBooks> list = new ArrayList<>();
        for (Long issuedBook : issuedBooks){
           Optional<IssuedBooks> issuedBooks1 = issuedBooksRepository.findById(issuedBook);
            issuedBooks1.ifPresent(list::add);
        }

        //Check for overDue or unpaid fine
        for (IssuedBooks issuedBook : list){
            boolean isOverDue = checkOverDue(issuedBook);
            if (isOverDue)
                return "Book is OverDue for User or Unpaid Fine,Please return the book at earliest or pay the fine";
        }

        //Check for book existence and availability
        Optional<Book> book1 = bookRepository.findByName(book);
        if (book1.isPresent()){
            if(book1.get().getQuantity() <= 0 || book1.get().getStatus().equalsIgnoreCase("Issued")){
                return "Book is not available at the moment,All copies are issued";
            }
        }

        //Create Issued record
        IssuedBooks issuedBookObj = new IssuedBooks();
        addIssuedBook(issuedBookObj,book1.get(),user);

        return "Book issued successfully";

    }


    public boolean checkOverDue(IssuedBooks issuedBook){
       LocalDate currentDate = LocalDate.now();

       if (currentDate.isAfter(issuedBook.getReturnDate()) || issuedBook.getStatus().equals("OverDue")
                          || issuedBook.getFine() > 0){
           issuedBook.setStatus("OverDue");

           long overDueDays = ChronoUnit.DAYS.between(issuedBook.getReturnDate(),currentDate);
           double fine = overDueDays * DAILY_FINE_RATE;
           issuedBook.setFine(fine);

           issuedBooksRepository.save(issuedBook);
           return true;
       }
       return false;
    }

    public void addIssuedBook(IssuedBooks issuedBook,Book book,Users user){
        issuedBook.setBookId(book);
        issuedBook.setUser(user);
        issuedBook.setIssuedDate(LocalDate.now());
        issuedBook.setReturnDate(LocalDate.now().plusDays(14));
        issuedBook.setStatus("Issued");
        issuedBook.setFine(0.0);
        book.setQuantity(book.getQuantity()-1);
        if (book.getQuantity() <= 0){
            book.setStatus("Issued");
        }
        bookRepository.save(book);
        IssuedBooks saved = issuedBooksRepository.save(issuedBook);

        long id = saved.getId();
        List<Long> list = new ArrayList<>();
        String toSave = user.getIssuedBooks();
        if (user.getIssuedBooks().isEmpty()){
            list.add(id);
            toSave = convertListToString(list);
            user.setIssuedBooks(toSave);
            userRepository.save(user);
        }else{
            List<Long> listOdIds = convertStringToList(toSave);
            list.addAll(listOdIds);
            list.add(id);
            toSave = convertListToString(list);
            user.setIssuedBooks(toSave);
            userRepository.save(user);
        }

    }

    public static String convertListToString(List<Long> ids){
        try {
            return ids.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
        }catch (Exception e){
            System.out.println("Exception occurred : "+e.getMessage());
        }
        return "";
    }

    public static List<Long> convertStringToList(String str){
        try {
            String[] arr = str.split(",");
            List<Long> listOfIds = Arrays.stream(arr)
                    .map(Long::valueOf)
                    .toList();
            return listOfIds;
        }catch (Exception e){
            System.out.println("Exception occurred : "+e.getMessage());
        }
        return new ArrayList<>();
    }

    public String returnBook(long userId,String book){

        //validate the user
        Optional<Users> usersOptional = userRepository.findById(userId);
        Users user = new Users();
        if (usersOptional.isPresent()){
           user = usersOptional.get();
        }else{
            return "User is not in the system";
        }

        //validate the book
        Optional<Book> bookOptional = bookRepository.findByName(book);
        Book bookIssued = new Book();
        if (bookOptional.isPresent())
            bookIssued = bookOptional.get();
        else
            return "Book is not in the system";

        //Validate whether book is issued to User
        String issuedBooks = user.getIssuedBooks();
        List<Long> ids = convertStringToList(issuedBooks);
        List<IssuedBooks> issuedBooksList = new ArrayList<>();
        IssuedBooks issuedBookRecord = new IssuedBooks();
        for (long id : ids){
            Optional<IssuedBooks> issuedBooks1 = issuedBooksRepository.findById(id);
            issuedBooks1.ifPresent(issuedBooksList::add);
        }
        List<Long> modifiableList = new ArrayList<>(ids);
        for(IssuedBooks issuedBook : issuedBooksList){
            if (issuedBook.getBookId().getId() == bookIssued.getId()){
                issuedBookRecord = issuedBook;
                modifiableList.remove(Long.valueOf(issuedBook.getId()));
                break;
            }else {
                return "Book is not issued to the user";
            }
        }

        //check for overdue
        checkOverDue(issuedBookRecord);
        issuedBookRecord.setStatus("Returned");
        issuedBooksRepository.save(issuedBookRecord);

        //Increase the book quantity
        bookIssued.setQuantity(bookIssued.getQuantity()+1);
        bookRepository.save(bookIssued);

        //Set Issued books ids in user after removing id of returned book
        String issuedBooksIdsToUser = convertListToString(modifiableList);
        user.setIssuedBooks(issuedBooksIdsToUser);
        userRepository.save(user);

        return "Book Returned Successfully";

    }

}
