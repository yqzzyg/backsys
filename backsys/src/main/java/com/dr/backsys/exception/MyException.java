package com.dr.backsys.exception;

public class MyException extends RuntimeException {
    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyException(){
        super();
    }

    public MyException(Integer code, String message){
        super();
        this.code = code;
        this.message = message;
    }

}
