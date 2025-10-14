package com.book.repository;

import com.book.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository<BookEntity, Long>
{

    List<BookEntity> findBookByAuthorContainingIgnoreCase(String author);

    List<BookEntity> findBookByTitleContainingIgnoreCase(String title);

}
