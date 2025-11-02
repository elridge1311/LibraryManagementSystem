package com.transaction.service;

import com.transaction.client.BookServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransactionService
{
    @Autowired
    private BookServiceClient bookServiceClient;

    public HttpStatusCode takeBook(Long bookId)
    {
        ResponseEntity<String> response = bookServiceClient.decrementBook(bookId);

        if (response.getStatusCode().is2xxSuccessful())
        {
            return response.getStatusCode();
        }
        else if (response.getStatusCode().is4xxClientError())
        {
            return response.getStatusCode();
        }
        else
        {
            return response.getStatusCode();
        }
    }

    public HttpStatusCode returnBook(Long bookId)
    {
        ResponseEntity<String> response = bookServiceClient.incrementBook(bookId);

        if (response.getStatusCode().is2xxSuccessful())
        {
            return response.getStatusCode();
        }
        else if (response.getStatusCode().is4xxClientError())
        {
            return response.getStatusCode();
        }
        else
        {
            return response.getStatusCode();
        }
    }
}
