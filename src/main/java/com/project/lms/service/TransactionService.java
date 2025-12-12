package com.project.lms.service;

import com.project.lms.constants.AppErrorCode;
import com.project.lms.constants.AppSuccessCode;
import com.project.lms.exception.ErrorException;
import com.project.lms.exception.SuccessException;
import com.project.lms.model.Book;
import com.project.lms.model.Member_Books;
import com.project.lms.model.Transaction;
import com.project.lms.model.TransactionRequest;
import com.project.lms.repo.BookRepo;
import com.project.lms.repo.Member_BooksRepo;
import com.project.lms.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepo transactionRepo;
    private final Member_BooksRepo memberBooksRepo;
    private final BookRepo bookRepo;
    private final AppSuccessCode success;
    private final AppErrorCode error;

    @Autowired
    public TransactionService(TransactionRepo transactionRepo,
                              Member_BooksRepo memberBooksRepo,
                              BookRepo bookRepo,AppErrorCode error,AppSuccessCode success) {
        this.transactionRepo = transactionRepo;
        this.memberBooksRepo = memberBooksRepo;
        this.bookRepo = bookRepo;
        this.success = success;
        this.error = error;

    }

    public List<Transaction> getAllTransactions() {
        try {
            List<Transaction> transactions = transactionRepo.findAll();
            throw new SuccessException(success.getTransactionsRetrievedSuccessfulCode(),success.getTransactionsRetrievedSuccessfulMsg(),transactions);
        }catch(SuccessException se){
            throw se;
        }catch(Exception e){
            throw new ErrorException(error.getTransactionsLoadingErrorCode(),error.getTransactionsLoadingErrorMsg());
        }

    }

    public Transaction getTransactionById(String transactionID) {
        try {
            Transaction txn = transactionRepo.findAll()
                    .stream()
                    .filter(t -> t.getTransactionId().equals(transactionID))

                    .findFirst()

                    .orElse(null);
            throw new SuccessException(success.getTransactionByIdSuccessfulCode(),success.getTransactionByIdSuccessfulMsg(),txn);
        }catch(SuccessException se){
            throw se;
        } catch (Exception e) {
            throw new ErrorException(error.getTransactionByIdErrorCode(),error.getTransactionByIdErrorMsg());
        }

    }
    @Transactional(
            rollbackFor = ErrorException.class,
            noRollbackFor = SuccessException.class
    )
    public void addTransaction(TransactionRequest transactionReq) {
        try {
            String type = transactionReq.getTransactionType().toUpperCase();

            if (type.equals("ISSUE")) {
                handleIssueTransaction(transactionReq);
            } else if(type.equals("RETURN")){
                handleReturnTransaction(transactionReq);
            }
            else{
                throw new ErrorException(error.getTransactionTypeInvalidErrorCode(),error.getTransactionTypeInvalidErrorMsg());
            }

        }catch(SuccessException se){
            throw se;
        }catch(ErrorException ee){
            throw ee;
        }catch (Exception e) {
            throw new ErrorException(error.getTransactionFailedErrorCode(),error.getTransactionFailedErrorMsg());
        }
    }

    private void handleIssueTransaction(TransactionRequest req) {
            try{
                Optional<Book> bookOpt = bookRepo.findById(req.getBookId());



                if (bookOpt.isEmpty()) {
                    throw new ErrorException(error.getBookNotFoundErrorCode(),error.getBookNotFoundErrorMsg());
                }

                Book book = bookOpt.get();
                if (book.getQuantity() <= 0) {
                    throw new ErrorException(error.getBookNotAvailableErrorCode(),error.getBookNotAvailableErrorMsg());
                }

                // Create transaction
                Transaction transaction = new Transaction();
                transaction.setBookId(req.getBookId());
                transaction.setMemberId(req.getMemberId());
                transaction.setTransactionType("ISSUE");
                transaction.setTransactionDate(LocalDate.now());
                transactionRepo.save(transaction);


                // Update member_books
                Optional<Member_Books> mbOpt = memberBooksRepo.findByMemberIdAndBookId(req.getMemberId(), req.getBookId());
                if (mbOpt.isPresent()) {
                    Member_Books mb = mbOpt.get();
                    mb.setQuantity(mb.getQuantity() + 1);
                    memberBooksRepo.save(mb);

                } else {
                    memberBooksRepo.save(new Member_Books(req.getMemberId(), req.getBookId(), 1));
                }

                // Decrease book quantity
                book.setQuantity(book.getQuantity() - 1);
                bookRepo.save(book);

                throw new SuccessException(success.getBookIssueSuccessfulCode(),success.getBookIssueSuccessfulMsg());
            }catch(ErrorException ee){
                throw ee;
            }catch(SuccessException se){
                throw se;
            }catch(Exception e){
                throw  new ErrorException(error.getTransactionFailedErrorCode(),error.getTransactionFailedErrorMsg());
            }
    }

    private ResponseEntity<String> handleReturnTransaction(TransactionRequest req) {
        try{
            Transaction transaction = new Transaction();
            transaction.setBookId(req.getBookId());
            transaction.setMemberId(req.getMemberId());
            transaction.setTransactionType("RETURN");
            transaction.setTransactionDate(LocalDate.now());
            transactionRepo.save(transaction);

            // Increase book quantity
            Optional<Book> bookOpt = bookRepo.findById(req.getBookId());
            bookOpt.ifPresent(book -> {
                book.setQuantity(book.getQuantity() + 1);
                bookRepo.save(book);
            });

            // Update member_books
            Optional<Member_Books> mbOpt = memberBooksRepo.findByMemberIdAndBookId(req.getMemberId(), req.getBookId());


            if (mbOpt.isPresent()) {
                Member_Books mb = mbOpt.get();
                mb.setQuantity(mb.getQuantity() - 1);

                if (mb.getQuantity() == 0) {
                    memberBooksRepo.delete(mb);
                } else {
                    memberBooksRepo.save(mb);
                }
            }

            throw new SuccessException(success.getBookReturnedSuccessfulCode(),success.getBookReturnedSuccessfulMsg());
        }catch(SuccessException se){
            throw se;
        }catch(Exception e){
            throw new ErrorException(error.getTransactionFailedErrorCode(),error.getTransactionFailedErrorMsg());
        }


    }

    public void deleteTransaction(String transactionId) {
        try{
            transactionRepo.findAll().stream()
                    .filter(t -> t.getTransactionId().equals(transactionId))
                    .findFirst()
                    .ifPresent(transactionRepo::delete);
            throw new SuccessException(success.getTransactionDeletionSuccessfulCode(),success.getTransactionDeletionSuccessfulMsg());
        }catch(SuccessException se){
            throw se;
        }catch(Exception e){
            throw new ErrorException(error.getTransactionDeletionErrorCode(),error.getTransactionDeletionErrorMsg());
        }
    }
}
