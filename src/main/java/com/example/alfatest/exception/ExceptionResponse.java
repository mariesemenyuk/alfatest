package com.example.alfatest.exception;

public class ExceptionResponse{

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ExceptionResponse(String error) {this.error = error;};
}
