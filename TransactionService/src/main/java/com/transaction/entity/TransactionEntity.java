package com.transaction.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class TransactionEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment in MySQL
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "user_id", length = 20)
    private String userId;

    @Column(name = "book_title", length = 255)
    private String bookTitle;

    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // --- Constructors ---
    public TransactionEntity() {
    }

    public TransactionEntity(String id, Long bookId, String userId, String bookTitle,
                             LocalDateTime dueDate,
                             LocalDateTime returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.bookTitle = bookTitle;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    // --- Lifecycle Callbacks ---
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.issueDate = LocalDateTime.now();
        this.status = "ISSUED";
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.status = "RETURNED";
        this.returnDate = LocalDateTime.now();
    }

    // --- Update method ---
    public void updateFrom(TransactionEntity source) {
        this.returnDate = source.getReturnDate();
    }

    // --- Getters and Setters ---
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // --- toString ---
    @Override
    public String toString() {
        return "TransactionEntity { " +
                "id='" + id + '\'' +
                ", bookId=" + bookId +
                ", userId='" + userId + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                " }";
    }
}