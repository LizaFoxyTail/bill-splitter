package com.bill_splitter;

import com.bill_splitter.dto.request.BillRequestDto;
import com.bill_splitter.dto.request.PositionRequestDto;
import com.bill_splitter.dto.response.BillResponseDto;
import com.bill_splitter.service.BillService;
import com.bill_splitter.service.BillServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class BillServiceTests {
    BillService billService;

    @BeforeEach
    void setUp() {
        billService = new BillServiceImpl();
    }

    @Test
    void split_ShouldCalculateCorrectly_ForSharedAndPrivateDishes() {
        BillRequestDto request = new BillRequestDto(
            //Arrange
            List.of("Alice", "Bob"),
            List.of(
                new PositionRequestDto("Pizza", new BigDecimal("20.00"), 1, true, "Alice"),
                new PositionRequestDto("Salad", new BigDecimal("10.00"), 1, false, "Bob")
            )
        );

        //Act
        BillResponseDto response = billService.split(request);

        //Assert
        Map<String, Double> totals = response.userTotals();
        assertThat(totals.get("Alice")).isEqualTo(15.0);
        assertThat(totals.get("Bob")).isEqualTo(25.0);
    }

    @Test
    void split_ShouldThrowException_WhenOwnerNotInUsers() {
        // Arrange
        BillRequestDto request = new BillRequestDto(
            List.of("Alice", "Bob"),
            List.of(
                new PositionRequestDto("Pizza", new BigDecimal("20.00"), 1, false, "Charlie")
            )
        );

        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> billService.split(request)
        );
    }
}
