package com.book.controller;

import com.book.entity.BookEntity;
import com.book.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

}
