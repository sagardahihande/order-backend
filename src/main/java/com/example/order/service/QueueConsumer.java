package com.example.order.service;

import com.example.order.model.OrderEntity;
import com.example.order.model.OrderStatus;
import com.example.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@RequiredArgsConstructor
public class QueueConsumer {

    private final OrderRepository orderRepository;
    private final BlockingQueue<Long> queue = new LinkedBlockingQueue<>();

    public void enqueue(Long orderId) {
        queue.offer(orderId);
    }

    @Scheduled(fixedRate = 2000)
    public void processOrders() {
        Long orderId = queue.poll();
        if (orderId != null) {
            orderRepository.findById(orderId).ifPresent(order -> {
                order.setStatus(OrderStatus.PROCESSING);
                orderRepository.save(order);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                order.setStatus(OrderStatus.PROCESSED);
                order.setProcessedTime(LocalDateTime.now());
                orderRepository.save(order);
            });
        }
    }
}
