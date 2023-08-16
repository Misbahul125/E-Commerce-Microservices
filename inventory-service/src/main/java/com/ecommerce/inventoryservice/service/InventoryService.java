package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.models.InventoryModel;
import com.ecommerce.inventoryservice.models.apiResponseModels.ApiResponseInventoryModels;

public interface InventoryService {

    InventoryModel createInventory(InventoryModel inventoryModel);

    InventoryModel getInventoryById(Long inventoryId);

    boolean isInventoryInStock(String skuCode);

    ApiResponseInventoryModels getAllInventories(Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode);

    InventoryModel updateInventory(InventoryModel inventoryModel);

    void deleteInventory(Long inventoryId);

}
