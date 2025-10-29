package com.project.lms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.lms.model.Member;
import org.springframework.stereotype.Repository;




@Repository
public interface MemberRepo extends JpaRepository<Member,Long> {
}
