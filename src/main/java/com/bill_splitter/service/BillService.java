package com.bill_splitter.service;

import com.bill_splitter.dto.request.BillRequestDto;
import com.bill_splitter.dto.response.BillResponseDto;

public interface BillService {
    BillResponseDto split(BillRequestDto dto);
}
