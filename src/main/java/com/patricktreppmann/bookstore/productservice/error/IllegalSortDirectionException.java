package com.patricktreppmann.bookstore.productservice.error;

public class IllegalSortDirectionException extends Exception {
    public IllegalSortDirectionException(String message) {
        super(message);
    }
}
