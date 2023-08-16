package com.ecommerce.productservice.models.apiResponseModels;

import com.ecommerce.productservice.models.ProductModel;
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
