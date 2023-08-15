package com.ecommerce.orderservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemModel {

    private Long orderLineItemId;
    private String skuCode;
    private Integer quantity;
    private BigDecimal price;

    private Date createdAt;
    private Date updatedAt;

}
