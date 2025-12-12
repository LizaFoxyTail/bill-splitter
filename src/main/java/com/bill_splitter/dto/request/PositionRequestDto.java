package com.bill_splitter.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PositionRequestDto(
    @NotBlank(message = "position name can't be blank")
    String name,

    @NotNull(message = "price can't be empty")
    @Positive(message = "price must be > 0")
    BigDecimal price,

    @NotNull(message = "quantity can't be empty")
    @Min(value = 1)
    int quantity,

    @NotNull(message = "isShared can't be null")
    Boolean isShared,

    @NotBlank(message = "name can't be blank")
    String owner ) {}
