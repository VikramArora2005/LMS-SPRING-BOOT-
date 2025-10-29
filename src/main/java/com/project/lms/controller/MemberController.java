package com.project.lms.controller;

import com.project.lms.model.Member;
import com.project.lms.repo.MemberRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/members")
public class MemberController {


    private final MemberRepo memberRepo;

    public MemberController(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    @GetMapping
    public List<Member> getBooks() {
        return memberRepo.findAll();
    }

    @PostMapping
    public Member addMember(@RequestBody Member member) {
        return memberRepo.save(member);
    }
    @DeleteMapping
    public void deleteMember(@RequestBody long memberId) {
        memberRepo.deleteById(memberId);

    }

    @PutMapping
    public Member updateMember(@RequestBody Member member) {
        return memberRepo.save(member);
    }

}
