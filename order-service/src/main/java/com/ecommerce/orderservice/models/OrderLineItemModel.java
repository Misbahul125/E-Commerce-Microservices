package com.ecommerce.orderservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemModel {

    private Long orderLineItemId;
    private String skuCode;
    private Integer quantity;
    private BigDecimal price;
    private OrderModel order;
}
