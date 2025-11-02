package com.transaction.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "book-service")
public interface BookServiceClient
{
    @PutMapping("/api/books/{id}/decrement")
    ResponseEntity<String> decrementBook(@PathVariable("id") Long id);

    @PutMapping("/api/books/{id}/increment")
    ResponseEntity<String> incrementBook(@PathVariable("id") Long id);
}
