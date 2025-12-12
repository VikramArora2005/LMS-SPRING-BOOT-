package com.project.lms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.project.lms.model.Member_Books;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Member_BooksRepo extends JpaRepository<Member_Books, Integer> {
    Optional<Member_Books> findByMemberIdAndBookId(Long memberId, Long bookId);
    Optional<Member_Books> findByBookId(Long bookId);

}
