package com.patricktreppmann.bookstore.productservice.error;

public class IllegalSortParamException extends Exception {
    public IllegalSortParamException(String message) {
        super(message);
    }
}
