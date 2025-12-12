package com.project.lms.service;

import com.project.lms.constants.AppErrorCode;
import com.project.lms.constants.AppSuccessCode;
import com.project.lms.exception.ErrorException;
import com.project.lms.exception.SuccessException;
import com.project.lms.model.Book;
import com.project.lms.model.Member;
import com.project.lms.model.MemberBookDTO;
import com.project.lms.model.Member_Books;
import com.project.lms.repo.BookRepo;
import com.project.lms.repo.MemberRepo;
import com.project.lms.repo.Member_BooksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepo memberRepo;
    private final Member_BooksRepo memberBooksRepo;
    private final BookRepo bookRepo;
    private final AppSuccessCode success;
    private final AppErrorCode error;

    @Autowired
    public MemberService(MemberRepo memberRepo, Member_BooksRepo memberBooksRepo, BookRepo bookRepo,AppErrorCode error,AppSuccessCode success) {
        this.memberRepo = memberRepo;
        this.memberBooksRepo = memberBooksRepo;
        this.bookRepo = bookRepo;
        this.success = success;
        this.error = error;

    }


    public List<Member> getAllMembers() {
        try{
            List<Member> members = memberRepo.findAll();
            throw new SuccessException(success.getMembersRetrievedSuccessfulCode(),success.getMembersRetrievedSuccessfulMsg(),members);
        }catch(SuccessException se){
            throw se;
        }catch(Exception e){
            throw new ErrorException(error.getMembersLoadingErrorCode(), error.getMembersLoadingErrorMsg());

        }


    }


    public Member getMemberById(long memberID) {
        try{
            Member m = null;
            Optional<Member> memberOpt = memberRepo.findById(memberID);
            if(memberOpt.isPresent()){
                m = memberOpt.get();
            }
            throw new SuccessException(success.getMemberFoundSuccessfulCode(),success.getMemberFoundSuccessfulMsg(),m);
        }catch(SuccessException se){
            throw se;
        }catch(Exception e){
            throw new ErrorException(error.getMemberNotFoundErrorCode(), error.getMemberNotFoundErrorMsg());
        }
    }


    public Member addMember(Member member) {
        try{
            Member m = memberRepo.save(member);
            throw new SuccessException(success.getMemberAdditionSuccessfulCode(),success.getMemberAdditionSuccessfulMsg(),m);
        }catch(SuccessException se){
            throw se;
        }catch (Exception e){
            throw new ErrorException(error.getMemberAdditionErrorCode(), error.getMemberAdditionErrorMsg());
        }
    }


    public Member updateMember(Member member) {
        try{
            Member m = memberRepo.save(member);
            throw new SuccessException(success.getMemberUpdateSuccessfulCode(),success.getMemberUpdateSuccessfulMsg(),m);

        }catch(SuccessException se){
            throw se;
        }catch (Exception e){
            throw new ErrorException(error.getMemberUpdateErrorCode(), error.getMemberUpdateErrorMsg());
        }



    }


    public void deleteMember(long memberId) {
        try{
            memberRepo.deleteById(memberId);
            memberBooksRepo.findAll()
                    .stream()
                    .filter(mb -> mb.getMemberId().equals(memberId))
                    .forEach(memberBooksRepo::delete);
            throw new SuccessException(success.getMemberDeletionSuccessfulCode(),success.getMemberDeletionSuccessfulMsg());
        }catch(SuccessException se){
            throw se;
        }
        catch(Exception e){
            throw new ErrorException(error.getMemberDeletionErrorCode(), error.getMemberDeletionErrorMsg());
        }

    }

    public ResponseEntity<Member> getMemberDetails(long memberID) {
        Member member = memberRepo.findById(memberID).orElse(null);
        if (member != null) {
            return ResponseEntity.ok(member);
        }else{
            return ResponseEntity.notFound().build();
        }
    }



    public List<MemberBookDTO> getMemberBooks(long memberID) {
        try{
            List<MemberBookDTO> bookList = new ArrayList<>();

            List<Member_Books> memberBooks = memberBooksRepo.findAll();
            for (Member_Books mb : memberBooks) {
                if (mb.getMemberId() == memberID) {
                    bookRepo.findById(mb.getBookId())
                            .ifPresent(book -> bookList.add(new MemberBookDTO(book, mb.getQuantity())));
                }
            }
            throw new SuccessException(success.getMemberBooksLoadingSuccessfulCode(),success.getMemberBooksLoadingSuccessfulMsg(),bookList);
        }catch(SuccessException se){
            throw se;
        }catch(Exception e){
            throw new ErrorException(error.getMemberBooksLoadingErrorCode(), error.getMemberBooksLoadingErrorMsg());

        }

    }
}



