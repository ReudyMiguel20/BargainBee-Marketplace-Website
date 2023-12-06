package com.bargainbee.service.impl;

import com.bargainbee.repository.OrderRepository;
import com.bargainbee.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

}
