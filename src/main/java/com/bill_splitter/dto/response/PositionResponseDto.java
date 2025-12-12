package com.bill_splitter.dto.response;

public record PositionResponseDto(
    String name,
    boolean shared,
    double totalPrice
) {}
