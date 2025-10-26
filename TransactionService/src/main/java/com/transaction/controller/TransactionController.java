package com.transaction.controller;

import com.transaction.entity.TransactionEntity;
import com.transaction.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TransactionController
{
    @Autowired
    TransactionRepo transactionRepo;

    @PostMapping("/api/transactions")
    public ResponseEntity<List<TransactionEntity>> issueBooks(@RequestBody List<TransactionEntity> issuedBook)
    {
        return new ResponseEntity<List<TransactionEntity>>(transactionRepo.saveAll(issuedBook), HttpStatus.CREATED);
    }

    @GetMapping("/api/transactions")
    public ResponseEntity<List<TransactionEntity>> getAllTransactions()
    {
        return new ResponseEntity<List<TransactionEntity>>(transactionRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/transactions/{id}")
    public ResponseEntity<TransactionEntity> getTransactionById(@PathVariable Long id)
    {
        return transactionRepo.findById(id)
                .map(transaction -> new ResponseEntity<>(transaction, HttpStatus.FOUND))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/api/transactions/{id}")
    public ResponseEntity<TransactionEntity> returnBook(@PathVariable Long id) {
        return transactionRepo.findById(id)
                .map(transaction -> {
                    transaction.setStatus("RETURNED");
                    TransactionEntity updatedTransaction = transactionRepo.save(transaction);
                    return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/api/transactions/active")
    public ResponseEntity<List<TransactionEntity>> getAllActiveTransactions()
    {
        return new ResponseEntity<List<TransactionEntity>>(transactionRepo.findTransactionByStatusContainingIgnoreCase("ISSUED"), HttpStatus.OK);
    }


//    @GetMapping("/api/books/search")
//    public ResponseEntity<List<TransactionEntity>> searchBooks(
//            @RequestParam(required = false) String title,
//            @RequestParam(required = false) String author)
//    {
//        if (title != null)
//        {
//            return ResponseEntity.ok(transactionRepo.findBookByTitleContainingIgnoreCase(title));
//        }
//        else if (author != null)
//        {
//            return ResponseEntity.ok(transactionRepo.findBookByAuthorContainingIgnoreCase(author));
//        }
//        else
//        {
//            return ResponseEntity.badRequest().build(); // nothing provided
//        }
//    }

}
