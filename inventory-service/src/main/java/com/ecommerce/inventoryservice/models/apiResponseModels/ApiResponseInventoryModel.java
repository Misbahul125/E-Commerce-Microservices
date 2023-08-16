package com.ecommerce.inventoryservice.models.apiResponseModels;

import com.ecommerce.inventoryservice.models.InventoryModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseInventoryModel {

    private Boolean isSuccess;
    private Integer code;
    private String message;

    private InventoryModel inventoryModel;

}
