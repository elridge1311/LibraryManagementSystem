package com.transaction.controller;

import com.transaction.entity.TransactionEntity;
import com.transaction.repository.TransactionRepo;
import com.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TransactionController
{
    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/api/transactions")
    public ResponseEntity<TransactionEntity> issueBooks(@RequestBody TransactionEntity issuedBook)
    {
        try
        {
            HttpStatusCode response = transactionService.takeBook(issuedBook.getBookId());

            if(response.is2xxSuccessful())
            {
                return new ResponseEntity<TransactionEntity>(transactionRepo.save(issuedBook), HttpStatus.CREATED);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error issuing book ID " + issuedBook.getBookId() + ": " + e.getMessage(), e);
        }
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
    public ResponseEntity<TransactionEntity> returnBook(@PathVariable Long id)
    {
        return transactionRepo.findById(id)
                .map(transaction ->
                {
                    try
                    {
                        HttpStatusCode response = transactionService.returnBook(transaction.getBookId());

                        if(response.is2xxSuccessful())
                        {
                            transaction.setStatus("RETURNED");
                            TransactionEntity updatedTransaction = transactionRepo.save(transaction);
                            return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
                        }
                        else
                        {
                            return new ResponseEntity<TransactionEntity>(HttpStatus.BAD_REQUEST);
                        }
                    }
                    catch (Exception e)
                    {
                        throw new RuntimeException("Error returning book ID " + transaction.getBookId() + ": " + e.getMessage(), e);
                    }
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/api/transactions/active")
    public ResponseEntity<List<TransactionEntity>> getAllActiveTransactions()
    {
        return new ResponseEntity<List<TransactionEntity>>(transactionRepo.findTransactionByStatusContainingIgnoreCase("ISSUED"), HttpStatus.OK);
    }



}
