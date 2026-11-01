package com.kigali.supplier_management_system.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigali.supplier_management_system.domain.SupplyCategory;
import com.kigali.supplier_management_system.repository.SupplyCategoryRepository;

@Service
public class SupplyCategoryService {

    @Autowired
    private SupplyCategoryRepository supplyCategoryRepo;

    public SupplyCategory saveSupplyCategory(SupplyCategory supplyCategory) {
        SupplyCategory savedCategory = supplyCategoryRepo.save(supplyCategory);
        return savedCategory;
    }

    public List<SupplyCategory> getAllSupplyCategories() {
        List<SupplyCategory> categories = supplyCategoryRepo.findAll();
        return categories;
    }

    public Optional<SupplyCategory> getSupplyCategoryById(UUID id) {
        Optional<SupplyCategory> category = supplyCategoryRepo.findById(id);

        if (category.isEmpty()) {
            return Optional.empty();
        }

        return category;
    }

    public Optional<SupplyCategory> updateSupplyCategory(UUID id, SupplyCategory supplyCategory) {
        Optional<SupplyCategory> savedCategory = supplyCategoryRepo.findById(id);

        if (savedCategory.isEmpty()) {
            return Optional.empty();
        }

        SupplyCategory updatedCategory = savedCategory.get();
        updatedCategory.setName(supplyCategory.getName());
        supplyCategoryRepo.save(updatedCategory);

        return Optional.of(updatedCategory);
    }

    public String deleteSupplyCategory(UUID id) {
        Optional<SupplyCategory> category = supplyCategoryRepo.findById(id);

        if (category.isEmpty()) {
            return "The supply category with that id does not exist";
        }

        supplyCategoryRepo.deleteById(id);
        return "Supply category deleted successfully";
    }

    public List<SupplyCategory> getUnusedCategories() {
        List<SupplyCategory> categories = supplyCategoryRepo.findUnusedCategories();
        return categories;
    }

}

