package com.patricktreppmann.bookstore.productservice.error;

public class BookNotFoundException extends Exception{
    public BookNotFoundException(String message) {
        super(message);
    }
}
