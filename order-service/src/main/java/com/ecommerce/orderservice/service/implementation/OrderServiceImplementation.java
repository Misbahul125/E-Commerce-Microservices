package com.ecommerce.orderservice.service.implementation;

import com.ecommerce.orderservice.entities.Order;
import com.ecommerce.orderservice.entities.OrderLineItem;
import com.ecommerce.orderservice.exceptions.ResourceNotFoundException;
import com.ecommerce.orderservice.models.OrderLineItemModel;
import com.ecommerce.orderservice.models.OrderModel;
import com.ecommerce.orderservice.models.apiResponseModels.ApiResponseOrderModels;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderModel placeOrder(OrderModel orderModel) {

        BigDecimal totalPrice = new BigDecimal(0);

        Order order = this.modelMapper.map(orderModel, Order.class);

        List<OrderLineItem> orderLineItems = new ArrayList<>();

        Date date = new Date();

        for (OrderLineItemModel orderLineItemModel : orderModel.getOrderLineItems()) {
            OrderLineItem orderLineItem = this.modelMapper.map(orderLineItemModel, OrderLineItem.class);
            totalPrice = totalPrice.add(
                    orderLineItemModel.getPrice().
                            multiply(BigDecimal.valueOf(orderLineItemModel.getQuantity()))
            );

            orderLineItem.setCreatedAt(date);
            orderLineItem.setUpdatedAt(null);

            orderLineItem.setOrder(order);
            orderLineItems.add(orderLineItem);
        }

        order.setOrderLineItems(orderLineItems);
        order.setTotalAmount(totalPrice);
        order.setOrderNumber("OID" + System.currentTimeMillis());
        order.setCreatedAt(date);
        order.setUpdatedAt(null);

        Order createdOrder = orderRepository.save(order);

        return this.modelMapper.map(createdOrder, OrderModel.class);
    }

    @Override
    public OrderModel getOrderById(Long orderId) {

        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "order ID", orderId.toString()));

        return this.modelMapper.map(order, OrderModel.class);

    }

    @Override
    public ApiResponseOrderModels getAllOrders(Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode) {

        // sorting format
        Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // paging format
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // retrieving paged data items
        Page<Order> pageOrders = this.orderRepository.findAll(pageable);

        List<Order> allOrders = pageOrders.getContent();

        List<OrderModel> orderModels = allOrders.stream()
                .map((order) -> this.modelMapper.map(order, OrderModel.class))
                .toList();

        ApiResponseOrderModels apiResponseOrderModels = new ApiResponseOrderModels(true, HttpStatus.OK.value(),
                "Order(s) Fetched Successfully", pageOrders.getNumber(), pageOrders.getSize(),
                pageOrders.getTotalElements(), pageOrders.getTotalPages(), pageOrders.isLast(),
                orderModels);

        if (pageOrders.getNumber() >= pageOrders.getTotalPages()) {

            apiResponseOrderModels.setMessage("No more order(s) found");
            apiResponseOrderModels.setOrderModels(null);

        }

        return apiResponseOrderModels;
    }

    @Override
    public ApiResponseOrderModels getOrdersByUser(Integer userId,Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode) {
        return null;
    }

    @Override
    public OrderModel updateOrder(OrderModel orderModel) {

        Order order = this.orderRepository.findById(orderModel.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "order ID", orderModel.getOrderId().toString()));

        return null;

    }

    @Override
    public void deleteOrder(Long orderId) {

        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "order ID", orderId.toString()));

        this.orderRepository.delete(order);

    }

}
