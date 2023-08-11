package com.ecommerce.orderservice.service.implementation;

import com.ecommerce.orderservice.models.OrderModel;
import com.ecommerce.orderservice.models.apiResponseModels.ApiResponseOrderModels;
import com.ecommerce.orderservice.service.OrderService;

public class OrderServiceImplementation implements OrderService {

    @Override
    public OrderModel createOrder(OrderModel orderModel) {
        return null;
    }

    @Override
    public OrderModel getOrderById(Long orderId) {
        return null;
    }

    @Override
    public ApiResponseOrderModels getAllOrders() {
        return null;
    }

    @Override
    public ApiResponseOrderModels getOrdersByUser(Integer userId) {
        return null;
    }

    @Override
    public OrderModel updateOrder(OrderModel orderModel) {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) {

    }

}
