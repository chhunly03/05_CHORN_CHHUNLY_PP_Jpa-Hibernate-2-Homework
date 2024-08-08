package com.khrd.jp_hibernate2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("")
    public ResponseEntity<BookResponce<List<Book>>>getAllBookByID(){
        List<Book> book = bookRepository.getAllBook();
        BookResponce<List<Book>> response;
        if (book!=null){
            response = BookResponce.<List<Book>>builder()
                    .message("Get all book Successfully: ")
                    .payload(bookRepository.getAllBook())
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else {
            response = BookResponce.<List<Book>>builder()
                    .message("Get not found book")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<BookResponce<Book>>getAllBookByID(@PathVariable UUID id){
        Book bookID = bookRepository.getAllBookByID(id);
        BookResponce<Book> response;
        if (bookID!=null){
            response = BookResponce.<Book>builder()
                    .message("Get book by id: " + id)
                    .payload(bookRepository.getAllBookByID(id))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else {
            response = BookResponce.<Book>builder()
                    .message("Get book by id: " + id)
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/removeBook/{id}")
    public ResponseEntity<BookResponce<Book>> removeBook(@PathVariable UUID id) {
        Book book = bookRepository.getAllBookByID(id);
        BookResponce<Book> response;
        if (book != null) {
            response = BookResponce.<Book>builder()
                    .message("Book with ID " + id + " successfully removed.")
                    .payload(bookRepository.removeBook(id))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        } else {
            response = BookResponce.<Book>builder()
                    .message("Book with ID " + id + " not found.")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }


    @PostMapping("")
    public ResponseEntity<BookResponce<Book>>InsertBook(@RequestBody BookRequest bookRequest){
        UUID book = bookRepository.InsertBook(bookRequest).getId();
        BookResponce<Book> response = null;
        if (book != null) {
            response = BookResponce.<Book>builder()
                    .message("Create book successfully")
                    .httpStatus(HttpStatus.CREATED)
                    .payload(bookRepository.getAllBookByID(book))
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        } else {
            response = BookResponce.<Book>builder()
                    .message("Create book not successfully")
                    .httpStatus(HttpStatus.CREATED)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<BookResponce<List<Book>>>getAllBookByTitle(@PathVariable String title){
        List<Book> bookTitle = bookRepository.getAllBookTitle(title);
        BookResponce<List<Book>> response;
        if (bookTitle!=null && !bookTitle.isEmpty()){
            response = BookResponce.<List<Book>>builder()
                    .message("Get book by title: " + title)
                    .payload(bookRepository.getAllBookTitle(title))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else {
            response = BookResponce.<List<Book>>builder()
                    .message("Get book by title: " + title)
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<BookResponce<Book>>updateBook(@PathVariable UUID id, @RequestBody BookRequest bookRequest){
        Book StoreData=bookRepository.getAllBookByID(id);
        System.out.println("store data: "+StoreData);
        BookResponce<Book>responce=null;
        if(StoreData == null){
            responce = BookResponce.<Book>builder()
                    .message("update data not success..!")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else {
            responce = BookResponce.<Book>builder()
                    .message("Update data success... ):")
                    .payload(bookRepository.updateBook(id,bookRequest))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(responce);
    }
}
