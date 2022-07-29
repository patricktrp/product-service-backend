package com.patricktreppmann.bookstore.productservice.error;

public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
