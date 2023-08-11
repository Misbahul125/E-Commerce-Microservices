package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.models.OrderModel;
import com.ecommerce.orderservice.models.apiResponseModels.ApiResponseOrderModels;

public interface OrderService {

    OrderModel createOrder(OrderModel orderModel);

    OrderModel getOrderById(Long orderId);

    ApiResponseOrderModels getAllOrders();

    ApiResponseOrderModels getOrdersByUser(Integer userId);

    OrderModel updateOrder(OrderModel orderModel);

    void deleteOrder(Long orderId);

}
