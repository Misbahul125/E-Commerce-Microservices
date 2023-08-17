package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.dto.InventoryResponse;
import com.ecommerce.inventoryservice.models.InventoryModel;
import com.ecommerce.inventoryservice.models.apiResponseModels.ApiResponseInventoryModels;

import java.util.List;

public interface InventoryService {

    InventoryModel createInventory(InventoryModel inventoryModel);

    InventoryModel getInventoryById(Long inventoryId);

    List<InventoryResponse> isInventoryInStock(List<String> skuCodes);

    ApiResponseInventoryModels getAllInventories(Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode);

    InventoryModel updateInventory(InventoryModel inventoryModel);

    void deleteInventory(Long inventoryId);

}
