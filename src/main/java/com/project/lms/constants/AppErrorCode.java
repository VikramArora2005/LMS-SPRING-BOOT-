package com.project.lms.constants;

import org.springframework.stereotype.Component;

@Component
public class AppErrorCode {

    //BOOK ERRORS

    private static final int BOOKS_RETRIEVE_ERROR_CODE = 101;
    private static final String BOOKS_RETRIEVE_ERROR_MSG = "CANNOT RETRIEVE BOOKS";


    private static final int BOOK_ISSUE_ERROR_CODE = 102;
    private static final String BOOK_ISSUE_ERROR_MSG = "BOOK ISSUE FAILED (NO BOOKS LEFT)";

    private static final int BOOK_ADDITION_ERROR_CODE = 103;
    private static final String BOOK_ADDITION_ERROR_MSG = "BOOK ADDITION FAILED";

    private static final int BOOK_DELETION_ERROR_CODE = 104;
    private static final String BOOK_DELETION_ERROR_MSG = "BOOK DELETION FAILED";

    private static final int BOOK_DELETION_CAUSE_ISSUED_ERROR_CODE = 105;
    private static final String BOOK_DELETION_CAUSE_ISSUED_ERROR_MSG = "BOOK DELETION FAILED (BOOK IS ISSUED)";


    private static final int BOOKS_LOADING_ERROR_CODE = 106;
    private static final String BOOKS_LOADING_ERROR_MSG = "ERROR LOADING BOOKS";


    private static final int BOOK_BY_ID_ERROR_CODE = 107;
    private static final String BOOK_BY_ID_ERROR_MSG = "COULDN'T RECIEVE BOOK BY ID HENCE PROCESS FAILED";

    private static final int BOOK_NOT_FOUND_ERROR_CODE = 108;
    private static final String BOOK_NOT_FOUND_ERROR_MSG = "BOOK DOESN'T EXIST";

    private static final int BOOKS_NULL_CODE = 109;
    private static final String BOOKS_NULL_MSG = "NO BOOKS PRESENT";


    private static final int BOOK_UPDATE_ERROR_CODE = 110;
    private static final String BOOK_UPDATE_ERROR_MSG = "BOOK UPDATE FAILED";


    private static final int BOOK_NOT_AVAILABLE_ERROR_CODE = 111;
    private static final String BOOK_NOT_AVAILABLE_ERROR_MSG = "BOOK NOT AVAILABLE";


    public static int getBookNotAvailableErrorCode()
    {
        return BOOK_NOT_AVAILABLE_ERROR_CODE;
    }
    public static String getBookNotAvailableErrorMsg()
    {
        return BOOK_NOT_AVAILABLE_ERROR_MSG;
    }
    public static int getBooksRetrieveErrorCode() {
        return BOOKS_RETRIEVE_ERROR_CODE;
    }
    public static String getBooksRetrieveErrorMsg() {
        return BOOKS_RETRIEVE_ERROR_MSG;
    }
    public static int getBookIssueErrorCode() {
        return BOOK_ISSUE_ERROR_CODE;
    }
    public static String getBookIssueErrorMsg() {
        return BOOK_ISSUE_ERROR_MSG;
    }
    public static int getBookAdditionErrorCode() {
        return BOOK_ADDITION_ERROR_CODE;
    }
    public static String getBookAdditionErrorMsg() {
        return BOOK_ADDITION_ERROR_MSG;
    }
    public static int getBookDeletionErrorCode() {
        return BOOK_DELETION_ERROR_CODE;
    }
    public static String getBookDeletionErrorMsg() {
        return BOOK_DELETION_ERROR_MSG;
    }
    public static int getBookDeletionCauseIssuedErrorCode() {
        return BOOK_DELETION_CAUSE_ISSUED_ERROR_CODE;
    }
    public static String getBookDeletionCauseIssuedErrorMsg() {
        return BOOK_DELETION_CAUSE_ISSUED_ERROR_MSG;
    }
    public static int getBooksLoadingErrorCode() {
        return BOOKS_LOADING_ERROR_CODE;
    }
    public static String getBooksLoadingErrorMsg() {
        return BOOKS_LOADING_ERROR_MSG;
    }
    public static int getBooksByIdErrorCode() {
        return BOOK_BY_ID_ERROR_CODE;
    }
    public static String getBooksByIdErrorMsg() {
        return BOOK_BY_ID_ERROR_MSG;
    }
    public static int getBookNotFoundErrorCode() {
        return BOOK_NOT_FOUND_ERROR_CODE;
    }
    public static String getBookNotFoundErrorMsg() {
        return BOOK_NOT_FOUND_ERROR_MSG;
    }
    public static int getBooksNullCode(){
        return BOOKS_NULL_CODE;
    }
    public static String getBooksNullMsg(){
        return BOOKS_NULL_MSG;
    }
    public static int getBookUpdateErrorCode() {
        return BOOK_UPDATE_ERROR_CODE;
    }
    public static String getBookUpdateErrorMsg() {
        return BOOK_UPDATE_ERROR_MSG;
    }


    //MEMBER ERRORS

    private static final int MEMBER_NOT_FOUND_ERROR_CODE = 201;
    private static final String MEMBER_NOT_FOUND_ERROR_MSG = "MEMBER NOT FOUND";

    private static final int MEMBER_ADDITION_ERROR_CODE = 202;
    private static final String MEMBER_ADDITION_ERROR_MSG = "MEMBER ADDITION FAILED";

    private static final int MEMBER_DELETION_ERROR_CODE = 203;
    private static final String MEMBER_DELETION_ERROR_MSG = "MEMBER DELETION FAILED";

    private static final int MEMBER_DETAILS_ERROR_CODE = 204;
    private static final String MEMBER_DETAILS_ERROR_MSG = "CANNOT LOAD MEMBER DETAILS";

    private static final int MEMBERS_LOADING_ERROR_CODE = 205;
    private static final String MEMBERS_LOADING_ERROR_MSG = "CANNOT LOAD MEMBERS";

    private static final int MEMBER_BOOKS_LOADING_ERROR_CODE = 206;
    private static final String MEMBER_BOOKS_LOADING_ERROR_MSG = "CANNOT LOAD MEMBER BOOKS";

    private static final int MEMBER_UPDATE_ERROR_CODE = 207;
    private static final String MEMBER_UPDATE_ERROR_MSG = "MEMBER UPDATE FAILED";



    public static int getMemberNotFoundErrorCode() {
        return MEMBER_NOT_FOUND_ERROR_CODE;
    }
    public static String getMemberNotFoundErrorMsg() {
        return MEMBER_NOT_FOUND_ERROR_MSG;
    }
    public static int getMemberAdditionErrorCode() {
        return MEMBER_ADDITION_ERROR_CODE;
    }
    public static String getMemberAdditionErrorMsg() {
        return MEMBER_ADDITION_ERROR_MSG;
    }
    public static int getMemberDeletionErrorCode() {
        return MEMBER_DELETION_ERROR_CODE;
    }
    public static String getMemberDeletionErrorMsg() {
        return MEMBER_DELETION_ERROR_MSG;
    }
    public static int getMemberDetailsErrorCode() {
        return MEMBER_DETAILS_ERROR_CODE;
    }
    public static String getMemberDetailsErrorMsg() {
        return MEMBER_DETAILS_ERROR_MSG;
    }
    public static int  getMembersLoadingErrorCode() {
        return MEMBERS_LOADING_ERROR_CODE;
    }
    public static String getMembersLoadingErrorMsg() {
        return MEMBERS_LOADING_ERROR_MSG;
    }

    public static int getMemberBooksLoadingErrorCode() {
        return MEMBER_BOOKS_LOADING_ERROR_CODE;
    }

    public static String getMemberBooksLoadingErrorMsg() {
        return MEMBER_BOOKS_LOADING_ERROR_MSG;
    }
    public static int getMemberUpdateErrorCode() {
        return MEMBER_UPDATE_ERROR_CODE;
    }
    public static String getMemberUpdateErrorMsg() {
        return MEMBER_UPDATE_ERROR_MSG;
    }

    //TRANSACTION ERRORS

    private static final int TRANSACTIONS_LOADING_ERROR_CODE = 501;
    private static final String TRANSACTIONS_LOADING_ERROR_MSG = "ERROR LOADING TRANSACTIONS";

    private static final int TRANSACTION_FAILED_ERROR_CODE = 502;
    private static final String TRANSACTION_FAILED_ERROR_MSG = "TRANSACTION FAILED";

    private static final int TRANSACTION_BY_ID_ERROR_CODE = 503;
    private static final String TRANSACTION_BY_ID_ERROR_MSG = "TRANSACTION NOT FOUND";

    private static final int TRANSACTION_DELETION_ERROR_CODE = 504;
    private static final String TRANSACTION_DELETION_ERROR_MSG = "TRANSACTION DELETION FAILED";

    private static final int TRANSACTION_TYPE_INVALID_ERROR_CODE = 505;
    private static final String TRANSACTION_TYPE_INVALID_ERROR_MSG = "TRANSACTION TYPE IS INVALID";




    public static int getTransactionTypeInvalidErrorCode() {
        return TRANSACTION_TYPE_INVALID_ERROR_CODE;
    }
    public static String getTransactionTypeInvalidErrorMsg() {
        return TRANSACTION_TYPE_INVALID_ERROR_MSG;
    }
    public static int getTransactionDeletionErrorCode() {
        return TRANSACTION_DELETION_ERROR_CODE;
    }
    public static String getTransactionDeletionErrorMsg() {
        return TRANSACTION_DELETION_ERROR_MSG;
    }
    public static int getTransactionByIdErrorCode() {
        return TRANSACTION_BY_ID_ERROR_CODE;
    }
    public static String getTransactionByIdErrorMsg() {
        return TRANSACTION_BY_ID_ERROR_MSG;
    }

    public static int  getTransactionsLoadingErrorCode() {
        return TRANSACTIONS_LOADING_ERROR_CODE;
    }
    public static String getTransactionsLoadingErrorMsg() {
        return TRANSACTIONS_LOADING_ERROR_MSG;
    }
    public static int getTransactionFailedErrorCode() {
        return TRANSACTION_FAILED_ERROR_CODE;
    }
    public static String getTransactionFailedErrorMsg() {
        return TRANSACTION_FAILED_ERROR_MSG;
    }
}


