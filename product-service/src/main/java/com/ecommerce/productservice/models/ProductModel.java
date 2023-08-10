package com.ecommerce.productservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductModel {

    private String productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private List<String> productImages;

    private Date createdAt;
    private Date updatedAt;

}
