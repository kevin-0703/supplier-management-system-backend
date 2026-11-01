package com.kigali.supplier_management_system.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kigali.supplier_management_system.domain.SupplyCategory;
import com.kigali.supplier_management_system.service.SupplyCategoryService;

@RestController
@RequestMapping(value = "/api/supply-categories")
public class SupplyCategoryController {

    @Autowired
    private SupplyCategoryService supplyCategoryService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveSupplyCategory(@RequestBody SupplyCategory supplyCategory) {
        supplyCategoryService.saveSupplyCategory(supplyCategory);
        return new ResponseEntity<>("Supply category saved successfully", HttpStatus.CREATED);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllSupplyCategories() {
        List<SupplyCategory> categories = supplyCategoryService.getAllSupplyCategories();

        if (categories.isEmpty()) {
            return new ResponseEntity<>("Supply categories not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getSupplyCategoryById(@PathVariable UUID id) {
        Optional<SupplyCategory> category = supplyCategoryService.getSupplyCategoryById(id);

        if (category == null || category.isEmpty()) {
            return new ResponseEntity<>("The supply category with that id does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateSupplyCategory(@PathVariable UUID id, @RequestBody SupplyCategory supplyCategory) {
        Optional<SupplyCategory> updatedCategory = supplyCategoryService.updateSupplyCategory(id, supplyCategory);

        if (updatedCategory.isEmpty()) {
            return new ResponseEntity<>("The supply category with that id does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Supply category updated successfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteSupplyCategory(@PathVariable UUID id) {
        String result = supplyCategoryService.deleteSupplyCategory(id);

        if (result.equals("The supply category with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/unused")
    public ResponseEntity<?> getUnusedCategories() {
        List<SupplyCategory> categories = supplyCategoryService.getUnusedCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

}

