package com.project.lms.exception;


public class ErrorException extends RuntimeException {

    private int code;
    private String message;
    private Object data;

    public ErrorException(int code, String message) {

        this.code = code;
        this.message = message;
        this.data = null;
    }
    public ErrorException(int code, String message, Object data) {

        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }
    public Object getData() {
        return data;
    }



    @Override
    public String getMessage() {
        return message;
    }








    @Override
    public String toString() {
        return super.toString();
    }

}
