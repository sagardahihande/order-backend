package com.example.order.dto;

import com.example.order.model.OrderStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long id;
    private String customerName;
    private List<String> items;
    private BigDecimal totalAmount;
    private LocalDateTime orderTime;
    private LocalDateTime processedTime;
    private OrderStatus status;
}
