package com.transaction.repository;

import com.transaction.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<TransactionEntity, Long>
{

   List<TransactionEntity> findTransactionByStatusContainingIgnoreCase(String status);

   //List<TransactionEntity> findBookByTitleContainingIgnoreCase(String title);

}
