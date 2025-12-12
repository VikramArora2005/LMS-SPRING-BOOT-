package com.project.lms.controller;

import com.project.lms.model.Member;
import com.project.lms.model.MemberBookDTO;
import com.project.lms.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public void getAllMembers() {
        memberService.getAllMembers();
    }

    @GetMapping("/{memberID}")
    public void getMember(@PathVariable long memberID) {
        memberService.getMemberById(memberID);
    }

    @PostMapping
    public void addMember(@RequestBody Member member) {
        memberService.addMember(member);
    }

    @PutMapping
    public void updateMember(@RequestBody Member member) {
        memberService.updateMember(member);
    }

    @DeleteMapping("/{memberId}")
    public void deleteMember(@PathVariable long memberId) {
        memberService.deleteMember(memberId);
    }

    @GetMapping("/{memberID}/books")
    public void getMemberBooks(@PathVariable long memberID) {
        memberService.getMemberBooks(memberID);
    }
}
