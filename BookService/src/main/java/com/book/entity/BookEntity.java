package com.book.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment in MySQL
    @Column(name = "book_id", updatable = false, nullable = false)
    private Long bookId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "author", nullable = false, length = 255)
    private String author;

    @Column(name = "publisher", length = 255)
    private String publisher;

    @Column(name = "publication_year")
    private Integer publicationYear;

    @Column(name = "total_copies", nullable = false)
    private Integer totalCopies;

    @Column(name = "available_copies", nullable = false)
    private Integer availableCopies;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // --- Constructors ---
    public BookEntity()
    {
    }

    public BookEntity(Long bookId, String title, String author, String publisher,
                Integer publicationYear, Integer totalCopies, Integer availableCopies)
    {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public void updateFrom(BookEntity source) {
        this.title = source.getTitle();
        this.author = source.getAuthor();
        this.publisher = source.getPublisher();
        this.publicationYear = source.getPublicationYear();
        this.totalCopies = source.getTotalCopies();
        this.availableCopies = source.getAvailableCopies();
    }

    // --- Lifecycle Callbacks ---
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Book details { id : " + getBookId()
                + ", bookTitle : " + getTitle()
                + ", createdAt : " + getCreatedAt()
                + ", publisher : " + getPublisher();
    }

    // --- Getters and Setters ---
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Integer getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(Integer totalCopies) {
        this.totalCopies = totalCopies;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}