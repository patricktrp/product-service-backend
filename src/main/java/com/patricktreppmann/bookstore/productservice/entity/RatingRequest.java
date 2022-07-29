package com.patricktreppmann.bookstore.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingRequest {
    @Min(value = 1, message = "rating can't be lower than one star")
    @Max(value = 5, message = "rating can't be higher than five stars")
    private int stars;
    @NotNull(message = "heading missing")
    private String heading;
    @NotNull(message = "description missing")
    private String description;
}
