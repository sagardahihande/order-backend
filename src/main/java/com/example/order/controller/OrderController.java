package com.example.order.controller;

import com.example.order.dto.OrderRequestDTO;
import com.example.order.dto.OrderResponseDTO;
import com.example.order.dto.OrderStatusUpdateDTO;
import com.example.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponseDTO placeOrder(@Valid @RequestBody OrderRequestDTO dto) {
        return orderService.placeOrder(dto);
    }

    @GetMapping
    public Page<OrderResponseDTO> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        return orderService.getAllOrders(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @PutMapping("/{id}/status")
    public OrderResponseDTO updateStatus(@PathVariable Long id, @Valid @RequestBody OrderStatusUpdateDTO dto) {
        return orderService.updateOrderStatus(id, dto);
    }
}
