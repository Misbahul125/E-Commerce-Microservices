package com.ecommerce.inventoryservice.controller;

import com.ecommerce.inventoryservice.dto.InventoryResponse;
import com.ecommerce.inventoryservice.models.InventoryModel;
import com.ecommerce.inventoryservice.models.apiResponseModels.ApiResponseInventoryModel;
import com.ecommerce.inventoryservice.models.apiResponseModels.ApiResponseInventoryModels;
import com.ecommerce.inventoryservice.service.InventoryService;
import com.ecommerce.inventoryservice.utility.AppConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/")
    public ResponseEntity<ApiResponseInventoryModel> createInventory(@RequestBody InventoryModel inventoryModel) {

        InventoryModel createdInventory = this.inventoryService.createInventory(inventoryModel);

        log.info("Inventory with inventory ID {} is created", createdInventory.getInventoryId());

        ApiResponseInventoryModel apiResponseInventoryModel = new ApiResponseInventoryModel(true,
                HttpStatus.CREATED.value(), "Inventory Created Successfully", createdInventory);

        return new ResponseEntity<>(apiResponseInventoryModel, HttpStatus.CREATED);
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<ApiResponseInventoryModel> getInventoryById(@PathVariable Long inventoryId) {
        InventoryModel inventoryModel = this.inventoryService.getInventoryById(inventoryId);

        ApiResponseInventoryModel apiResponseInventoryModel = new ApiResponseInventoryModel(true, HttpStatus.OK.value(),
                "Inventory Fetched Successfully", inventoryModel);

        return new ResponseEntity<>(apiResponseInventoryModel, HttpStatus.OK);
    }

    @GetMapping("/isInStock")
    @SneakyThrows
    public List<InventoryResponse> isInventoryInStock(@RequestParam List<String> skuCodes) {

        /*log.info("Wait started");
        Thread.sleep(10000);
        log.info("Wait ended");*/
        return this.inventoryService.isInventoryInStock(skuCodes);

    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseInventoryModels> getAllInventories(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CATEGORY_ID, required = false) String sortBy,
            @RequestParam(value = "sortMode", defaultValue = AppConstants.SORT_MODE, required = false) Integer sortMode
    ) {

        ApiResponseInventoryModels apiResponseInventoryModels = this.inventoryService.getAllInventories(pageNumber, pageSize, sortBy, sortMode);

        return new ResponseEntity<>(apiResponseInventoryModels, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<ApiResponseInventoryModel> updateInventory(@RequestBody InventoryModel inventoryModel) {
        InventoryModel updatedInventory = this.inventoryService.updateInventory(inventoryModel);

        ApiResponseInventoryModel apiResponseInventoryModel = new ApiResponseInventoryModel(true, HttpStatus.OK.value(),
                "Inventory Updated Successfully", updatedInventory);

        log.info("Inventory with inventory ID {} is updated", updatedInventory.getInventoryId());

        return new ResponseEntity<>(apiResponseInventoryModel, HttpStatus.OK);
    }

    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<ApiResponseInventoryModel> deleteInventory(@PathVariable Long inventoryId) {
        this.inventoryService.deleteInventory(inventoryId);

        log.info("Inventory with inventory ID {} is deleted", inventoryId);

        ApiResponseInventoryModel apiResponseInventoryModel = new ApiResponseInventoryModel(true, HttpStatus.OK.value(),
                "Inventory Deleted Successfully", null);

        return new ResponseEntity<>(apiResponseInventoryModel, HttpStatus.OK);
    }

}