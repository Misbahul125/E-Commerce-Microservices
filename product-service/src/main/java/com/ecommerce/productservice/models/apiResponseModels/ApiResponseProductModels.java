package com.ecommerce.productservice.models.apiResponseModels;

import com.ecommerce.productservice.models.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApiResponseProductModels {

    private Boolean isSuccess;
    private Integer code;
    private String message;

    private Integer pageNumber;
    private Integer pageSize;
    private Long totalItems;
    private Integer totalPages;
    private Boolean isLastPage;

    private List<ProductModel> productModels;
}
