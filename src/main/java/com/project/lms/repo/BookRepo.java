package com.project.lms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.lms.model.Book;
import org.springframework.stereotype.Repository;




@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
}
