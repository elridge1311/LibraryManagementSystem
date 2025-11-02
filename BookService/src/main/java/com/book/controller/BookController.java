package com.book.controller;

import com.book.entity.BookEntity;
import com.book.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class BookController
{
    @Autowired
    BookRepo bookRepo;

    @PostMapping("/api/books")
    public ResponseEntity<List<BookEntity>> saveBooks(@RequestBody List<BookEntity> book)
    {
        return new ResponseEntity<List<BookEntity>>(bookRepo.saveAll(book), HttpStatus.CREATED);
    }

    @GetMapping("/api/books")
    public ResponseEntity<List<BookEntity>> getAllBooks()
    {
        return new ResponseEntity<List<BookEntity>>(bookRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/books/{id}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Long id)
    {
        return bookRepo.findById(id)
                .map(book -> new ResponseEntity<>(book, HttpStatus.FOUND))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBookById(@PathVariable Long id)
    {
        bookRepo.deleteById(id);
    }

    @PutMapping("/api/books/{id}")
    public ResponseEntity<BookEntity> updateBookById(@PathVariable Long id, @RequestBody BookEntity book)
    {
        return bookRepo.findById(id)
                .map(existingBook -> {
                    existingBook.updateFrom(book);
                    return new ResponseEntity<>(bookRepo.save(existingBook), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/api/books/search")
    public ResponseEntity<List<BookEntity>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author)
    {
        if (title != null)
        {
            return ResponseEntity.ok(bookRepo.findBookByTitleContainingIgnoreCase(title));
        }
        else if (author != null)
        {
            return ResponseEntity.ok(bookRepo.findBookByAuthorContainingIgnoreCase(author));
        }
        else
        {
            return ResponseEntity.badRequest().build(); // nothing provided
        }
    }

    @GetMapping("/api/books/available")
    public ResponseEntity<List<BookEntity>> getAllBooksByAvailableCopies()
    {
        return new ResponseEntity<List<BookEntity>>(bookRepo.findByAvailableCopiesGreaterThan(0),
                HttpStatus.OK);
    }

    @PutMapping("/api/books/{id}/decrement")
    public ResponseEntity<String> decrementBook(@PathVariable Long id)
    {
        Optional<BookEntity> optionalBook = bookRepo.findById(id);
        if (optionalBook.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }

        BookEntity book = optionalBook.get();

        if (book.getAvailableCopies() <= 0)
        {
            return ResponseEntity.badRequest()
                    .body("No available copies to decrement.");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepo.save(book);

        return ResponseEntity.ok("Book availability decremented successfully.");
    }

    @PutMapping("/api/books/{id}/increment")
    public ResponseEntity<String> incrementBook(@PathVariable Long id)
    {
        Optional<BookEntity> optionalBook = bookRepo.findById(id);
        if (optionalBook.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }

        BookEntity book = optionalBook.get();

        if (book.getAvailableCopies() >= book.getTotalCopies())
        {
            return ResponseEntity.badRequest()
                    .body("Cannot exceed total copies.");
        }

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepo.save(book);

        return ResponseEntity.ok("Book availability incremented successfully.");
    }

}
