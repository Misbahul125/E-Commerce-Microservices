package com.ecommerce.orderservice.models.apiResponseModels;

import com.ecommerce.orderservice.models.OrderModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseOrderModel {

    private Boolean isSuccess;
    private Integer code;
    private String message;
    private OrderModel orderModel;

}
