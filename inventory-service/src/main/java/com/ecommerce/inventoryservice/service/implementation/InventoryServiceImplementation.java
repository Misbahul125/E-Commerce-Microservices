package com.ecommerce.inventoryservice.service.implementation;

import com.ecommerce.inventoryservice.entities.Inventory;
import com.ecommerce.inventoryservice.exceptions.ResourceNotFoundException;
import com.ecommerce.inventoryservice.models.InventoryModel;
import com.ecommerce.inventoryservice.models.apiResponseModels.ApiResponseInventoryModels;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import com.ecommerce.inventoryservice.service.InventoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryServiceImplementation implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InventoryModel createInventory(InventoryModel inventoryModel) {

        Inventory inventory = this.modelMapper.map(inventoryModel, Inventory.class);

        Inventory createdInventory = this.inventoryRepository.save(inventory);

        return this.modelMapper.map(createdInventory, InventoryModel.class);

    }

    @Override
    public InventoryModel getInventoryById(Long inventoryId) {

        Inventory inventory = this.inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "inventory ID", inventoryId.toString()));

        return this.modelMapper.map(inventory, InventoryModel.class);

    }

    @Transactional(readOnly = true)
    @Override
    public boolean isInventoryInStock(String skuCode) {
        return this.inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    @Override
    public ApiResponseInventoryModels getAllInventories(Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode) {

        // sorting format
        Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // paging format
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // retrieving paged data items
        Page<Inventory> pageInventories = this.inventoryRepository.findAll(pageable);

        List<Inventory> allInventories = pageInventories.getContent();

        List<InventoryModel> inventoryModels = allInventories.stream()
                .map((inventory) -> this.modelMapper.map(inventory, InventoryModel.class))
                .toList();

        ApiResponseInventoryModels apiResponseInventoryModels = new ApiResponseInventoryModels(true, HttpStatus.OK.value(),
                "Inventory(s) Fetched Successfully", pageInventories.getNumber(), pageInventories.getSize(),
                pageInventories.getTotalElements(), pageInventories.getTotalPages(), pageInventories.isLast(),
                inventoryModels);

        if (pageInventories.getNumber() >= pageInventories.getTotalPages()) {

            apiResponseInventoryModels.setMessage("No more inventory(s) found");
            apiResponseInventoryModels.setInventoryModels(null);

        }

        return apiResponseInventoryModels;

    }

    @Override
    public InventoryModel updateInventory(InventoryModel inventoryModel) {
        Inventory inventory = this.inventoryRepository.findById(inventoryModel.getInventoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "inventory ID", inventoryModel.getInventoryId().toString()));

        if (inventoryModel.getSkuCode() != null && !inventoryModel.getSkuCode().isEmpty())
            inventory.setSkuCode(inventoryModel.getSkuCode());

        if (inventoryModel.getQuantity() != null)
            inventory.setQuantity(inventoryModel.getQuantity());

        Inventory updatedInventory = this.inventoryRepository.save(inventory);

        return this.modelMapper.map(inventory, InventoryModel.class);

    }

    @Override
    public void deleteInventory(Long inventoryId) {

        Inventory inventory = this.inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "inventory ID", inventoryId.toString()));

        this.inventoryRepository.delete(inventory);

    }
}
