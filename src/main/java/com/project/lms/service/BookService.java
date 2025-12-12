package com.project.lms.service;



import com.project.lms.constants.AppErrorCode;
import com.project.lms.constants.AppSuccessCode;
import com.project.lms.exception.ErrorException;
import com.project.lms.exception.SuccessException;
import com.project.lms.model.Book;
import com.project.lms.model.Member_Books;
import com.project.lms.repo.BookRepo;
import com.project.lms.repo.Member_BooksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {


    private final BookRepo bookRepo;
    private final Member_BooksRepo memberBooksRepo;
    private final AppSuccessCode success;
    private final AppErrorCode error;



    @Autowired
    public BookService(BookRepo bookRepo, Member_BooksRepo memberBooksRepo,AppErrorCode error,AppSuccessCode success) {
        this.bookRepo = bookRepo;
        this.memberBooksRepo = memberBooksRepo;
        this.success = success;
        this.error = error;

    }

    public List<Book> getAllBooks(){
        try {
            List<Book> books =  bookRepo.findAll();
            throw new SuccessException(success.getBooksRetrievedSuccessfulCode(),success.getBooksRetrievedSuccessfulMsg(),books);
        }catch(SuccessException se){
            throw se;
        }
        catch (Exception e){
            throw new ErrorException(error.getBooksRetrieveErrorCode(), error.getBooksRetrieveErrorMsg());
        }

    }



    public Book addBook(Book book) {

        try{
            for (Book existingBook : bookRepo.findAll()) {
                if (existingBook.getTitle().equals(book.getTitle()) &&
                        existingBook.getAuthor().equals(book.getAuthor())) {

                    int newQuantity = existingBook.getQuantity() + book.getQuantity();
                    existingBook.setQuantity(newQuantity);
                    Book b = bookRepo.save(existingBook);
                    throw new SuccessException(success.getBookAdditionSuccessfulCode(), success.getBookAdditionSuccessfulMsg(), b);

                }
            }

            Book b = bookRepo.save(book);
            throw new SuccessException(success.getBookAdditionSuccessfulCode(), success.getBookAdditionSuccessfulMsg(), b);
        }catch(SuccessException se){
            throw se;
        }catch(Exception e){
            throw new ErrorException(error.getBookAdditionErrorCode(), error.getBookAdditionErrorMsg());
        }

    }

    public ResponseEntity<String> deleteBook(long bookID) {


        try{
            Optional<Member_Books> mb = memberBooksRepo.findByBookId(bookID);
            if (mb.isPresent()) {
                throw new ErrorException(error.getBookDeletionCauseIssuedErrorCode(), error.getBookDeletionCauseIssuedErrorMsg());

            } else {
                bookRepo.deleteById(bookID);
                throw new SuccessException(success.getBookDeletionSuccessfulCode(), success.getBookDeletionSuccessfulMsg());

            }

        }catch(ErrorException ee){
            throw ee;
        }catch(SuccessException se){
            throw se;
        }catch(Exception e){
            throw new ErrorException(error.getBookDeletionErrorCode(), error.getBookDeletionErrorMsg());
        }

    }

    public void deleteOneBook(long bookID) {

        try{
            Book book = bookRepo.findById(bookID).orElseThrow();
            int newQuantity = book.getQuantity() - 1;

            if (newQuantity > 0) {
                book.setQuantity(newQuantity);
                bookRepo.save(book);
                throw new SuccessException(success.getBookDeletionSuccessfulCode(), success.getBookDeletionSuccessfulMsg());

            } else if (newQuantity == 0) {
                Optional<Member_Books> mb = memberBooksRepo.findByBookId(bookID);
                if (mb.isPresent()) {
                    book.setQuantity(0);
                    bookRepo.save(book);
                    throw new SuccessException(success.getBookDeletionSuccessfulCode(), success.getBookDeletionSuccessfulMsg());

                } else {
                    bookRepo.deleteById(bookID);
                    throw new SuccessException(success.getBookDeletionSuccessfulCode(), success.getBookDeletionSuccessfulMsg());

                }
            }

        }catch(SuccessException se) {
            throw se;
        }catch(Exception e){
            throw new ErrorException(error.getBookDeletionErrorCode(), error.getBookDeletionErrorMsg());

        }

    }



    public Book updateBook(Book book) {
        try{
            Book b = bookRepo.save(book);
            throw new SuccessException(success.getBookUpdateSuccessfulCode(), success.getBookUpdateSuccessfulMsg(),b);

        }catch(SuccessException se){
            throw se;
        } catch (Exception e) {
            throw new ErrorException(error.getBookUpdateErrorCode(), error.getBookUpdateErrorMsg());
        }


    }
}
