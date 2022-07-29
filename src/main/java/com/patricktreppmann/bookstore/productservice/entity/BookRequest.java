package com.patricktreppmann.bookstore.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    @NotNull(message = "name must be set")
    private String name;
    @NotNull(message = "author must be set")
    private String author;
    @NotNull(message = "isbn must be set")
    private String isbn;
    @NotNull(message = "price must be set")
    private Double price;
    @NotEmpty(message = "coverImgUrl must be set")
    private String coverImgUrl;
    @NotNull(message = "categoryId must be set")
    private Long categoryId;
}
