package com.ecommerce.inventoryservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryModel {

    private Long inventoryId;
    private String skuCode;
    private Integer quantity;

}
