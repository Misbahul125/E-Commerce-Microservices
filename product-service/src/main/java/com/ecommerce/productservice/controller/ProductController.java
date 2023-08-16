package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.models.ProductModel;
import com.ecommerce.productservice.models.apiResponseModels.ApiResponseProductModel;
import com.ecommerce.productservice.models.apiResponseModels.ApiResponseProductModels;
import com.ecommerce.productservice.service.ProductService;
import com.ecommerce.productservice.utility.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<ApiResponseProductModel> createProduct(@RequestBody ProductModel productModel) {

        ProductModel createdProduct = this.productService.createProduct(productModel);

        log.info("{} with product ID {} is created", createdProduct.getProductName(), createdProduct.getProductId());

        ApiResponseProductModel apiResponseProductModel = new ApiResponseProductModel(true,
                HttpStatus.CREATED.value(), "Product Created Successfully", createdProduct);

        return new ResponseEntity<>(apiResponseProductModel, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponseProductModel> getProduct(@PathVariable String productId) {
        ProductModel productModel = this.productService.getProduct(productId);

        ApiResponseProductModel apiResponseProductModel = new ApiResponseProductModel(true, HttpStatus.OK.value(),
                "Product Fetched Successfully", productModel);

        return new ResponseEntity<>(apiResponseProductModel, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseProductModels> getAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CATEGORY_ID, required = false) String sortBy,
            @RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE, required = false) Integer sortMode
    ) {

        ApiResponseProductModels apiResponseProductModels = this.productService.getAllProducts(pageNumber, pageSize, sortBy, sortMode);

        return new ResponseEntity<>(apiResponseProductModels, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<ApiResponseProductModel> updateProduct(@RequestBody ProductModel productModel) {
        ProductModel updatedProduct = this.productService.updateProduct(productModel);

        ApiResponseProductModel apiResponseProductModel = new ApiResponseProductModel(true, HttpStatus.OK.value(),
                "Product Updated Successfully", updatedProduct);

        log.info("{} with product ID {} is updated", updatedProduct.getProductName(), updatedProduct.getProductId());

        return new ResponseEntity<>(apiResponseProductModel, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseProductModel> deleteProduct(@PathVariable String productId) {
        this.productService.deleteProduct(productId);

        log.info("Product with product ID {} is deleted", productId);

        ApiResponseProductModel apiResponseProductModel = new ApiResponseProductModel(true, HttpStatus.OK.value(),
                "Product Deleted Successfully", null);

        return new ResponseEntity<>(apiResponseProductModel, HttpStatus.OK);
    }
}
