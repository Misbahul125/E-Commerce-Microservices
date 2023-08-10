package com.ecommerce.productservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);

		System.out.println("Product Service Application Started Successfully !!");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
