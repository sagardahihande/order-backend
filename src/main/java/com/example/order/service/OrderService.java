package com.example.order.service;

import com.example.order.dto.OrderRequestDTO;
import com.example.order.dto.OrderResponseDTO;
import com.example.order.dto.OrderStatusUpdateDTO;
import com.example.order.model.OrderEntity;
import com.example.order.model.OrderStatus;
import com.example.order.repository.OrderRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final QueueConsumer queueConsumer;

    public OrderResponseDTO placeOrder(OrderRequestDTO dto) {
        try {
            OrderEntity entity = new OrderEntity();
            entity.setCustomerName(dto.getCustomerName());
            entity.setItems(objectMapper.writeValueAsString(dto.getItems()));
            entity.setTotalAmount(dto.getTotalAmount());
            entity.setOrderTime(LocalDateTime.now());
            entity.setStatus(OrderStatus.PENDING);

            entity = orderRepository.save(entity);
            queueConsumer.enqueue(entity.getId());
            return toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error placing order", e);
        }
    }

    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(this::toDto);
    }

    public OrderResponseDTO getOrder(Long id) {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return toDto(entity);
    }

    public OrderResponseDTO updateOrderStatus(Long id, OrderStatusUpdateDTO dto) {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        entity.setStatus(dto.getStatus());
        if (dto.getStatus() == OrderStatus.PROCESSED) {
            entity.setProcessedTime(LocalDateTime.now());
        }
        return toDto(orderRepository.save(entity));
    }

    private OrderResponseDTO toDto(OrderEntity entity) {
        try {
            OrderResponseDTO dto = new OrderResponseDTO();
            dto.setId(entity.getId());
            dto.setCustomerName(entity.getCustomerName());
            dto.setItems(objectMapper.readValue(entity.getItems(), new TypeReference<List<String>>() {}));
            dto.setTotalAmount(entity.getTotalAmount());
            dto.setOrderTime(entity.getOrderTime());
            dto.setProcessedTime(entity.getProcessedTime());
            dto.setStatus(entity.getStatus());
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping order", e);
        }
    }
}
