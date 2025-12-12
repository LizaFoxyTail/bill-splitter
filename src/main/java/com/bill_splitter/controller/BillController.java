package com.bill_splitter.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bill_splitter.dto.request.BillRequestDto;
import com.bill_splitter.dto.response.BillResponseDto;
import com.bill_splitter.service.BillService;

@RestController
@RequestMapping("/api/v1/bills")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BillController {

    BillService billService;

    @PostMapping("/splitting")
    public ResponseEntity<BillResponseDto> split(@Valid @RequestBody BillRequestDto dto) {
        return ResponseEntity.ok(billService.split(dto));
    }
}
