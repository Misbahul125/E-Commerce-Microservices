package com.ecommerce.productservice.service;

import com.ecommerce.productservice.models.ApiResponseProductModels;
import com.ecommerce.productservice.models.ProductModel;

public interface ProductService {

    ProductModel createProduct(ProductModel productModel);

    ProductModel getProduct(String productId);

    ApiResponseProductModels getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode);

    ProductModel updateProduct(ProductModel productModel);

    void deleteProduct(String productId);

}
