package com.bill_splitter.service;

import com.bill_splitter.dto.request.BillRequestDto;
import com.bill_splitter.dto.request.PositionRequestDto;
import com.bill_splitter.dto.response.BillResponseDto;
import com.bill_splitter.dto.response.PositionResponseDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BillServiceImpl implements BillService {
    @Override
    public BillResponseDto split(BillRequestDto dto) {

        Map<String, BigDecimal> totals = new HashMap<>();

        dto.users().forEach(user -> totals.put(user, BigDecimal.ZERO));
        List<PositionResponseDto> dishResponses = new ArrayList<>();
        for (PositionRequestDto p : dto.positions()) {
            BigDecimal totalPrice = p.price().multiply(BigDecimal.valueOf(p.quantity()));

            dishResponses.add(new PositionResponseDto(
                p.name(),
                p.isShared(),
                totalPrice.doubleValue()));

            if (p.isShared()) {
                int count = dto.users().size();
                BigDecimal share = totalPrice
                    .divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);

                dto.users().forEach(name -> totals.put(name, totals.get(name).add(share)));
                continue;
            }
            if (!dto.users().contains(p.owner())) {
                throw new IllegalArgumentException( "Owner '" + p.owner() + "' is not in users list" );
            }
            totals.put(p.owner(), totals.get(p.owner()).add(totalPrice));
        }

        double service_Fee = 10.0;

        BigDecimal feeShare = BigDecimal.valueOf(service_Fee)
            .divide(BigDecimal.valueOf(dto.users().size()), 2, RoundingMode.HALF_UP);

        dto.users().forEach(user -> totals.put(user, totals.get(user).add(feeShare)));

        Map<String, Double> finalTotals = new HashMap<>();
        totals.forEach((user, value) -> finalTotals.put(user, value.doubleValue()));

        return new BillResponseDto( service_Fee, finalTotals, dishResponses );
    }
}
