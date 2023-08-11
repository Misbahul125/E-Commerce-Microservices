package com.ecommerce.orderservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {

    private Long orderId;
    private String orderNumber;
    private List<OrderLineItemModel> orderLineItems;
    private BigDecimal totalAmount;

}
