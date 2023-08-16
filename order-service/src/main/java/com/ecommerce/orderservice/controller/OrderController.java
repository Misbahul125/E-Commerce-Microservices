package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.models.OrderModel;
import com.ecommerce.orderservice.models.apiResponseModels.ApiResponseOrderModel;
import com.ecommerce.orderservice.models.apiResponseModels.ApiResponseOrderModels;
import com.ecommerce.orderservice.service.OrderService;
import com.ecommerce.orderservice.utility.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<ApiResponseOrderModel> placeOrder(@RequestBody OrderModel orderModel) {

        OrderModel placedOrder = this.orderService.placeOrder(orderModel);

        log.info("Order with order ID {} is created", placedOrder.getOrderNumber());

        ApiResponseOrderModel apiResponseOrderModel = new ApiResponseOrderModel(true,
                HttpStatus.CREATED.value(), "Order Placed Successfully", placedOrder);

        return new ResponseEntity<>(apiResponseOrderModel, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponseOrderModel> getOrderById(@PathVariable Long orderId) {
        OrderModel orderModel = this.orderService.getOrderById(orderId);

        ApiResponseOrderModel apiResponseOrderModel = new ApiResponseOrderModel(true, HttpStatus.OK.value(),
                "Order Fetched Successfully", orderModel);

        return new ResponseEntity<>(apiResponseOrderModel, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseOrderModels> getAllOrders(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CATEGORY_ID, required = false) String sortBy,
            @RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE, required = false) Integer sortMode
    ) {

        ApiResponseOrderModels apiResponseOrderModels = this.orderService.getAllOrders(pageNumber, pageSize, sortBy, sortMode);

        return new ResponseEntity<>(apiResponseOrderModels, HttpStatus.OK);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<ApiResponseOrderModels> getOrdersByUserId(
            @PathVariable Integer userId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CATEGORY_ID, required = false) String sortBy,
            @RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE, required = false) Integer sortMode
    ) {

        ApiResponseOrderModels apiResponseOrderModels = this.orderService.getAllOrders(pageNumber, pageSize, sortBy, sortMode);

        return new ResponseEntity<>(apiResponseOrderModels, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<ApiResponseOrderModel> updateOrder(@RequestBody OrderModel orderModel) {
        OrderModel updatedOrder = this.orderService.updateOrder(orderModel);

        ApiResponseOrderModel apiResponseOrderModel = new ApiResponseOrderModel(true, HttpStatus.OK.value(),
                "Order Updated Successfully", updatedOrder);

        log.info("Order with order ID {} is updated", updatedOrder.getOrderNumber());

        return new ResponseEntity<>(apiResponseOrderModel, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseOrderModel> deleteProduct(@PathVariable Long orderId) {
        this.orderService.deleteOrder(orderId);

        log.info("Order with order ID {} is deleted", orderId);

        ApiResponseOrderModel apiResponseOrderModel = new ApiResponseOrderModel(true, HttpStatus.OK.value(),
                "Order Deleted Successfully", null);

        return new ResponseEntity<>(apiResponseOrderModel, HttpStatus.OK);
    }

}
