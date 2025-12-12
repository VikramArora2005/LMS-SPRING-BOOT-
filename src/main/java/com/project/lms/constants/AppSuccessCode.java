package com.project.lms.constants;

import org.springframework.stereotype.Component;

@Component
public class AppSuccessCode {

    private static final int BOOK_ADDITION_SUCCESSFUL_CODE = 301;
    private static final String BOOK_ADDITION_SUCCESSFUL_MSG = "BOOK ADDED SUCCESSFULLY";

    private static final int BOOK_DELETION_SUCCESSFUL_CODE = 302;
    private static final String BOOK_DELETION_SUCCESSFUL_MSG = "BOOK DELETED SUCCESSFULLY";


    private static final int BOOK_UPDATE_SUCCESSFUL_CODE = 303;
    private static final String BOOK_UPDATE_SUCCESSFUL_MSG = "BOOK UPDATED SUCCESSFULLY";

    private static final int BOOK_ISSUE_SUCCESSFUL_CODE = 304;
    private static final String BOOK_ISSUE_SUCCESSFUL_MSG = "BOOK ISSUED SUCCESSFULLY";

    private static final int BOOK_RETURNED_SUCCESSFUL_CODE = 305;
    private static final String BOOK_RETURNED_SUCCESSFUL_MSG = "BOOK RETURNED SUCCESSFULLY";

    private static final int BOOKS_RETRIEVED_SUCCESSFUL_CODE = 303;
    private static final String BOOKS_RETRIEVED_SUCCESSFUL_MSG = "BOOK UPDATED SUCCESSFULLY";


    public static int getBookIssueSuccessfulCode(){
        return BOOK_ISSUE_SUCCESSFUL_CODE;
    }
    public static String getBookIssueSuccessfulMsg(){
        return BOOK_ISSUE_SUCCESSFUL_MSG;
    }
    public static int getBookReturnedSuccessfulCode(){
        return BOOK_RETURNED_SUCCESSFUL_CODE;
    }
    public static String getBookReturnedSuccessfulMsg(){
        return BOOK_RETURNED_SUCCESSFUL_MSG;
    }
    public static int getBooksRetrievedSuccessfulCode() {
        return BOOKS_RETRIEVED_SUCCESSFUL_CODE;
    }
    public  static String getBooksRetrievedSuccessfulMsg() {
        return BOOKS_RETRIEVED_SUCCESSFUL_MSG;
    }

    public static int getBookAdditionSuccessfulCode() {
        return BOOK_ADDITION_SUCCESSFUL_CODE;
    }
    public static String getBookAdditionSuccessfulMsg() {
        return BOOK_ADDITION_SUCCESSFUL_MSG;
    }
    public static int getBookDeletionSuccessfulCode() {
        return BOOK_DELETION_SUCCESSFUL_CODE;
    }
    public static String getBookDeletionSuccessfulMsg() {
        return BOOK_DELETION_SUCCESSFUL_MSG;
    }
    public static int getBookUpdateSuccessfulCode() {
        return BOOK_UPDATE_SUCCESSFUL_CODE;
    }
    public static String getBookUpdateSuccessfulMsg() {
        return BOOK_UPDATE_SUCCESSFUL_MSG;
    }


    private static final int MEMBER_ADDITION_SUCCESSFUL_CODE = 401;
    private static final String MEMBER_ADDITION_SUCCESSFUL_MSG = "MEMBER ADDED SUCCESSFULLY";

    private static final int MEMBER_DELETION_SUCCESSFUL_CODE = 402;
    private static final String MEMBER_DELETION_SUCCESSFUL_MSG = "MEMBER DELETED SUCCESSFULLY";

    private static final int MEMBER_UPDATE_SUCCESSFUL_CODE = 403;
    private static final String MEMBER_UPDATE_SUCCESSFUL_MSG = "MEMBER UPDATED SUCCESSFULLY";

    private static final int MEMBERS_RETRIEVED_SUCCESSFUL_CODE = 404;
    private static final String MEMBERS_RETRIEVED_SUCCESSFUL_MSG = "MEMBERS LOADED SUCCESSFULLY";

    private static final int MEMBER_BOOKS_LOADING_SUCCESSFUL_CODE = 405;
    private static final String MEMBER_BOOKS_LOADING_SUCCESSFUL_MSG = "MEMBER BOOKS LOADED SUCCESSFULLY";

    private static final int MEMBER_FOUND_SUCCESSFUL_CODE = 406;
    private static final String MEMBER_FOUND_SUCCESSFUL_MSG = "MEMBER FOUND";


    public static int getMemberFoundSuccessfulCode(){
        return MEMBER_FOUND_SUCCESSFUL_CODE;
    }
    public static String getMemberFoundSuccessfulMsg(){
        return MEMBER_FOUND_SUCCESSFUL_MSG;
    }


    public static int getMemberBooksLoadingSuccessfulCode(){
        return MEMBER_BOOKS_LOADING_SUCCESSFUL_CODE;
    }
    public static String getMemberBooksLoadingSuccessfulMsg(){
        return MEMBER_BOOKS_LOADING_SUCCESSFUL_MSG;
    }
    public static int getMembersRetrievedSuccessfulCode() {
        return MEMBERS_RETRIEVED_SUCCESSFUL_CODE;
    }
    public static String getMembersRetrievedSuccessfulMsg() {
        return MEMBERS_RETRIEVED_SUCCESSFUL_MSG;
    }
    public static int getMemberAdditionSuccessfulCode() {
        return MEMBER_ADDITION_SUCCESSFUL_CODE;
    }
    public static String getMemberAdditionSuccessfulMsg() {
        return MEMBER_ADDITION_SUCCESSFUL_MSG;
    }
    public static int getMemberDeletionSuccessfulCode() {
        return MEMBER_DELETION_SUCCESSFUL_CODE;
    }
    public static String getMemberDeletionSuccessfulMsg() {
        return MEMBER_DELETION_SUCCESSFUL_MSG;
    }
    public static int getMemberUpdateSuccessfulCode() {
        return MEMBER_UPDATE_SUCCESSFUL_CODE;
    }
    public static String getMemberUpdateSuccessfulMsg() {
        return MEMBER_UPDATE_SUCCESSFUL_MSG;
    }

    private static final int TRANSACTION_SUCCESSFUL_CODE = 601;
    private static final String TRANSACTION_SUCCESSFUL_MSG = "TRANSACTION SUCCESSFUL";

    private static final int TRANSACTIONS_RETRIEVED_SUCCESSFUL_CODE = 602;
    private static final String TRANSACTIONS_RETRIEVED_SUCCESSFUL_MSG = "TRANSACTIONS RETRIEVED SUCCESSFULLY";

    private static final int TRANSACTION_BY_ID_SUCCESSFUL_CODE = 603;
    private static final String TRANSACTION_BY_ID_SUCCESSFUL_MSG = "TRANSACTION FOUND SUCCESSFULLY";

    private static final int TRANSACTION_DELETION_SUCCESSFUL_CODE = 604;
    private static final String TRANSACTION_DELETION_SUCCESSFUL_MSG = "TRANSACTION DELETED SUCCESSFULLY";

    public static int getTransactionDeletionSuccessfulCode(){
        return TRANSACTION_DELETION_SUCCESSFUL_CODE;
    }
    public  static String getTransactionDeletionSuccessfulMsg() {
        return TRANSACTION_DELETION_SUCCESSFUL_MSG;
    }
    public static int getTransactionByIdSuccessfulCode(){
        return TRANSACTION_BY_ID_SUCCESSFUL_CODE;
    }
    public static String getTransactionByIdSuccessfulMsg(){
        return TRANSACTION_BY_ID_SUCCESSFUL_MSG;
    }
    public static int getTransactionsRetrievedSuccessfulCode(){
        return TRANSACTIONS_RETRIEVED_SUCCESSFUL_CODE;
    }
    public static String getTransactionsRetrievedSuccessfulMsg(){
        return TRANSACTIONS_RETRIEVED_SUCCESSFUL_MSG;
    }
    public static int getTransactionSuccessfulCode(){
        return TRANSACTION_SUCCESSFUL_CODE;
    }
    public static String getTransactionSuccessfulMsg(){
        return TRANSACTION_SUCCESSFUL_MSG;
    }



}
