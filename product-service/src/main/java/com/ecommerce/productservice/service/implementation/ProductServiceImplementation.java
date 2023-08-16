package com.ecommerce.productservice.service.implementation;

import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exceptions.ResourceNotFoundException;
import com.ecommerce.productservice.models.ProductModel;
import com.ecommerce.productservice.models.apiResponseModels.ApiResponseProductModels;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductModel createProduct(ProductModel productModel) {

        productModel.setCreatedAt(new Date());
        productModel.setUpdatedAt(null);

        Product product = this.modelMapper.map(productModel, Product.class);

        Product savedProduct = this.productRepository.save(product);

        return this.modelMapper.map(savedProduct, ProductModel.class);

    }

    @Override
    public ProductModel getProduct(String productId) {

        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Product ID", productId));

        return this.modelMapper.map(product, ProductModel.class);
    }

    @Override
    public ApiResponseProductModels getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode) {

        // sorting format
        Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // paging format
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // retrieving paged data items
        Page<Product> pageProducts = this.productRepository.findAll(pageable);

        List<Product> allProducts = pageProducts.getContent();

        List<ProductModel> productModels = allProducts.stream()
                .map((product) -> this.modelMapper.map(product, ProductModel.class))
                .toList();

        ApiResponseProductModels apiResponseProductModels = new ApiResponseProductModels(true, HttpStatus.OK.value(),
                "Product(s) Fetched Successfully", pageProducts.getNumber(), pageProducts.getSize(),
                pageProducts.getTotalElements(), pageProducts.getTotalPages(), pageProducts.isLast(),
                productModels);

        if (pageProducts.getNumber() >= pageProducts.getTotalPages()) {

            apiResponseProductModels.setMessage("No more product(s) found");
            apiResponseProductModels.setProductModels(null);

        }

        return apiResponseProductModels;

    }

    @Override
    public ProductModel updateProduct(ProductModel productModel) {

        Product product = this.productRepository.findById(productModel.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Product ID", productModel.getProductId()));

        if (productModel.getProductName() != null && !productModel.getProductName().isEmpty())
            product.setProductName(productModel.getProductName());

        if (productModel.getProductDescription() != null && !productModel.getProductDescription().isEmpty())
            product.setProductDescription(productModel.getProductDescription());

        if (productModel.getProductPrice() != null)
            product.setProductPrice(productModel.getProductPrice());

        product.setUpdatedAt(new Date());

        Product updatedProduct = this.productRepository.save(product);

        return this.modelMapper.map(updatedProduct, ProductModel.class);

    }

    @Override
    public void deleteProduct(String productId) {

        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Product ID", productId));

        this.productRepository.delete(product);

    }
}
