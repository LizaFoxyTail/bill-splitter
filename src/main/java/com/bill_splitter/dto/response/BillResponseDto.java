package com.bill_splitter.dto.response;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record BillResponseDto(
    double serviceFee,
    Map<String, Double> userTotals,
    List<PositionResponseDto> positions ) {}
