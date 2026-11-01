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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kigali.supplier_management_system.domain.Supplier;
import com.kigali.supplier_management_system.service.SupplierService;

@RestController
@RequestMapping(value = "/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveSupplier(@RequestBody Supplier supplier) {
        supplierService.saveSupplier(supplier);
        return new ResponseEntity<>("Supplier saved successfully", HttpStatus.CREATED);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.getAllSuppliers();

        if (suppliers.isEmpty()) {
            return new ResponseEntity<>("Suppliers not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getSupplierById(@PathVariable UUID id) {
        Optional<Supplier> supplier = supplierService.getSupplierById(id);

        if (supplier == null || supplier.isEmpty()) {
            return new ResponseEntity<>("The supplier with that id does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable UUID id, @RequestBody Supplier supplier) {
        Optional<Supplier> updatedSupplier = supplierService.updateSupplier(id, supplier);

        if (updatedSupplier.isEmpty()) {
            return new ResponseEntity<>("The supplier with that id does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Supplier updated successfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable UUID id) {
        String result = supplierService.deleteSupplier(id);

        if (result.equals("The supplier with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/by-contact-name")
    public ResponseEntity<?> getSuppliersByContactName(@RequestParam String contactName) {
        List<Supplier> suppliers = supplierService.getSuppliersByContactName(contactName);
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    @GetMapping(value = "/of-procurement-officer/{procurementOfficerId}")
    public ResponseEntity<?> getSuppliersOfProcurementOfficer(@PathVariable UUID procurementOfficerId) {
        boolean exists = supplierService.procurementOfficerExists(procurementOfficerId);

        if (!exists) {
            return new ResponseEntity<>("The procurement officer with that id does not exist", HttpStatus.NOT_FOUND);
        }

        List<Supplier> suppliers = supplierService.getSuppliersOfProcurementOfficer(procurementOfficerId);
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

}

