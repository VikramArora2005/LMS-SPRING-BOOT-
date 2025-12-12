package com.project.lms.exception;

public class SuccessException extends RuntimeException {

    private final int code;
    private final String message;
    private final Object data;

    public SuccessException(int code, String message, Object data) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public SuccessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = null;


    }



    public int getCode() { return code; }

    public Object getData() { return data; }
}
