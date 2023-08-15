package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.models.OrderModel;
import com.ecommerce.orderservice.models.apiResponseModels.ApiResponseOrderModels;

public interface OrderService {

    OrderModel placeOrder(OrderModel orderModel);

    OrderModel getOrderById(Long orderId);

    ApiResponseOrderModels getAllOrders(Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode);

    ApiResponseOrderModels getOrdersByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode);

    OrderModel updateOrder(OrderModel orderModel);

    void deleteOrder(Long orderId);

}
