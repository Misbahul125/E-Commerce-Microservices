package com.ecommerce.inventoryservice.models.apiResponseModels;

import com.ecommerce.inventoryservice.models.InventoryModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseInventoryModels {

    private Boolean isSuccess;
    private Integer code;
    private String message;

    private Integer pageNumber;
    private Integer pageSize;
    private Long totalItems;
    private Integer totalPages;
    private Boolean isLastPage;

    private List<InventoryModel> inventoryModels;

}
