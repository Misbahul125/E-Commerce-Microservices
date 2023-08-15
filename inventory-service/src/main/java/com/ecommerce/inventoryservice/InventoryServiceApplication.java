package com.ecommerce.inventoryservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);

		System.out.println("Inventory Service Application Started Successfully");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
