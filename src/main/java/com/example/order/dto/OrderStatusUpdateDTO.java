package com.example.order.dto;

import com.example.order.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderStatusUpdateDTO {
    @NotNull
    private OrderStatus status;
}
