package com.ecommerce.productservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApiResponseProductModel {

    private Boolean isSuccess;
    private Integer code;
    private String message;
    private ProductModel productModel;

}
