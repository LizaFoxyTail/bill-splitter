package com.bill_splitter.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record BillRequestDto(
    @NotEmpty(message = "list of users can't be empty")
    List<String> users,
    @NotEmpty(message = "list of positions can't be empty")
    List<PositionRequestDto> positions ) {}
