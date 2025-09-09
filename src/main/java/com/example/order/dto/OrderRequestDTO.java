package com.example.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequestDTO {
    @NotBlank
    private String customerName;

    @NotEmpty
    private List<String> items;

    @NotNull
    private BigDecimal totalAmount;
}
