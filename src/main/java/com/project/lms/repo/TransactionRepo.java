package com.project.lms.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.lms.model.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {
}
